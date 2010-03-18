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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.FlexPmdViolations;
import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.impl.Violation;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.IFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class PmdEngineUtilsTest extends FlexPmdTestBase
{
   private class ErrorRule extends AbstractFlexRule implements IFlexRule
   {
      @Override
      protected List< IFlexViolation > findViolationsInCurrentFile()
      {
         return new ArrayList< IFlexViolation >();
      }

      @Override
      protected ViolationPriority getDefaultPriority()
      {
         return ViolationPriority.HIGH;
      }

      @Override
      protected boolean isConcernedByTheCurrentFile()
      {
         return true;
      }
   }

   private class WarningRule extends AbstractFlexRule
   {
      @Override
      protected List< IFlexViolation > findViolationsInCurrentFile()
      {
         return new ArrayList< IFlexViolation >();
      }

      @Override
      protected ViolationPriority getDefaultPriority()
      {
         return ViolationPriority.NORMAL;
      }

      @Override
      protected boolean isConcernedByTheCurrentFile()
      {
         return true;
      }
   }

   @Test
   public void testFindFirstViolationError()
   {
      final FlexPmdViolations violations = new FlexPmdViolations();
      final List< IFlexViolation > abstractRowDataViolations = new ArrayList< IFlexViolation >();

      assertEquals( "",
                    PmdEngineUtils.findFirstViolationError( violations ) );

      final IFlexFile abstractRowDataFlexFile = getTestFiles().get( "AbstractRowData.as" );

      abstractRowDataViolations.add( new Violation( new ViolationPosition( 0 ),
                                                    new ErrorRule(),
                                                    abstractRowDataFlexFile ) );
      abstractRowDataViolations.add( new Violation( new ViolationPosition( 0 ),
                                                    new WarningRule(),
                                                    abstractRowDataFlexFile ) );
      violations.getViolations().put( abstractRowDataFlexFile,
                                      abstractRowDataViolations );
      assertEquals( "An error violation has been found on the file AbstractRowData.as at line 0, with the rule"
                          + " \"com.adobe.ac.pmd.engines.PmdEngineUtilsTest$ErrorRule\": \n",
                    PmdEngineUtils.findFirstViolationError( violations ) );
   }
}
