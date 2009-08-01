package com.adobe.ac.pmd.files.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.pmd.PMDException;

import com.adobe.ac.ncss.filters.FlexFilter;
import com.adobe.ac.pmd.files.IFlexFile;

public final class FileUtils
{
   public static Map< String, IFlexFile > computeFilesList( final File sourceDirectory,
                                                            final String packageToExclude ) throws PMDException
   {
      final Map< String, IFlexFile > files = new HashMap< String, IFlexFile >();
      final FlexFilter flexFilter = new FlexFilter();
      final Collection< File > foundFiles = getFlexFiles( sourceDirectory,
                                                          flexFilter );

      for ( final File sourceFile : foundFiles )
      {
         final AbstractFlexFile file = create( sourceFile,
                                               sourceDirectory );

         if ( "".equals( packageToExclude )
               || !file.getFullyQualifiedName().startsWith( packageToExclude ) )
         {
            files.put( file.getFullyQualifiedName(),
                       file );
         }
      }

      return files;
   }

   public static AbstractFlexFile create( final File sourceFile,
                                          final File sourceDirectory )
   {
      AbstractFlexFile file;

      if ( sourceFile.getName().endsWith( ".as" ) )
      {
         file = new As3File( sourceFile, sourceDirectory );
      }
      else
      {
         file = new MxmlFile( sourceFile, sourceDirectory );
      }

      return file;
   }

   public static String[] readStrings( final File file ) throws IOException
   {
      final ArrayList< String > result = new ArrayList< String >();
      BufferedReader inReader = null;

      try
      {
         inReader = new BufferedReader( new FileReader( file ) );

         String line = inReader.readLine();

         while ( line != null )
         {
            result.add( line );
            line = inReader.readLine();
         }
      }
      finally
      {
         if ( inReader != null )
         {
            inReader.close();
         }
      }
      return result.toArray( new String[]
      {} );
   }

   private static Collection< File > getFlexFiles( final File sourceDirectory,
                                                   final FlexFilter flexFilter ) throws PMDException
   {
      if ( sourceDirectory == null )
      {
         throw new PMDException( "sourceDirectory is empty", null );
      }
      final Collection< File > foundFiles = com.adobe.ac.ncss.utils.FileUtils.listFiles( sourceDirectory,
                                                                                         flexFilter,
                                                                                         true );
      if ( foundFiles.isEmpty() )
      {
         throw new PMDException( "sourceDirectory does not contain any Flex sources "
               + "(Specify the source directory in relative (not absolute))", null );
      }
      return foundFiles;
   }

   private FileUtils()
   {
   }
}
