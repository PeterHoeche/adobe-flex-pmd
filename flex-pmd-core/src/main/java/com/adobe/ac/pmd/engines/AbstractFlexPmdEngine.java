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

import com.adobe.ac.pmd.FlexPmdParameters;
import com.adobe.ac.pmd.FlexPmdViolations;
import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.utils.StackTraceUtils;

public abstract class AbstractFlexPmdEngine
{
   private static final Logger LOGGER = Logger.getLogger( AbstractFlexPmdEngine.class.getName() );

   private static int computeViolationNumber( final FlexPmdViolations flexPmdViolations )
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

   private static File extractDefaultRuleSet() throws URISyntaxException,
                                              IOException
   {
      final String rulesetURI = "/com/adobe/ac/pmd/default_flex.xml";
      final InputStream resourceAsStream = AbstractFlexPmdEngine.class.getResourceAsStream( rulesetURI );
      final File temporaryRuleset = File.createTempFile( "default_flex",
                                                         ".xml" );
      temporaryRuleset.deleteOnExit();
      final FileOutputStream writter = new FileOutputStream( temporaryRuleset );
      IOUtil.copy( resourceAsStream,
                   writter );

      resourceAsStream.close();
      return temporaryRuleset;
   }

   private final File         outputDirectory;
   private final String       packageToExclude;
   private RuleSet            ruleSet;
   private final File         source;
   private final List< File > sourceList;

   public AbstractFlexPmdEngine( final FlexPmdParameters parameters )
   {
      super();

      source = parameters.getSource();
      sourceList = parameters.getSourceList();
      outputDirectory = parameters.getOutputDirectory();
      packageToExclude = parameters.getExcludePackage();
      try
      {
         ruleSet = loadRuleset( parameters.getRuleSet() );
      }
      catch ( final URISyntaxException e )
      {
         LOGGER.warning( StackTraceUtils.print( e ) );
      }
      catch ( final IOException e )
      {
         LOGGER.warning( StackTraceUtils.print( e ) );
      }
   }

   /**
    * @param flexPmdViolations
    * @return The number of violations with the given ruleset and the result
    *         wrapper in case of reuse
    * @throws PMDException
    * @throws URISyntaxException
    * @throws IOException
    */
   public final void executeReport( final FlexPmdViolations flexPmdViolations ) throws PMDException
   {
      if ( source == null
            && sourceList == null )
      {
         throw new PMDException( "unspecified sourceDirectory" );
      }
      if ( outputDirectory == null )
      {
         throw new PMDException( "unspecified outputDirectory" );
      }

      if ( ruleSet != null )
      {
         if ( !flexPmdViolations.hasViolationsBeenComputed() )
         {
            computeViolations( flexPmdViolations );
         }
         computeViolationNumber( flexPmdViolations );
         writeAnyReport( flexPmdViolations );
      }
   }

   public final RuleSet getRuleSet()
   {
      return ruleSet;
   }

   protected File getOutputDirectory()
   {
      return outputDirectory;
   }

   protected abstract void writeReport( final FlexPmdViolations pmd ) throws PMDException;

   private void computeViolations( final FlexPmdViolations flexPmdViolations ) throws PMDException
   {
      final long startTime = System.currentTimeMillis();

      flexPmdViolations.computeViolations( source,
                                           sourceList,
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

   private RuleSet loadRuleset( final File ruleSetFile ) throws URISyntaxException,
                                                        IOException
   {
      final File realRuleSet = extractRuleset( ruleSetFile );
      final FileInputStream inputStream = new FileInputStream( realRuleSet );
      final RuleSet loadedRuleSet = new RuleSetFactory().createRuleSet( inputStream );

      LOGGER.info( "Ruleset: "
            + realRuleSet.getAbsolutePath() );
      LOGGER.info( "Rules number in the ruleSet: "
            + loadedRuleSet.getRules().size() );
      inputStream.close();

      return loadedRuleSet;
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