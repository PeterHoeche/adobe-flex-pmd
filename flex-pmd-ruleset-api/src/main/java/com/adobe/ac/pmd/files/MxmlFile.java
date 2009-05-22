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
import java.util.ArrayList;
import java.util.List;

public class MxmlFile extends AbstractFlexFile
{
   private boolean mainApplication = false;

   public MxmlFile( final File file,
                    final File rootDirectory )
   {
      super( file, rootDirectory );
      computeIfIsMainApplication();
   }

   @Override
   public boolean doesCurrentLineContainOneLineComment( final String line )
   {
      return false;
   }

   @Override
   public String getCommentClosingTag()
   {
      return "-->";
   }

   @Override
   public String getCommentOpeningTag()
   {
      return "<!--";
   }

   public String[] getScriptBlock()
   {
      int i = 0;
      int startLine = 0;
      int endLine = 0;

      for ( final String line : lines )
      {
         if ( line.contains( "Script>" ) )
         {
            if ( line.contains( "</" ) )
            {
               endLine = i - 2;
               break;
            }
            else if ( line.contains( "<" ) )
            {
               startLine = i + 2;
            }
         }
         i++;
      }

      List< String > scriptLines;

      if ( startLine != 0
            && endLine != 0 && startLine != endLine )
      {
         scriptLines = new ArrayList< String >( lines );
         scriptLines = scriptLines.subList( startLine,
                                            endLine );
      }
      else
      {
         scriptLines = new ArrayList< String >();
      }

      scriptLines.add( 0,
                       "package "
                             + getPackageName() + "{" );
      scriptLines.add( 1,
                       "class "
                             + getClassName().split( "\\." )[ 0 ] + "{" );
      scriptLines.add( "}}" );

      return scriptLines.toArray( new String[ scriptLines.size() ] );
   }

   @Override
   public boolean isMainApplication()
   {
      return mainApplication;
   }

   @Override
   public boolean isMxml()
   {
      return true;
   }

   private void computeIfIsMainApplication()
   {
      for ( final String line : getLines() )
      {
         if ( line.contains( "Application " )
               && line.charAt( 0 ) == '<' )
         {
            mainApplication = true;
            break;
         }
      }
   }
}
