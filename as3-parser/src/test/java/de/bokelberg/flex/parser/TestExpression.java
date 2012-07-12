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

public class TestExpression extends AbstractStatementTest
{
   @Test
   public void testAddExpression() throws TokenException
   {
      assertStatement( "1",
                       "5+6",
                       "<add line=\"1\"><primary line=\"1\""
                             + ">5</primary><op line=\"1\">+</op>" + "<primary line=\"1\">6</primary></add>" );
   }

   @Test
   public void testAndExpression() throws TokenException
   {
      assertStatement( "1",
                       "5&&6",
                       "<and line=\"1\"><primary line=\"1\">5</primary>"
                             + "<op line=\"1\">&&</op>" + "<primary line=\"1\">6</primary></and>" );
   }

   @Test
   public void testAssignmentExpression() throws TokenException
   {
      assertStatement( "1",
                       "x+=6",
                       "<assign line=\"1\"><primary line=\"1\">x"
                             + "</primary><op line=\"1\">+=</op><primary line=\"1\""
                             + ">6</primary></assign>" );
   }

   @Test
   public void testBitwiseAndExpression() throws TokenException
   {
      assertStatement( "1",
                       "5&6",
                       "<b-and line=\"1\"><primary line=\"1\">5"
                             + "</primary><op line=\"1\">&</op><primary line=\"1\">6</primary></b-and>" );
   }

   @Test
   public void testBitwiseOrExpression() throws TokenException
   {
      assertStatement( "1",
                       "5|6",
                       "<b-or line=\"1\"><primary line=\"1\">5"
                             + "</primary><op line=\"1\">|</op><primary line=\"1\">6</primary></b-or>" );
   }

   @Test
   public void testBitwiseXorExpression() throws TokenException
   {
      assertStatement( "1",
                       "5^6",
                       "<b-xor line=\"1\"><primary line=\"1\">5"
                             + "</primary><op line=\"1\">^</op><primary line=\"1\">6</primary></b-xor>" );
   }

   @Test
   public void testConditionalExpression() throws TokenException
   {
      assertStatement( "1",
                       "true?5:6",
                       "<conditional line=\"1\"><primary line=\"1\">"
                             + "true</primary><primary line=\"1\">5" + "</primary><primary line=\"1\">6"
                             + "</primary></conditional>" );
   }

   @Test
   public void testDivision() throws TokenException
   {
      assertStatement( "",
                       "offset = ( this[ axis.unscaled ] / 2 - ( rightPos ) / 2 );",
                       "<assign line=\"1\"><primary line=\"1\">offset</primary><op line=\"1\">=</op>"
                             + "<primary line=\"1\"><encapsulated line=\"1\"><add line=\"1\"><mul line=\"1\">"
                             + "<arr-acc line=\"1\"><primary line=\"1\">this</primary><dot line=\"1\"><primary "
                             + "line=\"1\">axis</primary><primary line=\"1\">unscaled</primary></dot></arr-acc>"
                             + "<op line=\"1\">/</op><primary line=\"1\">2</primary></mul><op line=\"1\">-</op>"
                             + "<mul line=\"1\"><primary line=\"1\"><encapsulated line=\"1\"><primary line=\"1\">"
                             + "rightPos</primary></encapsulated></primary><op line=\"1\">/</op><primary line=\"1\">"
                             + "2</primary></mul></add></encapsulated></primary></assign>" );
   }

   @Test
   public void testEncapsulated() throws TokenException
   {
      assertStatement( "",
                       "(dataProvider as ArrayCollection) = null",
                       "<assign line=\"1\"><primary line=\"1\">"
                             + "<encapsulated line=\"1\"><relation line=\"1\">"
                             + "<primary line=\"1\">dataProvider</primary>"
                             + "<as line=\"1\">as</as><primary line=\"1\">"
                             + "ArrayCollection</primary></relation></encapsulated></primary>"
                             + "<op line=\"1\">=</op><primary line=\"1\">" + "null</primary></assign>" );
   }

   @Test
   public void testEqualityExpression() throws TokenException
   {
      assertStatement( "1",
                       "5!==6",
                       "<equality line=\"1\"><primary line=\"1\">5"
                             + "</primary><op line=\"1\">!==</op><primary line=\"1\">6</primary></equality>" );
   }

   @Test
   public void testExpressionList() throws TokenException
   {
      assertStatement( "1",
                       "5&&6,5&&9",
                       "<expr-list line=\"1\"><and line=\"1\">"
                             + "<primary line=\"1\">5</primary><op line=\"1\">"
                             + "&&</op><primary line=\"1\">6</primary></and><and line=\"1\""
                             + "><primary line=\"1\">5</primary><op line=\"1\""
                             + ">&&</op><primary line=\"1\">9</primary></and></expr-list>" );
   }

   @Test
   public void testInstanceOf() throws TokenException
   {
      assertStatement( "bug237",
                       "if (a instanceof b){}",
                       "<if line=\"1\"><condition line=\"1\"><relation line=\"1\"><primary "
                             + "line=\"1\">a</primary><op line=\"1\">instanceof</op><primary line=\"1\">"
                             + "b</primary></relation></condition><block line=\"1\"></block></if>" );
   }

   @Test
   public void testMulExpression() throws TokenException
   {
      assertStatement( "1",
                       "5/6",
                       "<mul line=\"1\"><primary line=\"1\">5"
                             + "</primary><op line=\"1\">/</op><primary line=\"1\">6</primary></mul>" );
   }

   @Test
   public void testNewExpression() throws TokenException
   {
      assertStatement( "",
                       "new Event()",
                       "<new line=\"1\"><call line=\"1\">"
                             + "<primary line=\"1\">Event</primary>"
                             + "<arguments line=\"1\"></arguments></call></new>" );

      assertStatement( "",
                       "new Event(\"lala\")",
                       "<new line=\"1\"><call line=\"1\">"
                             + "<primary line=\"1\">Event</primary>"
                             + "<arguments line=\"1\"><primary line=\"1\">"
                             + "\"lala\"</primary></arguments></call></new>" );

   }

   @Test
   public void testOrExpression() throws TokenException
   {
      assertStatement( "1",
                       "5||6",
                       "<or line=\"1\"><primary line=\"1\">5"
                             + "</primary><op line=\"1\">||</op><primary line=\"1\">6</primary></or>" );
   }

   @Test
   public void testRelationalExpression() throws TokenException
   {
      assertStatement( "1",
                       "5<=6",
                       "<relation line=\"1\"><primary line=\"1\">5"
                             + "</primary><op line=\"1\">&lt;=</op><primary line=\"1\""
                             + ">6</primary></relation>" );
   }

   @Test
   public void testShiftExpression() throws TokenException
   {
      assertStatement( "1",
                       "5<<6",
                       "<shift line=\"1\"><primary line=\"1\">5"
                             + "</primary><op line=\"1\">&lt;&lt;</op><primary line=\"1\""
                             + ">6</primary></shift>" );
   }
}
