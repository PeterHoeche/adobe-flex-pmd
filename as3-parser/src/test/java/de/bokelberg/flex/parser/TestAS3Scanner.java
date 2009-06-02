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

import de.bokelberg.flex.parser.AS3Scanner.Token;

public class TestAS3Scanner extends TestCase
{
   private AS3Scanner scn;

   @Override
   @Before
   public void setUp()
   {
      scn = new AS3Scanner();
   }

   @Test
   public void testAssignments()
   {
      final String[] lines = new String[]
      { "=",
                  "+=",
                  "-=",
                  "%=",
                  "^=",
                  "&=",
                  "|=",
                  "/=" };
      scn.setLines( lines );

      for ( int i = 0; i < lines.length; i++ )
      {
         assertText( Integer.toString( i ),
                     lines[ i ] );
         assertText( "\n" );
      }
   }

   @Test
   public void testBooleanOperators()
   {
      final String[] lines = new String[]
      { "&&",
                  "&=",
                  "||",
                  "|=" };
      scn.setLines( lines );

      for ( int i = 0; i < lines.length; i++ )
      {
         assertText( Integer.toString( i ),
                     lines[ i ] );
         assertText( "\n" );
      }
   }

   @Test
   public void testComparisonOperators()
   {
      final String[] lines = new String[]
      { ">",
                  ">>>=",
                  ">>>",
                  ">>=",
                  ">>",
                  ">=",
                  "===",
                  "==",
                  "!==",
                  "!=" };
      scn.setLines( lines );

      for ( int i = 0; i < lines.length; i++ )
      {
         assertText( Integer.toString( i ),
                     lines[ i ] );
         assertText( "\n" );
      }
   }

   @Test
   public void testIdentifiers()
   {
      final String[] lines = new String[]
      { "a",
                  "a.b.*",
                  "a.b::c",
                  "a.E" };
      scn.setLines( lines );

      assertText( "1",
                  lines[ 0 ] );
      assertText( "\n" );

      assertText( "2",
                  "a" );
      assertText( "2",
                  "." );
      assertText( "2",
                  "b" );
      assertText( "2",
                  "." );
      assertText( "2",
                  "*" );
      assertText( "\n" );

      assertText( "3",
                  "a" );
      assertText( "3",
                  "." );
      assertText( "3",
                  "b" );
      assertText( "3",
                  "::" );
      assertText( "3",
                  "c" );
      assertText( "\n" );

      assertText( "4",
                  "a" );
      assertText( "4",
                  "." );
      assertText( "4",
                  "E" );
   }

   @Test
   public void testIsDecimalChar()
   {
      final String decimalString = "0123456789";
      for ( int i = 0; i < decimalString.length(); i++ )
      {
         assertTrue( "",
                     scn.isDecimalChar( decimalString.charAt( i ) ) );
      }
      assertFalse( "",
                   scn.isDecimalChar( ( char ) 0 ) );

   }

   @Test
   public void testIsHex()
   {
      assertTrue( "",
                  scn.isHexChar( '0' ) );
      assertTrue( "",
                  scn.isHexChar( '9' ) );
      assertTrue( "",
                  scn.isHexChar( 'A' ) );
      assertTrue( "",
                  scn.isHexChar( 'a' ) );
      assertTrue( "",
                  scn.isHexChar( 'F' ) );
      assertTrue( "",
                  scn.isHexChar( 'f' ) );
      assertFalse( "",
                   scn.isHexChar( ';' ) );
      assertFalse( "",
                   scn.isHexChar( ']' ) );
      assertFalse( "",
                   scn.isHexChar( ' ' ) );
   }

   @Test
   public void testMultiLineComment()
   {
      final String[] lines = new String[]
      { "/* this is a multi line comment, not really */",
                  "/** now for real",
                  "/* now for real",
                  "*/" };
      scn.setLines( lines );

      assertText( lines[ 0 ] );
      assertText( "\n" );
      assertText( "/** now for real\n/* now for real\n*/" );
   }

   @Test
   public void testMultilineXML()
   {
      final String[] lines = new String[]
      { "<?xml version=\"1.0\"?>",
                  "<a>",
                  "<b>test</b>",
                  "</a>" };
      scn.setLines( lines );
      assertText( join( lines,
                        "\n" ) );
   }

   @Test
   public void testMultipleWords()
   {
      final String[] lines = new String[]
      { "word1 word2 word3",
                  "word4",
                  "word5 word6" };
      scn.setLines( lines );

      assertText( "word1" );
      assertText( "word2" );
      assertText( "word3" );
      assertText( "\n" );
      assertText( "word4" );
      assertText( "\n" );
      assertText( "word5" );
      assertText( "word6" );
   }

   @Test
   public void testNumbers()
   {
      final String[] lines = new String[]
      { "0",
                  "1.2",
                  "1.2E5",
                  "0xffgg" };
      scn.setLines( lines );

      assertText( lines[ 0 ] );
      assertText( "\n" );
      assertText( lines[ 1 ] );
      assertText( "\n" );
      assertText( lines[ 2 ] );
      assertText( "\n" );
      assertText( lines[ 3 ] );
   }

   @Test
   public void testPlusSymbols()
   {
      final String[] lines = new String[]
      { "++",
                  "+=",
                  "+",
                  "--",
                  "-=",
                  "-" };
      scn.setLines( lines );

      for ( int i = 0; i < lines.length; i++ )
      {
         assertText( Integer.toString( i ),
                     lines[ i ] );
         assertText( "\n" );
      }
   }

   @Test
   public void testRegExp()
   {
      final String[] lines = new String[]
      { "/a/",
                  "/[+-.]/",
                  "/[+-.\\/]/",
                  "/[+-.]\\\\//" };
      scn.setLines( lines );

      assertText( "1",
                  lines[ 0 ] );
      assertText( "\n" );
      assertText( "2",
                  lines[ 1 ] );
      assertText( "\n" );
      assertText( "3",
                  lines[ 2 ] );
      assertText( "\n" );
      assertText( "4",
                  "/[+-.]\\\\/" );
   }

   @Test
   public void testSingleCharacterSymbols()
   {
      final String[] lines = "{}()[]:;,?~".split( "" );
      scn.setLines( lines );

      // the first entry is empty, so we skip it
      for ( int i = 1; i < lines.length; i++ )
      {
         assertText( "\n" );
         assertText( Integer.toString( i ),
                     lines[ i ] );
      }
   }

   @Test
   public void testSingleLineComment()
   {
      final String[] lines = new String[]
      { "//this is a single line comment",
                  "word //another single line comment" };
      scn.setLines( lines );

      assertText( lines[ 0 ] );
      assertText( "\n" );
      assertText( "word" );
      assertText( "//another single line comment" );
   }

   @Test
   public void testSingleWord()
   {
      final String[] lines = new String[]
      { "word" };
      scn.setLines( lines );

      assertText( lines[ 0 ] );
   }

   @Test
   public void testStrings()
   {
      final String[] lines = new String[]
      { "\"string\"",
                  "\'string\'",
                  "\"string\\\"\"" };
      scn.setLines( lines );

      assertText( "1",
                  lines[ 0 ] );
      assertText( "\n" );
      assertText( "2",
                  lines[ 1 ] );
      assertText( "\n" );
      assertText( "3",
                  lines[ 2 ] );
   }

   @Test
   public void testXML()
   {
      final String[] lines = new String[]
      { "<root/>",
                  "<root>test</root>",
                  "<?xml version=\"1.0\"?><root>test</root>" };
      scn.setLines( lines );
      for ( int i = 0; i < lines.length; i++ )
      {
         assertText( Integer.toString( i ),
                     lines[ i ] );
         assertText( "\n" );
      }
   }

   private void assertText( final String text )
   {
      assertText( "",
                  text );
   }

   private void assertText( final String message,
                            final String text )
   {
      Token tokent = null;
      tokent = scn.nextToken();
      assertEquals( message,
                    text,
                    tokent.text );
   }

   private String join( final String[] lines,
                        final String delimiter )
   {
      final StringBuffer result = new StringBuffer();
      for ( int i = 0; i < lines.length; i++ )
      {
         if ( i > 0 )
         {
            result.append( delimiter );
         }
         result.append( lines[ i ] );
      }
      return result.toString();
   }

}
