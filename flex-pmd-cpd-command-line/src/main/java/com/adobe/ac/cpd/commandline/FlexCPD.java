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
package com.adobe.ac.cpd.commandline;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.cpd.CPD;
import net.sourceforge.pmd.cpd.FileReporter;
import net.sourceforge.pmd.cpd.Renderer;
import net.sourceforge.pmd.cpd.ReportException;
import net.sourceforge.pmd.cpd.XMLRenderer;

import com.adobe.ac.cpd.FlexLanguage;
import com.adobe.ac.cpd.FlexTokenizer;
import com.adobe.ac.pmd.CommandLineOptions;
import com.adobe.ac.pmd.CommandLineUtils;
import com.adobe.ac.pmd.ICommandLineOptions;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.files.impl.FileUtils;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;

public class FlexCPD
{
   private static JSAPResult        config;
   private static final String      encoding = System.getProperty( "file.encoding" );
   private static final Logger      LOGGER   = Logger.getLogger( FlexCPD.class.getName() );
   private static FlexCpdParameters parameters;

   public static void main( final String[] args ) throws JSAPException,
                                                 URISyntaxException,
                                                 IOException,
                                                 ReportException,
                                                 PMDException
   {
      LOGGER.setLevel( Level.SEVERE );
      startFlexCPD( args );
      LOGGER.info( "FlexPMD terminated" );
      System.exit( 0 );
   }

   static boolean areCommandLineOptionsCorrect( final String[] args ) throws JSAPException
   {
      final JSAP jsap = new JSAP();
      config = parseCommandLineArguments( args,
                                          jsap );

      if ( !config.success() )
      {
         LOGGER.log( Level.SEVERE,
                     "Usage: java "
                           + FlexCPD.class.getName() + " " + jsap.getUsage() );
      }

      return config.success();
   }

   static String getParameterValue( final ICommandLineOptions option )
   {
      return config.getString( option.toString() );
   }

   static boolean startFlexCPD( final String[] args ) throws JSAPException,
                                                     URISyntaxException,
                                                     IOException,
                                                     ReportException,
                                                     PMDException
   {
      if ( areCommandLineOptionsCorrect( args ) )
      {
         final String minimumTokens = getParameterValue( CpdCommandLineOptions.MINIMUM_TOKENS );
         final String excludePackage = getParameterValue( CommandLineOptions.EXLUDE_PACKAGE );

         final File sourceDirectory = new File( getParameterValue( CommandLineOptions.SOURCE_DIRECTORY ) );
         final File outputDirectory = new File( getParameterValue( CpdCommandLineOptions.OUTPUT_FILE ) );

         parameters = new FlexCpdParameters( excludePackage == null ? ""
                                                                   : excludePackage,
                                             outputDirectory,
                                             minimumTokens == null ? FlexTokenizer.DEFAULT_MINIMUM_TOKENS
                                                                  : Integer.valueOf( minimumTokens ),
                                             sourceDirectory );
         LOGGER.info( "Starting run, minimumTokenCount is "
               + parameters.getMinimumTokenCount() );

         LOGGER.info( "Tokenizing files" );
         final CPD cpd = new CPD( parameters.getMinimumTokenCount(), new FlexLanguage() );

         cpd.setEncoding( encoding );
         tokenizeFiles( cpd );

         LOGGER.info( "Starting to analyze code" );
         final long timeTaken = analyzeCode( cpd );
         LOGGER.info( "Done analyzing code; that took "
               + timeTaken + " milliseconds" );

         LOGGER.info( "Generating report" );
         report( cpd );
      }

      return config.success();
   }

   private static long analyzeCode( final CPD cpd )
   {
      final long start = System.currentTimeMillis();
      cpd.go();
      final long stop = System.currentTimeMillis();
      return stop
            - start;
   }

   private static JSAPResult parseCommandLineArguments( final String[] args,
                                                        final JSAP jsap ) throws JSAPException
   {
      CommandLineUtils.registerParameter( jsap,
                                          CommandLineOptions.SOURCE_DIRECTORY,
                                          true );
      CommandLineUtils.registerParameter( jsap,
                                          CpdCommandLineOptions.OUTPUT_FILE,
                                          true );
      CommandLineUtils.registerParameter( jsap,
                                          CpdCommandLineOptions.MINIMUM_TOKENS,
                                          false );
      CommandLineUtils.registerParameter( jsap,
                                          CommandLineOptions.EXLUDE_PACKAGE,
                                          false );

      return jsap.parse( args );
   }

   private static void report( final CPD cpd ) throws ReportException,
                                              IOException
   {
      if ( !cpd.getMatches().hasNext() )
      {
         LOGGER.info( "No duplicates over "
               + parameters.getMinimumTokenCount() + " tokens found" );
      }
      final Renderer renderer = new XMLRenderer( encoding );

      if ( !parameters.getOutputFile().exists() )
      {
         parameters.getOutputFile().createNewFile();
      }

      final FileReporter reporter = new FileReporter( parameters.getOutputFile(), encoding );
      reporter.report( renderer.render( cpd.getMatches() ) );
   }

   private static void tokenizeFiles( final CPD cpd ) throws IOException,
                                                     PMDException
   {
      final Map< String, IFlexFile > files = FileUtils.computeFilesList( parameters.getSourceDirectory(),
                                                                         "" );

      for ( final Entry< String, IFlexFile > fileEntry : files.entrySet() )
      {
         cpd.add( new File( fileEntry.getValue().getFilePath() ) );
      }
   }

   private FlexCPD()
   {
   }
}
