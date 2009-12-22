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

public class TestPackageContent extends AbstractAs3ParserTest
{
   @Test
   public void testClass() throws TokenException
   {
      assertPackageContent( "1",
                            "public class A { }",
                            "<content line=\"2\" column=\"1\"><class line=\"2\" column=\"14\">"
                                  + "<name line=\"2\" column=\"14\">A</name><mod-list line=\"2\" column=\"16\">"
                                  + "<mod line=\"2\" column=\"16\">public</mod>"
                                  + "</mod-list><content line=\"2\" column=\"18\">"
                                  + "</content></class></content>" );
   }

   @Test
   public void testClassWithAsDoc() throws TokenException
   {
      assertPackageContent( "1",
                            "/** AsDoc */ public class A { }",
                            "<content line=\"2\" column=\"12\"><class line=\"2\" column=\"27\">"
                                  + "<as-doc line=\"2\" column=\"12\">/** AsDoc */</as-doc><name line=\"2\" "
                                  + "column=\"27\">A</name><mod-list line=\"2\" column=\"29\"><mod line=\"2\" "
                                  + "column=\"29\">public</mod></mod-list><content line=\"2\" column=\"31\">"
                                  + "</content></class></content>" );
   }

   @Test
   public void testClassWithAsDocComplex() throws TokenException
   {
      assertPackageContent( "1",
                            "/** AsDoc */ public class A { "
                                  + "/** Member */ " + "public var tmp : Number; "
                                  + "private var tmp2 : int; " + "/** Function */ "
                                  + "protected function foo() : void { } }",
                            "<content line=\"2\" column=\"12\"><class line=\"2\" column=\"27\"><as-doc line=\"2\""
                                  + " column=\"12\">/** AsDoc */</as-doc><name line=\"2\" column=\"27\">A</name>"
                                  + "<mod-list line=\"2\" column=\"29\"><mod line=\"2\" column=\"29\">public</mod>"
                                  + "</mod-list><content line=\"2\" column=\"43\"><var-list line=\"2\" column=\"56\">"
                                  + "<mod-list line=\"2\" column=\"56\"><mod line=\"2\" column=\"56\">public</mod>"
                                  + "</mod-list><name-type-init line=\"2\" column=\"56\"><name line=\"2\" column=\"56\">tmp</name>"
                                  + "<type line=\"2\" column=\"62\">Number</type></name-type-init><as-doc line=\"2\" "
                                  + "column=\"43\">/** Member */</as-doc></var-list><var-list line=\"2\" column=\"82\">"
                                  + "<mod-list line=\"2\" column=\"82\"><mod line=\"2\" column=\"82\">private</mod></mod-list>"
                                  + "<name-type-init line=\"2\" column=\"82\"><name line=\"2\" column=\"82\">tmp2</name>"
                                  + "<type line=\"2\" column=\"89\">int</type></name-type-init></var-list><function "
                                  + "line=\"2\" column=\"142\"><as-doc line=\"2\" column=\"108\">/** Function */</as-doc>"
                                  + "<mod-list line=\"2\" column=\"142\"><mod line=\"2\" column=\"142\">protected</mod></mod-list>"
                                  + "<name line=\"2\" column=\"129\">foo</name><parameter-list line=\"2\" column=\"133\">"
                                  + "</parameter-list><type line=\"2\" column=\"137\">void</type><block line=\"2\" "
                                  + "column=\"144\"></block></function></content></class></content>" );
   }

   @Test
   public void testClassWithMetadata() throws TokenException
   {
      assertPackageContent( "1",
                            "[Bindable(name=\"abc\", value=\"123\")] public class A { }",
                            "<content line=\"2\" column=\"1\"><class line=\"2\" column=\"50\">"
                                  + "<name line=\"2\" column=\"50\">A</name><meta-list line=\"2\" column=\"52\">"
                                  + "<meta line=\"2\" column=\"1\""
                                  + ">Bindable ( name = \"abc\" , value = \"123\" )</meta>"
                                  + "</meta-list><mod-list line=\"2\" column=\"52\">"
                                  + "<mod line=\"2\" column=\"52\">public"
                                  + "</mod></mod-list><content line=\"2\" column=\"54\"></content></class></content>" );
   }

   @Test
   public void testClassWithSimpleMetadata() throws TokenException
   {
      assertPackageContent( "1",
                            "[Bindable] public class A { }",
                            "<content line=\"2\" column=\"1\"><class line=\"2\" column=\"25\">"
                                  + "<name line=\"2\" column=\"25\">A</name><meta-list line=\"2\" column=\"27\">"
                                  + "<meta line=\"2\" column=\"1\">Bindable</meta></meta-list>"
                                  + "<mod-list line=\"2\" column=\"27\"><mod line=\"2\" column=\"27\">public</mod>"
                                  + "</mod-list><content line=\"2\" column=\"29\"></content></class></content>" );
   }

   @Test
   public void testImports() throws TokenException
   {
      assertPackageContent( "1",
                            "import a.b.c;",
                            "<content line=\"2\" column=\"1\"><import line=\"2\" "
                                  + "column=\"8\">a.b.c</import></content>" );

      assertPackageContent( "2",
                            "import a.b.c import x.y.z",
                            "<content line=\"2\" column=\"1\"><import line=\"2\" column=\"8\">a.b.c"
                                  + "</import><import line=\"2\" column=\"21\">x.y.z</import></content>" );
   }

   @Test
   public void testInterface() throws TokenException
   {
      assertPackageContent( "1",
                            "public interface A { }",
                            "<content line=\"2\" column=\"1\"><interface line=\"2\" column=\"18\">"
                                  + "<name line=\"2\" column=\"18\">A</name><mod-list line=\"2\" column=\"20\">"
                                  + "<mod line=\"2\" column=\"20\">public</mod>"
                                  + "</mod-list><content line=\"2\" column=\"22\">"
                                  + "</content></interface></content>" );
   }

   @Test
   public void testMethodPackages() throws TokenException
   {
      assertPackageContent( "1",
                            "public function a() : void { }",
                            "<content line=\"2\" column=\"1\"><function line=\"2\" column=\"28\">"
                                  + "<mod-list line=\"2\" column=\"28\"><mod line=\"2\" column=\"28\">public</mod>"
                                  + "</mod-list><name line=\"2\" column=\"17\">a</name><parameter-list line=\"2\" "
                                  + "column=\"19\"></parameter-list><type line=\"2\" column=\"23\">void</type>"
                                  + "<block line=\"2\" column=\"30\"></block></function></content>" );
   }

   @Test
   public void testUse() throws TokenException
   {
      assertPackageContent( "1",
                            "use namespace myNamespace",
                            "<content line=\"2\" column=\"1\"><use line=\"2\" column=\"15\""
                                  + ">myNamespace</use></content>" );
   }

   @Test
   public void testUseNameSpace() throws TokenException
   {
      assertPackageContent( "FlexPMD-108",
                            "use namespace mx_internal;",
                            "<content line=\"2\" column=\"1\"><use line=\"2\" column=\"15\">mx_internal</use></content>" );
   }

   private void assertPackageContent( final String message,
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
      final String result = new ASTToXMLConverter().convert( asp.parsePackageContent() );
      assertEquals( message,
                    expected,
                    result );
   }
}
