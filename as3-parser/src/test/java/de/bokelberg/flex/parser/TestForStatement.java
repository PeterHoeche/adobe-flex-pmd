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
                       "<for line=\"1\" column=\"6\"><init line=\"1\" column=\"6\">"
                             + "<var-list line=\"1\" column=\"10\"><name-type-init line=\"1\" "
                             + "column=\"10\"><name line=\"1\" column=\"10\">i</name><type line=\"1\" "
                             + "column=\"12\">int</type><init line=\"1\" column=\"20\">"
                             + "<primary line=\"1\" column=\"20\">0</primary></init>"
                             + "</name-type-init></var-list></init>"
                             + "<cond line=\"1\" column=\"23\"><relation line=\"1\" column=\"23\">"
                             + "<primary line=\"1\" column=\"23\">i</primary><op line=\"1\" "
                             + "column=\"25\">&lt;</op><primary line=\"1\" column=\"27\">length"
                             + "</primary></relation></cond><iter line=\"1\" column=\"35\">"
                             + "<post-inc line=\"1\" column=\"39\"><primary line=\"1\" column=\"35\">i"
                             + "</primary></post-inc></iter><block line=\"1\" column=\"42\"><call line=\"1\" "
                             + "column=\"47\"><primary line=\"1\" column=\"42\">trace"
                             + "</primary><arguments line=\"1\" column=\"49\"><primary line=\"1\" column=\"49\">i"
                             + "</primary></arguments></call></block></for>" );

      assertStatement( "",
                       "        for (i = 0; i < n; i++)",
                       "<for line=\"1\" column=\"14\"><init line=\"1\" column=\"14\">"
                             + "<assign line=\"1\" column=\"14\"><primary line=\"1\" column=\"14\">"
                             + "i</primary><op line=\"1\" column=\"16\">=</op>"
                             + "<primary line=\"1\" column=\"18\">0</primary></assign></init>"
                             + "<cond line=\"1\" column=\"21\"><relation line=\"1\" column=\"21\">"
                             + "<primary line=\"1\" column=\"21\">i</primary>"
                             + "<op line=\"1\" column=\"23\">&lt;</op><primary line=\"1\" column=\"25\">n"
                             + "</primary></relation></cond><iter line=\"1\" column=\"28\">"
                             + "<post-inc line=\"1\" column=\"31\"><primary line=\"1\" column=\"28\">i"
                             + "</primary></post-inc></iter><primary line=\"2\" column=\"1\">__END__</primary></for>" );
   }

   @Test
   public void testSimpleForEach() throws TokenException
   {
      assertStatement( "1",
                       "for each( var obj : Object in list ){ obj.print( i ); }",
                       "<foreach line=\"1\" column=\"11\"><var line=\"1\" column=\"11\">"
                             + "<name-type-init line=\"1\" column=\"15\"><name line=\"1\" "
                             + "column=\"15\">obj</name><type line=\"1\" column=\"19\">Object"
                             + "</type></name-type-init></var><in line=\"1\" column=\"31\">"
                             + "<primary line=\"1\" column=\"31\">list</primary></in>"
                             + "<block line=\"1\" column=\"39\"><dot line=\"1\" column=\"43\">"
                             + "<primary line=\"1\" column=\"39\">obj</primary><call line=\"1\" "
                             + "column=\"48\"><primary line=\"1\" column=\"43\">print</primary>"
                             + "<arguments line=\"1\" column=\"50\"><primary line=\"1\" column=\"50\">"
                             + "i</primary></arguments></call></dot></block></foreach>" );

      // assertStatement(
      // "", "for each (var a:XML in classInfo..accessor) {}", "" );
   }

   @Test
   public void testSimpleForIn() throws TokenException
   {
      assertStatement( "1",
                       "for( var s : String in obj ){ trace( s, obj[ s ]); }",
                       "<forin line=\"1\" column=\"6\"><init line=\"1\" column=\"6\">"
                             + "<var-list line=\"1\" column=\"10\"><name-type-init line=\"1\" "
                             + "column=\"10\"><name line=\"1\" column=\"10\">s</name>"
                             + "<type line=\"1\" column=\"12\">String</type></name-type-init>"
                             + "</var-list></init><in line=\"1\" column=\"24\"><primary line=\"1\" "
                             + "column=\"24\">obj</primary></in></forin>" );

      assertStatement( "for in",
                       "            for (p in events);",
                       "<forin line=\"1\" column=\"18\"><init line=\"1\" column=\"18\">"
                             + "<primary line=\"1\" column=\"18\">p</primary></init><in line=\"1\" "
                             + "column=\"23\"><primary line=\"1\" column=\"23\">events</primary></in></forin>" );
   }
}
