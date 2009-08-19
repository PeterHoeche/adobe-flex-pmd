/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
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
 *      * Neither the name of the Adobe Systems, Inc. nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.parser;

public enum Operators
{
   AND("&&"),
   AND_EQUAL("&="),
   AT("@"),
   B_AND("&"),
   B_OR("|"),
   B_XOR("^"),
   COLUMN(":"),
   COMMA(","),
   DECREMENT("--"),
   DIVIDED_EQUAL("/="),
   DOT("."),
   DOUBLE_COLUMN("::"),
   DOUBLE_EQUAL("=="),
   DOUBLE_QUOTE("\""),
   DOUBLE_SHIFT_LEFT("<<"),
   DOUBLE_SHIFT_RIGHT(">>"),
   EQUAL("="),
   INCREMENT("++"),
   INFERIOR("<"),
   INFERIOR_OR_EQUAL("<="),
   LEFT_CURLY_BRACKET("{"),
   LEFT_PARENTHESIS("("),
   LEFT_SQUARE_BRACKET("["),
   LOGICAL_OR("||"),
   MINUS("-"),
   MINUS_EQUAL("-="),
   MODULO("%"),
   MODULO_EQUAL("%="),
   NON_EQUAL("!="),
   NON_STRICTLY_EQUAL("!=="),
   OR_EQUAL("|="),
   PLUS("+"),
   PLUS_EQUAL("+="),
   QUESTION_MARK("?"),
   REST_PARAMETERS("..."),
   RIGHT_CURLY_BRACKET("}"),
   RIGHT_PARENTHESIS(")"),
   RIGHT_SQUARE_BRACKET("]"),
   SEMI_COLUMN(";"),
   SIMPLE_QUOTE("'"),
   SLASH("/"),
   STRICTLY_EQUAL("==="),
   SUPERIOR(">"),
   SUPERIOR_OR_EQUAL(">="),
   TIMES("*"),
   TIMES_EQUAL("*="),
   TRIPLE_SHIFT_LEFT("<<<"),
   TRIPLE_SHIFT_RIGHT(">>>"),
   XOR_EQUAL("^=");

   private String symbol;

   private Operators( final String symbolToBeSet )
   {
      symbol = symbolToBeSet;
   }

   @Override
   public String toString()
   {
      return symbol;
   }
}
