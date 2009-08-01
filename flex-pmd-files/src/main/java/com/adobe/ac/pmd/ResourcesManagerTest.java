package com.adobe.ac.pmd;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import net.sourceforge.pmd.PMDException;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.files.impl.FileUtils;

/**
 * Internal utility which finds out the test resources, and map them to their
 * qualified names.
 * 
 * @author xagnetti
 */
final class ResourcesManagerTest
{
   private static ResourcesManagerTest instance = null;
   private static final Logger         LOGGER   = Logger.getLogger( ResourcesManagerTest.class.getName() );

   public static synchronized ResourcesManagerTest getInstance() // NOPMD by
                                                                 // xagnetti on
                                                                 // 7/9/09 6:45
                                                                 // AM
   {
      if ( instance == null )
      {
         try
         {
            instance = new ResourcesManagerTest();
         }
         catch ( final URISyntaxException e )
         {
            LOGGER.warning( StackTraceUtils.print( e ) );
         }
         catch ( final PMDException e )
         {
            LOGGER.warning( StackTraceUtils.print( e ) );
         }
      }
      return instance;
   }

   private final Map< String, IFlexFile > testFiles;
   private final File                     testRootDirectory;

   private ResourcesManagerTest() throws URISyntaxException,
                                 PMDException
   {
      final URL resource = this.getClass().getResource( "/test" );

      testRootDirectory = new File( resource.toURI().getPath() );
      testFiles = FileUtils.computeFilesList( testRootDirectory,
                                              "" );
   }

   public Map< String, IFlexFile > getTestFiles()
   {
      return testFiles;
   }

   protected File getTestRootDirectory()
   {
      return testRootDirectory;
   }
}
