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
import de.bokelberg.flex.parser.AS3Scanner.Token;

public class TestAS3Scanner
      extends TestCase
{

   private AS3Scanner scn;

   public void setUp()
   {
      scn = new AS3Scanner();
   }

   public void testAssignments()
   {
      final String[] lines = new String[]
      { "=", "+=", "-=", "*=", "%=", "^=", "&=", "|=", "/=" };
      scn.setLines( lines );

      for ( int i = 0; i < lines.length; i++ )
      {
         assertText(
               ""
                     + i, lines[ i ] );
         assertText( "\n" );
      }
   }

   public void testBooleanOperators()
   {
      final String[] lines = new String[]
      { "&&", "&=", "||", "|=" };
      scn.setLines( lines );

      for ( int i = 0; i < lines.length; i++ )
      {
         assertText(
               ""
                     + i, lines[ i ] );
         assertText( "\n" );
      }
   }

   public void testComparisonOperators()
   {
      final String[] lines = new String[]
      { ">", ">>>=", ">>>", ">>=", ">>", ">=", "===", "==", "!==", "!=" };
      scn.setLines( lines );

      for ( int i = 0; i < lines.length; i++ )
      {
         assertText(
               ""
                     + i, lines[ i ] );
         assertText( "\n" );
      }
   }

   public void testIdentifiers()
   {
      final String[] lines = new String[]
      { "a", "a.b.*", "a.b::c", "a.E" };
      scn.setLines( lines );

      assertText(
            "1", lines[ 0 ] );
      assertText( "\n" );

      assertText(
            "2", "a" );
      assertText(
            "2", "." );
      assertText(
            "2", "b" );
      assertText(
            "2", "." );
      assertText(
            "2", "*" );
      assertText( "\n" );

      assertText(
            "3", "a" );
      assertText(
            "3", "." );
      assertText(
            "3", "b" );
      assertText(
            "3", "::" );
      assertText(
            "3", "c" );
      assertText( "\n" );

      assertText(
            "4", "a" );
      assertText(
            "4", "." );
      assertText(
            "4", "E" );
   }

   public void testIsDecimalChar()
   {
      final String s = "0123456789";
      for ( int i = 0; i < s.length(); i++ )
      {
         assertTrue( scn.isDecimalChar( s.charAt( 0 ) ) );
      }
      assertFalse( scn.isDecimalChar( ( char ) 0 ) );

   }

   public void testMultiLineComment()
   {
      final String[] lines = new String[]
      { "/* this is a multi line comment, not really */", "/** now for real",
            "/* now for real", "*/" };
      scn.setLines( lines );

      assertText( lines[ 0 ] );
      assertText( "\n" );
      assertText( "/** now for real\n/* now for real\n*/" );
   }

   public void testMultilineXML()
   {
      final String[] lines = new String[]
      { "<?xml version=\"1.0\"?>", "<a>", "<b>test</b>", "</a>" };
      scn.setLines( lines );
      assertText( join(
            lines, "\n" ) );
   }

   public void testMultipleWords()
   {
      final String[] lines = new String[]
      { "word1 word2 word3", "word4", "word5 word6" };
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

   public void testNumbers()
   {
      final String[] lines = new String[]
      { "0", "1.2", "1.2E5", "0xffgg" };
      scn.setLines( lines );

      assertText( lines[ 0 ] );
      assertText( "\n" );
      assertText( lines[ 1 ] );
      assertText( "\n" );
      assertText( lines[ 2 ] );
      assertText( "\n" );
      assertText( lines[ 3 ] );
   }

   public void testPlusSymbols()
   {
      final String[] lines = new String[]
      { "++", "+=", "+", "--", "-=", "-" };
      scn.setLines( lines );

      for ( int i = 0; i < lines.length; i++ )
      {
         assertText(
               ""
                     + i, lines[ i ] );
         assertText( "\n" );
      }
   }

   public void testRegExp()
   {
      final String[] lines = new String[]
      { "/a/", "/[+-.]/", "/[+-.\\/]/", "/[+-.]\\\\//" };
      scn.setLines( lines );

      assertText(
            "1", lines[ 0 ] );
      assertText( "\n" );
      assertText(
            "2", lines[ 1 ] );
      assertText( "\n" );
      assertText(
            "3", lines[ 2 ] );
      assertText( "\n" );
      assertText(
            "4", "/[+-.]\\\\/" );
   }

   public void testSingleCharacterSymbols()
   {
      final String[] lines = "{}()[]:;,?~".split( "" );
      scn.setLines( lines );

      // the first entry is empty, so we skip it
      for ( int i = 1; i < lines.length; i++ )
      {
         assertText( "\n" );
         assertText(
               ""
                     + i, lines[ i ] );
      }
   }

   public void testSingleLineComment()
   {
      final String[] lines = new String[]
      { "//this is a single line comment", "word //another single line comment" };
      scn.setLines( lines );

      assertText( lines[ 0 ] );
      assertText( "\n" );
      assertText( "word" );
      assertText( "//another single line comment" );
   }

   public void testSingleWord()
   {
      final String[] lines = new String[]
      { "word" };
      scn.setLines( lines );

      assertText( lines[ 0 ] );
   }

   public void testStrings()
   {
      final String[] lines = new String[]
      { "\"string\"", "\'string\'", "\"string\\\"\"" };
      scn.setLines( lines );

      assertText(
            "1", lines[ 0 ] );
      assertText( "\n" );
      assertText(
            "2", lines[ 1 ] );
      assertText( "\n" );
      assertText(
            "3", lines[ 2 ] );
   }

   public void testXML()
   {
      final String[] lines = new String[]
      { "<root/>", "<root>test</root>",
            "<?xml version=\"1.0\"?><root>test</root>" };
      scn.setLines( lines );
      for ( int i = 0; i < lines.length; i++ )
      {
         assertText(
               ""
                     + i, lines[ i ] );
         assertText( "\n" );
      }
   }

   private void assertText(
         final String text )
   {
      assertText(
            "", text );
   }

   private void assertText(
         final String message, final String text )
   {
      Token t = null;
      t = scn.nextToken();
      assertEquals(
            message, text, t.text );
   }

   private String join(
         final String[] lines, final String delimiter )
   {
      String result = "";
      for ( int i = 0; i < lines.length; i++ )
      {
         if ( i > 0 )
         {
            result += delimiter;
         }
         result += lines[ i ];
      }
      return result;
   }

}
