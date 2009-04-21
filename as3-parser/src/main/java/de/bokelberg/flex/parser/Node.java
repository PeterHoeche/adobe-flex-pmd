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

import java.util.ArrayList;
import java.util.List;

/**
 * A single node of the ast
 *
 * @author rbokel
 */
public class Node
{
   static public final String ADD = "add";
   static public final String AND = "and";
   static public final String ARGUMENTS = "arguments";
   static public final String ARRAY = "array";
   static public final String ARRAY_ACCESSOR = "arr-acc";
   static public final String ASSIGN = "assign";
   static public final String B_AND = "b-and";
   static public final String B_NOT = "b-not";
   static public final String B_OR = "b-or";
   static public final String B_XOR = "b-xor";
   static public final String BLOCK = "block";
   static public final String CALL = "call";
   static public final String CASES = "cases";
   static public final String COMPILATION_UNIT = "compilation-unit";
   static public final String COND = "cond";
   static public final String CONDITION = "condition";
   static public final String CONDITIONAL = "conditional";
   static public final String CONST_LIST = "const-list";
   static public final String CONTENT = "content";
   static public final String DOT = "dot";
   static public final String E4X_ATTR = "e4x-attr";
   static public final String E4X_FILTER = "e4x-filter";
   static public final String E4X_STAR = "e4x-star";
   static public final String ENCAPSULATED = "encapsulated";
   static public final String EQUALITY = "equality";
   static public final String EXPR_LIST = "expr-list";
   static public final String IMPLEMENTS_LIST = "implements-list";
   static public final String IN = "in";
   static public final String INIT = "init";
   static public final String ITER = "iter";
   static public final String LAMBDA = "lambda";
   static public final String META = "meta";
   static public final String META_LIST = "meta-list";
   static public final String MINUS = "minus";
   static public final String MOD_LIST = "mod-list";
   static public final String MODIFIER = "mod";
   static public final String MULTIPLICATION = "mul";
   static public final String NAME = "name";
   static public final String NAME_TYPE_INIT = "name-type-init";
   static public final String NOT = "not";
   static public final String OBJECT = "object";
   static public final String OP = "op";
   static public final String OR = "or";
   static public final String PARAMETER = "parameter";
   static public final String PARAMETER_LIST = "parameter-list";
   static public final String PLUS = "plus";
   static public final String POST_DEC = "post-dec";
   static public final String POST_INC = "post-inc";
   static public final String PRE_DEC = "pre-dec";
   static public final String PRE_INC = "pre-inc";
   static public final String PRIMARY = "primary";
   static public final String PROP = "prop";
   static public final String RELATION = "relation";
   static public final String REST = "rest";
   static public final String SHIFT = "shift";
   static public final String STAR = "star";
   static public final String STMT_EMPTY = "stmt-empty";
   static public final String SWITCH_BLOCK = "switch-block";
   static public final String TYPE = "type";
   static public final String VALUE = "value";
   static public final String VAR_LIST = "var-list";

   public List< Node > children;
   public int column;
   public String id;
   public int line;
   public String stringValue;

   public Node(
         final String id, final int line, final int column )
   {
      this.id = id;
      this.line = line;
      this.column = column;
   }

   public Node(
         final String id, final int line, final int column, final Node child )
   {
      this( id, line, column );
      addChild( child );
   }

   public Node(
         final String id, final int line, final int column, final String value )
   {
      this( id, line, column );
      stringValue = value;
   }

   final public void addChild(
         final Node child )
   {
      if ( child == null )
      {
         return; // skip optional children
      }

      if ( children == null )
      {
         children = new ArrayList< Node >();
      }
      children.add( child );
   }

   public void addChild(
         final String id, final int line, final int column, final Node nephew )
   {
      addChild( new Node( id, line, column, nephew ) );
   }

   public void addChild(
         final String id, final int line, final int column, final String value )
   {
      addChild( new Node( id, line, column, value ) );
   }

   public boolean find(
         final String id )
   {
      if ( numChildren() == 0 )
      {
         return false;
      }

      for ( final Node node : children )
      {
         if ( node.is( id ) )
         {
            return true;
         }
      }
      return false;
   }

   public Node getChild(
         final int index )
   {
      return children == null
            || children.size() <= index ? null : children.get( index );
   }

   public Node getLastChild()
   {
      return getChild( numChildren() - 1 );
   }

   public boolean is(
         final String id )
   {
      return this.id == null
            && id == null || this.id.equals( id );
   }

   public boolean isString(
         final String value )
   {
      return stringValue == null
            && value == null || stringValue.equals( value );
   }

   public int numChildren()
   {
      return children == null ? 0 : children.size();
   }
}