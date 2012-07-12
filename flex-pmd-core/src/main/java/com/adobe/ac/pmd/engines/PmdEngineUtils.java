/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
 *    All rights reserved.
 *
 *    Redistribution  and  use  in  source  and  binary  forms, with or without
 *    modification,  are  permitted  provided  that  the  following  conditions
 *    are met:
 *
 *      * Redistributions  of  source  code  must  retain  the  above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions  in  binary  form  must reproduce the above copyright
 *        notice,  this  list  of  conditions  and  the following disclaimer in
 *        the    documentation   and/or   other  materials  provided  with  the
 *        distribution.
 *      * Neither the name of the Adobe Systems, Incorporated. nor the names of
 *        its  contributors  may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS  SOFTWARE  IS  PROVIDED  BY THE  COPYRIGHT  HOLDERS AND CONTRIBUTORS
 *    "AS IS"  AND  ANY  EXPRESS  OR  IMPLIED  WARRANTIES,  INCLUDING,  BUT NOT
 *    LIMITED  TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,  INCIDENTAL,  SPECIAL,
 *    EXEMPLARY,  OR  CONSEQUENTIAL  DAMAGES  (INCLUDING,  BUT  NOT  LIMITED TO,
 *    PROCUREMENT  OF  SUBSTITUTE   GOODS  OR   SERVICES;  LOSS  OF  USE,  DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY,  WHETHER  IN  CONTRACT,  STRICT  LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE  OR  OTHERWISE)  ARISING  IN  ANY  WAY  OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.engines;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map.Entry;

import com.adobe.ac.pmd.FlexPmdViolations;
import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public final class PmdEngineUtils
{
   public static String findFirstViolationError( final FlexPmdViolations violations )
   {
      final StringBuffer buffer = new StringBuffer();
      final String message = "An error violation has been found on the file {0} at "
            + "line {1}, with the rule \"{2}\": {3}";
      final MessageFormat form = new MessageFormat( message );

      for ( final Entry< IFlexFile, List< IFlexViolation >> violatedFile : violations.getViolations()
                                                                                     .entrySet() )
      {
         for ( final IFlexViolation violation : violatedFile.getValue() )
         {
            if ( violation.getRule().getPriority() == Integer.parseInt( ViolationPriority.HIGH.toString() ) )
            {
               final String[] formatArgument = computeArgumentFormat( violation );
               buffer.append( form.format( formatArgument ) );
               buffer.append( '\n' );
            }
         }
      }
      return buffer.toString();
   }

   private static String[] computeArgumentFormat( final IFlexViolation violation )
   {
      final String[] formatArgument = new String[]
      { violation.getFilename(),
                  String.valueOf( violation.getBeginLine() ),
                  violation.getRule().getRuleClass(),
                  violation.getRuleMessage() };
      return formatArgument;
   }

   private PmdEngineUtils()
   {
   }
}
