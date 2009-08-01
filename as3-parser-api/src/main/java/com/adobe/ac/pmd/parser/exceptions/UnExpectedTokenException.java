package com.adobe.ac.pmd.parser.exceptions;

@SuppressWarnings("serial")
public class UnExpectedTokenException extends TokenException
{
   public UnExpectedTokenException( final String tokenText,
                                    final int tokenLine,
                                    final int tokenColumn,
                                    final String fileName )
   {
      super( "Unexpected token: "
            + tokenText + " in " + fileName + " at " + tokenLine + ":" + tokenColumn + "." );
   }
}
