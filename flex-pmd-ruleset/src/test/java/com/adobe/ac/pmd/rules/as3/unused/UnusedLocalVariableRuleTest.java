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
package com.adobe.ac.pmd.rules.as3.unused;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class UnusedLocalVariableRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new UnusedLocalVariableRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      final HashMap< String, ViolationPosition[] > violations = new HashMap< String, ViolationPosition[] >();

      addToMap( addToMap( addToMap( violations,
                                    "com.adobe.ac.ncss.BigImporterModel.as",
                                    new ViolationPosition[]
                                    { new ViolationPosition( 64, 64 ) } ),
                          "GenericType.as",
                          new ViolationPosition[]
                          { new ViolationPosition( 46, 46 ) } ),
                "ErrorToltipSkin.as",
                new ViolationPosition[]
                { new ViolationPosition( 163, 163 ),
                            new ViolationPosition( 166, 166 ),
                            new ViolationPosition( 184, 184 ),
                            new ViolationPosition( 183, 183 ),
                            new ViolationPosition( 165, 165 ) } );

      return addToMap( addToMap( addToMap( addToMap( violations,
                                                     "DeleteButtonRenderer.mxml",
                                                     new ViolationPosition[]
                                                     { new ViolationPosition( 69, 69 ) } ),
                                           "com.adobe.ac.ncss.VoidConstructor.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 40, 40 ) } ),
                                 "RadonDataGrid.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 101, 101 ),
                                             new ViolationPosition( 100, 100 ) } ),
                       "com.adobe.ac.ncss.BigModel.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 47, 47 ) } );
   }
}
