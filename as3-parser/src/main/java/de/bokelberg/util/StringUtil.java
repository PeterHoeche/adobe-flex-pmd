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
package de.bokelberg.util;

import java.util.regex.Pattern;

public final class StringUtil
{
   public static String addSpaceAfter(
         final String str, final String character )
   {
      String buffer = str;

      buffer = replaceAll(
            buffer, character, character
                  + " " );
      return replaceAll(
            buffer, "  ", " " );
   }

   public static String compressWhitespace(
         final String string )
   {
      return Pattern.compile(
            "\\s+" ).matcher(
            string ).replaceAll(
            " " );
   }

   public static int countString(
         final String haystack, final String needle )
   {
      int pos = -1;
      int result = 0;
      while ( ( pos = haystack.indexOf(
            needle, pos + 1 ) ) >= 0 )
      {
         result++;
      }
      return result;
   }

   public static String escape(
         final String stringToEscape )
   {
      final StringBuffer result = new StringBuffer();
      for ( int i = 0; i < stringToEscape.length(); i++ )
      {
         final char currentCharacter = stringToEscape.charAt( i );
         if ( currentCharacter == '('
               || currentCharacter == ')' || currentCharacter == '.'
               || currentCharacter == '*' || currentCharacter == '['
               || currentCharacter == ']' || currentCharacter == '^'
               || currentCharacter == '$' || currentCharacter == '+'
               || currentCharacter == '-' || currentCharacter == '/'
               || currentCharacter == '?' || currentCharacter == '|'
               || currentCharacter == '{' || currentCharacter == '}'
               || currentCharacter == '\\' )
         {
            result.append( '\\' );
         }
         result.append( currentCharacter );
      }
      return result.toString();
   }

   public static String insertSpaces(
         final String operator )
   {
      if ( operator == null )
      {
         return null;
      }
      if ( operator.length() <= 1 )
      {
         return operator;
      }

      final StringBuffer result = new StringBuffer();

      result.append( '\\' );
      result.append( operator.charAt( 0 ) );

      for ( int i = 1; i < operator.length(); i++ )
      {
         result.append( " +\\" );
         result.append( operator.charAt( i ) );
      }
      return result.toString();
   }

   public static String join(
         final String[] strings, final String delimiter )
   {
      final StringBuffer result = new StringBuffer();

      if ( strings == null )
      {
         return result.toString();
      }

      for ( int i = 0; i < strings.length; i++ )
      {
         if ( i > 0 )
         {
            result.append( delimiter );
         }
         result.append( strings[ i ] );
      }
      return result.toString();
   }

   public static String ltrim(
         final String stringToTrim )
   {
      return replaceAll(
            stringToTrim, "^\\s+", "" ); // delete leading white spaces
   }

   public static boolean match(
         final String stringToInspect, final String regexp )
   {
      return Pattern.compile(
            regexp ).matcher(
            stringToInspect ).find();
   }

   public static String repeatString(
         final String stringToRepeat, final int count )
   {
      final StringBuffer result = new StringBuffer();
      for ( int i = 0; i < count; i++ )
      {
         result.append( stringToRepeat );
      }
      return result.toString();
   }

   public static String replaceAll(
         final String haystack, final String needle, final String replacement )
   {
      return Pattern.compile(
            needle ).matcher(
            haystack ).replaceAll(
            replacement );
   }

   public static String rtrim(
         final String stringToTrim )
   {
      return replaceAll(
            stringToTrim, "\\s+$", "" ); // delete trailing white spaces
   }

   public static String trim(
         final String stringToTrim )
   {
      return replaceAll(
            stringToTrim, "(^\\s+|//s+$)", "" ); // delete leading & trailing white spaces
   }

   private StringUtil()
   {
   }
}
