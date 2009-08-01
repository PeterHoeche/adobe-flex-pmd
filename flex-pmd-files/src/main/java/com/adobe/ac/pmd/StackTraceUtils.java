package com.adobe.ac.pmd;

public final class StackTraceUtils
{
   /**
    * Pretty print the first two lines of the stacktrace of the given exception
    *
    * @param exception Exception to print
    * @return The first two lines of the stacktrace
    */
   public static String print( final Exception exception )
   {
      final StringBuffer buffer = new StringBuffer();

      buffer.append( exception.getMessage()
            + " at " + exception.getStackTrace()[ 0 ] + "\n" );
      buffer.append( exception.getStackTrace()[ 1 ]
            + "\n" + exception.getStackTrace()[ 2 ] );
      return buffer.toString();
   }

   private StackTraceUtils()
   {
   }
}
