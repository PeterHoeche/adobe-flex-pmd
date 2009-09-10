/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
 *    All rights reserved.
 *
 *    Redistribution  and  use  in  source  and  binary  forms, with or without
 *    modification,  are  permitted  provided  that  the  following  conditions
 *    are met:
 *
 *      * Redistributions  of  source  code  must  retain  the  above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions  in  binary  form  must reproduce the above copyright
 *        notice,  this  list  of  conditions  and  the following disclaimer in
 *        the    documentation   and/or   other  materials  provided  with  the
 *        distribution.
 *      * Neither the name of the Adobe Systems, Incorporated. nor the names of
 *        its  contributors  may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS  SOFTWARE  IS  PROVIDED  BY THE  COPYRIGHT  HOLDERS AND CONTRIBUTORS
 *    "AS IS"  AND  ANY  EXPRESS  OR  IMPLIED  WARRANTIES,  INCLUDING,  BUT NOT
 *    LIMITED  TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,  INCIDENTAL,  SPECIAL,
 *    EXEMPLARY,  OR  CONSEQUENTIAL  DAMAGES  (INCLUDING,  BUT  NOT  LIMITED TO,
 *    PROCUREMENT  OF  SUBSTITUTE   GOODS  OR   SERVICES;  LOSS  OF  USE,  DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY,  WHETHER  IN  CONTRACT,  STRICT  LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE  OR  OTHERWISE)  ARISING  IN  ANY  WAY  OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleReference;
import net.sourceforge.pmd.RuleSet;

import com.adobe.ac.pmd.comparators.DescendentSpeedRuleComparator;
import com.adobe.ac.pmd.comparators.FlexFileComparator;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.files.impl.FileUtils;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.IFlexAstRule;
import com.adobe.ac.pmd.rules.core.IFlexRule;
import com.adobe.ac.utils.StackTraceUtils;

public class FlexPmdViolations
{
   private static final Logger                                  LOGGER;
   private static final Logger                                  SPEEDS_LOGGER;

   static
   {
      LOGGER = Logger.getLogger( FlexPmdViolations.class.getName() );
      SPEEDS_LOGGER = Logger.getLogger( FlexPmdViolations.class.getName()
            + "Speeds" );
      LOGGER.setLevel( Level.WARNING );
      SPEEDS_LOGGER.setLevel( Level.WARNING );
   }

   private Map< String, IPackage >                              asts;
   private Map< String, IFlexFile >                             files;
   private boolean                                              hasBeenComputed;
   private final Map< String, IFlexRule >                       rules;
   private final Map< IFlexRule, Long >                         ruleSpeeds;
   private final SortedMap< IFlexFile, List< IFlexViolation > > violations;

   public FlexPmdViolations()
   {
      violations = new TreeMap< IFlexFile, List< IFlexViolation > >( new FlexFileComparator() );
      rules = new HashMap< String, IFlexRule >();
      ruleSpeeds = new HashMap< IFlexRule, Long >();
      hasBeenComputed = false;
   }

   public final void computeViolations( final File sourceDirectory,
                                        final RuleSet ruleSet,
                                        final String packageToExclude ) throws PMDException
   {
      hasBeenComputed = true;

      computeRules( ruleSet );
      computeFiles( sourceDirectory,
                    packageToExclude );
      computeAsts();
      processRules();
      sortViolations();
      displayRuleSpeeds();
   }

   public final Map< IFlexFile, List< IFlexViolation >> getViolations()
   {
      return violations;
   }

   public final boolean hasViolationsBeenComputed()
   {
      return hasBeenComputed;
   }

   private void computeAsts() throws PMDException
   {
      LOGGER.info( "computing Asts" );

      final long startTime = System.currentTimeMillis();
      asts = FileSetUtils.computeAsts( files );

      SPEEDS_LOGGER.info( "computed Asts in "
            + ( System.currentTimeMillis() - startTime ) + " ms" );
   }

   private void computeFiles( final File sourceDirectory,
                              final String packageToExclude ) throws PMDException
   {
      LOGGER.info( "computing FilesList" );

      final long startTime = System.currentTimeMillis();

      files = FileUtils.computeFilesList( sourceDirectory,
                                          packageToExclude );
      SPEEDS_LOGGER.info( "computed FilesList in "
            + ( System.currentTimeMillis() - startTime ) + " ms" );
   }

   private void computeRules( final RuleSet ruleSet )
   {
      LOGGER.info( "computing RulesList" );

      final long startTime = System.currentTimeMillis();

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

      SPEEDS_LOGGER.info( "computed RulesList in "
            + ( System.currentTimeMillis() - startTime ) + " ms" );
   }

   private void displayRuleSpeeds()
   {
      if ( SPEEDS_LOGGER.isLoggable( Level.FINER ) )
      {
         final List< IFlexRule > rulesSortedByTime = new ArrayList< IFlexRule >( ruleSpeeds.keySet() );
         Collections.sort( rulesSortedByTime,
                           new DescendentSpeedRuleComparator( ruleSpeeds ) );
         for ( final IFlexRule flexRule : rulesSortedByTime )
         {
            SPEEDS_LOGGER.finer( flexRule.getRuleName()
                  + " took " + ruleSpeeds.get( flexRule ) + "ms to compute" );
         }
      }
   }

   private void processFile( final IFlexRule currentRule,
                             final IFlexFile currentFile )
   {
      try
      {
         final String fullyQualifiedName = currentFile.getFullyQualifiedName();
         final IPackage ast = currentRule instanceof IFlexAstRule ? asts.get( fullyQualifiedName )
                                                                 : null;
         final List< IFlexViolation > foundViolations = currentRule.processFile( currentFile,
                                                                                 ast,
                                                                                 files );

         if ( violations.containsKey( currentFile ) )
         {
            violations.get( currentFile ).addAll( foundViolations );
         }
         else
         {
            violations.put( currentFile,
                            foundViolations );
         }
      }
      catch ( final Exception e )
      {
         LOGGER.warning( StackTraceUtils.print( currentFile.getFullyQualifiedName(),
                                                e ) );
      }
   }

   private void processRule( final IFlexRule currentRule )
   {
      LOGGER.fine( "Processing "
            + currentRule.getRuleName() + "..." );

      for ( final Entry< String, IFlexFile > currentFileEntry : files.entrySet() )
      {
         processFile( currentRule,
                      currentFileEntry.getValue() );
      }
   }

   private void processRule( final String currentRuleName,
                             final IFlexRule currentRule )
   {
      final long startTime = System.currentTimeMillis();

      processRule( currentRule );
      final long ellapsedTime = System.currentTimeMillis()
            - startTime;

      if ( SPEEDS_LOGGER.isLoggable( Level.FINE ) )
      {
         SPEEDS_LOGGER.fine( "rule "
               + currentRuleName + " computed in " + ellapsedTime + "ms" );
      }
      if ( SPEEDS_LOGGER.isLoggable( Level.FINER ) )
      {
         ruleSpeeds.put( currentRule,
                         ellapsedTime );
      }
   }

   private void processRules()
   {
      for ( final Entry< String, IFlexRule > currentRuleEntry : rules.entrySet() )
      {
         processRule( currentRuleEntry.getKey(),
                      currentRuleEntry.getValue() );
      }
   }

   private void sortViolations()
   {
      for ( final Entry< String, IFlexFile > entry : files.entrySet() )
      {
         Collections.sort( violations.get( entry.getValue() ) );
      }
   }
}