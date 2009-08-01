package net.sourceforge.pmd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PMDExceptionTest
{
   @SuppressWarnings("deprecation")
   @Test
   public void testGetReason()
   {
      final Exception reason = new Exception();
      final PMDException exception = new PMDException( "message", reason );

      assertEquals( reason,
                    exception.getReason() );
   }

   @Test
   public void testPMDExceptionString()
   {
      assertEquals( "message",
                    new PMDException( "message" ).getMessage() );
   }

   @Test
   public void testPMDExceptionStringException()
   {
      final PMDException exception = new PMDException( "message", new Exception() );

      assertEquals( "message",
                    exception.getMessage() );
   }

   @Test
   public void testSetSeverity()
   {
      final PMDException exception = new PMDException( "message" );

      exception.setSeverity( 1 );
      assertEquals( 1,
                    exception.getSeverity() );
   }
}
