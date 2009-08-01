package com.adobe.ac.pmd.parser.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FlexPmdExceptionTest
{
   private static final String MY_FILE_NAME = "myFileName";

   @Test
   public void testNullTokenException()
   {
      assertEquals( "null token in "
                          + MY_FILE_NAME + ".",
                    new NullTokenException( MY_FILE_NAME ).getMessage() );
   }

   @Test
   public void testTokenException()
   {
      assertEquals( "myMessage",
                    new TokenException( "myMessage" ).getMessage() );
   }

   @Test
   public void testUnexpectedTokenException()
   {
      assertEquals( "Unexpected token: tokenText in myFileName at 1:1.",
                    new UnExpectedTokenException( "tokenText", 1, 1, MY_FILE_NAME ).getMessage() );
   }
}
