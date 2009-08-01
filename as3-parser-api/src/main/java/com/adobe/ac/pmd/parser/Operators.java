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
