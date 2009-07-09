/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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