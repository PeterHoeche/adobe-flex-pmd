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
package com.adobe.ac.pmd.files;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;

public class MxmlFileTest extends FlexPmdTestBase
{
   private MxmlFile deleteRenderer;
   private MxmlFile iterationsList;
   private MxmlFile nestedComponent;

   @Before
   public void setUp()
   {
      iterationsList = ( MxmlFile ) testFiles.get( "com.adobe.ac.ncss.mxml.IterationsList.mxml" );
      nestedComponent = ( MxmlFile ) testFiles.get( "com.adobe.ac.ncss.mxml.NestedComponent.mxml" );
      deleteRenderer = ( MxmlFile ) testFiles.get( "DeleteButtonRenderer.mxml" );
   }

   @Test
   public void testGetScriptBlock()
   {
      final String[] scriptBlock1 = iterationsList.getScriptBlock();

      assertEquals( "package com.adobe.ac.ncss.mxml{",
                    scriptBlock1[ 0 ] );
      assertEquals( "class IterationsList{",
                    scriptBlock1[ 1 ] );
      assertEquals( "         import com.adobe.ac.anthology.model.object.IterationModelLocator;",
                    scriptBlock1[ 2 ] );
      assertEquals( "}}",
                    scriptBlock1[ scriptBlock1.length - 1 ] );

      final String[] scriptBlock2 = nestedComponent.getScriptBlock();

      assertEquals( "package com.adobe.ac.ncss.mxml{",
                    scriptBlock2[ 0 ] );
      assertEquals( "class NestedComponent{",
                    scriptBlock2[ 1 ] );
      assertEquals( "}}",
                    scriptBlock2[ 2 ] );
      assertEquals( "}}",
                    scriptBlock2[ scriptBlock2.length - 1 ] );

      final String[] scriptBlock3 = deleteRenderer.getScriptBlock();

      assertEquals( "package {",
                    scriptBlock3[ 0 ] );
      assertEquals( "class DeleteButtonRenderer{",
                    scriptBlock3[ 1 ] );
      assertEquals( "            import com.adobe.ac.pmd.model.Rule;",
                    scriptBlock3[ 2 ] );
      assertEquals( "}}",
                    scriptBlock3[ scriptBlock3.length - 1 ] );
   }
}
