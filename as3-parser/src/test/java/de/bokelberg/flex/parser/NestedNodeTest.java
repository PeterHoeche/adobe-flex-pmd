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

import org.junit.Before;
import org.junit.Test;

import com.adobe.ac.pmd.parser.NodeKind;
import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class NestedNodeTest extends AbstractAs3ParserTest
{
   private NestedNode function;

   @Override
   @Before
   public void setUp()
   {
      super.setUp();

      final String code = "public function foo() : void     {"
            + "while(i>0){" + "while(true){" + "switch(a){" + "case 1:break;default:return;" + "}" + "}"
            + "}" + "}";
      final Node classNode = parseClass( code );

      function = ( Node ) classNode.getChild( 0 );
   }

   @Test
   public void testComputeCyclomaticComplexity()
   {
      assertEquals( 5,
                    function.computeCyclomaticComplexity() );
   }

   @Test
   public void testCountNodeFromType()
   {
      assertEquals( 2,
                    function.countNodeFromType( NodeKind.WHILE ) );
   }

   @Test
   public void testGetLastChild()
   {
      assertEquals( NodeKind.BLOCK,
                    function.getLastChild().getId() );

      assertNull( function.getChild( Integer.MAX_VALUE ) );
   }

   @Test
   public void testIs()
   {
      assertFalse( function.is( null ) );
   }

   private Node parseClass( final String input )
   {
      scn.setLines( new String[]
      { "{",
                  input,
                  "}",
                  "__END__" } );
      try
      {
         asp.nextToken();
         asp.nextToken(); // skip {
         return asp.parseClassContent();
      }
      catch ( final TokenException e )
      {
         e.printStackTrace();
      }
      return null;
   }
}
