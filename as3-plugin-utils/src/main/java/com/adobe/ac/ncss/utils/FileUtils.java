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
package com.adobe.ac.ncss.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import com.adobe.ac.utils.StackTraceUtils;

/**
 * @author xagnetti
 */
public final class FileUtils
{
   public static class FilePathComparator implements Comparator< File >
   {
      public int compare( final File fileOne,
                          final File fileTwo )
      {
         return fileOne.getAbsolutePath().compareToIgnoreCase( fileTwo.getAbsolutePath() );
      }
   }

   public static final Logger LOGGER = Logger.getLogger( FileUtils.class.getName() );

   /**
    * @param line
    * @return
    */
   public static boolean isLineACorrectStatement( final String line )
   {
      return line.compareTo( "" ) != 0
            && lrtrim( line ).compareTo( "{" ) != 0 && lrtrim( line ).compareTo( "}" ) != 0
            && line.endsWith( ";" );
   }

   /**
    * @param directory
    * @param filter
    * @param recurse
    * @return
    */
   public static Collection< File > listFiles( final File directory,
                                               final FilenameFilter filter,
                                               final boolean recurse )
   {
      final ArrayList< File > files = listFilesRecurse( directory,
                                                        filter,
                                                        recurse );
      Collections.sort( files,
                        new FilePathComparator() );
      return files;
   }

   /**
    * @param sourceDirectory
    * @param filter
    * @param recurse
    * @return
    */
   public static Collection< File > listFiles( final List< File > sourceDirectory,
                                               final FilenameFilter filter,
                                               final boolean recurse )
   {
      final ArrayList< File > files = new ArrayList< File >();

      for ( final File topDirectory : sourceDirectory )
      {
         files.addAll( listFilesRecurse( topDirectory,
                                         filter,
                                         recurse ) );
      }

      Collections.sort( files,
                        new FilePathComparator() );
      return files;
   }

   /**
    * @param file
    * @return
    */
   public static List< String > readFile( final File file )
   {
      final List< String > result = new ArrayList< String >();

      BufferedReader inReader = null;
      try
      {
         final Reader reader = new InputStreamReader( new FileInputStream( file ), "UTF-8" );
         inReader = new BufferedReader( reader );

         String line = readLine( inReader );

         while ( line != null )
         {
            result.add( line );
            line = readLine( inReader );
         }
         inReader.close();
      }
      catch ( final IOException e )
      {
         StackTraceUtils.print( e );
      }
      return result;
   }

   private static ArrayList< File > listFilesRecurse( final File directory,
                                                      final FilenameFilter filter,
                                                      final boolean recurse )
   {
      final ArrayList< File > files = new ArrayList< File >();
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

   private static String readLine( final BufferedReader inReader ) throws IOException
   {
      final String line = inReader.readLine();

      if ( line != null )
      {
         return line.replaceAll( "\uFEFF",
                                 "" );
      }
      return null;
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
