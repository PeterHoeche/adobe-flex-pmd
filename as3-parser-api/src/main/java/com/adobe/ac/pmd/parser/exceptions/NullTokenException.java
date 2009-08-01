package com.adobe.ac.pmd.parser.exceptions;

@SuppressWarnings("serial")
public class NullTokenException extends TokenException
{
   public NullTokenException( final String fileName )
   {
      super( "null token in "
            + fileName + "." );
   }
}
