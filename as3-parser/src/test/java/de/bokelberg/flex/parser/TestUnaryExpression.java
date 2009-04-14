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
package de.bokelberg.flex.parser;

import junit.framework.TestCase;
import de.bokelberg.flex.parser.exceptions.TokenException;

public class TestUnaryExpression
      extends TestCase
{

   private AS3Parser asp;
   private AS3Scanner scn;

   @Override
   public void setUp()
   {
      asp = new AS3Parser();
      scn = new AS3Scanner();
      asp.scn = scn;
   }

   public void testArrayAccess() throws TokenException
   {
      assertUnary(
            "1",
            "x[0]",
            "<arr-acc line=\"1\" column=\"2\"><primary line=\"1\" column=\"1\">x</primary><primary line=\"1\" column=\"3\">0</primary></arr-acc>" );
   }

   public void testComplex() throws TokenException
   {
      assertUnary(
            "1",
            "a.b['c'].d.e(1)",
            "<dot line=\"1\" column=\"3\"><primary line=\"1\" column=\"1\">a</primary><dot line=\"1\" column=\"10\"><arr-acc line=\"1\" column=\"4\"><primary line=\"1\" column=\"3\">b</primary><primary line=\"1\" column=\"5\">'c'</primary></arr-acc><dot line=\"1\" column=\"12\"><primary line=\"1\" column=\"10\">d</primary><call line=\"1\" column=\"13\"><primary line=\"1\" column=\"12\">e</primary><arguments line=\"1\" column=\"14\"><primary line=\"1\" column=\"14\">1</primary></arguments></call></dot></dot></dot>" );

      assertUnary(
            "2",
            "a.b['c']['d'].e(1)",
            "<dot line=\"1\" column=\"3\"><primary line=\"1\" column=\"1\">a</primary><dot line=\"1\" column=\"15\"><arr-acc line=\"1\" column=\"4\"><primary line=\"1\" column=\"3\">b</primary><primary line=\"1\" column=\"5\">'c'</primary><primary line=\"1\" column=\"10\">'d'</primary></arr-acc><call line=\"1\" column=\"16\"><primary line=\"1\" column=\"15\">e</primary><arguments line=\"1\" column=\"17\"><primary line=\"1\" column=\"17\">1</primary></arguments></call></dot></dot>" );
   }

   public void testMethodCall() throws TokenException
   {
      assertUnary(
            "1",
            "method()",
            "<call line=\"1\" column=\"7\"><primary line=\"1\" column=\"1\">method</primary><arguments line=\"1\" column=\"8\"></arguments></call>" );

      assertUnary(
            "2",
            "method( 1, \"two\" )",
            "<call line=\"1\" column=\"7\"><primary line=\"1\" column=\"1\">method</primary><arguments line=\"1\" column=\"9\"><primary line=\"1\" column=\"9\">1</primary><primary line=\"1\" column=\"12\">\"two\"</primary></arguments></call>" );
   }

   public void testMultipleMethodCall() throws TokenException
   {
      assertUnary(
            "1",
            "method()()",
            "<call line=\"1\" column=\"7\"><primary line=\"1\" column=\"1\">method</primary><arguments line=\"1\" column=\"8\"></arguments><arguments line=\"1\" column=\"10\"></arguments></call>" );
   }

   public void testParseUnaryExpressions() throws TokenException
   {
      assertUnary(
            "1",
            "++x",
            "<pre-inc line=\"1\" column=\"3\"><primary line=\"1\" column=\"3\">x</primary></pre-inc>" );

      assertUnary(
            "2",
            "x++",
            "<post-inc line=\"2\" column=\"1\"><primary line=\"1\" column=\"1\">x</primary></post-inc>" );

      assertUnary(
            "3",
            "--x",
            "<pre-dec line=\"1\" column=\"3\"><primary line=\"1\" column=\"3\">x</primary></pre-dec>" );

      assertUnary(
            "4",
            "x--",
            "<post-dec line=\"2\" column=\"1\"><primary line=\"1\" column=\"1\">x</primary></post-dec>" );

      assertUnary(
            "5",
            "+x",
            "<plus line=\"1\" column=\"2\"><primary line=\"1\" column=\"2\">x</primary></plus>" );

      assertUnary(
            "6",
            "+ x",
            "<plus line=\"1\" column=\"3\"><primary line=\"1\" column=\"3\">x</primary></plus>" );

      assertUnary(
            "7",
            "-x",
            "<minus line=\"1\" column=\"2\"><primary line=\"1\" column=\"2\">x</primary></minus>" );

      assertUnary(
            "8",
            "- x",
            "<minus line=\"1\" column=\"3\"><primary line=\"1\" column=\"3\">x</primary></minus>" );

      assertUnary(
            "9",
            "delete x",
            "<delete line=\"1\" column=\"8\"><primary line=\"1\" column=\"8\">x</primary></delete>" );

      assertUnary(
            "a",
            "void x",
            "<void line=\"1\" column=\"6\"><primary line=\"1\" column=\"6\">x</primary></void>" );

      assertUnary(
            "b",
            "typeof x",
            "<typeof line=\"1\" column=\"8\"><primary line=\"1\" column=\"8\">x</primary></typeof>" );

      assertUnary(
            "c",
            "! x",
            "<not line=\"1\" column=\"3\"><primary line=\"1\" column=\"3\">x</primary></not>" );

      assertUnary(
            "d",
            "~ x",
            "<b-not line=\"1\" column=\"3\"><primary line=\"1\" column=\"3\">x</primary></b-not>" );

      assertUnary(
            "e",
            "x++",
            "<post-inc line=\"2\" column=\"1\"><primary line=\"1\" column=\"1\">x</primary></post-inc>" );

      assertUnary(
            "f",
            "x--",
            "<post-dec line=\"2\" column=\"1\"><primary line=\"1\" column=\"1\">x</primary></post-dec>" );
   }

   private void assertUnary(
         final String message, final String input, final String expected ) throws TokenException
   {
      scn.setLines( new String[]
      { input, "__END__" } );
      asp.nextToken();
      final String result = new ASTToXMLConverter().convert( asp
            .parseUnaryExpression() );
      assertEquals(
            message, expected, result );
   }

}
