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

public class TestIfStatement extends AbstractStatementTest
{
   @Test
   public void testBug232() throws TokenException
   {
      assertStatement( "",
                       "if (true || /* comment */!false) ) )",
                       "<if line=\"1\"><condition line=\"1\"><or line=\"1\"><primary line=\"1\">"
                             + "true</primary><op line=\"1\">||</op><not line=\"1\"><primary "
                             + "line=\"1\">false</primary></not></or></condition><primary "
                             + "line=\"1\">)</primary></if>" );
   }

   @Test
   public void testIf() throws TokenException
   {
      assertStatement( "1",
                       "if( true ){ trace( true ); }",
                       "<if line=\"1\">"
                             + "<condition line=\"1\">" + "<primary line=\"1\">true</primary></condition>"
                             + "<block line=\"1\"><call line=\"1\">" + "<primary line=\"1\">trace"
                             + "</primary><arguments line=\"1\">" + "<primary line=\"1\">true</primary>"
                             + "</arguments></call></block></if>" );

      assertStatement( "1",
                       "if( \"i\" in oaderContext ){ }",
                       "<if line=\"1\"><condition line=\"1\">"
                             + "<relation line=\"1\"><primary line=\"1\">"
                             + "\"i\"</primary><op line=\"1\">in</op>"
                             + "<primary line=\"1\">oaderContext</primary>"
                             + "</relation></condition><block line=\"1\"></block></if>" );

      assertStatement( "internal",
                       "if (col.mx_internal::contentSize) {col.mx_internal::_width = NaN;}",
                       "<if line=\"1\"><condition line=\"1\">"
                             + "<dot line=\"1\"><primary line=\"1\">col"
                             + "</primary><dot line=\"1\"><primary line=\"1\">"
                             + "mx_internal</primary><primary line=\"1\">contentSize"
                             + "</primary></dot></dot></condition><block line=\"1\">"
                             + "<dot line=\"1\"><primary line=\"1\">col"
                             + "</primary><dot line=\"1\"><primary line=\"1\">"
                             + "mx_internal</primary><assign line=\"1\">"
                             + "<primary line=\"1\">_width</primary>"
                             + "<op line=\"1\">=</op><primary line=\"1\">"
                             + "NaN</primary></assign></dot></dot></block></if>" );
   }

   @Test
   public void testIfElse() throws TokenException
   {
      assertStatement( "1",
                       "if( true ){ trace( true ); } else { trace( false )}",
                       "<if line=\"1\"><condition line=\"1\">"
                             + "<primary line=\"1\">true" + "</primary></condition><block line=\"1\">"
                             + "<call line=\"1\"><primary line=\"1\">trace"
                             + "</primary><arguments line=\"1\">"
                             + "<primary line=\"1\">true</primary></arguments>"
                             + "</call></block><block line=\"1\">" + "<call line=\"1\">"
                             + "<primary line=\"1\">trace</primary>" + "<arguments line=\"1\">"
                             + "<primary line=\"1\">false</primary>" + "</arguments></call></block></if>" );
   }

   @Test
   public void testIfWithArrayAccessor() throws TokenException
   {
      assertStatement( "",
                       "if ( chart.getItemAt( 0 )[ xField ] > targetXFieldValue ){}",
                       "<if line=\"1\"><condition line=\"1\"><dot line=\"1\""
                             + "><primary line=\"1\">chart</primary><relation line=\"1\""
                             + "><call line=\"1\"><primary line=\"1\""
                             + ">getItemAt</primary><arguments line=\"1\"" + "><primary line=\"1\""
                             + ">0</primary></arguments><array line=\"1\"" + "><primary line=\"1\""
                             + ">xField</primary></array></call><op line=\"1\"" + ">&gt;</op><primary "
                             + "line=\"1\">targetXFieldValue</primary>"
                             + "</relation></dot></condition><block line=\"1\"" + "></block></if>" );
   }

   @Test
   public void testIfWithEmptyStatement() throws TokenException
   {
      assertStatement( "1",
                       "if( i++ ); ",
                       "<if line=\"1\"><condition line=\"1\">"
                             + "<post-inc line=\"1\"><primary line=\"1\">"
                             + "i</primary></post-inc></condition><stmt-empty line=\"1\">;"
                             + "</stmt-empty></if>" );
   }

   @Test
   public void testIfWithoutBlock() throws TokenException
   {
      assertStatement( "1",
                       "if( i++ ) trace( i ); ",
                       "<if line=\"1\"><condition line=\"1\">"
                             + "<post-inc line=\"1\"><primary line=\"1\">i"
                             + "</primary></post-inc></condition><call line=\"1\">"
                             + "<primary line=\"1\">trace</primary><arguments line=\"1\""
                             + "><primary line=\"1\">i</primary>" + "</arguments></call></if>" );
   }

   @Test
   public void testIfWithReturn() throws TokenException
   {
      assertStatement( "",
                       "if ( true )return;",
                       "<if line=\"1\"><condition line=\"1\"><primary line=\"1\""
                             + ">true</primary></condition><return line=\"2\"" + "></return></if>" );

      assertStatement( "",
                       "if ( true )throw new Error();",
                       "<if line=\"1\"><condition line=\"1\"><primary line=\"1\""
                             + ">true</primary></condition><primary line=\"1\">" + "throw</primary></if>" );
   }
}
