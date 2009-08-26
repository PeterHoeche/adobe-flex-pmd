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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

import net.sourceforge.pmd.PMDException;

import com.adobe.ac.pmd.FlexPmdViolations;
import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;

public class FlexPmdXmlEngine extends AbstractFlexPmdEngine
{
   private static final Logger LOGGER = Logger.getLogger( FlexPmdXmlEngine.class.getName() );

   public FlexPmdXmlEngine( final File sourceDirectory,
                            final File outputDirectory,
                            final String packageToExclude )
   {
      super( sourceDirectory, outputDirectory, packageToExclude );
   }

   @Override
   protected final void writeReport( final FlexPmdViolations pmd ) throws PMDException
   {
      final File realOutputDirectory = outputDirectory;
      final String filePath = realOutputDirectory.getAbsoluteFile()
            + File.separator + FlexPMDFormat.XML.toString();

      makeSureOutputDirectoryExists( realOutputDirectory );

      Writer writter = null;
      try
      {
         LOGGER.finest( "Start writting XML report" );

         writter = new OutputStreamWriter( new FileOutputStream( filePath ), "UTF-8" );
         writeReportHeader( writter );
         writeFileViolations( pmd,
                              writter );
         writeReportFooter( writter );
         writter.close();
      }
      catch ( final IOException e )
      {
         throw new PMDException( "Error creating file "
               + filePath, e );
      }
      finally
      {
         finalizeReport( writter );
      }
   }

   private void finalizeReport( final Writer writter )
   {
      LOGGER.finest( "End writting XML report" );

      if ( writter != null )
      {
         try
         {
            LOGGER.finest( "Closing the XML writter" );
            writter.close();
         }
         catch ( final IOException e )
         {
            LOGGER.warning( Arrays.toString( e.getStackTrace() ) );
         }
         LOGGER.finest( "Closed the XML writter" );
      }
   }

   private void formatFileFiolation( final Writer writter,
                                     final IFlexFile sourceFile,
                                     final Collection< IFlexViolation > violations,
                                     final String sourceFilePath ) throws IOException
   {
      if ( !violations.isEmpty() )
      {
         writter.write( "   <file name=\""
               + sourceFilePath.substring( 1,
                                           sourceFilePath.length() ) + "\">" + getNewLine() );
         for ( final IFlexViolation violation : violations )
         {
            writter.write( violation.toXmlString( sourceFile,
                                                  violation.getRule().getRuleSetName() ) );
         }
         writter.write( "   </file>"
               + getNewLine() );
      }
   }

   private String getNewLine()
   {
      return System.getProperty( "line.separator" );
   }

   private void makeSureOutputDirectoryExists( final File realOutputDirectory )
   {
      if ( !realOutputDirectory.exists()
            && !realOutputDirectory.mkdirs() )
      {
         LOGGER.severe( "Unable to create an output folder" );
      }
   }

   private void writeFileViolations( final FlexPmdViolations pmd,
                                     final Writer writter ) throws IOException
   {
      for ( final IFlexFile sourceFile : pmd.getViolations().keySet() )
      {
         final Collection< IFlexViolation > violations = pmd.getViolations().get( sourceFile );
         final String sourceFilePath = sourceFile.getFilePath();

         formatFileFiolation( writter,
                              sourceFile,
                              violations,
                              sourceFilePath );
      }
   }

   private void writeReportFooter( final Writer writter ) throws IOException
   {
      writter.write( "</pmd>"
            + getNewLine() );
   }

   private void writeReportHeader( final Writer writter ) throws IOException
   {
      writter.write( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + getNewLine() );
      writter.write( "<pmd version=\"4.2.1\" timestamp=\""
            + new Date().toString() + "\">" + getNewLine() );
   }
}
