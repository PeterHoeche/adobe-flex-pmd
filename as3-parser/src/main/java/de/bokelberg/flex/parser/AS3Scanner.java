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

import java.io.StringReader;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

/**
 * convert a actionscript to a stream of tokens
 *
 * @author rbokel
 */
public class AS3Scanner
{

   static public class Token
   {
      public int column;
      public boolean isNum;
      public int line;
      public String text;

      public Token(
            final String text, final int line, final int column )
      {
         this.text = text;
         this.line = line + 1;
         this.column = column + 1;
      }
   }

   static public class XMLVerifier
         extends DefaultHandler
   {

      public boolean verify(
            final String text )
      {

         // Use the default (non-validating) parser
         final SAXParserFactory factory = SAXParserFactory.newInstance();
         factory.setNamespaceAware( false );

         // Parse the input
         SAXParser saxParser;
         try
         {
            saxParser = factory.newSAXParser();
            saxParser.parse(
                  new InputSource( new StringReader( text ) ), this );
            return true;
         }
         catch ( final Throwable e )
         {
            e.printStackTrace();
         }
         return false;
      }

   }
   private int column;
   private int line;

   private String[] lines = null;

   public boolean isDecimalChar(
         final char c )
   {
      return c >= '0'
            && c <= '9';
   }

   public Token nextToken()
   {
      char c;

      try
      {
         c = nextNonWhitespaceCharacter();
      }
      catch ( final Exception e )
      {
         return new Token( "__END__", line, column );
      }

      if ( c == '\n' )
      {
         return new Token( "\n", line, column );
      }
      if ( c == '/' )
      {
         return scanCommentRegExpOrOperator( c );
      }
      if ( c == '"' )
      {
         return scanString( c );
      }
      if ( c == '\'' )
      {
         return scanString( c );
      }
      if ( c == '<' )
      {
         return scanXMLOrOperator( c );
      }
      if ( c >= '0'
            && c <= '9' || c == '.' )
      {
         return scanNumberOrDots( c );
      }
      if ( c == '{'
            || c == '}' || c == '(' || c == ')' || c == '[' || c == ']'
            // a number can start with a dot as well, see number || c == '.'
            || c == ';' || c == ',' || c == '?' || c == '~' )
      {
         return scanSingleCharacterToken( c );
      }
      if ( c == ':' )
      {
         return scanCharacterSequence(
               c, new String[]
               { "::" }, 2 );
      }
      if ( c == '+' )
      {
         return scanCharacterSequence(
               c, new String[]
               { "++", "+=" }, 2 );
      }
      if ( c == '-' )
      {
         return scanCharacterSequence(
               c, new String[]
               { "--", "-=" }, 2 );
      }
      if ( c == '*' )
      {
         return scanCharacterSequence(
               c, new String[]
               { "*=" }, 2 );
      }
      // called by scanCommentOrRegExp if( c == '/' ) return
      // scanCharacterSequence( c, new String[]{"/="}, 2);
      if ( c == '%' )
      {
         return scanCharacterSequence(
               c, new String[]
               { "%=" }, 2 );
      }
      if ( c == '&' )
      {
         return scanCharacterSequence(
               c, new String[]
               { "&&", "&=" }, 2 );
      }
      if ( c == '|' )
      {
         return scanCharacterSequence(
               c, new String[]
               { "||", "|=" }, 2 );
      }
      if ( c == '^' )
      {
         return scanCharacterSequence(
               c, new String[]
               { "^=" }, 2 );
      }
      // called by scanXML if( c == '<' ) return scanCharacterSequence( c, new
      // String[]{"<<=","<<","<="}, 3);
      if ( c == '>' )
      {
         return scanCharacterSequence(
               c, new String[]
               { ">>>=", ">>>", ">>=", ">>", ">=" }, 4 );
      }
      if ( c == '=' )
      {
         return scanCharacterSequence(
               c, new String[]
               { "===", "==" }, 3 );
      }
      if ( c == '!' )
      {
         return scanCharacterSequence(
               c, new String[]
               { "!==", "!=" }, 3 );
      }

      return scanWord( c );
   }

   public void setLines(
         final String[] lines )
   {
      this.lines = lines;
      line = 0;
      column = -1;
   }

   private String getRemainingLine()
   {
      return lines[ line ].substring( column );
   }

   private boolean isHexChar(
         final char c2 )
   {
      return c2 >= '0'
            && c2 <= '9' || c2 >= 'A' && c2 <= 'z' || c2 >= 'a' && c2 <= 'z';
   }

   private boolean isIdentifierCharacter(
         final char c )
   {
      return c >= 'A'
            && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9'
            || c == '_' || c == '$';
   }

   private boolean isProcessingInstruction(
         final String text )
   {
      return text.startsWith( "<?" );
   }

   /**
    * try to parse the complete string into symbols without an error
    *
    * @param remainingLine
    * @return
    */
   private boolean isValid(
         final String remainingLine )
   {
      return true;
   }

   private boolean isValidRegExp(
         final String pattern )
   {
      try
      {
         return null != Pattern.compile( pattern );
      }
      catch ( final Throwable t )
      {
         // ignore
      }
      return false;
   }

   private boolean isValidXML(
         final String text )
   {
      return new XMLVerifier().verify( text );
   }

   private char nextChar()
   {
      final String currentLine = lines[ line ];

      column++;
      if ( currentLine.length() <= column )
      {
         column = -1;
         line++;
         return '\n';
      }

      final char result = currentLine.charAt( column );
      return result;
   }

   private char nextNonWhitespaceCharacter()
   {
      char result;
      do
      {
         result = nextChar();
      }
      while ( result == ' '
            || result == '\t' );
      return result;
   }

   private char peekChar(
         final int offset )
   {
      final String currentLine = lines[ line ];
      final int index = column
            + offset;
      if ( index >= currentLine.length() )
      {
         return '\n';
      }

      final char result = currentLine.charAt( index );
      return result;
   }

   /**
    * find the longest matching sequence
    *
    * @param c
    * @param possibleMatches
    * @param maxLength
    * @return
    */
   private Token scanCharacterSequence(
         final char c, final String[] possibleMatches, final int maxLength )
   {
      int peekPos = 1;
      final StringBuffer buffer = new StringBuffer();

      buffer.append( c );
      String found = buffer.toString();
      while ( peekPos < maxLength )
      {
         buffer.append( peekChar( peekPos ) );
         peekPos++;
         for ( final String possibleMatche : possibleMatches )
         {
            if ( buffer.toString().equals( possibleMatche ) )
            {
               found = buffer.toString();
            }
         }
      }
      final Token result = new Token( found, line, column );
      skipChars( found.length() - 1 );
      return result;
   }

   /**
    * Something started with a slash This might be a comment, a regexp or a
    * operator
    *
    * @param c
    * @return
    */
   private Token scanCommentRegExpOrOperator(
         final char c )
   {
      final char c2 = peekChar( 1 );
      if ( c2 == '/' )
      {
         return scanSingleLineComment();
      }
      if ( c2 == '*' )
      {
         return scanMultiLineComment();
      }

      // how can we know, if something is a regExp or a operator?
      // it looks like we have to look at the rest of the line and
      // see if we can parse it without errors, eg.
      // return 5/=2 == 1 && s == "/";
      // if you parse the text between slashes as a regexp,
      // the doublequote at the end is left over. So as soon
      // as we see this error, we have to go back and try
      // another option

      String remainingLine = getRemainingLine();
      Token result = scanRegExp( remainingLine );

      if ( result != null )
      {
         remainingLine = remainingLine.substring( result.text.length() );
         if ( isValid( remainingLine ) )
         {
            return result;
         }
      }

      // it is not a regular expression
      if ( c2 == '=' )
      {
         result = new Token( "/=", line, column );
         skipChars( 1 );
         return result;
      }
      // it is a simple divide symbol
      result = new Token( "/", line, column );
      return result;
   }

   /**
    * c is either a dot or a number
    *
    * @return
    */
   private Token scanDecimal(
         final char c )
   {
      char currentChar = c;
      final StringBuffer buffer = new StringBuffer();
      int peekPos = 1;
      // before dot
      while ( isDecimalChar( currentChar ) )
      {
         buffer.append( currentChar );
         currentChar = peekChar( peekPos++ );
      }
      // the optional dot
      if ( currentChar == '.' )
      {
         buffer.append( currentChar );
         currentChar = peekChar( peekPos++ );
         // after the dot
         while ( isDecimalChar( currentChar ) )
         {
            buffer.append( currentChar );
            currentChar = peekChar( peekPos++ );
         }
         // the optional exponent
         if ( currentChar == 'E' )
         {
            buffer.append( currentChar );
            currentChar = peekChar( peekPos++ );
            while ( isDecimalChar( currentChar ) )
            {
               buffer.append( currentChar );
               currentChar = peekChar( peekPos++ );
            }
         }
      }
      final Token result = new Token( buffer.toString(), line, column );
      result.isNum = true;
      skipChars( result.text.length() - 1 );
      return result;
   }

   /**
    * The first dot has been scanned Are the next chars dots as well?
    *
    * @return
    */
   private Token scanDots()
   {
      final char c2 = peekChar( 1 );
      if ( c2 == '.' )
      {
         final char c3 = peekChar( 2 );

         final String text = c3 == '.' ? "..." : "..";
         final Token result = new Token( text, line, column );
         skipChars( text.length() - 1 );
         return result;
      }
      return null;
   }

   /**
    * we have seen the 0x prefix
    *
    * @return
    */
   private Token scanHex()
   {
      final StringBuffer buffer = new StringBuffer();

      buffer.append( "0x" );
      int peekPos = 2;
      for ( ;; )
      {
         final char c2 = peekChar( peekPos++ );
         if ( !isHexChar( c2 ) )
         {
            break;
         }
         buffer.append( c2 );
      }
      final Token result = new Token( buffer.toString(), line, column );
      result.isNum = true;
      skipChars( result.text.length() - 1 );
      return result;
   }

   /**
    * the current char is the first slash plus we know, that a * is following
    *
    * @return
    */
   private Token scanMultiLineComment()
   {
      String text = "/*";
      char c = ' ';
      char cOld = ' ';
      skipChar();
      do
      {
         cOld = c;
         c = nextChar();
         text += c;
      }
      while ( c != 0
            && !( c == '/' && cOld == '*' ) );
      final Token result = new Token( text, line, column );
      return result;
   }

   /**
    * Something started with a number or a dot.
    *
    * @param c
    * @return
    */
   private Token scanNumberOrDots(
         final char c )
   {
      if ( c == '.' )
      {
         final Token result = scanDots();
         if ( result != null )
         {
            return result;
         }

         final char c2 = peekChar( 1 );
         if ( !isDecimalChar( c2 ) )
         {
            return new Token( ".", line, column );
         }
      }
      if ( c == '0' )
      {
         final char c2 = peekChar( 1 );
         if ( c2 == 'x' )
         {
            return scanHex();
         }
      }
      return scanDecimal( c );
   }

   private Token scanRegExp(
         final String remainingLine )
   {
      final Token t = scanUntilDelimiter( '/' );
      if ( t != null
            && isValidRegExp( t.text ) )
      {
         return t;
      }
      return null;
   }

   private Token scanSingleCharacterToken(
         final char c )
   {
      return new Token( ""
            + c, line, column );
   }

   /**
    * the current char is the first slash plus we know, that another slash is
    * following
    *
    * @return
    */
   private Token scanSingleLineComment()
   {
      final Token result = new Token( getRemainingLine(), line, column );
      skipChars( result.text.length() - 1 );
      return result;
   }

   /**
    * Something started with a quote or double quote consume characters until
    * the quote/double quote shows up again and is not escaped
    *
    * @param c
    * @return
    */
   private Token scanString(
         final char c )
   {
      return scanUntilDelimiter( c );
   }

   private Token scanUntilDelimiter(
         final char delimiter )
   {
      return scanUntilDelimiter(
            delimiter, delimiter );

   }

   private Token scanUntilDelimiter(
         final char start, final char delimiter )
   {
      final StringBuffer buffer = new StringBuffer();

      buffer.append( start );
      int peekPos = 1;
      int numberOfBackslashes = 0;
      for ( ;; )
      {
         final char c2 = peekChar( peekPos++ );
         if ( c2 == '\n' )
         {
            return null;
         }
         buffer.append( c2 );
         if ( c2 == delimiter
               && numberOfBackslashes == 0 )
         {
            final Token result = new Token( buffer.toString(), line, column );
            skipChars( buffer.toString().length() - 1 );
            return result;
         }
         numberOfBackslashes = c2 == '\\' ? ( numberOfBackslashes + 1 ) % 2
               : 0;
      }
   }

   private Token scanWord(
         final char c )
   {
      char currentChar = c;
      final StringBuffer buffer = new StringBuffer();

      buffer.append( currentChar );
      int peekPos = 1;
      for ( ;; )
      {
         currentChar = peekChar( peekPos++ );
         if ( !isIdentifierCharacter( currentChar ) )
         {
            break;
         }

         buffer.append( currentChar );
      }
      final Token result = new Token( buffer.toString(), line, column );
      skipChars( buffer.toString().length() - 1 );
      return result;
   }

   /**
    * Try to parse a XML document
    *
    * @return
    */
   private Token scanXML()
   {
      final int currentLine = line;
      final int currentColumn = column;
      int level = 0;
      final StringBuffer buffer = new StringBuffer();
      char c = '<';

      for ( ;; )
      {
         Token t = null;
         do
         {
            t = scanUntilDelimiter(
                  '<', '>' );
            if ( t == null )
            {
               line = currentLine;
               column = currentColumn;
               return null;
            }
            buffer.append( t.text );
            if ( isProcessingInstruction( t.text ) )
            {
               c = nextChar();
               if ( c == '\n' )
               {
                  buffer.append( '\n' );
                  skipChar();
               }
               t = null;
            }
         }
         while ( t == null );

         if ( t.text.startsWith( "</" ) )
         {
            level--;
         }
         else if ( t.text.endsWith( "/>" ) )
         {
            ;
         }
         else
         {
            level++;
         }

         if ( level <= 0 )
         {
            final Token result = new Token( buffer.toString(), line, column );
            return result;
         }

         for ( ;; )
         {
            c = nextChar();
            if ( c == '<' )
            {
               break;
            }
            buffer.append( c );
         }
      }
   }

   /**
    * Something started with a lower sign <
    *
    * @param c
    * @return
    */
   private Token scanXMLOrOperator(
         final char c )
   {
      final Token t = scanXML();
      // S ystem.out.println( t.text );
      if ( t != null
            && isValidXML( t.text ) )
      {
         return t;
      }
      return scanCharacterSequence(
            c, new String[]
            { "<<<=", "<<<", "<<=", "<<", "<=" }, 4 );
   }

   private void skipChar()
   {
      nextChar();
   }

   private void skipChars(
         final int count )
   {
      int decrementCount = count;

      while ( decrementCount-- > 0 )
      {
         nextChar();
      }
   }
}
