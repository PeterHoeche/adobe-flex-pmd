package com.adobe.ac.cpd;

import java.util.List;

import net.sourceforge.pmd.cpd.SourceCode;
import net.sourceforge.pmd.cpd.TokenEntry;
import net.sourceforge.pmd.cpd.Tokenizer;
import net.sourceforge.pmd.cpd.Tokens;

public class FlexTokenizer implements Tokenizer
{
   public void tokenize( final SourceCode source,
                         final Tokens tokens )
   {
      final List< String > code = source.getCode();

      for ( int i = 0; i < code.size(); i++ )
      {
         final String currentLine = code.get( i );

         for ( int j = 0; j < currentLine.length(); j++ )
         {
            final char tok = currentLine.charAt( j );
            if ( !Character.isWhitespace( tok )
                  && tok != '{' && tok != '}' && tok != ';' )
            {
               tokens.add( new TokenEntry( String.valueOf( tok ), source.getFileName(), i + 1 ) );
            }
         }
      }
      tokens.add( TokenEntry.getEOF() );
   }
}
