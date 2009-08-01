package com.adobe.ac.pmd.engines;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.TestCase;
import net.sourceforge.pmd.PMDException;

import org.junit.Test;
import org.xml.sax.SAXException;

public abstract class AbstractTestFlexPmdEngineTest extends TestCase
{
   private static final String OUTPUT_DIRECTORY_URL = "target/report/";

   public AbstractTestFlexPmdEngineTest( final String name )
   {
      super( name );
   }

   @Test
   public final void testExecuteReport() throws PMDException,
                                        SAXException,
                                        URISyntaxException,
                                        IOException
   {
      final URL sourceDirectoryResource = getClass().getResource( "/test" );

      assertNotNull( "Source directory is not found as a resource",
                     sourceDirectoryResource );

      assertNotNull( "Source directory is not found as a resource",
                     sourceDirectoryResource.toURI() );

      final File sourceDirectory = new File( sourceDirectoryResource.toURI().getPath() );
      final File outputDirectory = new File( OUTPUT_DIRECTORY_URL );

      getFlexPmdEngine( sourceDirectory,
                        outputDirectory,
                        "" ).executeReport( null );

      onTestExecuteReportDone();
   }

   protected abstract AbstractFlexPmdEngine getFlexPmdEngine( final File sourceDirectory,
                                                              final File outputDirectory,
                                                              final String packageToExclude );

   protected abstract void onTestExecuteReportDone();
}