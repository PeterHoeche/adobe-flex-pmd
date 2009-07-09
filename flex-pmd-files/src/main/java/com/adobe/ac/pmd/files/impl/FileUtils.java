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

         if ( packageToExclude.equals( "" )
               || !file.getFullyQualifiedName().startsWith( packageToExclude ) )
         {
            files.put( file.getFullyQualifiedName(),
                       file );
         }
      }

      return files;
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

   private static AbstractFlexFile create( final File sourceFile,
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
