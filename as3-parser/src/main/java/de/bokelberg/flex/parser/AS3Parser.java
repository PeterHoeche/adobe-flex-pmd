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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.bokelberg.flex.parser.AS3Scanner.Token;
import de.bokelberg.flex.parser.exceptions.NullTokenException;
import de.bokelberg.flex.parser.exceptions.TokenException;
import de.bokelberg.flex.parser.exceptions.UnExpectedTokenException;
import de.bokelberg.util.FileUtil;

public class AS3Parser
{
   public AS3Scanner scn;
   private String    fileName;
   private Token     tok;

   public Node buildAst( final String filePath ) throws IOException,
                                                TokenException
   {
      final String[] lines = FileUtil.readStrings( new File( filePath ) );
      setFileName( filePath );
      scn = new AS3Scanner();
      scn.setLines( lines );
      return parseCompilationUnit();
   }

   /**
    * Get the next token Skip comments and newlines for now In the end we want
    * to keep them though.
    * 
    * @throws TokenException
    */
   void nextToken() throws TokenException
   {
      do
      {
         nextTokenAllowNewLine();
      }
      while ( tok.text.equals( KeyWords.NEW_LINE ) );
      // S ystem.out.println("tok:" + tok.text + ".");
   }

   /**
    * tok is first content token
    * 
    * @throws TokenException
    */
   Node parseClassContent() throws TokenException
   {
      final Node result = new Node( Node.CONTENT, tok.line, tok.column );
      final ArrayList< Token > modifiers = new ArrayList< Token >();
      final ArrayList< Node > meta = new ArrayList< Node >();

      while ( !tokIs( Operators.RIGHT_CURLY_BRACKET ) )
      {
         if ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
         {
            meta.add( parseMetaData() );
         }
         else if ( tokIs( KeyWords.VAR ) )
         {
            result.addChild( parseVarList( meta,
                                           modifiers ) );
            if ( tokIs( Operators.SEMI_COLUMN ) )
            {
               nextToken();
            }
            meta.clear();
            modifiers.clear();
         }
         else if ( tokIs( KeyWords.CONST ) )
         {
            result.addChild( parseConstList( meta,
                                             modifiers ) );
            if ( tokIs( Operators.SEMI_COLUMN ) )
            {
               nextToken();
            }
            meta.clear();
            modifiers.clear();
         }
         else if ( tokIs( KeyWords.IMPORT ) )
         {
            result.addChild( parseImport() );
         }
         else if ( tokIs( KeyWords.FUNCTION ) )
         {
            result.addChild( parseFunction( meta,
                                            modifiers ) );
            meta.clear();
            modifiers.clear();
         }
         else
         {
            modifiers.add( tok );
            // S ystem.out.println("add mod: " + tok.text + ".");
            nextToken();
         }
      }
      return result;
   }

   /**
    * tok is empty, since nextToken has not been called before
    * 
    * @throws UnExpectedTokenException
    */
   Node parseCompilationUnit() throws TokenException
   {
      final Node result = new Node( Node.COMPILATION_UNIT, -1, -1 );

      nextToken();
      if ( tokIs( KeyWords.PACKAGE ) )
      {
         result.addChild( parsePackage() );
      }
      result.addChild( parsePackageContent() );
      return result;
   }

   Node parseExpression() throws TokenException
   {
      return parseAssignmentExpression();
   }

   /**
    * tok is first content token
    * 
    * @throws TokenException
    */
   Node parseInterfaceContent() throws TokenException
   {
      final Node result = new Node( Node.CONTENT, tok.line, tok.column );
      while ( !tokIs( Operators.RIGHT_CURLY_BRACKET ) )
      {
         if ( tokIs( KeyWords.IMPORT ) )
         {
            result.addChild( parseImport() );
         }
         else if ( tokIs( KeyWords.FUNCTION ) )
         {
            result.addChild( parseFunctionSignature() );
         }
         else if ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
         {
            while ( !tokIs( Operators.RIGHT_SQUARE_BRACKET ) )
            {
               nextToken();
            }
            nextToken();
         }
      }
      return result;
   }

   /**
    * tok is first token of content
    * 
    * @throws UnExpectedTokenException
    */
   Node parsePackageContent() throws TokenException
   {
      final Node result = new Node( Node.CONTENT, tok.line, tok.column );
      final ArrayList< Token > modifier = new ArrayList< Token >();
      final ArrayList< Node > meta = new ArrayList< Node >();

      while ( !tokIs( Operators.RIGHT_CURLY_BRACKET )
            && !tokIs( KeyWords.EOF ) )
      {
         if ( tokIs( KeyWords.IMPORT ) )
         {
            result.addChild( parseImport() );
         }
         else if ( tokIs( KeyWords.USE ) )
         {
            result.addChild( parseUse() );
         }
         else if ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
         {
            meta.add( parseMetaData() );
         }
         else if ( tokIs( KeyWords.CLASS ) )
         {
            result.addChild( parseClass( meta,
                                         modifier ) );
            modifier.clear();
            meta.clear();
         }
         else if ( tokIs( KeyWords.INTERFACE ) )
         {
            result.addChild( parseInterface( meta,
                                             modifier ) );
            modifier.clear();
            meta.clear();
         }
         else
         {
            modifier.add( tok );
            nextToken();
         }
      }
      return result;
   }

   // ------------------------------------------------------------------------
   // language specific recursive descent parsing
   // ------------------------------------------------------------------------

   Node parsePrimaryExpression() throws TokenException
   {
      Node result = new Node( Node.PRIMARY, tok.line, tok.column );
      result.stringValue = tok.text;
      // S ystem.out.println("primary " + result );
      if ( tokIs( KeyWords.UNDEFINED ) )
      {
         nextToken(); // undefined
      }
      else if ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
      {
         result.addChild( parseArrayLiteral() );
      }
      else if ( tokIs( Operators.LEFT_CURLY_BRACKET ) )
      {
         result.addChild( parseObjectLiteral() );
      }
      else if ( tokIs( KeyWords.FUNCTION ) )
      {
         result.addChild( parseLambdaExpression() );
      }
      else if ( tokIs( KeyWords.NEW ) )
      {
         result = parseNewExpression();
      }
      else if ( tokIs( Operators.LEFT_PARENTHESIS ) )
      {
         result.addChild( parseEncapsulatedExpression() );
      }
      else if ( tok.text.startsWith( Operators.DOUBLE_QUOTE ) )
      {
         nextToken(); // string
      }
      else if ( tok.text.startsWith( Operators.SIMPLE_QUOTE ) )
      {
         nextToken(); // string
      }
      else if ( tok.text.startsWith( Operators.SLASH ) )
      {
         nextToken(); // regexp
      }
      else if ( tok.text.startsWith( Operators.INFERIOR ) )
      {
         nextToken(); // xml literal
      }
      else if ( tokIs( KeyWords.TRUE ) )
      {
         nextToken(); // true
      }
      else if ( tokIs( KeyWords.FALSE ) )
      {
         nextToken(); // false
      }
      else if ( tokIs( KeyWords.NULL ) )
      {
         nextToken(); // null
      }
      else if ( tok.isNum )
      {
         nextToken();
      }
      else if ( tokIs( KeyWords.AT ) )
      {
         result.addChild( parseE4XAttributeIdentifier() );
      }
      else
      {
         nextToken();
      }
      return result;
   }

   /**
    * tok is the first token of a statement
    * 
    * @throws TokenException
    */
   Node parseStatement() throws TokenException
   {

      Node result;
      if ( tokIs( KeyWords.FOR ) )
      {
         result = parseFor();
      }
      else if ( tokIs( KeyWords.IF ) )
      {
         result = parseIf();
      }
      else if ( tokIs( KeyWords.SWITCH ) )
      {
         result = parseSwitch();
      }
      else if ( tokIs( KeyWords.DO ) )
      {
         result = parseDo();
      }
      else if ( tokIs( KeyWords.WHILE ) )
      {
         result = parseWhile();
      }
      else if ( tokIs( KeyWords.TRY ) )
      {
         nextToken();
         result = new Node( KeyWords.TRY, tok.line, tok.column, parseBlock() );
      }
      else if ( tokIs( KeyWords.CATCH ) )
      {
         result = parseCatch();
      }
      else if ( tokIs( KeyWords.FINALLY ) )
      {
         nextToken();
         result = new Node( KeyWords.FINALLY, tok.line, tok.column, parseBlock() );
      }
      else if ( tokIs( Operators.LEFT_CURLY_BRACKET ) )
      {
         result = parseBlock();
      }
      else if ( tokIs( KeyWords.VAR ) )
      {
         result = parseVarList( null,
                                null );
         skip( Operators.SEMI_COLUMN );
      }
      else if ( tokIs( KeyWords.CONST ) )
      {
         result = parseConstList( null,
                                  null );
         skip( Operators.SEMI_COLUMN );
      }
      else if ( tokIs( KeyWords.RETURN ) )
      {
         /**
          * TODO How can we decide if return needs an expression? The type of a
          * method is optional, so we cant really derive it from the context.
          * Aaah, i know! That is why the expression needs to start on the same
          * line! So either the next symbol is a semicolon, which means no
          * expression, or we have something else on the same line, which should
          * parse as a expression. In Java it is a bit different, because the
          * semicolon at the end of the line is mandatory.
          */
         nextTokenAllowNewLine();
         if ( tokIs( KeyWords.NEW_LINE )
               || tokIs( Operators.SEMI_COLUMN ) )
         {
            nextToken();
            result = new Node( KeyWords.RETURN, tok.line, tok.column, "" );
         }
         else
         {
            result = new Node( KeyWords.RETURN, tok.line, tok.column, parseExpression() );
            skip( Operators.SEMI_COLUMN );
         }
      }
      else if ( tokIs( Operators.SEMI_COLUMN ) )
      {
         result = new Node( Node.STMT_EMPTY, tok.line, tok.column, Operators.SEMI_COLUMN );
         nextToken();
      }
      else
      {
         result = parseExpressionList();
         skip( Operators.SEMI_COLUMN );
      }
      return result;
   }

   Node parseUnaryExpression() throws TokenException
   {
      Node result;
      if ( tokIs( Operators.INCREMENT ) )
      {
         nextToken();
         result = new Node( Node.PRE_INC, tok.line, tok.column, parseUnaryExpression() );
      }
      else if ( tokIs( Operators.DECREMENT ) )
      {
         nextToken();
         result = new Node( Node.PRE_DEC, tok.line, tok.column, parseUnaryExpression() );
      }
      else if ( tokIs( Operators.MINUS ) )
      {
         nextToken();
         result = new Node( Node.MINUS, tok.line, tok.column, parseUnaryExpression() );
      }
      else if ( tokIs( Operators.PLUS ) )
      {
         nextToken();
         result = new Node( Node.PLUS, tok.line, tok.column, parseUnaryExpression() );
      }
      else
      {
         result = parseUnaryExpressionNotPlusMinus();
      }
      return result;
   }

   private Node collectVarListContent( final Node result ) throws TokenException
   {
      result.addChild( parseNameTypeInit() );
      while ( tokIs( Operators.COMMA ) )
      {
         nextToken();
         result.addChild( parseNameTypeInit() );
      }
      return result;
   }

   /**
    * Compare the current token to the parameter. If it equals, get the next
    * token. If not, throw a runtime exception.
    * 
    * @param text
    * @throws UnExpectedTokenException
    */
   private void consume( final String text ) throws TokenException
   {
      if ( !tokIs( text ) )
      {
         throw new UnExpectedTokenException( tok, fileName );
      }
      nextToken();
   }

   private Node convertMeta( final List< Node > metadataList )
   {
      if ( metadataList == null
            || metadataList.isEmpty() )
      {
         return null;
      }

      final Node result = new Node( Node.META_LIST, tok.line, tok.column );

      for ( final Node metadataNode : metadataList )
      {
         result.addChild( metadataNode );
      }
      return result;
   }

   private Node convertModifiers( final List< Token > modifierList )
   {
      if ( modifierList == null )
      {
         return null;
      }

      final Node result = new Node( Node.MOD_LIST, tok.line, tok.column );

      for ( final Token modifierToken : modifierList )
      {
         result.addChild( Node.MODIFIER,
                          tok.line,
                          tok.column,
                          modifierToken.text );
      }
      return result;
   }

   private Node[] doParseSignature() throws TokenException
   {
      consume( KeyWords.FUNCTION );

      final Node type = new Node( Node.TYPE, tok.line, tok.column, KeyWords.FUNCTION );
      if ( tokIs( KeyWords.SET )
            || tokIs( KeyWords.GET ) )
      {
         type.stringValue = tok.text;
         nextToken(); // set or get
      }
      final Node name = new Node( Node.NAME, tok.line, tok.column, tok.text );
      nextToken(); // name
      final Node params = parseParameterList();
      final Node returnType = parseOptionalType();
      return new Node[]
      { type,
                  name,
                  params,
                  returnType };
   }

   /**
    * Get the next token Skip comments but keep newlines We need this method for
    * beeing able to decide if a returnStatement has an expression
    * 
    * @throws UnExpectedTokenException
    */
   private void nextTokenAllowNewLine() throws TokenException
   {
      do
      {
         tok = scn.nextToken();
         /*
          * try { throw new Exception(); } catch( Exception e) {
          * StackTraceElement[] st = e.getStackTrace(); StackTraceElement ste =
          * st[ 1 ]; System.out.println( ste.getMethodName() + ":" + tok.text );
          * }
          */
         if ( tok == null )
         {
            throw new NullTokenException( fileName );

         }
         if ( tok.text == null )
         {
            throw new UnExpectedTokenException( tok, fileName );
         }
      }
      while ( tok.text.startsWith( "//" )
            || tok.text.startsWith( "/*" ) );
   }

   private Node parseAdditiveExpression() throws TokenException
   {
      final Node result = new Node( Node.ADD, tok.line, tok.column, parseMultiplicativeExpression() );
      while ( tokIs( Operators.PLUS )
            || tokIs( Operators.MINUS ) )
      {
         result.addChild( new Node( Node.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseMultiplicativeExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private Node parseAndExpression() throws TokenException
   {
      final Node result = new Node( Node.AND, tok.line, tok.column, parseBitwiseOrExpression() );
      while ( tokIs( Operators.AND ) )
      {
         result.addChild( new Node( Node.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseBitwiseOrExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   /**
    * tok is ( exit tok is first token after )
    */
   private Node parseArgumentList() throws TokenException
   {
      consume( Operators.LEFT_PARENTHESIS );
      final Node result = new Node( Node.ARGUMENTS, tok.line, tok.column );
      while ( !tokIs( Operators.RIGHT_PARENTHESIS ) )
      {
         result.addChild( parseExpression() );
         skip( Operators.COMMA );
      }
      consume( Operators.RIGHT_PARENTHESIS );
      return result;
   }

   /**
    * tok is [
    */
   private Node parseArrayLiteral() throws TokenException
   {
      final Node result = new Node( Node.ARRAY, tok.line, tok.column );
      consume( Operators.LEFT_SQUARE_BRACKET );
      while ( !tokIs( Operators.RIGHT_SQUARE_BRACKET ) )
      {
         result.addChild( parseExpression() );
         skip( Operators.COMMA );
      }
      consume( Operators.RIGHT_SQUARE_BRACKET );
      // S ystem.out.println("arrayliteral " + result );
      return result;
   }

   private Node parseAssignmentExpression() throws TokenException
   {
      final Node result = new Node( Node.ASSIGN, tok.line, tok.column, parseConditionalExpression() );
      while ( tokIs( KeyWords.EQUAL )
            || tokIs( KeyWords.PLUS_EQUAL ) || tokIs( KeyWords.MINUS_EQUAL ) || tokIs( KeyWords.TIMES_EQUAL )
            || tokIs( KeyWords.DIVIDED_EQUAL ) || tokIs( KeyWords.MODULO_EQUAL )
            || tokIs( KeyWords.AND_EQUAL ) || tokIs( KeyWords.OR_EQUAL ) || tokIs( KeyWords.XOR_EQUAL ) )
      {
         result.addChild( new Node( Node.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private Node parseBitwiseAndExpression() throws TokenException
   {
      final Node result = new Node( Node.B_AND, tok.line, tok.column, parseEqualityExpression() );
      while ( tokIs( KeyWords.B_AND ) )
      {
         result.addChild( new Node( Node.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseEqualityExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private Node parseBitwiseOrExpression() throws TokenException
   {
      final Node result = new Node( Node.B_OR, tok.line, tok.column, parseBitwiseXorExpression() );
      while ( tokIs( KeyWords.B_OR ) )
      {
         result.addChild( new Node( Node.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseBitwiseXorExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private Node parseBitwiseXorExpression() throws TokenException
   {
      final Node result = new Node( Node.B_XOR, tok.line, tok.column, parseBitwiseAndExpression() );
      while ( tokIs( KeyWords.B_XOR ) )
      {
         result.addChild( new Node( Node.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseBitwiseAndExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   /**
    * tok is { exit tok is the first tok after }
    * 
    * @throws TokenException
    */
   private Node parseBlock() throws TokenException
   {
      consume( Operators.LEFT_CURLY_BRACKET );

      final Node result = new Node( Node.BLOCK, tok.line, tok.column );
      while ( !tokIs( Operators.RIGHT_CURLY_BRACKET ) )
      {
         result.addChild( parseStatement() );
      }
      consume( Operators.RIGHT_CURLY_BRACKET );
      return result;
   }

   /**
    * tok is catch
    * 
    * @throws TokenException
    */
   private Node parseCatch() throws TokenException
   {
      consume( KeyWords.CATCH );
      consume( Operators.LEFT_PARENTHESIS );
      final Node result = new Node( KeyWords.CATCH, tok.line, tok.column, new Node( Node.NAME,
                                                                                    tok.line,
                                                                                    tok.column,
                                                                                    tok.text ) );
      nextToken(); // name
      if ( tokIs( KeyWords.DOUBLE_COLUMN ) )
      {
         nextToken(); // :
         result.addChild( new Node( Node.TYPE, tok.line, tok.column, tok.text ) );
         nextToken(); // type
      }
      consume( Operators.RIGHT_PARENTHESIS );
      result.addChild( parseBlock() );
      return result;
   }

   /**
    * tok is class
    * 
    * @param meta
    * @param modifier
    * @throws TokenException
    */
   private Node parseClass( final List< Node > meta,
                            final List< Token > modifier ) throws TokenException
   {
      consume( KeyWords.CLASS );

      final Node result = new Node( KeyWords.CLASS, tok.line, tok.column );
      result.addChild( Node.NAME,
                       tok.line,
                       tok.column,
                       tok.text );
      nextToken(); // name
      result.addChild( convertMeta( meta ) );
      result.addChild( convertModifiers( modifier ) );
      do
      {
         if ( tokIs( KeyWords.EXTENDS ) )
         {
            nextToken(); // extends
            result.addChild( KeyWords.EXTENDS,
                             tok.line,
                             tok.column,
                             parseQualifiedName() );
         }
         else if ( tokIs( KeyWords.IMPLEMENTS ) )
         {
            result.addChild( parseImplementsList() );
         }
      }
      while ( !tokIs( Operators.LEFT_CURLY_BRACKET ) );
      consume( Operators.LEFT_CURLY_BRACKET );
      result.addChild( parseClassContent() );
      consume( Operators.RIGHT_CURLY_BRACKET );
      return result;
   }

   /**
    * tok is (
    * 
    * @throws TokenException
    */
   private Node parseCondition() throws TokenException
   {
      consume( Operators.LEFT_PARENTHESIS );
      final Node result = new Node( Node.CONDITION, tok.line, tok.column, parseExpression() );
      consume( Operators.RIGHT_PARENTHESIS );
      return result;
   }

   private Node parseConditionalExpression() throws TokenException
   {
      Node result = parseOrExpression();
      if ( tokIs( KeyWords.QUESTION_MARK ) )
      {
         result = new Node( Node.CONDITIONAL, tok.line, tok.column, result );
         nextToken(); // ?
         result.addChild( parseExpression() );
         nextToken(); // :
         result.addChild( parseExpression() );
      }
      return result;
   }

   /**
    * tok is const
    * 
    * @param modifiers
    * @param meta
    * @throws TokenException
    */
   private Node parseConstList( final List< Node > meta,
                                final List< Token > modifiers ) throws TokenException
   {
      consume( KeyWords.CONST );
      final Node result = new Node( Node.CONST_LIST, tok.line, tok.column );
      result.addChild( convertMeta( meta ) );
      result.addChild( convertModifiers( modifiers ) );
      collectVarListContent( result );
      return result;
   }

   /**
    * tok is do
    * 
    * @throws TokenException
    */
   private Node parseDo() throws TokenException
   {
      consume( KeyWords.DO );
      final Node result = new Node( KeyWords.DO, tok.line, tok.column, parseStatement() );
      consume( KeyWords.WHILE );
      result.addChild( parseCondition() );
      return result;
   }

   private Node parseE4XAttributeIdentifier() throws TokenException
   {
      consume( KeyWords.AT );

      final Node result = new Node( Node.E4X_ATTR, tok.line, tok.column );
      if ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
      {
         nextToken();
         result.addChild( parseExpression() );
         consume( Operators.RIGHT_SQUARE_BRACKET );
      }
      else if ( tokIs( Operators.TIMES ) )
      {
         nextToken();
         result.addChild( new Node( Node.STAR, tok.line, tok.column ) );
      }
      else
      {
         result.addChild( new Node( Node.NAME, tok.line, tok.column, parseQualifiedName() ) );
      }
      return result;
   }

   private Node parseEncapsulatedExpression() throws TokenException
   {
      consume( Operators.LEFT_PARENTHESIS );
      final Node result = new Node( Node.ENCAPSULATED, tok.line, tok.column );
      result.addChild( parseExpression() );
      consume( Operators.RIGHT_PARENTHESIS );
      return result;
   }

   private Node parseEqualityExpression() throws TokenException
   {
      final Node result = new Node( Node.EQUALITY, tok.line, tok.column, parseRelationalExpression() );
      while ( tokIs( Operators.EQUAL )
            || tokIs( Operators.STRICTLY_EQUAL ) || tokIs( Operators.NON_EQUAL )
            || tokIs( Operators.NON_STRICTLY_EQUAL ) )
      {
         result.addChild( new Node( Node.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseRelationalExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private Node parseExpressionList() throws TokenException
   {
      final Node result = new Node( Node.EXPR_LIST, tok.line, tok.column, parseAssignmentExpression() );
      while ( tokIs( Operators.COMMA ) )
      {
         nextToken();
         result.addChild( parseAssignmentExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   /**
    * tok is for
    * 
    * @throws TokenException
    */
   private Node parseFor() throws TokenException
   {
      consume( KeyWords.FOR );

      if ( tokIs( KeyWords.EACH ) )
      {
         nextToken();
         return parseForEach();
      }
      else
      {
         return parseTraditionalFor();
      }
   }

   /**
    * tok is ( for each( var obj : Type in List )
    * 
    * @throws TokenException
    */
   private Node parseForEach() throws TokenException
   {
      consume( Operators.LEFT_PARENTHESIS );

      final Node result = new Node( KeyWords.FOREACH, tok.line, tok.column );
      if ( tokIs( KeyWords.VAR ) )
      {
         final Node var = new Node( KeyWords.VAR, tok.line, tok.column );
         nextToken();
         var.addChild( parseNameTypeInit() );
         result.addChild( var );
      }
      else
      {
         result.addChild( Node.NAME,
                          tok.line,
                          tok.column,
                          tok.text ); // TODO: Qualified
         // names allowed?
         nextToken();
      }
      nextToken(); // in
      result.addChild( Node.IN,
                       tok.line,
                       tok.column,
                       parseExpression() );
      consume( Operators.RIGHT_PARENTHESIS );
      result.addChild( parseStatement() );
      return result;
   }

   /**
    * tok is function
    * 
    * @param modifiers
    * @param meta
    * @throws TokenException
    */
   private Node parseFunction( final List< Node > meta,
                               final List< Token > modifiers ) throws TokenException
   {
      final Node[] signature = doParseSignature();
      final Node result = new Node( signature[ 0 ].stringValue, tok.line, tok.column );

      result.addChild( convertMeta( meta ) );
      result.addChild( convertModifiers( modifiers ) );
      result.addChild( signature[ 1 ] );
      result.addChild( signature[ 2 ] );
      result.addChild( signature[ 3 ] );
      result.addChild( parseBlock() );
      return result;
   }

   /**
    * tok is function exit tok is the first token after the optional ;
    * 
    * @throws TokenException
    */
   private Node parseFunctionSignature() throws TokenException
   {
      final Node[] signature = doParseSignature();
      skip( Operators.SEMI_COLUMN );
      final Node result = new Node( signature[ 0 ].stringValue, tok.line, tok.column );
      result.addChild( signature[ 1 ] );
      result.addChild( signature[ 2 ] );
      result.addChild( signature[ 3 ] );
      return result;
   }

   /**
    * tok is if
    * 
    * @throws TokenException
    */
   private Node parseIf() throws TokenException
   {
      consume( KeyWords.IF );
      final Node result = new Node( KeyWords.IF, tok.line, tok.column, parseCondition() );
      result.addChild( parseStatement() );
      if ( tokIs( KeyWords.ELSE ) )
      {
         nextToken();
         result.addChild( parseStatement() );
      }
      return result;
   }

   /**
    * tok is implements implements a,b,c exit tok is the first token after the
    * list of qualfied names
    * 
    * @throws TokenException
    */
   private Node parseImplementsList() throws TokenException
   {
      consume( KeyWords.IMPLEMENTS );

      final Node result = new Node( Node.IMPLEMENTS_LIST, tok.line, tok.column );
      result.addChild( KeyWords.IMPLEMENTS,
                       tok.line,
                       tok.column,
                       parseQualifiedName() );
      while ( tokIs( Operators.COMMA ) )
      {
         nextToken();
         result.addChild( KeyWords.IMPLEMENTS,
                          tok.line,
                          tok.column,
                          parseQualifiedName() );
      }
      return result;
   }

   /**
    * tok is import
    * 
    * @throws TokenException
    */
   private Node parseImport() throws TokenException
   {
      consume( KeyWords.IMPORT );
      final Node result = new Node( KeyWords.IMPORT, tok.line, tok.column, parseImportName() );
      skip( Operators.SEMI_COLUMN );
      return result;
   }

   /**
    * tok is the first part of a name the last part can be a star exit tok is
    * the first token, which doesn't belong to the name
    * 
    * @throws TokenException
    */
   private String parseImportName() throws TokenException
   {
      final StringBuffer result = new StringBuffer();

      result.append( tok.text );
      nextToken();
      while ( tokIs( Operators.DOT ) )
      {
         result.append( Operators.DOT );
         nextToken(); // .
         result.append( tok.text );
         nextToken(); // part of name
      }
      return result.toString();
   }

   /**
    * tok is interface
    * 
    * @param meta
    * @param modifier
    * @throws TokenException
    */
   private Node parseInterface( final List< Node > meta,
                                final List< Token > modifier ) throws TokenException
   {
      consume( KeyWords.INTERFACE );
      final Node result = new Node( KeyWords.INTERFACE, tok.line, tok.column );

      result.addChild( Node.NAME,
                       tok.line,
                       tok.column,
                       tok.text );
      nextToken(); // name
      result.addChild( convertMeta( meta ) );
      result.addChild( convertModifiers( modifier ) );

      if ( tokIs( KeyWords.EXTENDS ) )
      {
         nextToken(); // extends
         result.addChild( KeyWords.EXTENDS,
                          tok.line,
                          tok.column,
                          parseQualifiedName() );
      }
      while ( tokIs( Operators.COMMA ) )
      {
         nextToken(); // comma
         result.addChild( KeyWords.EXTENDS,
                          tok.line,
                          tok.column,
                          parseQualifiedName() );
      }
      consume( Operators.LEFT_CURLY_BRACKET );
      result.addChild( parseInterfaceContent() );
      consume( Operators.RIGHT_CURLY_BRACKET );
      return result;
   }

   /**
    * tok is function
    * 
    * @throws TokenException
    */
   private Node parseLambdaExpression() throws TokenException
   {
      consume( KeyWords.FUNCTION );
      final Node result = new Node( Node.LAMBDA, tok.line, tok.column );
      result.addChild( parseParameterList() );
      result.addChild( parseOptionalType() );
      result.addChild( parseBlock() );
      return result;
   }

   /**
    * tok is [ [id] [id ("test")] [id (name="test",type="a.b.c.Event")] exit
    * token is the first token after ]
    * 
    * @throws TokenException
    */
   private Node parseMetaData() throws TokenException
   {
      consume( Operators.LEFT_SQUARE_BRACKET );

      String result = "";
      while ( !tokIs( Operators.RIGHT_SQUARE_BRACKET ) )
      {
         if ( result.length() > 0 )
         {
            result += " ";
         }
         result += tok.text;
         nextToken();
      }
      skip( Operators.RIGHT_SQUARE_BRACKET );
      return new Node( Node.META, tok.line, tok.column, result );
   }

   private Node parseMultiplicativeExpression() throws TokenException
   {
      final Node result = new Node( Node.MULTIPLICATION, tok.line, tok.column, parseUnaryExpression() );
      while ( tokIs( Operators.TIMES )
            || tokIs( Operators.SLASH ) || tokIs( Operators.MODULO ) )
      {
         result.addChild( new Node( Node.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseUnaryExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private String parseNamespaceName() throws TokenException
   {
      final String name = tok.text;
      nextToken(); // simple name for now
      return name;
   }

   private Node parseNameTypeInit() throws TokenException
   {
      final Node result = new Node( Node.NAME_TYPE_INIT, tok.line, tok.column );
      result.addChild( Node.NAME,
                       tok.line,
                       tok.column,
                       tok.text );
      nextToken(); // name
      result.addChild( parseOptionalType() );
      result.addChild( parseOptionalInit() );
      return result;
   }

   private Node parseNewExpression() throws TokenException
   {
      consume( KeyWords.NEW );

      final Node result = new Node( KeyWords.NEW, tok.line, tok.column );
      result.addChild( parseExpression() ); // name
      if ( tokIs( Operators.LEFT_PARENTHESIS ) )
      {
         result.addChild( parseArgumentList() );
      }
      return result;
   }

   /**
    * tok is {
    */
   private Node parseObjectLiteral() throws TokenException
   {
      final Node result = new Node( Node.OBJECT, tok.line, tok.column );
      consume( Operators.LEFT_CURLY_BRACKET );
      while ( !tokIs( Operators.RIGHT_CURLY_BRACKET ) )
      {
         result.addChild( parseObjectLiteralPropertyDeclaration() );
         skip( Operators.COMMA );
      }
      consume( Operators.RIGHT_CURLY_BRACKET );
      return result;
   }

   /*
    * tok is name
    */
   private Node parseObjectLiteralPropertyDeclaration() throws TokenException
   {
      final Node result = new Node( Node.PROP, tok.line, tok.column );
      final Node name = new Node( Node.NAME, tok.line, tok.column );
      name.stringValue = tok.text;
      result.addChild( name );
      nextToken(); // name
      consume( KeyWords.DOUBLE_COLUMN );
      result.addChild( Node.VALUE,
                       tok.line,
                       tok.column,
                       parseExpression() );
      return result;
   }

   /**
    * if tok is "=" parse the expression otherwise do nothing
    * 
    * @return
    */
   private Node parseOptionalInit() throws TokenException
   {
      Node result = null;
      if ( tokIs( KeyWords.EQUAL ) )
      {
         nextToken();
         result = new Node( Node.INIT, tok.line, tok.column, parseExpression() );
      }
      return result;
   }

   /**
    * if tok is ":" parse the type otherwise do nothing
    * 
    * @return
    * @throws TokenException
    */
   private Node parseOptionalType() throws TokenException
   {
      final Node result = new Node( Node.TYPE, tok.line, tok.column, "" );
      if ( tokIs( KeyWords.DOUBLE_COLUMN ) )
      {
         nextToken();
         result.stringValue = tok.text; // TODO: What about qualified names
         // here?
         nextToken();
      }
      return result;
   }

   private Node parseOrExpression() throws TokenException
   {
      final Node result = new Node( Node.OR, tok.line, tok.column, parseAndExpression() );
      while ( tokIs( Operators.LOGICAL_OR ) )
      {
         result.addChild( new Node( Node.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseAndExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   /**
    * tok is package
    * 
    * @throws UnExpectedTokenException
    */
   private Node parsePackage() throws TokenException
   {
      consume( KeyWords.PACKAGE );

      final Node result = new Node( KeyWords.PACKAGE, tok.line, tok.column );
      String name = "";
      while ( !tokIs( Operators.LEFT_CURLY_BRACKET ) )
      {
         name += tok.text;
         nextToken();
      }
      result.addChild( Node.NAME,
                       tok.line,
                       tok.column,
                       name );
      consume( Operators.LEFT_CURLY_BRACKET );
      result.addChild( parsePackageContent() );
      consume( Operators.RIGHT_CURLY_BRACKET );
      return result;
   }

   /**
    * tok is the name of a parameter or ...
    */
   private Node parseParameter() throws TokenException
   {
      final Node result = new Node( Node.PARAMETER, tok.line, tok.column );
      if ( tokIs( Operators.REST_PARAMETERS ) )
      {
         nextToken(); // ...
         final Node rest = new Node( Node.REST, tok.line, tok.column, tok.text );
         nextToken(); // rest
         result.addChild( rest );
      }
      else
      {
         result.addChild( parseNameTypeInit() );
      }
      return result;
   }

   /**
    * tok is (
    * 
    * @throws TokenException
    */
   private Node parseParameterList() throws TokenException
   {
      consume( Operators.LEFT_PARENTHESIS );

      final Node result = new Node( Node.PARAMETER_LIST, tok.line, tok.column );
      while ( !tokIs( Operators.RIGHT_PARENTHESIS ) )
      {
         result.addChild( parseParameter() );
         if ( tokIs( Operators.COMMA ) )
         {
            nextToken();
         }
         else
         {
            break;
         }
      }
      consume( Operators.RIGHT_PARENTHESIS );
      return result;
   }

   /**
    * tok is first part of the name exit tok is the first token after the name
    * 
    * @throws TokenException
    */
   private String parseQualifiedName() throws TokenException
   {
      String result = tok.text;
      nextToken();
      while ( tokIs( Operators.DOT )
            || tokIs( Operators.DOUBLE_COLUMN ) )
      {
         result += tok.text;
         nextToken();
         result += tok.text;
         nextToken(); // name
      }
      // S ystem.out.println("parseQualifiedName " + result );
      return result;
   }

   private Node parseRelationalExpression() throws TokenException
   {
      final Node result = new Node( Node.RELATION, tok.line, tok.column, parseShiftExpression() );
      while ( tokIs( Operators.INFERIOR )
            || tokIs( Operators.INFERIOR_OR_EQUAL ) || tokIs( Operators.SUPERIOR )
            || tokIs( Operators.SUPERIOR_OR_EQUAL ) || tokIs( KeyWords.IS ) || tokIs( KeyWords.AS )
            || tokIs( KeyWords.INSTANCE_OF ) )
      {
         result.addChild( new Node( Node.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseShiftExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private Node parseShiftExpression() throws TokenException
   {
      final Node result = new Node( Node.SHIFT, tok.line, tok.column, parseAdditiveExpression() );
      while ( tokIs( Operators.DOUBLE_SHIFT_LEFT )
            || tokIs( Operators.TRIPLE_SHIFT_LEFT ) || tokIs( Operators.DOUBLE_SHIFT_RIGHT )
            || tokIs( Operators.TRIPLE_SHIFT_RIGHT ) )
      {
         result.addChild( new Node( Node.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseAdditiveExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   /**
    * tok is switch
    * 
    * @throws TokenException
    */
   private Node parseSwitch() throws TokenException
   {
      consume( KeyWords.SWITCH );
      final Node result = new Node( KeyWords.SWITCH, tok.line, tok.column, parseCondition() );
      if ( tokIs( Operators.LEFT_CURLY_BRACKET ) )
      {
         nextToken();
         result.addChild( parseSwitchCases() );
         consume( Operators.RIGHT_CURLY_BRACKET );
      }
      return result;
   }

   /**
    * tok is case, default or the first token of the first statement
    * 
    * @throws TokenException
    */
   private Node parseSwitchBlock() throws TokenException
   {
      final Node result = new Node( Node.SWITCH_BLOCK, tok.line, tok.column );
      while ( !tokIs( KeyWords.CASE )
            && !tokIs( KeyWords.DEFAULT ) && !tokIs( Operators.RIGHT_CURLY_BRACKET ) )
      {
         result.addChild( parseStatement() );
      }
      return result;
   }

   /**
    * tok is { exit tok is }
    * 
    * @throws TokenException
    */
   private Node parseSwitchCases() throws TokenException
   {
      final Node result = new Node( Node.CASES, tok.line, tok.column );
      for ( ;; )
      {
         if ( tokIs( Operators.RIGHT_CURLY_BRACKET ) )
         {
            break;
         }
         else if ( tokIs( KeyWords.CASE ) )
         {
            nextToken(); // case
            final Node caseNode = new Node( KeyWords.CASE, tok.line, tok.column, parseExpression() );
            consume( KeyWords.DOUBLE_COLUMN );
            caseNode.addChild( parseSwitchBlock() );
            result.addChild( caseNode );
         }
         else if ( tokIs( KeyWords.DEFAULT ) )
         {
            nextToken(); // default
            consume( KeyWords.DOUBLE_COLUMN );
            final Node caseNode = new Node( KeyWords.CASE, tok.line, tok.column, new Node( KeyWords.DEFAULT,
                                                                                           tok.line,
                                                                                           tok.column,
                                                                                           KeyWords.DEFAULT ) );
            caseNode.addChild( parseSwitchBlock() );
            result.addChild( caseNode );
         }
      }
      return result;
   }

   /**
    * tok is ( for( var x : int = 0; i < length; i++ ) for( var s : String in
    * Object )
    * 
    * @throws TokenException
    */
   private Node parseTraditionalFor() throws TokenException
   {
      consume( Operators.LEFT_PARENTHESIS );

      final Node result = new Node( KeyWords.FOR, tok.line, tok.column );
      if ( tokIs( Operators.SEMI_COLUMN ) )
      {
         // no init, do nothing
      }
      else
      {
         if ( tokIs( KeyWords.VAR ) )
         {
            result.addChild( Node.INIT,
                             tok.line,
                             tok.column,
                             parseVarList( null,
                                           null ) );
         }
         else
         {
            result.addChild( Node.INIT,
                             tok.line,
                             tok.column,
                             parseExpressionList() );
         }
         if ( tokIs( Node.IN ) )
         {
            // omg, we have a for-in situation here
            nextToken();
            result.addChild( Node.IN,
                             tok.line,
                             tok.column,
                             parseExpression() );
            result.id = KeyWords.FORIN;
            return result;
         }
      }
      consume( Operators.SEMI_COLUMN );
      if ( tokIs( Operators.SEMI_COLUMN ) )
      {
         // no condition, do nothing
      }
      else
      {
         result.addChild( Node.COND,
                          tok.line,
                          tok.column,
                          parseExpression() );
      }
      consume( Operators.SEMI_COLUMN );
      if ( tokIs( Operators.RIGHT_PARENTHESIS ) )
      {
         // no iter, do nothing
      }
      else
      {
         result.addChild( Node.ITER,
                          tok.line,
                          tok.column,
                          parseExpressionList() );
      }
      consume( Operators.RIGHT_PARENTHESIS );
      result.addChild( parseStatement() );
      return result;
   }

   private Node parseUnaryExpressionNotPlusMinus() throws TokenException
   {
      Node result;
      if ( tokIs( KeyWords.DELETE ) )
      {
         nextToken();
         result = new Node( KeyWords.DELETE, tok.line, tok.column, parseExpression() );
      }
      else if ( tokIs( KeyWords.VOID ) )
      {
         nextToken();
         result = new Node( KeyWords.VOID, tok.line, tok.column, parseExpression() );
      }
      else if ( tokIs( KeyWords.TYPEOF ) )
      {
         nextToken();
         result = new Node( KeyWords.TYPEOF, tok.line, tok.column, parseExpression() );
      }
      else if ( tokIs( "!" ) )
      {
         nextToken();
         result = new Node( Node.NOT, tok.line, tok.column, parseExpression() );
      }
      else if ( tokIs( "~" ) )
      {
         nextToken();
         result = new Node( Node.B_NOT, tok.line, tok.column, parseExpression() );
      }
      else
      {
         result = parseUnaryPostfixExpression();
      }
      return result;
   }

   private Node parseUnaryPostfixExpression() throws TokenException
   {
      Node e1 = parsePrimaryExpression();

      if ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
      {
         final Node result = new Node( Node.ARRAY_ACCESSOR, tok.line, tok.column );
         result.addChild( e1 );
         while ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
         {
            nextToken(); // [
            result.addChild( parseExpression() );
            consume( Operators.RIGHT_SQUARE_BRACKET );
         }
         e1 = result;
      }
      else if ( tokIs( Operators.LEFT_PARENTHESIS ) )
      {
         final Node result = new Node( Node.CALL, tok.line, tok.column );
         result.addChild( e1 );
         while ( tokIs( Operators.LEFT_PARENTHESIS ) )
         {
            result.addChild( parseArgumentList() );
         }
         while ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
         {
            result.addChild( parseArrayLiteral() );
         }

         e1 = result;
      }
      else
      {
         /*
          * TODO a few e4x things need to be implemented as well (
          * poi=propOrIdent[root_0, retval.start] -> $poi | E4X_DESC
          * qualifiedIdentifier -> ^(E4X_DESC $postfixExpression
          * qualifiedIdentifier) | d=DOT e4xAttributeIdentifier ->
          * ^(PROPERTY_OR_IDENTIFIER[$d] $postfixExpression
          * e4xAttributeIdentifier)
          */
      }
      if ( tokIs( Operators.INCREMENT ) )
      {
         nextToken();
         final Node result = new Node( Node.POST_INC, tok.line, tok.column );
         result.addChild( e1 );
         e1 = result;
      }
      else if ( tokIs( Operators.DECREMENT ) )
      {
         nextToken();
         final Node result = new Node( Node.POST_DEC, tok.line, tok.column );
         result.addChild( e1 );
         e1 = result;
      }
      else if ( tokIs( Operators.DOT ) )
      {
         nextToken();
         if ( tokIs( Operators.LEFT_PARENTHESIS ) )
         {
            nextToken();
            final Node result = new Node( Node.E4X_FILTER, tok.line, tok.column );
            result.addChild( e1 );
            result.addChild( parseExpression() );
            e1 = result;
            consume( Operators.RIGHT_PARENTHESIS );
         }
         else if ( tokIs( "*" ) )
         {
            final Node result = new Node( Node.E4X_STAR, tok.line, tok.column );
            result.addChild( e1 );
            e1 = result;
         }
         else
         {
            final Node result = new Node( Node.DOT, tok.line, tok.column );
            result.addChild( e1 );
            result.addChild( parseExpression() );
            e1 = result;
         }
      }
      return e1;
   }

   /**
    * tok is use TODO: we could maintain a table of namespaces and allow the
    * valid ones only
    * 
    * @throws TokenException
    */
   private Node parseUse() throws TokenException
   {
      consume( KeyWords.USE );
      return new Node( KeyWords.USE, tok.line, tok.column, parseNamespaceName() );
   }

   /**
    * tok is var var x, y : String, z : int = 0;
    * 
    * @param modifiers
    * @param meta
    * @throws TokenException
    */
   private Node parseVarList( final List< Node > meta,
                              final List< Token > modifiers ) throws TokenException
   {
      consume( KeyWords.VAR );
      final Node result = new Node( Node.VAR_LIST, tok.line, tok.column );
      result.addChild( convertMeta( meta ) );
      result.addChild( convertModifiers( modifiers ) );
      collectVarListContent( result );
      return result;
   }

   /**
    * tok is while
    * 
    * @throws TokenException
    */
   private Node parseWhile() throws TokenException
   {
      consume( KeyWords.WHILE );
      final Node result = new Node( KeyWords.WHILE, tok.line, tok.column );
      result.addChild( parseCondition() );
      result.addChild( parseStatement() );
      return result;
   }

   private void setFileName( final String fileNameToParse )
   {
      fileName = fileNameToParse;
   }

   /**
    * Skip the current token, if it equals to the parameter
    * 
    * @param text
    * @throws UnExpectedTokenException
    */
   private void skip( final String text ) throws TokenException
   {
      if ( tokIs( text ) )
      {
         nextToken();
      }
   }

   /**
    * Compare the current token to the parameter
    * 
    * @param text
    * @return true, if tok's text property equals the parameter
    */
   private boolean tokIs( final String text )
   {
      return tok.text.equals( text );
   }
}
