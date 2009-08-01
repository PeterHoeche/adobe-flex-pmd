/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd;

/**
 * A convenience exception wrapper. Contains the original exception, if any.
 * Also, contains a severity number (int). Zero implies no severity. The higher
 * the number the greater the severity.
 * 
 * @author Donald A. Leckie
 * @version $Revision: 5681 $, $Date: 2007-11-30 14:00:56 -0800 (Fri, 30 Nov
 *          2007) $
 * @since August 30, 2002
 */
public class PMDException extends Exception
{
   private static final long serialVersionUID = 6938647389367956874L;

   private int               severity;

   public PMDException( final String message )
   {
      super( message );
   }

   public PMDException( final String message,
                        final Exception reason )
   {
      super( message, reason );
   }

   /**
    * Returns the cause of this exception or <code>null</code>
    * 
    * @return the cause of this exception or <code>null</code>
    * @deprecated use {@link #getCause()} instead
    */
   @Deprecated
   public Exception getReason()
   {
      return ( Exception ) getCause();
   }

   public int getSeverity()
   {
      return severity;
   }

   public void setSeverity( final int severityToBeSet )
   {
      severity = severityToBeSet;
   }
}
