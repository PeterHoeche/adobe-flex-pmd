package com.adobe.ac.ncss.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public final class FileUtils
{
   public static final Logger LOGGER = Logger.getLogger( FileUtils.class.getName() );

   public static boolean isLineACorrectStatement( final String line )
   {
      return line.compareTo( "" ) != 0
            && lrtrim( line ).compareTo( "{" ) != 0 && lrtrim( line ).compareTo( "}" ) != 0
            && line.endsWith( ";" );
   }

   public static Collection< File > listFiles( final File directory,
                                               final FilenameFilter filter,
                                               final boolean recurse )
   {
      return listFilesRecurse( directory,
                               filter,
                               recurse );
   }

   public static List< String > readFile( final File file )
   {
      final List< String > content = new ArrayList< String >();

      FileInputStream fis = null;
      BufferedInputStream bis = null;
      DataInputStream dis = null;

      try
      {
         fis = new FileInputStream( file );
         bis = new BufferedInputStream( fis );
         dis = new DataInputStream( bis );

         while ( dis.available() != 0 )
         {
            content.add( dis.readLine() );
         }

         // dispose all the resources after using them.
         fis.close();
         bis.close();
         dis.close();

      }
      catch ( final FileNotFoundException e )
      {
         e.printStackTrace();
      }
      catch ( final IOException e )
      {
         e.printStackTrace();
      }
      return content;
   }

   private static Collection< File > listFilesRecurse( final File directory,
                                                       final FilenameFilter filter,
                                                       final boolean recurse )
   {
      final Collection< File > files = new ArrayList< File >();
      final File[] entries = directory.listFiles();

      if ( entries != null )
      {
         for ( final File entry : entries )
         {
            if ( filter == null
                  || filter.accept( directory,
                                    entry.getName() ) )
            {
               files.add( entry );
            }
            if ( recurse
                  && entry.isDirectory() )
            {
               files.addAll( listFilesRecurse( entry,
                                               filter,
                                               recurse ) );
            }
         }
      }
      return files;
   }

   private static String lrtrim( final String source )
   {
      return ltrim( rtrim( source ) );
   }

   /* remove leading whitespace */
   private static String ltrim( final String source )
   {
      return source.replaceAll( "^\\s+",
                                "" );
   }

   /* remove trailing whitespace */
   private static String rtrim( final String source )
   {
      return source.replaceAll( "\\s+$",
                                "" );
   }

   private FileUtils()
   {
   }
}
