package com.adobe.ac.pmd.files.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.adobe.ac.pmd.files.IMxmlFile;

class MxmlFile extends AbstractFlexFile implements IMxmlFile
{
   private int      endLine;
   private boolean  mainApplication = false;
   private String[] scriptBlock;
   private int      startLine;

   protected MxmlFile( final File file,
                       final File rootDirectory )
   {
      super( file, rootDirectory );

      computeIfIsMainApplication();
      extractScriptBlock();
   }

   public int getBeginningScriptBlock()
   {
      return startLine;
   }

   @Override
   public final String getCommentClosingTag()
   {
      return "-->";
   }

   @Override
   public final String getCommentOpeningTag()
   {
      return "<!--";
   }

   public int getEndingScriptBlock()
   {
      return endLine;
   }

   public final String[] getScriptBlock()
   {
      return scriptBlock; // NOPMD by xagnetti on 7/7/09 3:15 PM
   }

   @Override
   public final boolean isMainApplication()
   {
      return mainApplication;
   }

   @Override
   public final boolean isMxml()
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

   private void copyScriptLinesKeepingOriginalLineIndices()
   {
      final List< String > scriptLines = fillMxmlLine();
      final String firstLine = "package "
            + getPackageName() + "{";
      final String secondLine = "class "
            + getClassName().split( "\\." )[ 0 ] + "{";

      scriptLines.set( 0,
                       firstLine );
      scriptLines.set( 1,
                       secondLine );
      scriptLines.set( scriptLines.size() - 1,
                       "}}" );

      scriptBlock = scriptLines.toArray( new String[ scriptLines.size() ] );
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
               startLine = currentLineIndex + 2;
            }
         }
         currentLineIndex++;
      }

      copyScriptLinesKeepingOriginalLineIndices();
   }

   private List< String > fillMxmlLine()
   {
      final List< String > scriptLines = new ArrayList< String >();

      for ( int j = 0; j < startLine; j++ )
      {
         scriptLines.add( "" );
      }
      scriptLines.addAll( new ArrayList< String >( getLines().subList( startLine,
                                                                       endLine ) ) );
      for ( int j = endLine; j < getLines().size(); j++ )
      {
         scriptLines.add( "" );
      }
      return scriptLines;
   }
}
