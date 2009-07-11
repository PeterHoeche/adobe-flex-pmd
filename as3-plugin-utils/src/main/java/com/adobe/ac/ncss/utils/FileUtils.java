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

import com.adobe.ac.ncss.filters.DirectoryFilter;
import com.adobe.ac.ncss.filters.FlexFilter;

public class FileUtils
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

      if ( directory.listFiles() == null )
      {
         LOGGER.severe( directory.getAbsolutePath()
               + " does not contain any related files" );
         return new ArrayList< File >();
      }

      return listFilesRecurse( directory,
                               filter,
                               recurse );
   }

   public static Collection< File > listNonEmptyDirectories( final File directory,
                                                             final boolean recurse )
   {
      final Collection< File > files = new ArrayList< File >();
      final File[] entries = directory.listFiles( new DirectoryFilter() );
      final FlexFilter flexFilter = new FlexFilter();

      if ( entries != null )
      {
         for ( final File entry : entries )
         {
            if ( entry.isDirectory()
                  && !listFiles( entry,
                                 flexFilter,
                                 false ).isEmpty() )
            {
               files.add( entry );
            }
            if ( recurse
                  && entry.isDirectory() )
            {
               files.addAll( listNonEmptyDirectories( entry,
                                                      recurse ) );
            }
         }
      }
      return files;
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

   /* remove all superfluous whitespaces in source string */
   public static String trim( final String source )
   {
      return itrim( ltrim( rtrim( source ) ) );
   }

   /* replace multiple whitespaces between words with single blank */
   private static String itrim( final String source )
   {
      return source.replaceAll( "\\b\\s{2,}\\b",
                                " " );
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
}
