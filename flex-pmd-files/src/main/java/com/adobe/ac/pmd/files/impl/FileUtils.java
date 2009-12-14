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
package com.adobe.ac.pmd.files.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.PMDException;

import com.adobe.ac.ncss.filters.FlexFilter;
import com.adobe.ac.pmd.files.IFlexFile;

public final class FileUtils
{
   public static Map< String, IFlexFile > computeFilesList( final File source,
                                                            final List< File > sourceList,
                                                            final String packageToExclude ) throws PMDException
   {
      final Map< String, IFlexFile > files = new LinkedHashMap< String, IFlexFile >();
      final FlexFilter flexFilter = new FlexFilter();
      final Collection< File > foundFiles = getFlexFiles( source,
                                                          sourceList,
                                                          flexFilter );

      for ( final File sourceFile : foundFiles )
      {
         final AbstractFlexFile file = create( sourceFile,
                                               source );

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

   public static String[] readLines( final File file ) throws IOException
   {
      final List< String > lines = com.adobe.ac.ncss.utils.FileUtils.readFile( file );

      return lines.toArray( new String[ lines.size() ] );
   }

   private static Collection< File > getFlexFiles( final File source,
                                                   final List< File > sourceList,
                                                   final FlexFilter flexFilter ) throws PMDException
   {
      if ( source == null
            && sourceList == null )
      {
         throw new PMDException( "sourceDirectory is empty", null );
      }
      Collection< File > foundFiles;
      if ( source != null )
      {
         if ( source.isDirectory() )
         {
            foundFiles = com.adobe.ac.ncss.utils.FileUtils.listFiles( source,
                                                                      flexFilter,
                                                                      true );
         }
         else
         {
            foundFiles = new ArrayList< File >();
            foundFiles.add( source );
         }
      }
      else
      {
         foundFiles = com.adobe.ac.ncss.utils.FileUtils.listFiles( sourceList,
                                                                   flexFilter,
                                                                   true );
      }
      if ( foundFiles.isEmpty() )
      {
         return new ArrayList< File >();
      }
      return foundFiles;
   }

   private FileUtils()
   {
   }
}
