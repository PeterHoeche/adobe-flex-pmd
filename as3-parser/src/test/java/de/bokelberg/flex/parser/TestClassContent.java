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

import org.junit.Before;
import org.junit.Test;

import de.bokelberg.flex.parser.exceptions.TokenException;

public class TestClassContent
      extends TestCase
{

   private AS3Parser asp;
   private AS3Scanner scn;

   @Override
   @Before
   public void setUp()
   {
      asp = new AS3Parser();
      scn = new AS3Scanner();
      asp.scn = scn;
   }

   @Test
   public void testConstDeclarations() throws TokenException
   {
      assertClassContent(
            "1",
            "const a",
            "<const-list line=\"2\" column=\"7\"><mod-list line=\"2\" column=\"7\"></mod-list><name-type-init line=\"2\" column=\"7\"><name line=\"2\" column=\"7\">a</name><type line=\"3\" column=\"1\"></type></name-type-init></const-list>" );

      assertClassContent(
            "2",
            "public const a",
            "<const-list line=\"2\" column=\"14\"><mod-list line=\"2\" column=\"14\"><mod line=\"2\" column=\"14\">public</mod></mod-list><name-type-init line=\"2\" column=\"14\"><name line=\"2\" column=\"14\">a</name><type line=\"3\" column=\"1\"></type></name-type-init></const-list>" );

      assertClassContent(
            "3",
            "public static const a : int = 0",
            "<const-list line=\"2\" column=\"21\"><mod-list line=\"2\" column=\"21\"><mod line=\"2\" column=\"21\">public</mod><mod line=\"2\" column=\"21\">static</mod></mod-list><name-type-init line=\"2\" column=\"21\"><name line=\"2\" column=\"21\">a</name><type line=\"2\" column=\"23\">int</type><init line=\"2\" column=\"31\"><primary line=\"2\" column=\"31\">0</primary></init></name-type-init></const-list>" );

      assertClassContent(
            "4",
            "[Bindable] const a",
            "<const-list line=\"2\" column=\"18\"><meta-list line=\"2\" column=\"18\"><meta line=\"2\" column=\"12\">Bindable</meta></meta-list><mod-list line=\"2\" column=\"18\"></mod-list><name-type-init line=\"2\" column=\"18\"><name line=\"2\" column=\"18\">a</name><type line=\"3\" column=\"1\"></type></name-type-init></const-list>" );
   }

   @Test
   public void testImports() throws TokenException
   {
      assertClassContent(
            "1", "import a.b.c;",
            "<import line=\"2\" column=\"8\">a.b.c</import>" );
      assertClassContent(
            "2",
            "import a.b.c import x.y.z",
            "<import line=\"2\" column=\"8\">a.b.c</import><import line=\"2\" column=\"21\">x.y.z</import>" );
   }

   @Test
   public void testMethods() throws TokenException
   {
      assertClassContent(
            "1",
            "function a(){}",
            "<function line=\"2\" column=\"13\"><mod-list line=\"2\" column=\"13\"></mod-list><name line=\"2\" column=\"10\">a</name><parameter-list line=\"2\" column=\"12\"></parameter-list><type line=\"2\" column=\"13\"></type><block line=\"2\" column=\"14\"></block></function>" );

      assertClassContent(
            "2",
            "function set a( value : int ) : void {}",
            "<set line=\"2\" column=\"38\"><mod-list line=\"2\" column=\"38\"></mod-list><name line=\"2\" column=\"14\">a</name><parameter-list line=\"2\" column=\"17\"><parameter line=\"2\" column=\"17\"><name-type-init line=\"2\" column=\"17\"><name line=\"2\" column=\"17\">value</name><type line=\"2\" column=\"23\">int</type></name-type-init></parameter></parameter-list><type line=\"2\" column=\"31\">void</type><block line=\"2\" column=\"39\"></block></set>" );

      assertClassContent(
            "3",
            "function get a() : int {}",
            "<get line=\"2\" column=\"24\"><mod-list line=\"2\" column=\"24\"></mod-list><name line=\"2\" column=\"14\">a</name><parameter-list line=\"2\" column=\"16\"></parameter-list><type line=\"2\" column=\"18\">int</type><block line=\"2\" column=\"25\"></block></get>" );
   }

   @Test
   public void testVarDeclarations() throws TokenException
   {
      assertClassContent(
            "1",
            "var a",
            "<var-list line=\"2\" column=\"5\"><mod-list line=\"2\" column=\"5\"></mod-list><name-type-init line=\"2\" column=\"5\"><name line=\"2\" column=\"5\">a</name><type line=\"3\" column=\"1\"></type></name-type-init></var-list>" );

      assertClassContent(
            "2",
            "public var a",
            "<var-list line=\"2\" column=\"12\"><mod-list line=\"2\" column=\"12\"><mod line=\"2\" column=\"12\">public</mod></mod-list><name-type-init line=\"2\" column=\"12\"><name line=\"2\" column=\"12\">a</name><type line=\"3\" column=\"1\"></type></name-type-init></var-list>" );

      assertClassContent(
            "3",
            "public static var a : int = 0",
            "<var-list line=\"2\" column=\"19\"><mod-list line=\"2\" column=\"19\"><mod line=\"2\" column=\"19\">public</mod><mod line=\"2\" column=\"19\">static</mod></mod-list><name-type-init line=\"2\" column=\"19\"><name line=\"2\" column=\"19\">a</name><type line=\"2\" column=\"21\">int</type><init line=\"2\" column=\"29\"><primary line=\"2\" column=\"29\">0</primary></init></name-type-init></var-list>" );

      assertClassContent(
            "4",
            "[Bindable] var a",
            "<var-list line=\"2\" column=\"16\"><meta-list line=\"2\" column=\"16\"><meta line=\"2\" column=\"12\">Bindable</meta></meta-list><mod-list line=\"2\" column=\"16\"></mod-list><name-type-init line=\"2\" column=\"16\"><name line=\"2\" column=\"16\">a</name><type line=\"3\" column=\"1\"></type></name-type-init></var-list>" );
   }

   private void assertClassContent(
         final String message, final String input, final String expected ) throws TokenException
   {
      scn.setLines( new String[]
      { "{", input, "}", "__END__" } );
      asp.nextToken(); // first call
      asp.nextToken(); // skip {
      final String result = new ASTToXMLConverter().convert( asp.parseClassContent() );
      assertEquals(
            message, "<content line=\"2\" column=\"1\">"
                  + expected + "</content>", result );
   }

}
