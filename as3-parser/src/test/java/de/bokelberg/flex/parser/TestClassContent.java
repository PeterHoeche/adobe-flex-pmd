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
package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestClassContent extends AbstractAs3ParserTest
{
   @Test
   public void testCommentInMethod() throws TokenException
   {
      assertClassContent( "",
                          "public function log():void{/* comment */}",
                          "<function line=\"2\"><mod-list line=\"2\"><mod "
                                + "line=\"2\">public</mod></mod-list><name line=\"2\">log</name>"
                                + "<parameter-list line=\"2\"></parameter-list><type line=\"2\">"
                                + "void</type><block line=\"2\"><multi-line-comment line=\"2\">"
                                + "/* comment */</multi-line-comment></block></function>" );

      assertClassContent( "",
                          new String[]
                          { "{",
                                      "public function log():void{// comment ",
                                      "}",
                                      "}",
                                      "__END__" },
                          "<function line=\"2\"><mod-list line=\"2\"><mod line=\"2\""
                                + ">public</mod></mod-list><name line=\"2\">log</name>"
                                + "<parameter-list line=\"2\"></parameter-list><type line=\"2\""
                                + ">void</type><block line=\"2\"></block></function>" );
   }

   @Test
   public void testConstDeclarations() throws TokenException
   {
      assertClassContent( "1",
                          "const a",
                          "<const-list line=\"2\"><mod-list line=\"2\">"
                                + "</mod-list><name-type-init line=\"2\"><name line=\"2\">a"
                                + "</name><type line=\"3\"></type></name-type-init></const-list>" );

      assertClassContent( "2",
                          "public const a",
                          "<const-list line=\"2\"><mod-list line=\"2\">"
                                + "<mod line=\"2\">public</mod></mod-list><name-type-init line=\"2\""
                                + "><name line=\"2\">a</name><type line=\"3\">"
                                + "</type></name-type-init></const-list>" );

      assertClassContent( "3",
                          "public static const a : int = 0",
                          "<const-list line=\"2\"><mod-list line=\"2\">"
                                + "<mod line=\"2\">public</mod><mod line=\"2\">"
                                + "static</mod></mod-list><name-type-init line=\"2\"><name "
                                + "line=\"2\">a</name><type line=\"2\">int</type>"
                                + "<init line=\"2\"><primary line=\"2\">0</primary>"
                                + "</init></name-type-init></const-list>" );

      assertClassContent( "4",
                          "[Bindable] const a",
                          "<const-list line=\"2\"><meta-list line=\"2\">"
                                + "<meta line=\"2\">Bindable</meta></meta-list><mod-list line=\"2\""
                                + "></mod-list><name-type-init line=\"2\">"
                                + "<name line=\"2\">a</name><type line=\"3\">"
                                + "</type></name-type-init></const-list>" );
   }

   @Test
   public void testImports() throws TokenException
   {
      assertClassContent( "1",
                          "import a.b.c;",
                          "<import line=\"2\">a.b.c</import>" );
      assertClassContent( "2",
                          "import a.b.c import x.y.z",
                          "<import line=\"2\">a.b.c</import>"
                                + "<import line=\"2\">x.y.z</import>" );
   }

   @Test
   public void testMethods() throws TokenException
   {
      assertClassContent( "1",
                          "function a(){}",
                          "<function line=\"2\"><mod-list line=\"2\">"
                                + "</mod-list><name line=\"2\">a</name><parameter-list line=\"2\""
                                + "></parameter-list><type line=\"2\"></type><block "
                                + "line=\"2\"></block></function>" );

      assertClassContent( "2",
                          "function set a( value : int ) : void {}",
                          "<set line=\"2\"><mod-list line=\"2\">"
                                + "</mod-list><name line=\"2\">a</name>"
                                + "<parameter-list line=\"2\"><parameter line=\"2\">"
                                + "<name-type-init line=\"2\"><name line=\"2\">value"
                                + "</name><type line=\"2\">int</type></name-type-init></parameter>"
                                + "</parameter-list><type line=\"2\">void</type><block line=\"2\""
                                + "></block></set>" );

      assertClassContent( "3",
                          "function get a() : int {}",
                          "<get line=\"2\"><mod-list line=\"2\">"
                                + "</mod-list><name line=\"2\">a</name><parameter-list line=\"2\""
                                + "></parameter-list><type line=\"2\">int"
                                + "</type><block line=\"2\"></block></get>" );

      assertClassContent( "function with default parameter",
                          "public function newLine ( height:*='' ):void{}",
                          "<function line=\"2\"><mod-list line=\"2\"><mod line=\"2\""
                                + ">public</mod></mod-list><name line=\"2\">newLine"
                                + "</name><parameter-list line=\"2\"><parameter line=\"2\""
                                + "><name-type-init line=\"2\"><name line=\"2\""
                                + ">height</name><type line=\"2\">*</type>"
                                + "<init line=\"2\"><primary line=\"2\">''"
                                + "</primary></init></name-type-init></parameter></parameter-list>"
                                + "<type line=\"2\">void</type><block line=\"2\">" + "</block></function>" );
   }

   @Test
   public void testMethodsWithAsDoc() throws TokenException
   {
      scn.setLines( new String[]
      { "{",
                  "/** AsDoc */public function a(){}",
                  "}",
                  "__END__" } );
      asp.nextToken(); // first call
      asp.nextToken(); // skip {

      assertEquals( "<content line=\"2\"><function line=\"2\">"
                          + "<as-doc line=\"2\">/** AsDoc */</as-doc><mod-list "
                          + "line=\"2\"><mod line=\"2\">public</mod>"
                          + "</mod-list><name line=\"2\">a</name><parameter-list "
                          + "line=\"2\"></parameter-list><type line=\"2\">"
                          + "</type><block line=\"2\"></block></function></content>",
                    new ASTToXMLConverter().convert( asp.parseClassContent() ) );
   }

   @Test
   public void testMethodsWithMultiLineComments() throws TokenException
   {
      scn.setLines( new String[]
      { "{",
                  "/* Commented */public function a(){}",
                  "}",
                  "__END__" } );
      asp.nextToken(); // first call
      asp.nextToken(); // skip {

      assertEquals( "<content line=\"2\"><multi-line-comment line=\"2\">"
                          + "/* Commented */</multi-line-comment><function line=\"2\">"
                          + "<mod-list line=\"2\"><mod line=\"2\">public"
                          + "</mod></mod-list><name line=\"2\">a</name><parameter-list "
                          + "line=\"2\"></parameter-list><type line=\"2\">"
                          + "</type><block line=\"2\"></block></function></content>",
                    new ASTToXMLConverter().convert( asp.parseClassContent() ) );
   }

   @Test
   public void testMethodWithMetadataComment() throws TokenException
   {
      scn.setLines( new String[]
      { "{",
                  "/* Comment */ [Bindable] public function a () : void { }",
                  "}",
                  "__END__" } );
      asp.nextToken(); // first call
      asp.nextToken(); // skip {

      assertEquals( "1",
                    "<content line=\"2\"><multi-line-comment line=\"2\">"
                          + "/* Comment */</multi-line-comment><function line=\"2\">"
                          + "<meta-list line=\"2\"><meta line=\"2\">Bindable"
                          + "</meta></meta-list><mod-list line=\"2\"><mod line=\"2\""
                          + ">public</mod></mod-list><name line=\"2\">a</name>"
                          + "<parameter-list line=\"2\"></parameter-list><type line=\"2\""
                          + ">void</type><block line=\"2\"></block></function>" + "</content>",
                    new ASTToXMLConverter().convert( asp.parseClassContent() ) );
   }

   @Test
   public void testRestParameter() throws TokenException
   {
      assertClassContent( "",
                          "public function log(message:String, ... rest):void{}",
                          "<function line=\"2\"><mod-list line=\"2\">"
                                + "<mod line=\"2\">public</mod></mod-list><name line=\"2\">"
                                + "log</name><parameter-list line=\"2\">"
                                + "<parameter line=\"2\"><name-type-init line=\"2\">"
                                + "<name line=\"2\">message</name><type line=\"2\">String"
                                + "</type></name-type-init></parameter><parameter line=\"2\">"
                                + "<rest line=\"2\">rest</rest></parameter></parameter-list>"
                                + "<type line=\"2\">void</type><block line=\"2\">" + "</block></function>" );
   }

   @Test
   public void testVarDeclarations() throws TokenException
   {
      assertClassContent( "1",
                          "var a",
                          "<var-list line=\"2\"><mod-list line=\"2\">"
                                + "</mod-list><name-type-init line=\"2\"><name line=\"2\">a"
                                + "</name><type line=\"3\"></type></name-type-init></var-list>" );

      assertClassContent( "2",
                          "public var a;",
                          "<var-list line=\"2\"><mod-list line=\"2\">"
                                + "<mod line=\"2\">public</mod></mod-list><name-type-init line=\"2\""
                                + "><name line=\"2\">a</name><type line=\"2\""
                                + "></type></name-type-init></var-list>" );

      assertClassContent( "3",
                          "public static var a : int = 0",
                          "<var-list line=\"2\"><mod-list line=\"2\">"
                                + "<mod line=\"2\">public</mod><mod line=\"2\">"
                                + "static</mod></mod-list><name-type-init line=\"2\">"
                                + "<name line=\"2\">a</name><type line=\"2\">int</type>"
                                + "<init line=\"2\"><primary line=\"2\">0</primary>"
                                + "</init></name-type-init></var-list>" );

      assertClassContent( "4",
                          "[Bindable] var a",
                          "<var-list line=\"2\"><meta-list line=\"2\">"
                                + "<meta line=\"2\">Bindable</meta></meta-list>"
                                + "<mod-list line=\"2\"></mod-list>" + "<name-type-init line=\"2\">"
                                + "<name line=\"2\">a</name><type line=\"3\">"
                                + "</type></name-type-init></var-list>" );
   }

   private void assertClassContent( final String message,
                                    final String input,
                                    final String expected ) throws TokenException
   {
      scn.setLines( new String[]
      { "{",
                  input,
                  "}",
                  "__END__" } );
      asp.nextToken(); // first call
      asp.nextToken(); // skip {
      final String result = new ASTToXMLConverter().convert( asp.parseClassContent() );
      assertEquals( message,
                    "<content line=\"2\">"
                          + expected + "</content>",
                    result );
   }

   private void assertClassContent( final String message,
                                    final String[] input,
                                    final String expected ) throws TokenException
   {
      scn.setLines( input );
      asp.nextToken(); // first call
      asp.nextToken(); // skip {
      final String result = new ASTToXMLConverter().convert( asp.parseClassContent() );
      assertEquals( message,
                    "<content line=\"2\">"
                          + expected + "</content>",
                    result );
   }
}
