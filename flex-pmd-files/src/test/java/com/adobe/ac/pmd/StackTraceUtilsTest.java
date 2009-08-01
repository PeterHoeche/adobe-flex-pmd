package com.adobe.ac.pmd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StackTraceUtilsTest
{
   @SuppressWarnings("serial")
   private static class CustomException extends Exception
   {
      public CustomException( final String message )
      {
         super( message );
      }
   }

   @Test
   public void testPrint()
   {
      final Exception exception = new CustomException( "message" );

      assertEquals( "stackTrace is not correct",
                    "message at com.adobe.ac.pmd.StackTraceUtilsTest.testPrint(StackTraceUtilsTest.java:51)\n"
                          + "sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n"
                          + "sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)",
                    StackTraceUtils.print( exception ) );
   }
}
