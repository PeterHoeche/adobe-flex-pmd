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

public class TestInterfaceContent extends AbstractAs3ParserTest
{
   @Test
   public void testImports() throws TokenException
   {
      assertInterfaceContent( "1",
                              "import a.b.c;",
                              "<import line=\"2\" column=\"8\">a.b.c</import>" );

      assertInterfaceContent( "2",
                              "import a.b.c import x.y.z",
                              "<import line=\"2\" column=\"8\">a.b.c</import>"
                                    + "<import line=\"2\" column=\"21\">x.y.z</import>" );
   }

   @Test
   public void testMethods() throws TokenException
   {
      assertInterfaceContent( "1",
                              "function a()",
                              "<function line=\"3\" column=\"1\">"
                                    + "<name line=\"2\" column=\"10\">a</name>"
                                    + "<parameter-list line=\"2\" column=\"12\">"
                                    + "</parameter-list><type line=\"3\" column=\"1\">"
                                    + "</type></function>" );

      assertInterfaceContent( "2",
                              "function set a( value : int ) : void",
                              "<set line=\"3\" column=\"1\"><name line=\"2\" column=\"14\">a"
                                    + "</name><parameter-list line=\"2\" column=\"17\">"
                                    + "<parameter line=\"2\" column=\"17\">"
                                    + "<name-type-init line=\"2\" column=\"17\">"
                                    + "<name line=\"2\" column=\"17\">value</name>"
                                    + "<type line=\"2\" column=\"23\">int</type>"
                                    + "</name-type-init></parameter></parameter-list>"
                                    + "<type line=\"2\" column=\"31\">void</type></set>" );

      assertInterfaceContent( "3",
                              "function get a() : int",
                              "<get line=\"3\" column=\"1\"><name line=\"2\" column=\"14\">a"
                                    + "</name><parameter-list line=\"2\" column=\"16\">"
                                    + "</parameter-list><type line=\"2\" column=\"18\">int" + "</type></get>" );
   }

   private void assertInterfaceContent( final String message,
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
      final String result = new ASTToXMLConverter().convert( asp.parseInterfaceContent() );
      assertEquals( message,
                    "<content line=\"2\" column=\"1\">"
                          + expected + "</content>",
                    result );
   }
}
