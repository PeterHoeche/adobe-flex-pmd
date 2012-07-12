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

public class TestDoStatement extends AbstractStatementTest
{
   @Test
   public void testDo() throws TokenException
   {
      assertStatement( "1",
                       "do{ trace( i ); } while( i++ );",
                       "<do line=\"1\"><block line=\"1\">"
                             + "<call line=\"1\"><primary line=\"1\">"
                             + "trace</primary><arguments line=\"1\">"
                             + "<primary line=\"1\">i</primary></arguments>"
                             + "</call></block><condition line=\"1\">"
                             + "<post-inc line=\"1\"><primary line=\"1\">"
                             + "i</primary></post-inc></condition></do>" );
   }

   @Test
   public void testDoWithEmptyStatement() throws TokenException
   {
      assertStatement( "1",
                       "do ; while( i++ ); ",
                       "<do line=\"1\"><stmt-empty line=\"1\">;</stmt-empty>"
                             + "<condition line=\"1\"><post-inc line=\"1\">"
                             + "<primary line=\"1\">i</primary></post-inc></condition></do>" );
   }

   @Test
   public void testDoWithoutBlock() throws TokenException
   {
      assertStatement( "1",
                       "do trace( i ); while( i++ ); ",
                       "<do line=\"1\"><call line=\"1\">"
                             + "<primary line=\"1\">trace</primary><arguments line=\"1\""
                             + "><primary line=\"1\">i</primary>"
                             + "</arguments></call><condition line=\"1\">"
                             + "<post-inc line=\"1\"><primary line=\"1\""
                             + ">i</primary></post-inc></condition></do>" );
   }
}
