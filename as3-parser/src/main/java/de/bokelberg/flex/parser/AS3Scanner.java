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
class AS3Scanner
{

   static public class Token
   {
      public int     column;
      public boolean isNum;
      public int     line;
      public String  text;

      public Token( final String textContent,
                    final int tokenLine,
                    final int tokenColumn )
      {
         text = textContent;
         line = tokenLine + 1;
         column = tokenColumn + 1;
      }
   }

   static public class XMLVerifier extends DefaultHandler
   {
      public boolean verify( final String text )
      {
         // Use the default (non-validating) parser
         final SAXParserFactory factory = SAXParserFactory.newInstance();
         factory.setNamespaceAware( false );

         // Parse the input
         SAXParser saxParser;
         try
         {
            saxParser = factory.newSAXParser();
            saxParser.parse( new InputSource( new StringReader( text ) ),
                             this );
            return true;
         }
         catch ( final Throwable e )
         {
            e.printStackTrace();
         }
         return false;
      }
   }

   public static boolean isDecimalChar( final char currentCharacter )
   {
      return currentCharacter >= '0'
            && currentCharacter <= '9';
   }

   private int      column;
   private int      line;
   private String[] lines = null;

   public Token nextToken()
   {
      char currentCharacter;

      try
      {
         currentCharacter = nextNonWhitespaceCharacter();
      }
      catch ( final Exception e )
      {
         return new Token( "__END__", line, column );
      }

      if ( currentCharacter == '\n' )
      {
         return new Token( "\n", line, column );
      }
      if ( currentCharacter == '/' )
      {
         return scanCommentRegExpOrOperator();
      }
      if ( currentCharacter == '"' )
      {
         return scanString( currentCharacter );
      }
      if ( currentCharacter == '\'' )
      {
         return scanString( currentCharacter );
      }
      if ( currentCharacter == '<' )
      {
         return scanXMLOrOperator( currentCharacter );
      }
      if ( currentCharacter >= '0'
            && currentCharacter <= '9' || currentCharacter == '.' )
      {
         return scanNumberOrDots( currentCharacter );
      }
      if ( currentCharacter == '{'
            || currentCharacter == '}' || currentCharacter == '(' || currentCharacter == ')'
            || currentCharacter == '[' || currentCharacter == ']'
            // a number can start with a dot as well, see number || c == '.'
            || currentCharacter == ';' || currentCharacter == ',' || currentCharacter == '?'
            || currentCharacter == '~' )
      {
         return scanSingleCharacterToken( currentCharacter );
      }
      if ( currentCharacter == ':' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "::" },
                                       2 );
      }
      if ( currentCharacter == '*' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       {},
                                       1 );
      }
      if ( currentCharacter == '+' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "++",
                                                   "+=" },
                                       2 );
      }
      if ( currentCharacter == '-' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "--",
                                                   "-=" },
                                       2 );
      }
      // called by scanCommentOrRegExp if( c == '/' ) return
      // scanCharacterSequence( c, new String[]{"/="}, 2);
      if ( currentCharacter == '%' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "%=" },
                                       2 );
      }
      if ( currentCharacter == '&' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "&&",
                                                   "&=" },
                                       2 );
      }
      if ( currentCharacter == '|' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "||",
                                                   "|=" },
                                       2 );
      }
      if ( currentCharacter == '^' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "^=" },
                                       2 );
      }
      // called by scanXML if( c == '<' ) return scanCharacterSequence( c, new
      // String[]{"<<=","<<","<="}, 3);
      if ( currentCharacter == '>' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { ">>>=",
                                                   ">>>",
                                                   ">>=",
                                                   ">>",
                                                   ">=" },
                                       4 );
      }
      if ( currentCharacter == '=' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "===",
                                                   "==" },
                                       3 );
      }
      if ( currentCharacter == '!' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "!==",
                                                   "!=" },
                                       3 );
      }

      return scanWord( currentCharacter );
   }

   public void setLines( final String[] linesToBeSet )
   {
      lines = linesToBeSet;
      line = 0;
      column = -1;
   }

   boolean isHexChar( final char currentCharacter )
   {
      final boolean isNum = currentCharacter >= '0'
            && currentCharacter <= '9';
      final boolean isLower = currentCharacter >= 'A'
            && currentCharacter <= 'Z';
      final boolean isUpper = currentCharacter >= 'a'
            && currentCharacter <= 'z';

      return isNum
            || isLower || isUpper;
   }

   private String getRemainingLine()
   {
      return lines[ line ].substring( column );
   }

   private boolean isIdentifierCharacter( final char currentCharacter )
   {
      return currentCharacter >= 'A'
            && currentCharacter <= 'Z' || currentCharacter >= 'a' && currentCharacter <= 'z'
            || currentCharacter >= '0' && currentCharacter <= '9' || currentCharacter == '_'
            || currentCharacter == '$';
   }

   private boolean isProcessingInstruction( final String text )
   {
      return text.startsWith( "<?" );
   }

   private boolean isValidRegExp( final String pattern )
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

   private boolean isValidXML( final String text )
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

      return currentLine.charAt( column );
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

   private char peekChar( final int offset )
   {
      final String currentLine = lines[ line ];
      final int index = column
            + offset;
      if ( index >= currentLine.length() )
      {
         return '\n';
      }

      return currentLine.charAt( index );
   }

   /**
    * find the longest matching sequence
    * 
    * @param currentCharacter
    * @param possibleMatches
    * @param maxLength
    * @return
    */
   private Token scanCharacterSequence( final char currentCharacter,
                                        final String[] possibleMatches,
                                        final int maxLength )
   {
      int peekPos = 1;
      final StringBuffer buffer = new StringBuffer();

      buffer.append( currentCharacter );
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
    * @param currentCharacter
    * @return
    */
   private Token scanCommentRegExpOrOperator()
   {
      final char firstCharacter = peekChar( 1 );

      if ( firstCharacter == '/' )
      {
         return scanSingleLineComment();
      }
      if ( firstCharacter == '*' )
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

      Token result = scanRegExp();

      if ( result != null )
      {
         return result;
      }

      // it is not a regular expression
      if ( firstCharacter == '=' )
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
   private Token scanDecimal( final char currentCharacter )
   {
      char currentChar = currentCharacter;
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
      final char secondCharacter = peekChar( 1 );

      if ( secondCharacter == '.' )
      {
         final char thirdCharacter = peekChar( 2 );

         final String text = thirdCharacter == '.' ? "..."
                                                  : "..";
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
         final char character = peekChar( peekPos++ );

         if ( !isHexChar( character ) )
         {
            break;
         }
         buffer.append( character );
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
      final StringBuffer buffer = new StringBuffer();
      char currentCharacter = ' ';
      char previousCharacter = ' ';

      buffer.append( "/*" );
      skipChar();
      do
      {
         previousCharacter = currentCharacter;
         currentCharacter = nextChar();
         buffer.append( currentCharacter );
      }
      while ( currentCharacter != 0
            && !( currentCharacter == '/' && previousCharacter == '*' ) );

      return new Token( buffer.toString(), line, column );
   }

   /**
    * Something started with a number or a dot.
    * 
    * @param characterToBeScanned
    * @return
    */
   private Token scanNumberOrDots( final char characterToBeScanned )
   {
      if ( characterToBeScanned == '.' )
      {
         final Token result = scanDots();
         if ( result != null )
         {
            return result;
         }

         final char firstCharacter = peekChar( 1 );
         if ( !isDecimalChar( firstCharacter ) )
         {
            return new Token( ".", line, column );
         }
      }
      if ( characterToBeScanned == '0' )
      {
         final char firstCharacter = peekChar( 1 );
         if ( firstCharacter == 'x' )
         {
            return scanHex();
         }
      }
      return scanDecimal( characterToBeScanned );
   }

   private Token scanRegExp()
   {
      final Token token = scanUntilDelimiter( '/' );
      if ( token != null
            && isValidRegExp( token.text ) )
      {
         return token;
      }
      return null;
   }

   private Token scanSingleCharacterToken( final char character )
   {
      return new Token( String.valueOf( character ), line, column );
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
    * @param startingCharacter
    * @return
    */
   private Token scanString( final char startingCharacter )
   {
      return scanUntilDelimiter( startingCharacter );
   }

   private Token scanUntilDelimiter( final char delimiter )
   {
      return scanUntilDelimiter( delimiter,
                                 delimiter );

   }

   private Token scanUntilDelimiter( final char start,
                                     final char delimiter )
   {
      final StringBuffer buffer = new StringBuffer();
      int peekPos = 1;
      int numberOfBackslashes = 0;

      buffer.append( start );

      for ( ;; )
      {
         final char currentCharacter = peekChar( peekPos++ );
         if ( currentCharacter == '\n' )
         {
            return null;
         }
         buffer.append( currentCharacter );
         if ( currentCharacter == delimiter
               && numberOfBackslashes == 0 )
         {
            final Token result = new Token( buffer.toString(), line, column );
            skipChars( buffer.toString().length() - 1 );
            return result;
         }
         numberOfBackslashes = currentCharacter == '\\' ? ( numberOfBackslashes + 1 ) % 2
                                                       : 0;
      }
   }

   private Token scanWord( final char startingCharacter )
   {
      char currentChar = startingCharacter;
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
      char currentCharacter = '<';

      for ( ;; )
      {
         Token currentToken = null;
         do
         {
            currentToken = scanUntilDelimiter( '<',
                                               '>' );
            if ( currentToken == null )
            {
               line = currentLine;
               column = currentColumn;
               return null;
            }
            buffer.append( currentToken.text );
            if ( isProcessingInstruction( currentToken.text ) )
            {
               currentCharacter = nextChar();
               if ( currentCharacter == '\n' )
               {
                  buffer.append( '\n' );
                  skipChar();
               }
               currentToken = null;
            }
         }
         while ( currentToken == null );

         if ( currentToken.text.startsWith( "</" ) )
         {
            level--;
         }
         else if ( !currentToken.text.endsWith( "/>" ) )
         {
            level++;
         }

         if ( level <= 0 )
         {
            return new Token( buffer.toString(), line, column );
         }

         for ( ;; )
         {
            currentCharacter = nextChar();
            if ( currentCharacter == '<' )
            {
               break;
            }
            buffer.append( currentCharacter );
         }
      }
   }

   /**
    * Something started with a lower sign <
    * 
    * @param startingCharacterc
    * @return
    */
   private Token scanXMLOrOperator( final char startingCharacterc )
   {
      final Token xmlToken = scanXML();

      if ( xmlToken != null
            && isValidXML( xmlToken.text ) )
      {
         return xmlToken;
      }
      return scanCharacterSequence( startingCharacterc,
                                    new String[]
                                    { "<<<=",
                                                "<<<",
                                                "<<=",
                                                "<<",
                                                "<=" },
                                    4 );
   }

   private void skipChar()
   {
      nextChar();
   }

   private void skipChars( final int count )
   {
      int decrementCount = count;

      while ( decrementCount-- > 0 )
      {
         nextChar();
      }
   }
}
