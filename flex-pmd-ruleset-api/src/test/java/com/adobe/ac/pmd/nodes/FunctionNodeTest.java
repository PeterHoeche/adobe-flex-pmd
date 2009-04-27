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
package com.adobe.ac.pmd.nodes;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;

import de.bokelberg.flex.parser.AS3Parser;
import de.bokelberg.flex.parser.exceptions.TokenException;

public class FunctionNodeTest
      extends FlexPmdTestBase
{
   private FunctionNode constructor;
   private FunctionNode drawHighlightIndicator;
   private FunctionNode drawRowBackground;
   private FunctionNode drawSelectionIndicator;
   private FunctionNode placeSortArrow;

   @Before
   public void setup() throws IOException, TokenException
   {
      final AS3Parser parser = new AS3Parser();
      final ClassNode radonDataGridClassNode = new PackageNode( parser
            .buildAst( testFiles.get(
                  "RadonDataGrid.as" ).getFilePath() ) ).getClassNode();

      constructor = radonDataGridClassNode.getFunctions().get(
            0 );
      drawHighlightIndicator = radonDataGridClassNode.getFunctions().get(
            1 );
      drawSelectionIndicator = radonDataGridClassNode.getFunctions().get(
            2 );
      drawRowBackground = radonDataGridClassNode.getFunctions().get(
            3 );
      placeSortArrow = radonDataGridClassNode.getFunctions().get(
            4 );
   }

   @Test
   public void testGetCyclomaticComplexity()
   {
      assertEquals(
            1, constructor.getCyclomaticComplexity() );
      assertEquals(
            1, drawHighlightIndicator.getCyclomaticComplexity() );
      assertEquals(
            1, drawSelectionIndicator.getCyclomaticComplexity() );
      assertEquals(
            4, drawRowBackground.getCyclomaticComplexity() );
      assertEquals(
            13, placeSortArrow.getCyclomaticComplexity() );
   }

   @Test
   public void testGetName()
   {
      assertEquals(
            "RadonDataGrid", constructor.getName() );
      assertEquals(
            "drawHighlightIndicator", drawHighlightIndicator.getName() );
      assertEquals(
            "drawSelectionIndicator", drawSelectionIndicator.getName() );
      assertEquals(
            "drawRowBackground", drawRowBackground.getName() );
      assertEquals(
            "placeSortArrow", placeSortArrow.getName() );
   }

   @Test
   public void testGetParameters()
   {
      assertEquals(
            0, constructor.getParameters().size() );
      assertEquals(
            7, drawHighlightIndicator.getParameters().size() );
      assertEquals(
            7, drawSelectionIndicator.getParameters().size() );
      assertEquals(
            6, drawRowBackground.getParameters().size() );
      assertEquals(
            0, placeSortArrow.getParameters().size() );
   }

   @Test
   public void testGetReturnType()
   {
      assertEquals(
            "", constructor.getReturnType().getInternalNode().stringValue );
      assertEquals(
            "void",
            drawHighlightIndicator.getReturnType().getInternalNode().stringValue );
      assertEquals(
            "void",
            drawSelectionIndicator.getReturnType().getInternalNode().stringValue );
      assertEquals(
            "void",
            drawRowBackground.getReturnType().getInternalNode().stringValue );
      assertEquals(
            "void",
            placeSortArrow.getReturnType().getInternalNode().stringValue );
   }
}
