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

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.adobe.ac.utils.StackTraceUtils;

/**
 * convert a actionscript to a stream of tokens
 * 
 * @author rbokel
 */
class AS3Scanner
{
   static final class Token
   {
      private static Token create( final String textContent,
                                   final int tokenLine,
                                   final int tokenColumn )
      {
         return new Token( textContent, tokenLine, tokenColumn );
      }

      private final int     column;
      private final boolean isNumeric;
      private final int     line;
      private final String  text;

      protected Token( final String textContent,
                       final int tokenLine,
                       final int tokenColumn )
      {
         this( textContent, tokenLine, tokenColumn, false );
      }

      protected Token( final String textContent,
                       final int tokenLine,
                       final int tokenColumn,
                       final boolean isNumToSet )
      {
         text = textContent;
         line = tokenLine + 1;
         column = tokenColumn + 1;
         isNumeric = isNumToSet;
      }

      public int getColumn()
      {
         return column;
      }

      public int getLine()
      {
         return line;
      }

      public String getText()
      {
         return text;
      }

      public boolean isNum()
      {
         return isNumeric;
      }
   }

   private static class XMLVerifier
   {
      private static DefaultHandler handler;
      private static SAXParser      saxParser;

      static
      {
         final SAXParserFactory factory = SAXParserFactory.newInstance();

         handler = new DefaultHandler();
         factory.setNamespaceAware( false );

         try
         {
            saxParser = factory.newSAXParser();
         }
         catch ( final ParserConfigurationException e )
         {
            LOGGER.warning( StackTraceUtils.print( e ) );
         }
         catch ( final SAXException e )
         {
            LOGGER.warning( StackTraceUtils.print( e ) );
         }
      }

      public static boolean verify( final String text )
      {
         try
         {
            saxParser.parse( new InputSource( new StringReader( text ) ),
                             handler );
            return true;
         }
         catch ( final SAXException e )
         {
            LOGGER.warning( StackTraceUtils.print( e ) );
            return false;
         }
         catch ( final IOException e )
         {
            LOGGER.warning( StackTraceUtils.print( e ) );
            return false;
         }
      }
   }

   private static final Logger LOGGER = Logger.getLogger( AS3Scanner.class.getName() );

   protected static boolean isDecimalChar( final char currentCharacter )
   {
      return currentCharacter >= '0'
            && currentCharacter <= '9';
   }

   private int      column;
   private int      line;
   private String[] lines = null;

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

   protected Token nextToken()
   {
      char currentCharacter;

      if ( lines != null
            && line < lines.length )
      {
         currentCharacter = nextNonWhitespaceCharacter();
      }
      else
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
                                       { "::" } );
      }
      if ( currentCharacter == '*' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       {} );
      }
      if ( currentCharacter == '+' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "++",
                                                   "+=" } );
      }
      if ( currentCharacter == '-' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "--",
                                                   "-=" } );
      }
      // called by scanCommentOrRegExp if( c == '/' ) return
      // scanCharacterSequence( c, new String[]{"/="}, 2);
      if ( currentCharacter == '%' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "%=" } );
      }
      if ( currentCharacter == '&' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "&&",
                                                   "&=" } );
      }
      if ( currentCharacter == '|' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "||",
                                                   "|=" } );
      }
      if ( currentCharacter == '^' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "^=" } );
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
                                                   ">=" } );
      }
      if ( currentCharacter == '=' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "===",
                                                   "==" } );
      }
      if ( currentCharacter == '!' )
      {
         return scanCharacterSequence( currentCharacter,
                                       new String[]
                                       { "!==",
                                                   "!=" } );
      }

      return scanWord( currentCharacter );
   }

   protected void setLines( final String[] linesToBeSet )
   {
      lines = linesToBeSet;
      line = 0;
      column = -1;
   }

   private int computePossibleMatchesMaxLength( final String[] possibleMatches )
   {
      int max = 0;

      for ( final String possibleMatch : possibleMatches )
      {
         max = Math.max( max,
                         possibleMatch.length() );
      }
      return max;
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
         Pattern.compile( pattern );
      }
      catch ( final Throwable t )
      {
         return false;
      }
      return true;
   }

   private boolean isValidXML( final String text )
   {
      return XMLVerifier.verify( text );
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

      char currentChar = currentLine.charAt( column );

      while ( currentChar == '\uFEFF' )
      {
         column++;
         currentChar = currentLine.charAt( column );
      }
      return currentChar;
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
                                        final String[] possibleMatches )
   {
      int peekPos = 1;
      final StringBuffer buffer = new StringBuffer();
      final int maxLength = computePossibleMatchesMaxLength( possibleMatches );

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
      final Token result = new Token( buffer.toString(), line, column, true );
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
      final Token result = new Token( buffer.toString(), line, column, true );
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
            final Token result = Token.create( buffer.toString(),
                                               line,
                                               column );
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
                                                "<=" } );
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
