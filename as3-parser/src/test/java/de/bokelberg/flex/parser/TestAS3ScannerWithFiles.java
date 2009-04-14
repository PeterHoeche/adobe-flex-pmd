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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import junit.framework.TestCase;
import de.bokelberg.flex.parser.AS3Scanner.Token;
import de.bokelberg.util.FileUtil;

public class TestAS3ScannerWithFiles
      extends TestCase
{

   private AS3Scanner scn;

   @Override
   public void setUp()
   {
      scn = new AS3Scanner();
   }

   public void testSimple() throws IOException, URISyntaxException
   {
      final String[] expected = new String[]
      { "package", "simple", "{", "public", "class", "Simple", "{", "public",
            "function", "Simple", "(", ")", "{", "trace", "(", "\"Simple\"",
            ")", ";", "}", "}" };
      assertFile(
            expected, "Simple.as" );
   }

   private void assertFile(
         final String[] expected, final String fileName ) throws IOException,
         URISyntaxException
   {
      final String[] lines = FileUtil.readStrings( new File( getClass().getResource(
            "/examples/unformatted/" ).toURI().getPath()
            + fileName ) );
      assertLines(
            expected, lines );
   }

   private void assertLines(
         final String[] expected, final String[] lines )
   {
      scn.setLines( lines );
      for ( int i = 0; i < expected.length; i++ )
      {
         assertText(
               ""
                     + i, expected[ i ] );
      }
   }

   private void assertText(
         final String message, final String text )
   {
      Token t = null;
      t = scn.nextToken();
      assertEquals(
            message, text, t.text );
   }

}
