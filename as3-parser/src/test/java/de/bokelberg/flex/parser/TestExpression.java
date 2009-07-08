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

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestExpression extends AbstractStatementTest
{
   @Test
   public void testAddExpression() throws TokenException
   {
      assertStatement( "1",
                       "5+6",
                       "<add line=\"1\" column=\"1\"><primary line=\"1\" "
                             + "column=\"1\">5</primary><op line=\"1\" "
                             + "column=\"2\">+</op><primary line=\"1\" column=\"3\">6</primary></add>" );
   }

   @Test
   public void testAndExpression() throws TokenException
   {
      assertStatement( "1",
                       "5&&6",
                       "<and line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5</primary>"
                             + "<op line=\"1\" column=\"2\">&&</op>"
                             + "<primary line=\"1\" column=\"4\">6</primary></and>" );
   }

   @Test
   public void testAssignmentExpression() throws TokenException
   {
      assertStatement( "1",
                       "x+=6",
                       "<assign line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">x"
                             + "</primary><op line=\"1\" column=\"2\">+=</op><primary line=\"1\" "
                             + "column=\"4\">6</primary></assign>" );
   }

   @Test
   public void testBitwiseAndExpression() throws TokenException
   {
      assertStatement( "1",
                       "5&6",
                       "<b-and line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">&</op><primary line=\"1\" "
                             + "column=\"3\">6</primary></b-and>" );
   }

   @Test
   public void testBitwiseOrExpression() throws TokenException
   {
      assertStatement( "1",
                       "5|6",
                       "<b-or line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">|</op><primary line=\"1\" "
                             + "column=\"3\">6</primary></b-or>" );
   }

   @Test
   public void testBitwiseXorExpression() throws TokenException
   {
      assertStatement( "1",
                       "5^6",
                       "<b-xor line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">^</op><primary line=\"1\" "
                             + "column=\"3\">6</primary></b-xor>" );
   }

   @Test
   public void testConditionalExpression() throws TokenException
   {
      assertStatement( "1",
                       "true?5:6",
                       "<conditional line=\"1\" column=\"5\"><primary line=\"1\" column=\"1\">"
                             + "true</primary><primary line=\"1\" column=\"6\">5"
                             + "</primary><primary line=\"1\" column=\"8\">6" + "</primary></conditional>" );
   }

   @Test
   public void testEqualityExpression() throws TokenException
   {
      assertStatement( "1",
                       "5!==6",
                       "<equality line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">!==</op><primary line=\"1\" "
                             + "column=\"5\">6</primary></equality>" );
   }

   @Test
   public void testMulExpression() throws TokenException
   {
      assertStatement( "1",
                       "5/6",
                       "<mul line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">/</op><primary line=\"1\" "
                             + "column=\"3\">6</primary></mul>" );
   }

   @Test
   public void testOrExpression() throws TokenException
   {
      assertStatement( "1",
                       "5||6",
                       "<or line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">||</op><primary line=\"1\" "
                             + "column=\"4\">6</primary></or>" );
   }

   @Test
   public void testRelationalExpression() throws TokenException
   {
      assertStatement( "1",
                       "5<=6",
                       "<relation line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">&lt;=</op><primary line=\"1\" "
                             + "column=\"4\">6</primary></relation>" );
   }

   @Test
   public void testShiftExpression() throws TokenException
   {
      assertStatement( "1",
                       "5<<6",
                       "<shift line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">&lt;&lt;</op><primary line=\"1\" "
                             + "column=\"4\">6</primary></shift>" );
   }
}
