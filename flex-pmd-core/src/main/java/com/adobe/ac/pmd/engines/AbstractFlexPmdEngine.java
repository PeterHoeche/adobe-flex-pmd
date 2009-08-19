/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
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
 *      * Neither the name of the Adobe Systems, Inc. nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
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

   private static File extractDefaultRuleSet() throws URISyntaxException,
                                              IOException
   {
      final String rulesetURI = "/com/adobe/ac/pmd/rulesets/all_flex.xml";
      final InputStream resourceAsStream = AbstractFlexPmdEngine.class.getResourceAsStream( rulesetURI );
      final File temporaryRuleset = File.createTempFile( "all_flex",
                                                         ".xml" );
      // Delete temp file when program exits.
      temporaryRuleset.deleteOnExit();
      final FileOutputStream writter = new FileOutputStream( temporaryRuleset );
      IOUtil.copy( resourceAsStream,
                   writter );

      resourceAsStream.close();
      return temporaryRuleset;
   }

   protected final File outputDirectory;
   private final String packageToExclude;
   private RuleSet      ruleSet;
   private final File   sourceDirectory;

   public AbstractFlexPmdEngine( final File sourceDirectoryToBeSet,
                                 final File outputDirectoryToBeSet,
                                 final String packageToExcludeToBeSet )
   {
      super();

      sourceDirectory = sourceDirectoryToBeSet;
      outputDirectory = outputDirectoryToBeSet;
      packageToExclude = packageToExcludeToBeSet;
   }

   /**
    * @param ruleSetFile
    * @return The number of violations with the given ruleset
    * @throws PMDException
    * @throws URISyntaxException
    * @throws IOException
    */
   public final int executeReport( final File ruleSetFile ) throws PMDException,
                                                           URISyntaxException,
                                                           IOException
   {
      return executeReport( new FlexPmdViolations(),
                            ruleSetFile );
   }

   /**
    * @param flexPmdViolations
    * @param ruleSetFile
    * @return The number of violations with the given ruleset and the result
    *         wrapper in case of reuse
    * @throws PMDException
    * @throws URISyntaxException
    * @throws IOException
    */
   public final int executeReport( final FlexPmdViolations flexPmdViolations,
                                   final File ruleSetFile ) throws PMDException,
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

      loadRuleset( ruleSetFile );

      LOGGER.fine( "Search Flex files in "
            + sourceDirectory.getPath() );

      if ( !flexPmdViolations.hasViolationsBeenComputed() )
      {
         computeViolations( flexPmdViolations );
      }
      writeAnyReport( flexPmdViolations );

      return computeViolationNumber( flexPmdViolations );
   }

   public final RuleSet getRuleSet()
   {
      return ruleSet;
   }

   protected abstract void writeReport( final FlexPmdViolations pmd ) throws PMDException;

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

   private void computeViolations( final FlexPmdViolations flexPmdViolations ) throws PMDException
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

   private void loadRuleset( final File ruleSetFile ) throws URISyntaxException,
                                                     IOException
   {
      final File realRuleSet = extractRuleset( ruleSetFile );

      final FileInputStream inputStream = new FileInputStream( realRuleSet );

      ruleSet = new RuleSetFactory().createRuleSet( inputStream );

      LOGGER.info( "Ruleset: "
            + realRuleSet.getAbsolutePath() );
      LOGGER.info( "Rules number in the ruleSet: "
            + ruleSet.getRules().size() );
      inputStream.close();
   }

   private void writeAnyReport( final FlexPmdViolations flexPmdViolations ) throws PMDException
   {
      long startTime;
      long ellapsedTime;
      startTime = System.currentTimeMillis();
      writeReport( flexPmdViolations );
      ellapsedTime = System.currentTimeMillis()
            - startTime;

      LOGGER.info( "It took "
            + ellapsedTime + "ms to write the " + getReportType() + " report" );
   }
}