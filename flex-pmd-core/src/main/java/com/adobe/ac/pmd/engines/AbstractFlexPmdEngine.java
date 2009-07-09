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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.RuleSetFactory;

import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.IOUtil;

import com.adobe.ac.pmd.FlexPmdViolations;
import com.adobe.ac.pmd.IFlexViolation;

public abstract class AbstractFlexPmdEngine
{
   private static final Logger LOGGER = Logger.getLogger( AbstractFlexPmdEngine.class.getName() );

   private RuleSet             ruleSet;

   public final int executeReport( final File sourceDirectory,
                                   final File outputDirectory,
                                   final File ruleSetFile,
                                   final FlexPmdViolations flexPmdViolations,
                                   final String packageToExclude ) throws PMDException,
                                                                  URISyntaxException,
                                                                  IOException
   {

      if ( sourceDirectory == null )
      {
         throw new PMDException( "unspecified sourceDirectory" );
      }
      if ( outputDirectory == null )
      {
         throw new PMDException( "unspecified outputDirectory" );
      }

      loadRuleset( sourceDirectory,
                   ruleSetFile );
      if ( !flexPmdViolations.hasViolationsBeenComputed() )
      {
         computeViolations( sourceDirectory,
                            flexPmdViolations,
                            packageToExclude );
      }
      writeReport( outputDirectory,
                   flexPmdViolations );

      return computeViolationNumber( flexPmdViolations );
   }

   public final RuleSet getRuleSet()
   {
      return ruleSet;
   }

   protected abstract void writeReport( final FlexPmdViolations pmd,
                                        final File outputDirectory ) throws PMDException;

   private int computeViolationNumber( final FlexPmdViolations flexPmdViolations )
   {
      int foundViolations = 0;
      for ( final List< IFlexViolation > violations : flexPmdViolations.getViolations().values() )
      {
         foundViolations += violations.size();
      }
      LOGGER.info( "Violations number found: "
            + foundViolations );
      return foundViolations;
   }

   private void computeViolations( final File sourceDirectory,
                                   final FlexPmdViolations flexPmdViolations,
                                   final String packageToExclude ) throws PMDException
   {
      final long startTime = System.currentTimeMillis();

      flexPmdViolations.computeViolations( sourceDirectory,
                                           ruleSet,
                                           packageToExclude );
      final long ellapsedTime = System.currentTimeMillis()
            - startTime;
      LOGGER.info( "It took "
            + ellapsedTime + "ms to compute violations" );
   }

   private File extractDefaultRuleSet() throws URISyntaxException,
                                       IOException
   {
      final String rulesetURI = "/com/adobe/ac/pmd/rulesets/all_flex.xml";

      final InputStream resourceAsStream = getClass().getResourceAsStream( rulesetURI );
      final File temp = File.createTempFile( "all_flex",
                                             ".xml" );
      // Delete temp file when program exits.
      temp.deleteOnExit();
      final FileOutputStream writter = new FileOutputStream( temp );
      IOUtil.copy( resourceAsStream,
                   writter );

      return temp;
   }

   private File extractRuleset( final File ruleSetFile ) throws URISyntaxException,
                                                        IOException
   {
      return ruleSetFile == null ? extractDefaultRuleSet()
                                : ruleSetFile;
   }

   private String getReportType()
   {
      return StringUtils.substringBefore( StringUtils.substringAfter( getClass().getName(),
                                                                      "FlexPmd" ),
                                          "Engine" );
   }

   private void loadRuleset( final File sourceDirectory,
                             final File ruleSetFile ) throws URISyntaxException,
                                                     IOException
   {
      final File realRuleSet = extractRuleset( ruleSetFile );

      ruleSet = new RuleSetFactory().createRuleSet( new FileInputStream( realRuleSet ) );

      LOGGER.info( "Ruleset: "
            + realRuleSet.getAbsolutePath() );

      LOGGER.info( "Rules number in the ruleSet: "
            + ruleSet.getRules().size() );

      LOGGER.fine( "Search Flex files in "
            + sourceDirectory.getPath() );
   }

   private void writeReport( final File outputDirectory,
                             final FlexPmdViolations flexPmdViolations ) throws PMDException
   {
      long startTime;
      long ellapsedTime;
      startTime = System.currentTimeMillis();
      writeReport( flexPmdViolations,
                   outputDirectory );
      ellapsedTime = System.currentTimeMillis()
            - startTime;

      LOGGER.info( "It took "
            + ellapsedTime + "ms to write the " + getReportType() + " report" );
   }
}