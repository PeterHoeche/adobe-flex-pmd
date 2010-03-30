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
package com.adobe.ac.pmd.nodes.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.sourceforge.pmd.PMDException;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.parser.IParserNode;

public class CommentNodeTest extends FlexPmdTestBase
{
   private final IPackage flexPMD60Package;

   public CommentNodeTest() throws PMDException
   {
      final IParserNode bug60Ast = FileSetUtils.buildAst( getTestFiles().get( "bug."
            + "FlexPMD60.as" ) );
      flexPMD60Package = NodeFactory.createPackage( bug60Ast );
   }

   @Test
   public void testClassComment()
   {
      assertNotNull( flexPMD60Package.getClassNode().getAsDoc().getStringValue() );

      assertEquals( "/**   * AsDoc class   */",
                    flexPMD60Package.getClassNode()
                                    .getAsDoc()
                                    .getStringValue()
                                    .replace( "\t",
                                              "   " )
                                    .replace( '\n',
                                              ' ' )
                                    .replaceAll( "  ",
                                                 " " ) );

      assertEquals( 2,
                    flexPMD60Package.getClassNode().getMultiLinesComment().size() );

      assertNotNull( flexPMD60Package.getClassNode().getMultiLinesComment().get( 0 ) );

      assertEquals( "/*   * comment   */",
                    flexPMD60Package.getClassNode()
                                    .getMultiLinesComment()
                                    .get( 0 )
                                    .getStringValue()
                                    .replace( "\t",
                                              "   " )
                                    .replace( '\n',
                                              ' ' )
                                    .replaceAll( "  ",
                                                 " " ) );
   }

   @Test
   public void testFieldComment()
   {
      assertNotNull( flexPMD60Package.getClassNode().getAttributes().get( 0 ).getAsDoc() );

      assertEquals( "/**    * AsDoc attribute    */",
                    flexPMD60Package.getClassNode()
                                    .getAttributes()
                                    .get( 0 )
                                    .getAsDoc()
                                    .getStringValue()
                                    .replace( "\t",
                                              "   " )
                                    .replace( '\n',
                                              ' ' )
                                    .replaceAll( "  ",
                                                 " " ) );

   }

   @Test
   public void testFunctionComment()
   {
      assertNotNull( flexPMD60Package.getClassNode().getFunctions().get( 0 ).getAsDoc() );

      assertEquals( "/**    * AsDoc method    */",
                    flexPMD60Package.getClassNode()
                                    .getFunctions()
                                    .get( 0 )
                                    .getAsDoc()
                                    .getStringValue()
                                    .replace( "\t",
                                              "   " )
                                    .replace( '\n',
                                              ' ' )
                                    .replaceAll( "  ",
                                                 " " ) );

      assertEquals( 2,
                    flexPMD60Package.getClassNode().getMultiLinesComment().size() );

      assertEquals( "/*   * comment   */",
                    flexPMD60Package.getClassNode()
                                    .getMultiLinesComment()
                                    .get( 0 )
                                    .getStringValue()
                                    .replace( "\t",
                                              "   " )
                                    .replace( '\n',
                                              ' ' )
                                    .replaceAll( "  ",
                                                 " " ) );

      assertEquals( 1,
                    flexPMD60Package.getClassNode().getFunctions().get( 0 ).getMultiLinesComment().size() );

      assertEquals( "/*     var i : int = 0;*/",
                    flexPMD60Package.getClassNode()
                                    .getFunctions()
                                    .get( 0 )
                                    .getMultiLinesComment()
                                    .get( 0 )
                                    .getStringValue()
                                    .replace( "\t",
                                              "   " )
                                    .replace( '\n',
                                              ' ' )
                                    .replaceAll( "  ",
                                                 " " ) );
   }

   @Test
   public void testMetadataComment()
   {
      assertNotNull( flexPMD60Package.getClassNode().getAsDoc() );

      assertEquals( "/**   * AsDoc class   */",
                    flexPMD60Package.getClassNode()
                                    .getAsDoc()
                                    .getStringValue()
                                    .replace( "\t",
                                              "   " )
                                    .replace( '\n',
                                              ' ' )
                                    .replaceAll( "  ",
                                                 " " ) );

   }
}
