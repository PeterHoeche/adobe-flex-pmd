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
import java.util.ArrayList;
import java.util.List;

import com.adobe.ac.pmd.files.IMxmlFile;

/**
 * @author xagnetti
 */
class MxmlFile extends AbstractFlexFile implements IMxmlFile
{
   private static final String METADATA_TAG    = "Metadata>";
   private String[]            actualScriptBlock;
   private int                 endLine;
   private boolean             mainApplication = false;
   private String[]            scriptBlock;
   private int                 startLine;

   /**
    * @param file
    * @param rootDirectory
    */
   protected MxmlFile( final File file,
                       final File rootDirectory )
   {
      super( file, rootDirectory );

      computeIfIsMainApplication();
      if ( getLinesNb() > 0 )
      {
         extractScriptBlock();
      }
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

   private int computeScriptOffSet( final int startingLineIndex )
   {
      int currentLineIndex = startingLineIndex + 1;
      while ( getLines().get( currentLineIndex ).contains( "CDATA[" )
            || getLines().get( currentLineIndex ).contains( "/*" )
            || getLines().get( currentLineIndex ).trim().equals( "" ) )
      {
         currentLineIndex++;
      }
      return currentLineIndex
            - startingLineIndex;
   }

   private void copyScriptLinesKeepingOriginalLineIndices()
   {
      final List< String > scriptLines = extractScriptLines();
      final List< String > metaDataLines = extractMetaDataLines();
      final String packageLine = "package "
            + getPackageName() + "{";
      final String classLine = "class "
            + getClassName().split( "\\." )[ 0 ] + "{";

      scriptLines.set( 0,
                       packageLine );

      if ( metaDataLines.isEmpty() )
      {
         if ( scriptLines.size() > 1 )
         {
            scriptLines.set( 1,
                             classLine );
         }
      }
      else
      {
         final int firstMetaDataLine = getFirstMetaDataLine( getLines() );

         for ( int i = firstMetaDataLine; i < firstMetaDataLine
               + metaDataLines.size(); i++ )
         {
            scriptLines.set( i,
                             metaDataLines.get( i
                                   - firstMetaDataLine ) );
         }
         scriptLines.set( firstMetaDataLine
                                + metaDataLines.size(),
                          classLine );
      }

      scriptLines.set( scriptLines.size() - 1,
                       "}}" );
      scriptBlock = scriptLines.toArray( new String[ scriptLines.size() ] );
   }

   private List< String > extractMetaDataLines()
   {
      final ArrayList< String > metaDataLines = new ArrayList< String >();
      int currentLineIndex = 0;
      int start = 0;
      int end = 0;

      for ( final String line : getLines() )
      {
         if ( line.contains( METADATA_TAG ) )
         {
            if ( line.contains( "</" ) )
            {
               end = currentLineIndex;
               break;
            }
            else if ( line.contains( "<" ) )
            {
               start = currentLineIndex + 1;
            }
         }
         currentLineIndex++;
      }
      metaDataLines.addAll( getLines().subList( start,
                                                end ) );
      return metaDataLines;
   }

   private void extractScriptBlock()
   {
      int currentLineIndex = 0;
      startLine = 0;
      endLine = 0;

      for ( final String line : getLines() )
      {
         if ( line.contains( "Script>" ) )
         {
            if ( line.contains( "</" ) )
            {
               endLine = currentLineIndex - 1;
               break;
            }
            else if ( line.contains( "<" ) )
            {
               startLine = currentLineIndex
                     + computeScriptOffSet( currentLineIndex );
            }
         }
         currentLineIndex++;
      }

      copyScriptLinesKeepingOriginalLineIndices();
   }

   private List< String > extractScriptLines()
   {
      final List< String > scriptLines = new ArrayList< String >();

      for ( int j = 0; j < startLine; j++ )
      {
         scriptLines.add( "" );
      }
      if ( startLine < endLine )
      {
         actualScriptBlock = getLines().subList( startLine,
                                                 endLine ).toArray( new String[ endLine
               - startLine ] );
         scriptLines.addAll( new ArrayList< String >( getLines().subList( startLine,
                                                                          endLine ) ) );
      }
      for ( int j = endLine; j < getLines().size(); j++ )
      {
         scriptLines.add( "" );
      }
      return scriptLines;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.files.IMxmlFile#getActualScriptBlock()
    */
   public final String[] getActualScriptBlock()
   {
      return actualScriptBlock; // NOPMD
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.files.IMxmlFile#getBeginningScriptBlock()
    */
   public int getBeginningScriptBlock()
   {
      return startLine;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.files.impl.AbstractFlexFile#getCommentClosingTag()
    */
   @Override
   public final String getCommentClosingTag()
   {
      return "-->";
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.files.impl.AbstractFlexFile#getCommentOpeningTag()
    */
   @Override
   public final String getCommentOpeningTag()
   {
      return "<!--";
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.files.IMxmlFile#getEndingScriptBlock()
    */
   public int getEndingScriptBlock()
   {
      return endLine;
   }

   private int getFirstMetaDataLine( final List< String > lines )
   {
      for ( int i = 0; i < lines.size(); i++ )
      {
         final String line = lines.get( i );

         if ( line.contains( METADATA_TAG )
               && line.contains( "<" ) )
         {
            return i;
         }
      }
      return 0;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.files.IMxmlFile#getScriptBlock()
    */
   public final String[] getScriptBlock()
   {
      return scriptBlock; // NOPMD by xagnetti on 7/7/09 3:15 PM
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.files.IFlexFile#getSingleLineComment()
    */
   public String getSingleLineComment()
   {
      return getCommentOpeningTag();
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.files.impl.AbstractFlexFile#isMainApplication()
    */
   @Override
   public final boolean isMainApplication()
   {
      return mainApplication;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.files.impl.AbstractFlexFile#isMxml()
    */
   @Override
   public final boolean isMxml()
   {
      return true;
   }

   // private String printMetaData( final List< String > metaDataLines )
   // {
   // final StringBuffer buffer = new StringBuffer();
   // if ( metaDataLines == null
   // || metaDataLines.isEmpty() )
   // {
   // return "";
   // }
   // for ( final String line : metaDataLines )
   // {
   // buffer.append( line );
   // }
   // return buffer + " ";
   // }
}
