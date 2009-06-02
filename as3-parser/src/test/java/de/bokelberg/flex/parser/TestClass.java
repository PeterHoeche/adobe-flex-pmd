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

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestClass extends TestCase
{

   private AS3Parser  asp;
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
   public void testExtends() throws TokenException
   {
      assertPackageContent( "1",
                            "public class A extends B { } ",
                            "<content line=\"2\" column=\"1\">"
                                  + "<class line=\"2\" column=\"14\">"
                                  + "<name line=\"2\" column=\"14\">A</name><mod-list line=\"2\" column=\"16\">"
                                  + "<mod line=\"2\" column=\"16\">public</mod></mod-list><extends line=\"2\" "
                                  + "column=\"24\">B</extends><content line=\"2\" column=\"28\"></content>"
                                  + "</class></content>" );
   }

   @Test
   public void testFinalClass() throws TokenException
   {
      assertPackageContent( "",
                            "public final class Title{ }",
                            "<content line=\"2\" column=\"1\">"
                                  + "<class line=\"2\" column=\"20\">"
                                  + "<name line=\"2\" column=\"20\">Title</name>"
                                  + "<mod-list line=\"2\" column=\"25\">"
                                  + "<mod line=\"2\" column=\"25\">public</mod>"
                                  + "<mod line=\"2\" column=\"25\">final</mod></mod-list>"
                                  + "<content line=\"2\" " + "column=\"27\"></content>" + "</class>"
                                  + "</content>" );
   }

   @Test
   public void testFullFeatured() throws TokenException
   {
      // assertPackageContent( "",
      // "public class A { public static const RULE_REMOVED : String = \"ruleRemoved\";}",
      // "" );

      assertPackageContent( "1",
                            "public class A extends B implements C,D { } ",
                            "<content line=\"2\" column=\"1\"><class line=\"2\" column=\"14\">"
                                  + "<name line=\"2\" column=\"14\">A</name><mod-list line=\"2\" column=\"16\">"
                                  + "<mod line=\"2\" column=\"16\">public</mod></mod-list><extends line=\"2\" "
                                  + "column=\"24\">B</extends><implements-list line=\"2\" column=\"37\">"
                                  + "<implements line=\"2\" column=\"37\">C</implements><implements line=\"2\" "
                                  + "column=\"39\">D</implements></implements-list><content line=\"2\" column=\"43\">"
                                  + "</content></class></content>" );
   }

   @Test
   public void testImplementsList() throws TokenException
   {
      assertPackageContent( "1",
                            "public class A implements B,C { } ",
                            "<content line=\"2\" column=\"1\"><class line=\"2\" column=\"14\">"
                                  + "<name line=\"2\" column=\"14\">A</name><mod-list line=\"2\" "
                                  + "column=\"16\"><mod line=\"2\" column=\"16\">public</mod></mod-list>"
                                  + "<implements-list line=\"2\" column=\"27\"><implements line=\"2\" "
                                  + "column=\"27\">B</implements><implements line=\"2\" column=\"29\">"
                                  + "C</implements></implements-list><content line=\"2\" column=\"33\">"
                                  + "</content></class></content>" );
   }

   @Test
   public void testImplementsSingle() throws TokenException
   {
      assertPackageContent( "1",
                            "public class A implements B { } ",
                            "<content line=\"2\" column=\"1\"><class line=\"2\" column=\"14\">"
                                  + "<name line=\"2\" column=\"14\">A</name><mod-list line=\"2\" "
                                  + "column=\"16\"><mod line=\"2\" column=\"16\">public</mod></mod-list>"
                                  + "<implements-list line=\"2\" column=\"27\"><implements line=\"2\" "
                                  + "column=\"27\">B</implements></implements-list><content line=\"2\" "
                                  + "column=\"31\"></content></class></content>" );
   }

   @Test
   public void testImportInsideClass() throws TokenException
   {
      assertPackageContent( "",
                            "public final class Title{ import lala.lala; }",
                            "<content line=\"2\" column=\"1\">"
                                  + "<class line=\"2\" column=\"20\">"
                                  + "<name line=\"2\" column=\"20\">Title</name>"
                                  + "<mod-list line=\"2\" column=\"25\">"
                                  + "<mod line=\"2\" column=\"25\">public</mod>"
                                  + "<mod line=\"2\" column=\"25\">final</mod>" + "</mod-list>"
                                  + "<content line=\"2\" column=\"27\"><import line=\"2\" "
                                  + "column=\"34\">lala.lala</import></content>" + "</class>" + "</content>" );

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
