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

import com.adobe.ac.pmd.files.AbstractFlexFile;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;

public class FlexPmdViolations
{
   public static final Logger                                     LOGGER       = Logger.getLogger( FlexPmdViolations.class.getName() );

   private boolean                                                beenComputed = false;

   private final SortedMap< AbstractFlexFile, List< Violation > > violations;

   public FlexPmdViolations()
   {
      violations = new TreeMap< AbstractFlexFile, List< Violation > >( new FlexFileComparator() );
   }

   public void computeViolations( final File sourceDirectory,
                                  final RuleSet ruleSet ) throws PMDException
   {
      beenComputed = true;

      final Map< String, AbstractFlexRule > rules = computeRulesList( ruleSet );
      final Map< String, AbstractFlexFile > files = FileSetUtils.computeFilesList( sourceDirectory );
      final Map< String, IPackage > asts = FileSetUtils.computeAsts( files );

      for ( final Entry< String, AbstractFlexRule > ruleEntry : rules.entrySet() )
      {
         final AbstractFlexRule rule = ruleEntry.getValue();

         LOGGER.fine( "Processing "
               + rule.getRuleName() + "..." );
         for ( final Entry< String, AbstractFlexFile > fileEntry : files.entrySet() )
         {
            final AbstractFlexFile file = fileEntry.getValue();
            final List< Violation > foundViolations = rule.processFile( file,
                                                                        rule instanceof AbstractAstFlexRule ? asts.get( file.getFullyQualifiedName() )
                                                                                                           : null,
                                                                        files );

            if ( violations.containsKey( file ) )
            {
               violations.get( file ).addAll( foundViolations );
            }
            else
            {
               violations.put( file,
                               foundViolations );
            }
         }
      }
      for ( final Entry< String, AbstractFlexFile > entry : files.entrySet() )
      {
         sortViolations( entry.getValue() );
      }
   }

   public Map< AbstractFlexFile, List< Violation >> getViolations()
   {
      return violations;
   }

   public boolean hasViolationsBeenComputed()
   {
      return beenComputed;
   }

   private Map< String, AbstractFlexRule > computeRulesList( final RuleSet ruleSet )
   {
      final Map< String, AbstractFlexRule > rules = new HashMap< String, AbstractFlexRule >();

      for ( Rule rule : ruleSet.getRules() )
      {
         while ( rule instanceof RuleReference )
         {
            rule = ( ( RuleReference ) rule ).getRule();
         }
         if ( rule instanceof AbstractFlexRule )
         {
            final AbstractFlexRule flexRule = ( AbstractFlexRule ) rule;

            rules.put( flexRule.getRuleName(),
                       flexRule );
         }
      }

      return rules;
   }

   private void sortViolations( final AbstractFlexFile file )
   {
      if ( violations.get( file ) != null )
      {
         Collections.sort( violations.get( file ) );
      }
   }
}