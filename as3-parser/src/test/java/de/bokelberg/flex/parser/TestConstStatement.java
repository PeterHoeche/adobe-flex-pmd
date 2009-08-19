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

public class TestConstStatement extends AbstractStatementTest
{
   @Test
   public void testFullFeaturedConst() throws TokenException
   {
      assertStatement( "1",
                       "const a : int = 4",
                       "<const-list line=\"1\" column=\"7\">"
                             + "<name-type-init line=\"1\" column=\"7\">"
                             + "<name line=\"1\" column=\"7\">a</name><type line=\"1\" column=\"9\">int</type>"
                             + "<init line=\"1\" column=\"17\"><primary line=\"1\" column=\"17\">4</primary>"
                             + "</init></name-type-init></const-list>" );
   }

   @Test
   public void testInitializedConst() throws TokenException
   {
      assertStatement( "1",
                       "const a = 4",
                       "<const-list line=\"1\" column=\"7\"><name-type-init line=\"1\" column=\"7\">"
                             + "<name line=\"1\" column=\"7\">a</name><type line=\"1\" column=\"9\">"
                             + "</type><init line=\"1\" column=\"11\"><primary line=\"1\" column=\"11\">4"
                             + "</primary></init></name-type-init></const-list>" );
   }

   @Test
   public void testSimpleConst() throws TokenException
   {
      assertStatement( "1",
                       "const a",
                       "<const-list line=\"1\" column=\"7\"><name-type-init line=\"1\" column=\"7\">"
                             + "<name line=\"1\" column=\"7\">a</name><type line=\"2\" column=\"1\">"
                             + "</type></name-type-init></const-list>" );
   }

   @Test
   public void testTypedConst() throws TokenException
   {
      assertStatement( "1",
                       "const a : Object",
                       "<const-list line=\"1\" column=\"7\"><name-type-init line=\"1\" column=\"7\">"
                             + "<name line=\"1\" column=\"7\">a</name><type line=\"1\" column=\"9\">Object</type>"
                             + "</name-type-init></const-list>" );
   }
}
