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
package com.adobe.ac.pmd.rules.naming;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class WronglyNamedVariableRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected ExpectedViolation[] getExpectedViolatingFiles()
   {
      return new ExpectedViolation[]
      { new ExpectedViolation( "PngEncoder.as", new ViolationPosition[]
       { new ViolationPosition( 340 ),
                   new ViolationPosition( 341 ),
                   new ViolationPosition( 342 ),
                   new ViolationPosition( 343 ),
                   new ViolationPosition( 344 ),
                   new ViolationPosition( 345 ),
                   new ViolationPosition( 346 ),
                   new ViolationPosition( 347 ),
                   new ViolationPosition( 350 ),
                   new ViolationPosition( 351 ),
                   new ViolationPosition( 352 ),
                   new ViolationPosition( 353 ),
                   new ViolationPosition( 387 ),
                   new ViolationPosition( 388 ),
                   new ViolationPosition( 389 ),
                   new ViolationPosition( 390 ),
                   new ViolationPosition( 391 ),
                   new ViolationPosition( 392 ),
                   new ViolationPosition( 393 ),
                   new ViolationPosition( 394 ),
                   new ViolationPosition( 397 ),
                   new ViolationPosition( 398 ),
                   new ViolationPosition( 399 ),
                   new ViolationPosition( 400 ) } ),
                  new ExpectedViolation( "GenericType.as", new ViolationPosition[]
                  { new ViolationPosition( 44 ),
                              new ViolationPosition( 46 ) } ),
                  new ExpectedViolation( "com.adobe.ac.ncss.ConfigProxy.as", new ViolationPosition[]
                  { new ViolationPosition( 42 ) } ),
                  new ExpectedViolation( "bug.FlexPMD141a.mxml", new ViolationPosition[]
                  { new ViolationPosition( 40 ) } ),
                  new ExpectedViolation( "AbstractRowData.as", new ViolationPosition[]
                  { new ViolationPosition( 52 ) } ) };
   }

   @Override
   protected AbstractFlexRule getRule()
   {
      return new WronglyNamedVariableRule();
   }
}
