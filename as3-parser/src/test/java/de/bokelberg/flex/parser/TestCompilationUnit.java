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

public class TestCompilationUnit
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
   public void testEmptyPackage() throws TokenException
   {
      assertCompilationUnit(
            "1",
            "package a { } ",
            "<compilation-unit line=\"-1\" column=\"-1\"><package line=\"1\" column=\"9\"><name line=\"1\" column=\"11\">a</name><content line=\"1\" column=\"13\"></content></package><content line=\"2\" column=\"1\"></content></compilation-unit>" );
   }

   @Test
   public void testEmptyPackagePlusLocalClass() throws TokenException
   {
      assertCompilationUnit(
            "1",
            "package a { } class Local { }",
            "<compilation-unit line=\"-1\" column=\"-1\"><package line=\"1\" column=\"9\"><name line=\"1\" column=\"11\">a</name><content line=\"1\" column=\"13\"></content></package><content line=\"1\" column=\"15\"><class line=\"1\" column=\"21\"><name line=\"1\" column=\"21\">Local</name><mod-list line=\"1\" column=\"27\"></mod-list><content line=\"1\" column=\"29\"></content></class></content></compilation-unit>" );
   }

   @Test
   public void testPackageWithClass() throws TokenException
   {
      assertCompilationUnit(
            "1",
            "package a { public class B { } } ",
            "<compilation-unit line=\"-1\" column=\"-1\"><package line=\"1\" column=\"9\"><name line=\"1\" column=\"11\">a</name><content line=\"1\" column=\"13\"><class line=\"1\" column=\"26\"><name line=\"1\" column=\"26\">B</name><mod-list line=\"1\" column=\"28\"><mod line=\"1\" column=\"28\">public</mod></mod-list><content line=\"1\" column=\"30\"></content></class></content></package><content line=\"2\" column=\"1\"></content></compilation-unit>" );
   }

   @Test
   public void testPackageWithInterface() throws TokenException
   {
      assertCompilationUnit(
            "1",
            "package a { public interface B { } } ",
            "<compilation-unit line=\"-1\" column=\"-1\"><package line=\"1\" column=\"9\"><name line=\"1\" column=\"11\">a</name><content line=\"1\" column=\"13\"><interface line=\"1\" column=\"30\"><name line=\"1\" column=\"30\">B</name><mod-list line=\"1\" column=\"32\"><mod line=\"1\" column=\"32\">public</mod></mod-list><content line=\"1\" column=\"34\"></content></interface></content></package><content line=\"2\" column=\"1\"></content></compilation-unit>" );
   }

   private void assertCompilationUnit(
         final String message, final String input, final String expected ) throws TokenException
   {
      scn.setLines( new String[]
      { input, "__END__" } );
      final String result = new ASTToXMLConverter().convert( asp
            .parseCompilationUnit() );
      assertEquals(
            message, expected, result );
   }
}
