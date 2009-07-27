/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
