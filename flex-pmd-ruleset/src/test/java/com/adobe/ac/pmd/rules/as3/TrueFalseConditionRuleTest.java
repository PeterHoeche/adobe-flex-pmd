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
package com.adobe.ac.pmd.rules.as3;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;
import com.adobe.ac.pmd.rules.maintanability.TrueFalseConditionRule;

public class TrueFalseConditionRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new TrueFalseConditionRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                                                         "PngEncoder.as",
                                                                         new ViolationPosition[]
                                                                         { new ViolationPosition( 574, 574 ),
                                                                                     new ViolationPosition( 576,
                                                                                                            576 ),
                                                                                     new ViolationPosition( 578,
                                                                                                            578 ),
                                                                                     new ViolationPosition( 584,
                                                                                                            584 ) } ),
                                                               "Looping.as",
                                                               new ViolationPosition[]
                                                               { new ViolationPosition( 44, 44 ),
                                                                           new ViolationPosition( 57, 57 ),
                                                                           new ViolationPosition( 66, 66 ),
                                                                           new ViolationPosition( 69, 69 ) } ),
                                                     "RadonDataGrid.as",
                                                     new ViolationPosition[]
                                                     { new ViolationPosition( 63, 63 ),
                                                                 new ViolationPosition( 190, 190 ),
                                                                 new ViolationPosition( 194, 194 ) } ),
                                           "AbstractRowData.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 106, 106 ),
                                                       new ViolationPosition( 113, 113 ) } ),
                                 "com.adobe.ac.AbstractRowData.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 59, 59 ) } ),
                       "com.adobe.ac.ncss.TestResult.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 206, 206 ) } );
   }
}
