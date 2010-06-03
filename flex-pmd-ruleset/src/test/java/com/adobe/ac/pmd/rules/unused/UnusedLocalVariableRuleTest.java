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

public class UnusedLocalVariableRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected ExpectedViolation[] getExpectedViolatingFiles()
   {
      return new ExpectedViolation[]
      { new ExpectedViolation( "DeleteButtonRenderer.mxml", new ViolationPosition[]
       { new ViolationPosition( 69 ) } ),
                  new ExpectedViolation( "com.adobe.ac.ncss.VoidConstructor.as", new ViolationPosition[]
                  { new ViolationPosition( 40 ) } ),
                  new ExpectedViolation( "RadonDataGrid.as", new ViolationPosition[]
                  { new ViolationPosition( 100 ),
                              new ViolationPosition( 101 ) } ),
                  new ExpectedViolation( "com.adobe.ac.ncss.BigModel.as", new ViolationPosition[]
                  { new ViolationPosition( 47 ) } ),
                  new ExpectedViolation( "UnboundMetadata.as", new ViolationPosition[]
                  { new ViolationPosition( 50 ) } ),
                  new ExpectedViolation( "com.adobe.ac.ncss.BigImporterModel.as", new ViolationPosition[]
                  { new ViolationPosition( 64 ) } ),
                  new ExpectedViolation( "GenericType.as", new ViolationPosition[]
                  { new ViolationPosition( 46 ) } ),
                  new ExpectedViolation( "ErrorToltipSkin.as", new ViolationPosition[]
                  { new ViolationPosition( 163 ),
                              new ViolationPosition( 165 ),
                              new ViolationPosition( 166 ),
                              new ViolationPosition( 183 ),
                              new ViolationPosition( 184 ) } ),
                  new ExpectedViolation( "bug.Duane.mxml", new ViolationPosition[]
                  { new ViolationPosition( 68 ) } ),
                  new ExpectedViolation( "bug.FlexPMD88.as", new ViolationPosition[]
                  { new ViolationPosition( 42 ),
                              new ViolationPosition( 43 ),
                              new ViolationPosition( 44 ),
                              new ViolationPosition( 45 ) } ),
                  new ExpectedViolation( "flexpmd114.a.Test.as", new ViolationPosition[]
                  { new ViolationPosition( 42 ) } ),
                  new ExpectedViolation( "flexpmd114.b.Test.as", new ViolationPosition[]
                  { new ViolationPosition( 42 ) } ),
                  new ExpectedViolation( "flexpmd114.c.Test.as", new ViolationPosition[]
                  { new ViolationPosition( 42 ) } ) };
   }

   @Override
   protected AbstractFlexRule getRule()
   {
      return new UnusedLocalVariableRule();
   }
}
