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
package com.adobe.ac.pmd.engines;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.RuleSetFactory;

import com.adobe.ac.pmd.FlexPmdViolations;
import com.adobe.ac.pmd.Violation;

public abstract class AbstractFlexPmdEngine
{
   private static final Logger LOGGER = Logger.getLogger( AbstractFlexPmdEngine.class.getName() );

   public static String getNewLine()
   {
      return System.getProperty( "line.separator" );
   }

   private RuleSet ruleSet;

   public final int executeReport( final File sourceDirectory,
                                   final File outputDirectory,
                                   final File ruleSetFile,
                                   final FlexPmdViolations flexPmdViolations ) throws PMDException,
                                                                              FileNotFoundException,
                                                                              URISyntaxException
   {

      if ( sourceDirectory == null )
      {
         throw new PMDException( "unspecified sourceDirectory" );
      }
      if ( outputDirectory == null )
      {
         throw new PMDException( "unspecified outputDirectory" );
      }

      final String rulesetURI = "com/adobe/ac/pmd/rulesets/all_flex.xml";
      final File realRuleSet = ruleSetFile == null ? new File( getClass().getResource( rulesetURI )
                                                                         .toURI()
                                                                         .getPath() )
                                                  : ruleSetFile;

      ruleSet = new RuleSetFactory().createRuleSet( new FileInputStream( realRuleSet ) );
      long startTime;
      int foundViolations = 0;
      long ellapsedTime = 0;

      LOGGER.info( "Ruleset: "
            + realRuleSet.getAbsolutePath() );

      LOGGER.info( "Rules number in the ruleSet: "
            + ruleSet.getRules().size() );

      LOGGER.fine( "Search Flex files in "
            + sourceDirectory.getPath() );

      if ( !flexPmdViolations.hasViolationsBeenComputed() )
      {
         startTime = System.currentTimeMillis();
         flexPmdViolations.computeViolations( sourceDirectory,
                                              ruleSet );
         ellapsedTime = System.currentTimeMillis()
               - startTime;
         LOGGER.info( "It took "
               + ellapsedTime + "ms to compute violations" );
      }
      for ( final List< Violation > violations : flexPmdViolations.getViolations().values() )
      {
         foundViolations += violations.size();
      }
      LOGGER.info( "Violations number found: "
            + foundViolations );

      startTime = System.currentTimeMillis();
      writeReport( flexPmdViolations,
                   outputDirectory );
      ellapsedTime = System.currentTimeMillis()
            - startTime;

      LOGGER.info( "It took "
            + ellapsedTime + "ms to write the " + getReportType() + " report" );

      return foundViolations;
   }

   public RuleSet getRuleSet()
   {
      return ruleSet;
   }

   protected abstract String getReportType();

   protected abstract void writeReport( final FlexPmdViolations pmd,
                                        final File outputDirectory ) throws PMDException;
}