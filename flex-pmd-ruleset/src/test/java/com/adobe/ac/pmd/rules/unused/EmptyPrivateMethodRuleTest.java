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
package com.adobe.ac.pmd.rules.unused;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class EmptyPrivateMethodRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected ExpectedViolation[] getExpectedViolatingFiles()
   {
      return new ExpectedViolation[]
      { new ExpectedViolation( "bug.FlexPMD97.as", new ViolationPosition[]
       { new ViolationPosition( 39 ) } ),
                  new ExpectedViolation( "Sorted.as", new ViolationPosition[]
                  { new ViolationPosition( 62 ),
                              new ViolationPosition( 71 ) } ),
                  new ExpectedViolation( "com.adobe.ac.ncss.BigModel.as", new ViolationPosition[]
                  { new ViolationPosition( 82 ),
                              new ViolationPosition( 86 ),
                              new ViolationPosition( 90 ),
                              new ViolationPosition( 94 ),
                              new ViolationPosition( 98 ) } ),
                  new ExpectedViolation( "cairngorm.events.CorrectConstantEvent.as", new ViolationPosition[]
                  { new ViolationPosition( 53 ) } ) };
   }

   @Override
   protected AbstractFlexRule getRule()
   {
      return new EmptyPrivateMethodRule();
   }
}
