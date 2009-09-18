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

public class TestVarStatement extends AbstractStatementTest
{
   @Test
   public void testFullFeaturedVar() throws TokenException
   {
      assertStatement( "1",
                       "var a : int = 4",
                       "<var-list line=\"1\" column=\"5\">"
                             + "<name-type-init line=\"1\" column=\"5\">"
                             + "<name line=\"1\" column=\"5\">a</name><type line=\"1\" column=\"9\">int</type>"
                             + "<init line=\"1\" column=\"15\"><primary line=\"1\" column=\"15\">4</primary>"
                             + "</init></name-type-init></var-list>" );

      assertStatement( "1",
                       "var a : int = 4, b : int = 2;",
                       "<var-list line=\"1\" column=\"5\"><name-type-init line=\"1\" column=\"5\">"
                             + "<name line=\"1\" column=\"5\">a</name><type line=\"1\" column=\"9\">int</type>"
                             + "<init line=\"1\" column=\"15\"><primary line=\"1\" column=\"15\">4</primary></init>"
                             + "</name-type-init><name-type-init line=\"1\" column=\"18\">"
                             + "<name line=\"1\" column=\"18\">b</name><type line=\"1\" column=\"22\">int</type>"
                             + "<init line=\"1\" column=\"28\"><primary line=\"1\" column=\"28\">2</primary></init>"
                             + "</name-type-init></var-list>" );

      assertStatement( "with array",
                       "var colors:Array = [0x2bc9f6, 0x0086ad];",
                       "<var-list line=\"1\" column=\"5\">"
                             + "<name-type-init line=\"1\" column=\"5\">"
                             + "<name line=\"1\" column=\"5\">colors</name><type line=\"1\" column=\"12\">Array</type>"
                             + "<init line=\"1\" column=\"20\">"
                             + "<primary line=\"1\" column=\"20\"><array line=\"1\" column=\"20\">"
                             + "<primary line=\"1\" column=\"21\">0x2bc9f6</primary>"
                             + "<primary line=\"1\" column=\"31\">0x0086ad</primary>"
                             + "</array></primary></init></name-type-init></var-list>" );
   }

   @Test
   public void testInitializedVar() throws TokenException
   {
      assertStatement( "1",
                       "var a = 4",
                       "<var-list line=\"1\" column=\"5\"><name-type-init line=\"1\" column=\"5\">"
                             + "<name line=\"1\" column=\"5\">a</name><type line=\"1\" column=\"7\">"
                             + "</type><init line=\"1\" column=\"9\"><primary line=\"1\" column=\"9\">4</primary>"
                             + "</init></name-type-init></var-list>" );
   }

   @Test
   public void testSimpleVar() throws TokenException
   {
      assertStatement( "1",
                       "var a",
                       "<var-list line=\"1\" column=\"5\"><name-type-init line=\"1\" column=\"5\">"
                             + "<name line=\"1\" column=\"5\">a</name><type line=\"2\" column=\"1\">"
                             + "</type></name-type-init></var-list>" );
   }

   @Test
   public void testTypedVar() throws TokenException
   {
      assertStatement( "1",
                       "var a : Object",
                       "<var-list line=\"1\" column=\"5\"><name-type-init line=\"1\" column=\"5\">"
                             + "<name line=\"1\" column=\"5\">a</name><type line=\"1\" column=\"9\">Object</type>"
                             + "</name-type-init></var-list>" );
   }
}
