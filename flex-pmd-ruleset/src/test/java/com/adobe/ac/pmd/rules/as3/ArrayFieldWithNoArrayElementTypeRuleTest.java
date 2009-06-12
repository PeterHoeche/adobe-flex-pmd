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

public class ArrayFieldWithNoArrayElementTypeRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new ArrayFieldWithNoArrayElementTypeRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "PngEncoder.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 47, 47 ),
                                             new ViolationPosition( 49, 49 ),
                                             new ViolationPosition( 60, 60 ),
                                             new ViolationPosition( 61, 61 ),
                                             new ViolationPosition( 62, 62 ),
                                             new ViolationPosition( 63, 63 ),
                                             new ViolationPosition( 64, 64 ),
                                             new ViolationPosition( 65, 65 ),
                                             new ViolationPosition( 66, 66 ),
                                             new ViolationPosition( 67, 67 ),
                                             new ViolationPosition( 68, 68 ),
                                             new ViolationPosition( 69, 69 ),
                                             new ViolationPosition( 70, 70 ),
                                             new ViolationPosition( 71, 71 ),
                                             new ViolationPosition( 95, 95 ),
                                             new ViolationPosition( 96, 96 ),
                                             new ViolationPosition( 97, 97 ),
                                             new ViolationPosition( 98, 98 ),
                                             new ViolationPosition( 121, 121 ),
                                             new ViolationPosition( 122, 122 ),
                                             new ViolationPosition( 126, 126 ),
                                             new ViolationPosition( 127, 127 ),
                                             new ViolationPosition( 128, 128 ) } ),
                       "com.adobe.ac.ncss.ArrayVO.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 33, 33 ) } );
   }
}
