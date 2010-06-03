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
package com.adobe.ac.pmd.rules.parsley;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class MisplacedMetaDataRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected ExpectedViolation[] getExpectedViolatingFiles()
   {
      return new ExpectedViolation[]
      { new ExpectedViolation( "parsley.MisplacedMetaData.as", new ViolationPosition[]
      { new ViolationPosition( 48, 48 ),
                  new ViolationPosition( 49, 49 ),
                  new ViolationPosition( 50, 50 ),
                  new ViolationPosition( 51, 51 ),
                  new ViolationPosition( 52, 52 ),
                  new ViolationPosition( 53, 53 ),
                  new ViolationPosition( 54, 54 ),
                  new ViolationPosition( 55, 55 ),
                  new ViolationPosition( 58, 58 ),
                  new ViolationPosition( 63, 63 ),
                  new ViolationPosition( 64, 64 ),
                  new ViolationPosition( 65, 65 ),
                  new ViolationPosition( 66, 66 ),
                  new ViolationPosition( 67, 67 ),
                  new ViolationPosition( 68, 68 ),
                  new ViolationPosition( 69, 69 ),
                  new ViolationPosition( 70, 70 ),
                  new ViolationPosition( 33, 33 ),
                  new ViolationPosition( 34, 34 ),
                  new ViolationPosition( 35, 35 ),
                  new ViolationPosition( 36, 36 ),
                  new ViolationPosition( 37, 37 ),
                  new ViolationPosition( 38, 38 ),
                  new ViolationPosition( 39, 39 ),
                  new ViolationPosition( 40, 40 ),
                  new ViolationPosition( 41, 41 ),
                  new ViolationPosition( 42, 42 ),
                  new ViolationPosition( 43, 43 ),
                  new ViolationPosition( 44, 44 ),
                  new ViolationPosition( 45, 45 ), } ) };
   }

   @Override
   protected AbstractFlexRule getRule()
   {
      return new MisplacedMetaDataRule();
   }
}
