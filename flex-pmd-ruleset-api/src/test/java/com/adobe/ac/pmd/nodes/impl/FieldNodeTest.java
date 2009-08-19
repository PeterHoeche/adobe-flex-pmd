/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
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
 *      * Neither the name of the Adobe Systems, Inc. nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.nodes.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sourceforge.pmd.PMDException;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.parser.IParserNode;

public class FieldNodeTest extends FlexPmdTestBase
{
   @Test
   public void testVisibility() throws PMDException
   {
      final IParserNode ast = FileSetUtils.buildAst( getTestFiles().get( "cairngorm.NonBindableModelLocator.as" ) );
      final IClass nonBindableModelLocator = NodeFactory.createPackage( ast ).getClassNode();
      final IAttribute first = nonBindableModelLocator.getAttributes().get( 0 );
      final IAttribute second = nonBindableModelLocator.getAttributes().get( 1 );
      final IAttribute third = nonBindableModelLocator.getAttributes().get( 2 );

      assertTrue( first.is( Modifier.PRIVATE ) );
      assertFalse( first.isPublic() );
      assertFalse( first.is( Modifier.PROTECTED ) );
      assertTrue( second.is( Modifier.PROTECTED ) );
      assertFalse( second.isPublic() );
      assertFalse( second.is( Modifier.PRIVATE ) );
      assertTrue( third.isPublic() );
      assertFalse( third.is( Modifier.PROTECTED ) );
      assertFalse( third.is( Modifier.PRIVATE ) );
   }
}
