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

public class StringUtil
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
         final String in )
   {
      return Pattern.compile(
            "\\s+" ).matcher(
            in ).replaceAll(
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
         final String in )
   {
      final StringBuffer result = new StringBuffer();
      for ( int i = 0; i < in.length(); i++ )
      {
         final char c = in.charAt( i );
         if ( c == '('
               || c == ')' || c == '.' || c == '*' || c == '[' || c == ']'
               || c == '^' || c == '$' || c == '+' || c == '-' || c == '/'
               || c == '?' || c == '|' || c == '{' || c == '}' || c == '\\' )
         {
            result.append( "\\" );
         }
         result.append( c );
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

      String result = "\\"
            + operator.charAt( 0 );
      for ( int i = 1; i < operator.length(); i++ )
      {
         result += " +\\"
               + operator.charAt( i );
      }
      return result;
   }

   public static String join(
         final String[] strings, final String delimiter )
   {
      String result = "";
      if ( strings == null )
      {
         return result;
      }

      for ( int i = 0; i < strings.length; i++ )
      {
         if ( i > 0 )
         {
            result += delimiter;
         }
         result += strings[ i ];
      }
      return result;
   }

   public static String ltrim(
         final String in )
   {
      return replaceAll(
            in, "^\\s+", "" ); // delete leading white spaces
   }

   public static boolean match(
         final String in, final String regexp )
   {
      return Pattern.compile(
            regexp ).matcher(
            in ).find();
   }

   public static String repeatString(
         final String in, final int count )
   {
      String result = "";
      for ( int i = 0; i < count; i++ )
      {
         result += in;
      }
      return result;
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
         final String in )
   {
      return replaceAll(
            in, "\\s+$", "" ); // delete trailing white spaces
   }

   public static String trim(
         final String in )
   {
      return replaceAll(
            in, "(^\\s+|//s+$)", "" ); // delete leading & trailing white spaces
   }

}
