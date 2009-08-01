package com.adobe.ac.pmd.engines;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

      makeSureOutputDirectoryExists( realOutputDirectory );

      final File pmdReport = new File( realOutputDirectory, FlexPMDFormat.XML.toString() );

      FileWriter writter = null;
      try
      {
         LOGGER.finest( "Start writting XML report" );

         writter = new FileWriter( pmdReport );
         writeReportHeader( writter );
         writeFileViolations( pmd,
                              writter );
         writeReportFooter( writter );
         writter.close();
      }
      catch ( final IOException e )
      {
         throw new PMDException( "Error creating file "
               + pmdReport, e );
      }
      finally
      {
         finalizeReport( writter );
      }
   }

   private void finalizeReport( final FileWriter writter )
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

   private void formatFileFiolation( final FileWriter writter,
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
                                     final FileWriter writter ) throws IOException
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

   private void writeReportFooter( final FileWriter writter ) throws IOException
   {
      writter.write( "</pmd>"
            + getNewLine() );
   }

   private void writeReportHeader( final FileWriter writter ) throws IOException
   {
      writter.write( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + getNewLine() );
      writter.write( "<pmd version=\"4.2.1\" timestamp=\""
            + new Date().toString() + "\">" + getNewLine() );
   }
}
