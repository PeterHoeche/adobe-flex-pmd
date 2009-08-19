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
package com.adobe.ac.pmd.files;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;

public class MxmlFileTest extends FlexPmdTestBase
{
   private IMxmlFile deleteRenderer;
   private IMxmlFile iterationsList;
   private IMxmlFile nestedComponent;

   @Before
   public void setUp()
   {
      iterationsList = ( IMxmlFile ) getTestFiles().get( "com.adobe.ac.ncss.mxml.IterationsList.mxml" );
      nestedComponent = ( IMxmlFile ) getTestFiles().get( "com.adobe.ac.ncss.mxml.NestedComponent.mxml" );
      deleteRenderer = ( IMxmlFile ) getTestFiles().get( "DeleteButtonRenderer.mxml" );
   }

   @Test
   public void testGetActionScriptScriptBlock()
   {
      final String[] deleteRendererLines = deleteRenderer.getScriptBlock();

      assertEquals( "package {",
                    deleteRendererLines[ 0 ] );
      assertEquals( "class DeleteButtonRenderer{",
                    deleteRendererLines[ 1 ] );
      assertEquals( 107,
                    deleteRendererLines.length );
      assertEquals( "            import com.adobe.ac.pmd.model.Rule;",
                    deleteRendererLines[ 49 ] );
      assertEquals( "}}",
                    deleteRendererLines[ deleteRendererLines.length - 1 ] );
   }

   @Test
   public void testGetMxmlScriptBlock()
   {
      final String[] iterationsListLines = iterationsList.getScriptBlock();

      assertEquals( "package com.adobe.ac.ncss.mxml{",
                    iterationsListLines[ 0 ] );
      assertEquals( "class IterationsList{",
                    iterationsListLines[ 1 ] );
      assertEquals( "         import com.adobe.ac.anthology.model.object.IterationModelLocator;",
                    iterationsListLines[ 40 ] );
      assertEquals( "}}",
                    iterationsListLines[ iterationsListLines.length - 1 ] );
      assertEquals( 100,
                    iterationsListLines.length );

   }

   @Test
   public void testGetMxmlScriptBlock2()
   {
      final String[] nestedLines = nestedComponent.getScriptBlock();

      assertEquals( "package com.adobe.ac.ncss.mxml{",
                    nestedLines[ 0 ] );
      assertEquals( "class NestedComponent{",
                    nestedLines[ 1 ] );
      assertEquals( 57,
                    nestedLines.length );
      assertEquals( "}}",
                    nestedLines[ nestedLines.length - 1 ] );
   }
}
