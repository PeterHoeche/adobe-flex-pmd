package com.adobe.ac.pmd;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleReference;
import net.sourceforge.pmd.RuleSet;

import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.files.impl.FileUtils;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.IFlexAstRule;
import com.adobe.ac.pmd.rules.core.IFlexRule;

public class FlexPmdViolations
{
   private static final Logger                                  LOGGER       = Logger.getLogger( "FlexPmdViolation" );
   private boolean                                              beenComputed = false;
   private final SortedMap< IFlexFile, List< IFlexViolation > > violations;

   public FlexPmdViolations()
   {
      violations = new TreeMap< IFlexFile, List< IFlexViolation > >( new FlexFileComparator() );
   }

   public final void computeViolations( final File sourceDirectory,
                                        final RuleSet ruleSet,
                                        final String packageToExclude ) throws PMDException
   {
      beenComputed = true;

      final Map< String, IFlexRule > rules = computeRulesList( ruleSet );
      final Map< String, IFlexFile > filesInSourceDirectory = FileUtils.computeFilesList( sourceDirectory,
                                                                                          packageToExclude );
      final Map< String, IPackage > astsInSourceDirectory = FileSetUtils.computeAsts( filesInSourceDirectory );

      for ( final Entry< String, IFlexRule > currentRuleEntry : rules.entrySet() )
      {
         processRule( filesInSourceDirectory,
                      astsInSourceDirectory,
                      currentRuleEntry.getValue() );
      }
      for ( final Entry< String, IFlexFile > entry : filesInSourceDirectory.entrySet() )
      {
         Collections.sort( violations.get( entry.getValue() ) );
      }
   }

   public final Map< IFlexFile, List< IFlexViolation >> getViolations()
   {
      return violations;
   }

   public final boolean hasViolationsBeenComputed()
   {
      return beenComputed;
   }

   private Map< String, IFlexRule > computeRulesList( final RuleSet ruleSet )
   {
      final Map< String, IFlexRule > rules = new HashMap< String, IFlexRule >();

      for ( Rule rule : ruleSet.getRules() )
      {
         while ( rule instanceof RuleReference )
         {
            rule = ( ( RuleReference ) rule ).getRule();
         }
         final IFlexRule flexRule = ( IFlexRule ) rule;

         rules.put( flexRule.getRuleName(),
                    flexRule );
      }

      return rules;
   }

   private void processFile( final Map< String, IFlexFile > filesInSourceDirectory,
                             final Map< String, IPackage > astsInSourceDirectory,
                             final IFlexRule ruleToProcess,
                             final IFlexFile fileToProcess )
   {
      final String fullyQualifiedName = fileToProcess.getFullyQualifiedName();
      final IPackage ast = ruleToProcess instanceof IFlexAstRule ? astsInSourceDirectory.get( fullyQualifiedName )
                                                                : null;
      final List< IFlexViolation > foundViolations = ruleToProcess.processFile( fileToProcess,
                                                                                ast,
                                                                                filesInSourceDirectory );

      if ( violations.containsKey( fileToProcess ) )
      {
         violations.get( fileToProcess ).addAll( foundViolations );
      }
      else
      {
         violations.put( fileToProcess,
                         foundViolations );
      }
   }

   private void processRule( final Map< String, IFlexFile > filesInSourceDirectory,
                             final Map< String, IPackage > astsInSourceDirectory,
                             final IFlexRule currentRule )
   {
      LOGGER.fine( "Processing "
            + currentRule.getRuleName() + "..." );
      for ( final Entry< String, IFlexFile > currentFileEntry : filesInSourceDirectory.entrySet() )
      {
         processFile( filesInSourceDirectory,
                      astsInSourceDirectory,
                      currentRule,
                      currentFileEntry.getValue() );
      }
   }
}