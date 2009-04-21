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

import de.bokelberg.flex.parser.exceptions.TokenException;

public class TestTryCatchFinallyStatement
      extends TestCase
{
   private AS3Parser asp;
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
   public void testCatch() throws TokenException
   {
      assertStatement(
            "1",
            "catch( e : Error ) {trace( true ); }",
            "<catch line=\"1\" column=\"8\"><name line=\"1\" column=\"8\">e</name><type line=\"1\" column=\"12\">Error</type><block line=\"1\" column=\"21\"><call line=\"1\" column=\"26\"><primary line=\"1\" column=\"21\">trace</primary><arguments line=\"1\" column=\"28\"><primary line=\"1\" column=\"28\">true</primary></arguments></call></block></catch>" );
   }

   @Test
   public void testFinally() throws TokenException
   {
      assertStatement(
            "1",
            "finally {trace( true ); }",
            "<finally line=\"1\" column=\"9\"><block line=\"1\" column=\"10\"><call line=\"1\" column=\"15\"><primary line=\"1\" column=\"10\">trace</primary><arguments line=\"1\" column=\"17\"><primary line=\"1\" column=\"17\">true</primary></arguments></call></block></finally>" );
   }

   @Test
   public void testTry() throws TokenException
   {
      assertStatement(
            "1",
            "try {trace( true ); }",
            "<try line=\"1\" column=\"5\"><block line=\"1\" column=\"6\"><call line=\"1\" column=\"11\"><primary line=\"1\" column=\"6\">trace</primary><arguments line=\"1\" column=\"13\"><primary line=\"1\" column=\"13\">true</primary></arguments></call></block></try>" );
   }

   private void assertStatement(
         final String message, final String input, final String expected ) throws TokenException
   {
      scn.setLines( new String[]
      { input, "__END__" } );
      asp.nextToken();
      final String result = new ASTToXMLConverter().convert( asp.parseStatement() );
      assertEquals(
            message, expected, result );
   }
}
