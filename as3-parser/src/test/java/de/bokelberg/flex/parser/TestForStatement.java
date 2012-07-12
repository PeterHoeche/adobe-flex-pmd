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

public class TestForStatement extends AbstractStatementTest
{
   @Test
   public void testSimpleFor() throws TokenException
   {
      assertStatement( "1",
                       "for( var i : int = 0; i < length; i++ ){ trace( i ); }",
                       "<for line=\"1\"><init line=\"1\">"
                             + "<var-list line=\"1\"><name-type-init line=\"1\""
                             + "><name line=\"1\">i</name><type line=\"1\">int</type><init line=\"1\">"
                             + "<primary line=\"1\">0</primary></init>"
                             + "</name-type-init></var-list></init>"
                             + "<cond line=\"1\"><relation line=\"1\">"
                             + "<primary line=\"1\">i</primary><op line=\"1\""
                             + ">&lt;</op><primary line=\"1\">length"
                             + "</primary></relation></cond><iter line=\"1\">"
                             + "<post-inc line=\"1\"><primary line=\"1\">i"
                             + "</primary></post-inc></iter><block line=\"1\"><call line=\"1\""
                             + "><primary line=\"1\">trace"
                             + "</primary><arguments line=\"1\"><primary line=\"1\">i"
                             + "</primary></arguments></call></block></for>" );

      assertStatement( "",
                       "        for (i = 0; i < n; i++)",
                       "<for line=\"1\"><init line=\"1\">"
                             + "<assign line=\"1\"><primary line=\"1\">i</primary><op line=\"1\">=</op>"
                             + "<primary line=\"1\">0</primary></assign></init>"
                             + "<cond line=\"1\"><relation line=\"1\"><primary line=\"1\">i</primary>"
                             + "<op line=\"1\">&lt;</op><primary line=\"1\">n"
                             + "</primary></relation></cond><iter line=\"1\">"
                             + "<post-inc line=\"1\"><primary line=\"1\">i"
                             + "</primary></post-inc></iter><primary line=\"2\">__END__</primary></for>" );
   }

   @Test
   public void testSimpleForEach() throws TokenException
   {
      assertStatement( "1",
                       "for each( var obj : Object in list ){ obj.print( i ); }",
                       "<foreach line=\"1\"><var line=\"1\">"
                             + "<name-type-init line=\"1\"><name line=\"1\""
                             + ">obj</name><type line=\"1\">Object"
                             + "</type></name-type-init></var><in line=\"1\">"
                             + "<primary line=\"1\">list</primary></in>"
                             + "<block line=\"1\"><dot line=\"1\">"
                             + "<primary line=\"1\">obj</primary><call line=\"1\""
                             + "><primary line=\"1\">print</primary>"
                             + "<arguments line=\"1\"><primary line=\"1\">"
                             + "i</primary></arguments></call></dot></block></foreach>" );

      assertStatement( "1",
                       "for each( obj in list ){}",
                       "<foreach line=\"1\"><name line=\"1\">obj</name>"
                             + "<in line=\"1\"><primary line=\"1\">list</primary>"
                             + "</in><block line=\"1\"></block></foreach>" );

      // assertStatement(
      // "", "for each (var a:XML in classInfo..accessor) {}", "" );
   }

   @Test
   public void testSimpleForIn() throws TokenException
   {
      assertStatement( "1",
                       "for( var s : String in obj ){ trace( s, obj[ s ]); }",
                       "<forin line=\"1\"><init line=\"1\">"
                             + "<var-list line=\"1\"><name-type-init line=\"1\""
                             + "><name line=\"1\">s</name>"
                             + "<type line=\"1\">String</type></name-type-init>"
                             + "</var-list></init><in line=\"1\"><primary line=\"1\""
                             + ">obj</primary></in></forin>" );

      assertStatement( "for in",
                       "            for (p in events);",
                       "<forin line=\"1\"><init line=\"1\">"
                             + "<primary line=\"1\">p</primary></init><in line=\"1\""
                             + "><primary line=\"1\">events</primary></in></forin>" );
   }
}
