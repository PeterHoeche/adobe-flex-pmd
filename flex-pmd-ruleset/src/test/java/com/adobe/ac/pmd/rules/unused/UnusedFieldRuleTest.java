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
package com.adobe.ac.pmd.rules.unused;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class UnusedFieldRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new UnusedFieldRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      final HashMap< String, ViolationPosition[] > violatedFiles = new HashMap< String, ViolationPosition[] >();

      addToMap( addToMap( addToMap( violatedFiles,
                                    "com.adobe.ac.ncss.event.DynamicCustomEvent.as",
                                    new ViolationPosition[]
                                    { new ViolationPosition( 35, 35 ) } ),
                          "GenericType.as",
                          new ViolationPosition[]
                          { new ViolationPosition( 35, 35 ) } ),
                "com.adobe.ac.ncss.TestResult.as",
                new ViolationPosition[]
                { new ViolationPosition( 298, 298 ),
                            new ViolationPosition( 300, 300 ),
                            new ViolationPosition( 302, 302 ),
                            new ViolationPosition( 46, 46 ),
                            new ViolationPosition( 301, 301 ),
                            new ViolationPosition( 299, 299 ),
                            new ViolationPosition( 303, 303 ) } );

      addToMap( addToMap( addToMap( violatedFiles,
                                    "Sorted.as",
                                    new ViolationPosition[]
                                    { new ViolationPosition( 54, 54 ) } ),
                          "com.adobe.ac.ncss.mxml.IterationsList2.mxml",
                          new ViolationPosition[]
                          { new ViolationPosition( 45, 45 ),
                                      new ViolationPosition( 47, 47 ) } ),
                "com.adobe.ac.ncss.BigModel.as",
                new ViolationPosition[]
                { new ViolationPosition( 42, 42 ) } );

      addToMap( addToMap( addToMap( violatedFiles,
                                    "com.adobe.ac.ncss.event.FirstCustomEvent.as",
                                    new ViolationPosition[]
                                    { new ViolationPosition( 35, 35 ) } ),
                          "com.adobe.ac.ncss.mxml.IterationsList.mxml",
                          new ViolationPosition[]
                          { new ViolationPosition( 45, 45 ),
                                      new ViolationPosition( 46, 46 ),
                                      new ViolationPosition( 48, 48 ) } ),
                "PngEncoder.as",
                new ViolationPosition[]
                { new ViolationPosition( 632, 632 ),
                            new ViolationPosition( 633, 633 ) } );

      addToMap( addToMap( violatedFiles,
                          "com.adobe.ac.AbstractRowData.as",
                          new ViolationPosition[]
                          { new ViolationPosition( 45, 45 ),
                                      new ViolationPosition( 43, 43 ),
                                      new ViolationPosition( 46, 46 ) } ),
                "Title.as",
                new ViolationPosition[]
                { new ViolationPosition( 41, 41 ) } );

      return addToMap( addToMap( addToMap( violatedFiles,
                                           "cairngorm.NonBindableModelLocator.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 39, 39 ) } ),
                                 "com.adobe.ac.ncss.ArrayVO.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 34, 34 ),
                                             new ViolationPosition( 56, 56 ),
                                             new ViolationPosition( 37, 37 ) } ),
                       "AbstractRowData.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 44, 44 ),
                                   new ViolationPosition( 52, 52 ) } );
   }
}
