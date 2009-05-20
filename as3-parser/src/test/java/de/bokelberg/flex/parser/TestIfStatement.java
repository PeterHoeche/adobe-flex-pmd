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

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;


public class TestIfStatement extends TestCase
{
   private AS3Parser  asp;
   private AS3Scanner scn;

   @Override
   @Before
   public void setUp()
   {
      asp = new AS3Parser();
      scn = new AS3Scanner();
      asp.scn = scn;
   }

   @Test
   public void testIf() throws TokenException
   {
      assertStatement( "1",
                       "if( true ){ trace( true ); }",
                       "<if line=\"1\" column=\"3\"><condition line=\"1\" column=\"5\"><primary line=\"1\" column=\"5\">true</primary></condition><block line=\"1\" column=\"13\"><call line=\"1\" column=\"18\"><primary line=\"1\" column=\"13\">trace</primary><arguments line=\"1\" column=\"20\"><primary line=\"1\" column=\"20\">true</primary></arguments></call></block></if>" );
   }

   @Test
   public void testIfElse() throws TokenException
   {
      assertStatement( "1",
                       "if( true ){ trace( true ); } else { trace( false )}",
                       "<if line=\"1\" column=\"3\"><condition line=\"1\" column=\"5\"><primary line=\"1\" column=\"5\">true</primary></condition><block line=\"1\" column=\"13\"><call line=\"1\" column=\"18\"><primary line=\"1\" column=\"13\">trace</primary><arguments line=\"1\" column=\"20\"><primary line=\"1\" column=\"20\">true</primary></arguments></call></block><block line=\"1\" column=\"37\"><call line=\"1\" column=\"42\"><primary line=\"1\" column=\"37\">trace</primary><arguments line=\"1\" column=\"44\"><primary line=\"1\" column=\"44\">false</primary></arguments></call></block></if>" );
   }

   @Test
   public void testIfWithArrayAccessor() throws TokenException
   {
      assertStatement( "",
                       "if ( chart.getItemAt( 0 )[ xField ] > targetXFieldValue ){}",
                       "<if line=\"1\" column=\"4\"><condition line=\"1\" column=\"6\"><dot line=\"1\""
                             + " column=\"12\"><primary line=\"1\" "
                             + "column=\"6\">chart</primary><relation line=\"1\" "
                             + "column=\"12\"><call line=\"1\" column=\"21\"><primary line=\"1\" "
                             + "column=\"12\">getItemAt</primary><arguments line=\"1\" column=\"23\"><primary line=\"1\" "
                             + "column=\"23\">0</primary></arguments><array line=\"1\" column=\"26\"><primary line=\"1\" "
                             + "column=\"28\">xField</primary></array></call><op line=\"1\" column=\"37\">&gt;</op><primary "
                             + "line=\"1\" column=\"39\">targetXFieldValue</primary>"
                             + "</relation></dot></condition><block line=\"1\" "
                             + "column=\"59\"></block></if>" );
   }

   @Test
   public void testIfWithEmptyStatement() throws TokenException
   {
      assertStatement( "1",
                       "if( i++ ); ",
                       "<if line=\"1\" column=\"3\"><condition line=\"1\" column=\"5\"><post-inc line=\"1\" column=\"9\"><primary line=\"1\" column=\"5\">i</primary></post-inc></condition><stmt-empty line=\"1\" column=\"10\">;</stmt-empty></if>" );
   }

   @Test
   public void testIfWithoutBlock() throws TokenException
   {
      assertStatement( "1",
                       "if( i++ ) trace( i ); ",
                       "<if line=\"1\" column=\"3\"><condition line=\"1\" column=\"5\"><post-inc line=\"1\" column=\"9\"><primary line=\"1\" column=\"5\">i</primary></post-inc></condition><call line=\"1\" column=\"16\"><primary line=\"1\" column=\"11\">trace</primary><arguments line=\"1\" column=\"18\"><primary line=\"1\" column=\"18\">i</primary></arguments></call></if>" );
   }

   // @Test
   // public void testWithE4x() throws TokenException
   // {
   // assertStatement(
   // "",
   // "if(xml.factory.constructor.parameter.(@index==\"1\" && @optional==\"false\").length() !=0){}",
   // "" );
   // }

   private void assertStatement( final String message,
                                 final String input,
                                 final String expected ) throws TokenException
   {
      scn.setLines( new String[]
      { input,
                  "__END__" } );
      asp.nextToken();
      final String result = new ASTToXMLConverter().convert( asp.parseStatement() );
      assertEquals( message,
                    expected,
                    result );
   }

}
