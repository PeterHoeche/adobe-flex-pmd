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
package com.adobe.ac.pmd.parser;

public enum NodeKind
{
   ADD("add"),
   AND("and"),
   ARGUMENTS("arguments"),
   ARRAY("array"),
   ARRAY_ACCESSOR("arr-acc"),
   ASSIGN("assign"),
   B_AND("b-and"),
   B_NOT("b-not"),
   B_OR("b-or"),
   B_XOR("b-xor"),
   BLOCK("block"),
   CALL("call"),
   CASE("case"),
   CASES("cases"),
   CATCH("catch"),
   CLASS("class"),
   COMPILATION_UNIT("compilation-unit"),
   COND("cond"),
   CONDITION("condition"),
   CONDITIONAL("conditional"),
   CONST("const"),
   CONST_LIST("const-list"),
   CONTENT("content"),
   DEFAULT("default"),
   DELETE("delete"),
   DO("do"),
   DOT("dot"),
   E4X_ATTR("e4x-attr"),
   E4X_FILTER("e4x-filter"),
   E4X_STAR("e4x-star"),
   ENCAPSULATED("encapsulated"),
   EQUALITY("equality"),
   EXPR_LIST("expr-list"),
   EXTENDS("extends"),
   FINALLY("finally"),
   FOR("for"),
   FOREACH("foreach"),
   FORIN("forin"),
   FUNCTION("function"),
   GET("get"),
   IF("if"),
   IMPLEMENTS("implements"),
   IMPLEMENTS_LIST("implements-list"),
   IMPORT("import"),
   IN("in"),
   INIT("init"),
   INTERFACE("interface"),
   ITER("iter"),
   LAMBDA("lambda"),
   LEFT_CURLY_BRACKET("{"),
   META("meta"),
   META_LIST("meta-list"),
   MINUS("minus"),
   MOD_LIST("mod-list"),
   MODIFIER("mod"),
   MULTIPLICATION("mul"),
   NAME("name"),
   NAME_TYPE_INIT("name-type-init"),
   NEW("new"),
   NOT("not"),
   OBJECT("object"),
   OP("op"),
   OR("or"),
   PACKAGE("package"),
   PARAMETER("parameter"),
   PARAMETER_LIST("parameter-list"),
   PLUS("plus"),
   POST_DEC("post-dec"),
   POST_INC("post-inc"),
   PRE_DEC("pre-dec"),
   PRE_INC("pre-inc"),
   PRIMARY("primary"),
   PROP("prop"),
   RELATION("relation"),
   REST("rest"),
   RETURN("return"),
   SET("set"),
   SHIFT("shift"),
   STAR("star"),
   STMT_EMPTY("stmt-empty"),
   SWITCH("switch"),
   SWITCH_BLOCK("switch-block"),
   TRY("try"),
   TYPE("type"),
   TYPEOF("typeof"),
   USE("use"),
   VALUE("value"),
   VAR("var"),
   VAR_LIST("var-list"),
   VOID("void"),
   WHILE("while");

   private String name;

   private NodeKind( final String nameToBeSet )
   {
      name = nameToBeSet;
   }

   @Override
   public String toString()
   {
      return name;
   }
}
