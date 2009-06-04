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
package com.adobe.ac.pmd.files;

import java.io.File;
import java.util.List;

import com.adobe.ac.ncss.utils.FileUtils;

/**
 * Abstract class representing a Flex File (either MXML or AS)
 * 
 * @author xagnetti
 */
public abstract class AbstractFlexFile implements Comparable< AbstractFlexFile >
{
   /**
    * @param line
    * @param search
    * @return true if the search string is contained in the given line
    */
   protected static final boolean doesCurrentLineContain( final String line,
                                                          final String search )
   {
      return line.contains( search );
   }

   protected final List< String > lines;
   private final String           className;
   private final File             file;
   private String                 packageName;

   protected AbstractFlexFile( final File underlyingFile,
                               final File rootDirectory )
   {
      final String filePath = underlyingFile.getPath();
      final String rootPath = rootDirectory.getPath();

      file = underlyingFile;
      lines = FileUtils.readFile( underlyingFile );
      className = underlyingFile.getName();
      packageName = filePath.replace( className,
                                      "" ).replace( rootPath,
                                                    "" ).replace( System.getProperty( "file.separator" ),
                                                                  "." );

      if ( packageName.endsWith( "." ) )
      {
         packageName = packageName.substring( 0,
                                              packageName.length() - 1 );
      }
      if ( packageName.length() > 0
            && packageName.charAt( 0 ) == '.' )
      {
         packageName = packageName.substring( 1,
                                              packageName.length() );
      }
   }

   public final int compareTo( final AbstractFlexFile otherViolation )
   {
      return getFilename().compareTo( otherViolation.getFilename() );
   }

   public boolean contains( final String stringToLookup,
                            final int lineToBeIgnored )
   {
      int lineIndex = 0;
      boolean found = false;

      for ( final String line : lines )
      {
         if ( doesCurrentLineContain( line,
                                      stringToLookup )
               && lineIndex != lineToBeIgnored )
         {
            found = true;
            break;
         }
         lineIndex++;
      }
      return found;
   }

   /**
    * @param line
    * @return true if the given line contains a comment closing tag
    */
   public final boolean doesCurrentLineContainCommentClosingTag( final String line )
   {
      return doesCurrentLineContain( line,
                                     getCommentClosingTag() );
   }

   /**
    * @param line
    * @return true if the given line contains a comment opening tag
    */
   public final boolean doesCurrentLineContainCommentOpeningTag( final String line )
   {
      return doesCurrentLineContain( line,
                                     getCommentOpeningTag() );
   }

   /**
    * @param line
    * @return true if the given line contain a one line comment
    */
   public abstract boolean doesCurrentLineContainOneLineComment( final String line );

   public final String getClassName()
   {
      return className;
   }

   /**
    * @return the token for comment closing
    */
   public abstract String getCommentClosingTag();

   /**
    * @return the token for comment opening
    */
   public abstract String getCommentOpeningTag();

   /**
    * @return java.io.File name
    */
   public final String getFilename()
   {
      return file.getName();
   }

   /**
    * @return java.io.File absolute path
    */
   public final String getFilePath()
   {
      return file.toURI().getPath();
   }

   public final String getFullyQualifiedName()
   {
      return ( packageName.compareTo( "" ) == 0 ? ""
                                               : packageName
                                                     + "." )
            + className;
   }

   public final List< String > getLines()
   {
      return lines;
   }

   public final String getPackageName()
   {
      return packageName;
   }

   /**
    * @return true if the file is a main MXML file
    */
   public abstract boolean isMainApplication();

   /**
    * @return true if the file is a MXML file
    */
   public abstract boolean isMxml();
}
