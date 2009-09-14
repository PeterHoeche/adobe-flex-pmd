package com.adobe.ac.pmd.nodes.utils;

import com.adobe.ac.ncss.utils.FileUtils;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.parser.IParserNode;

public final class FunctionUtils
{
   public static int computeFunctionLength( final IFlexFile currentFile,
                                            final IParserNode block )
   {
      int lineNb = 1;
      final int firstLine = block.getChild( 0 ).getLine();
      final int lastLine = block.getLastChild().getLine();

      for ( int lineIndex = firstLine; lineIndex < lastLine; lineIndex++ )
      {
         if ( FileUtils.isLineACorrectStatement( currentFile.getLineAt( lineIndex ) ) )
         {
            lineNb++;
         }
      }
      return lineNb;
   }
}
