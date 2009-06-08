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

import com.adobe.ac.pmd.parser.IAS3Parser;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.KeyWords;
import com.adobe.ac.pmd.parser.NodeKind;
import com.adobe.ac.pmd.parser.Operators;
import com.adobe.ac.pmd.parser.exceptions.NullTokenException;
import com.adobe.ac.pmd.parser.exceptions.TokenException;
import com.adobe.ac.pmd.parser.exceptions.UnExpectedTokenException;

import de.bokelberg.flex.parser.AS3Scanner.Token;
import de.bokelberg.util.FileUtil;

public class AS3Parser implements IAS3Parser
{
   private static final String NEW_LINE = "\n";
   public AS3Scanner           scn;
   private String              fileName;
   private Token               tok;

   /*
    * (non-Javadoc)
    * @see de.bokelberg.flex.parser.IAS3Parser#buildAst(java.lang.String)
    */
   public IParserNode buildAst( final String filePath ) throws IOException,
                                                       TokenException
   {
      final String[] lines = FileUtil.readStrings( new File( filePath ) );
      return parseLines( filePath,
                         lines );
   }

   public IParserNode buildAst( final String filePath,
                                final String[] scriptBlockLines ) throws TokenException
   {
      return parseLines( filePath,
                         scriptBlockLines );
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
      while ( tok.text.equals( NEW_LINE ) );
      // S ystem.out.println("tok:" + tok.text + ".");
   }

   /**
    * tok is first content token
    * 
    * @throws TokenException
    */
   Node parseClassContent() throws TokenException
   {
      final Node result = new Node( NodeKind.CONTENT, tok.line, tok.column );
      final List< Token > modifiers = new ArrayList< Token >();
      final List< Node > meta = new ArrayList< Node >();

      while ( !tokIs( Operators.RIGHT_CURLY_BRACKET ) )
      {
         if ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
         {
            meta.add( parseMetaData() );
         }
         else if ( tokIs( KeyWords.VAR ) )
         {
            parseClassField( result,
                             modifiers,
                             meta );
         }
         else if ( tokIs( KeyWords.CONST ) )
         {
            parseClassConstant( result,
                                modifiers,
                                meta );
         }
         else if ( tokIs( KeyWords.IMPORT ) )
         {
            result.addChild( parseImport() );
         }
         else if ( tokIs( KeyWords.FUNCTION ) )
         {
            parseClassFunctions( result,
                                 modifiers,
                                 meta );
         }
         else
         {
            modifiers.add( tok );
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
      final Node result = new Node( NodeKind.COMPILATION_UNIT, -1, -1 );

      nextToken();
      if ( tokIs( KeyWords.PACKAGE ) )
      {
         result.addChild( parsePackage() );
      }
      result.addChild( parsePackageContent() );
      return result;
   }

   IParserNode parseExpression() throws TokenException
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
      final Node result = new Node( NodeKind.CONTENT, tok.line, tok.column );
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
      final Node result = new Node( NodeKind.CONTENT, tok.line, tok.column );
      final List< Token > modifier = new ArrayList< Token >();
      final List< Node > meta = new ArrayList< Node >();

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

   Node parsePrimaryExpression() throws TokenException
   {
      Node result = new Node( NodeKind.PRIMARY, tok.line, tok.column );
      result.setStringValue( tok.text );

      if ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
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
      else if ( tokIs( Operators.AT ) )
      {
         result.addChild( parseE4XAttributeIdentifier() );
      }
      else if ( tok.isNum
            || tokIs( KeyWords.TRUE ) || tokIs( KeyWords.FALSE ) || tokIs( KeyWords.NULL )
            || tok.text.startsWith( Operators.DOUBLE_QUOTE.toString() )
            || tok.text.startsWith( Operators.SIMPLE_QUOTE.toString() )
            || tok.text.startsWith( Operators.SLASH.toString() )
            || tok.text.startsWith( Operators.INFERIOR.toString() ) || tokIs( KeyWords.UNDEFINED ) )
      {
         nextToken();
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
   IParserNode parseStatement() throws TokenException
   {
      IParserNode result;

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
         result = parseTry();
      }
      else if ( tokIs( KeyWords.CATCH ) )
      {
         result = parseCatch();
      }
      else if ( tokIs( KeyWords.FINALLY ) )
      {
         result = parseFinally();
      }
      else if ( tokIs( Operators.LEFT_CURLY_BRACKET ) )
      {
         result = parseBlock();
      }
      else if ( tokIs( KeyWords.VAR ) )
      {
         result = parseVar();
      }
      else if ( tokIs( KeyWords.CONST ) )
      {
         result = parseConst();
      }
      else if ( tokIs( KeyWords.RETURN ) )
      {
         result = parseReturnStatement();
      }
      else if ( tokIs( Operators.SEMI_COLUMN ) )
      {
         result = parseEmptyStatement();
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
         result = new Node( NodeKind.PRE_INC, tok.line, tok.column, parseUnaryExpression() );
      }
      else if ( tokIs( Operators.DECREMENT ) )
      {
         nextToken();
         result = new Node( NodeKind.PRE_DEC, tok.line, tok.column, parseUnaryExpression() );
      }
      else if ( tokIs( Operators.MINUS ) )
      {
         nextToken();
         result = new Node( NodeKind.MINUS, tok.line, tok.column, parseUnaryExpression() );
      }
      else if ( tokIs( Operators.PLUS ) )
      {
         nextToken();
         result = new Node( NodeKind.PLUS, tok.line, tok.column, parseUnaryExpression() );
      }
      else
      {
         result = parseUnaryExpressionNotPlusMinus();
      }
      return result;
   }

   private IParserNode collectVarListContent( final Node result ) throws TokenException
   {
      result.addChild( parseNameTypeInit() );
      while ( tokIs( Operators.COMMA ) )
      {
         nextToken();
         result.addChild( parseNameTypeInit() );
      }
      return result;
   }

   private void consume( final KeyWords keyword ) throws TokenException
   {
      consume( keyword.toString() );
   }

   private void consume( final Operators operator ) throws TokenException
   {
      consume( operator.toString() );
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
         throw new UnExpectedTokenException( tok.text, tok.line, tok.column, fileName );
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

      final Node result = new Node( NodeKind.META_LIST, tok.line, tok.column );

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

      final Node result = new Node( NodeKind.MOD_LIST, tok.line, tok.column );

      for ( final Token modifierToken : modifierList )
      {
         result.addChild( NodeKind.MODIFIER,
                          tok.line,
                          tok.column,
                          modifierToken.text );
      }
      return result;
   }

   private Node[] doParseSignature() throws TokenException
   {
      consume( KeyWords.FUNCTION );

      final Node type = new Node( NodeKind.TYPE, tok.line, tok.column, KeyWords.FUNCTION.toString() );
      if ( tokIs( KeyWords.SET )
            || tokIs( KeyWords.GET ) )
      {
         type.setStringValue( tok.text );
         nextToken(); // set or get
      }
      final Node name = new Node( NodeKind.NAME, tok.line, tok.column, tok.text );
      nextToken(); // name
      final Node params = parseParameterList();
      final Node returnType = parseOptionalType();
      return new Node[]
      { type,
                  name,
                  params,
                  returnType };
   }

   private NodeKind findFunctionTypeFromSignature( final Node[] signature )
   {
      for ( final Node node : signature )
      {
         if ( node.is( NodeKind.TYPE ) )
         {
            if ( node.getStringValue().compareTo( "set" ) == 0 )
            {
               return NodeKind.SET;
            }
            if ( node.getStringValue().compareTo( "get" ) == 0 )
            {
               return NodeKind.GET;
            }
            return NodeKind.FUNCTION;
         }
      }
      return NodeKind.FUNCTION;
   }

   // ------------------------------------------------------------------------
   // language specific recursive descent parsing
   // ------------------------------------------------------------------------

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
            throw new UnExpectedTokenException( tok.text, tok.line, tok.column, fileName );
         }
      }
      while ( tok.text.startsWith( "//" )
            || tok.text.startsWith( "/*" ) );
   }

   private IParserNode parseAdditiveExpression() throws TokenException
   {
      final Node result = new Node( NodeKind.ADD, tok.line, tok.column, parseMultiplicativeExpression() );
      while ( tokIs( Operators.PLUS )
            || tokIs( Operators.MINUS ) )
      {
         result.addChild( new Node( NodeKind.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseMultiplicativeExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private IParserNode parseAndExpression() throws TokenException
   {
      final Node result = new Node( NodeKind.AND, tok.line, tok.column, parseBitwiseOrExpression() );
      while ( tokIs( Operators.AND ) )
      {
         result.addChild( new Node( NodeKind.OP, tok.line, tok.column, tok.text ) );
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
      final Node result = new Node( NodeKind.ARGUMENTS, tok.line, tok.column );
      while ( !tokIs( Operators.RIGHT_PARENTHESIS ) )
      {
         result.addChild( parseExpression() );
         skip( Operators.COMMA );
      }
      consume( Operators.RIGHT_PARENTHESIS );
      return result;
   }

   private Node parseArrayAccessor( final Node node ) throws TokenException
   {
      final Node result = new Node( NodeKind.ARRAY_ACCESSOR, tok.line, tok.column );
      result.addChild( node );
      while ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
      {
         nextToken(); // [
         result.addChild( parseExpression() );
         consume( Operators.RIGHT_SQUARE_BRACKET );
      }
      return result;
   }

   /**
    * tok is [
    */
   private IParserNode parseArrayLiteral() throws TokenException
   {
      final Node result = new Node( NodeKind.ARRAY, tok.line, tok.column );
      consume( Operators.LEFT_SQUARE_BRACKET );
      while ( !tokIs( Operators.RIGHT_SQUARE_BRACKET ) )
      {
         result.addChild( parseExpression() );
         skip( Operators.COMMA );
      }
      consume( Operators.RIGHT_SQUARE_BRACKET );
      return result;
   }

   private IParserNode parseAssignmentExpression() throws TokenException
   {
      final Node result = new Node( NodeKind.ASSIGN, tok.line, tok.column, parseConditionalExpression() );
      while ( tokIs( Operators.EQUAL )
            || tokIs( Operators.PLUS_EQUAL ) || tokIs( Operators.MINUS_EQUAL )
            || tokIs( Operators.TIMES_EQUAL ) || tokIs( Operators.DIVIDED_EQUAL )
            || tokIs( Operators.MODULO_EQUAL ) || tokIs( Operators.AND_EQUAL ) || tokIs( Operators.OR_EQUAL )
            || tokIs( Operators.XOR_EQUAL ) )
      {
         result.addChild( new Node( NodeKind.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private IParserNode parseBitwiseAndExpression() throws TokenException
   {
      final Node result = new Node( NodeKind.B_AND, tok.line, tok.column, parseEqualityExpression() );
      while ( tokIs( Operators.B_AND ) )
      {
         result.addChild( new Node( NodeKind.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseEqualityExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private IParserNode parseBitwiseOrExpression() throws TokenException
   {
      final Node result = new Node( NodeKind.B_OR, tok.line, tok.column, parseBitwiseXorExpression() );
      while ( tokIs( Operators.B_OR ) )
      {
         result.addChild( new Node( NodeKind.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseBitwiseXorExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private IParserNode parseBitwiseXorExpression() throws TokenException
   {
      final Node result = new Node( NodeKind.B_XOR, tok.line, tok.column, parseBitwiseAndExpression() );
      while ( tokIs( Operators.B_XOR ) )
      {
         result.addChild( new Node( NodeKind.OP, tok.line, tok.column, tok.text ) );
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

      final Node result = new Node( NodeKind.BLOCK, tok.line, tok.column );
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
      final Node result = new Node( NodeKind.CATCH, tok.line, tok.column, new Node( NodeKind.NAME,
                                                                                    tok.line,
                                                                                    tok.column,
                                                                                    tok.text ) );
      nextToken(); // name
      if ( tokIs( Operators.COLUMN ) )
      {
         nextToken(); // :
         result.addChild( new Node( NodeKind.TYPE, tok.line, tok.column, tok.text ) );
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

      final Node result = new Node( NodeKind.CLASS, tok.line, tok.column );
      result.addChild( NodeKind.NAME,
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
            result.addChild( NodeKind.EXTENDS,
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

   private void parseClassConstant( final Node result,
                                    final List< Token > modifiers,
                                    final List< Node > meta ) throws TokenException
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

   private void parseClassField( final Node result,
                                 final List< Token > modifiers,
                                 final List< Node > meta ) throws TokenException
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

   private void parseClassFunctions( final Node result,
                                     final List< Token > modifiers,
                                     final List< Node > meta ) throws TokenException
   {
      result.addChild( parseFunction( meta,
                                      modifiers ) );
      meta.clear();
      modifiers.clear();
   }

   /**
    * tok is (
    * 
    * @throws TokenException
    */
   private Node parseCondition() throws TokenException
   {
      consume( Operators.LEFT_PARENTHESIS );
      final Node result = new Node( NodeKind.CONDITION, tok.line, tok.column, parseExpression() );
      consume( Operators.RIGHT_PARENTHESIS );
      return result;
   }

   private IParserNode parseConditionalExpression() throws TokenException
   {
      final IParserNode result = parseOrExpression();
      if ( tokIs( Operators.QUESTION_MARK ) )
      {
         final Node conditional = new Node( NodeKind.CONDITIONAL, tok.line, tok.column, result );
         nextToken(); // ?
         conditional.addChild( parseExpression() );
         nextToken(); // :
         conditional.addChild( parseExpression() );

         return conditional;
      }
      return result;
   }

   private Node parseConst() throws TokenException
   {
      Node result;
      result = parseConstList( null,
                               null );
      skip( Operators.SEMI_COLUMN );
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
      final Node result = new Node( NodeKind.CONST_LIST, tok.line, tok.column );
      result.addChild( convertMeta( meta ) );
      result.addChild( convertModifiers( modifiers ) );
      collectVarListContent( result );
      return result;
   }

   private Node parseDecrement( final Node node ) throws TokenException
   {
      nextToken();
      final Node result = new Node( NodeKind.POST_DEC, tok.line, tok.column );
      result.addChild( node );
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
      final Node result = new Node( NodeKind.DO, tok.line, tok.column, parseStatement() );
      consume( KeyWords.WHILE );
      result.addChild( parseCondition() );
      return result;
   }

   private Node parseDot( final Node node ) throws TokenException
   {
      nextToken();
      if ( tokIs( Operators.LEFT_PARENTHESIS ) )
      {
         nextToken();
         final Node result = new Node( NodeKind.E4X_FILTER, tok.line, tok.column );
         result.addChild( node );
         result.addChild( parseExpression() );
         consume( Operators.RIGHT_PARENTHESIS );
         return result;
      }
      else if ( tokIs( "*" ) )
      {
         final Node result = new Node( NodeKind.E4X_STAR, tok.line, tok.column );
         result.addChild( node );
         return result;
      }
      final Node result = new Node( NodeKind.DOT, tok.line, tok.column );
      result.addChild( node );
      result.addChild( parseExpression() );
      return result;
   }

   private Node parseE4XAttributeIdentifier() throws TokenException
   {
      consume( Operators.AT );

      final Node result = new Node( NodeKind.E4X_ATTR, tok.line, tok.column );
      if ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
      {
         nextToken();
         result.addChild( parseExpression() );
         consume( Operators.RIGHT_SQUARE_BRACKET );
      }
      else if ( tokIs( Operators.TIMES ) )
      {
         nextToken();
         result.addChild( new Node( NodeKind.STAR, tok.line, tok.column ) );
      }
      else
      {
         result.addChild( new Node( NodeKind.NAME, tok.line, tok.column, parseQualifiedName() ) );
      }
      return result;
   }

   private Node parseEmptyStatement() throws TokenException
   {
      Node result;
      result = new Node( NodeKind.STMT_EMPTY, tok.line, tok.column, Operators.SEMI_COLUMN.toString() );
      nextToken();
      return result;
   }

   private Node parseEncapsulatedExpression() throws TokenException
   {
      consume( Operators.LEFT_PARENTHESIS );
      final Node result = new Node( NodeKind.ENCAPSULATED, tok.line, tok.column );
      result.addChild( parseExpression() );
      consume( Operators.RIGHT_PARENTHESIS );
      return result;
   }

   private IParserNode parseEqualityExpression() throws TokenException
   {
      final Node result = new Node( NodeKind.EQUALITY, tok.line, tok.column, parseRelationalExpression() );
      while ( tokIs( Operators.DOUBLE_EQUAL )
            || tokIs( Operators.STRICTLY_EQUAL ) || tokIs( Operators.NON_EQUAL )
            || tokIs( Operators.NON_STRICTLY_EQUAL ) )
      {
         result.addChild( new Node( NodeKind.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseRelationalExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private IParserNode parseExpressionList() throws TokenException
   {
      final Node result = new Node( NodeKind.EXPR_LIST, tok.line, tok.column, parseAssignmentExpression() );
      while ( tokIs( Operators.COMMA ) )
      {
         nextToken();
         result.addChild( parseAssignmentExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private Node parseFinally() throws TokenException
   {
      Node result;
      nextToken();
      result = new Node( NodeKind.FINALLY, tok.line, tok.column, parseBlock() );
      return result;
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

      final Node result = new Node( NodeKind.FOREACH, tok.line, tok.column );
      if ( tokIs( KeyWords.VAR ) )
      {
         final Node var = new Node( NodeKind.VAR, tok.line, tok.column );
         nextToken();
         var.addChild( parseNameTypeInit() );
         result.addChild( var );
      }
      else
      {
         result.addChild( NodeKind.NAME,
                          tok.line,
                          tok.column,
                          tok.text );
         // names allowed?
         nextToken();
      }
      nextToken(); // in
      result.addChild( NodeKind.IN,
                       tok.line,
                       tok.column,
                       parseExpression() );
      consume( Operators.RIGHT_PARENTHESIS );
      result.addChild( parseStatement() );
      return result;
   }

   private Node parseForIn( final Node result ) throws TokenException
   {
      nextToken();
      result.addChild( NodeKind.IN,
                       tok.line,
                       tok.column,
                       parseExpression() );
      result.setId( NodeKind.FORIN );
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
      final Node result = new Node( findFunctionTypeFromSignature( signature ),
                                    tok.line,
                                    tok.column,
                                    signature[ 0 ].getStringValue() );

      result.addChild( convertMeta( meta ) );
      result.addChild( convertModifiers( modifiers ) );
      result.addChild( signature[ 1 ] );
      result.addChild( signature[ 2 ] );
      result.addChild( signature[ 3 ] );
      result.addChild( parseBlock() );
      return result;
   }

   private Node parseFunctionCall( final Node node ) throws TokenException
   {
      final Node result = new Node( NodeKind.CALL, tok.line, tok.column );
      result.addChild( node );
      while ( tokIs( Operators.LEFT_PARENTHESIS ) )
      {
         result.addChild( parseArgumentList() );
      }
      while ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
      {
         result.addChild( parseArrayLiteral() );
      }

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
      final Node result = new Node( findFunctionTypeFromSignature( signature ),
                                    tok.line,
                                    tok.column,
                                    signature[ 0 ].getStringValue() );
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
      final Node result = new Node( NodeKind.IF, tok.line, tok.column, parseCondition() );
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

      final Node result = new Node( NodeKind.IMPLEMENTS_LIST, tok.line, tok.column );
      result.addChild( NodeKind.IMPLEMENTS,
                       tok.line,
                       tok.column,
                       parseQualifiedName() );
      while ( tokIs( Operators.COMMA ) )
      {
         nextToken();
         result.addChild( NodeKind.IMPLEMENTS,
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
      final Node result = new Node( NodeKind.IMPORT, tok.line, tok.column, parseImportName() );
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

   private Node parseIncrement( final Node node ) throws TokenException
   {
      nextToken();
      final Node result = new Node( NodeKind.POST_INC, tok.line, tok.column );
      result.addChild( node );
      return result;
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
      final Node result = new Node( NodeKind.INTERFACE, tok.line, tok.column );

      result.addChild( NodeKind.NAME,
                       tok.line,
                       tok.column,
                       tok.text );
      nextToken(); // name
      result.addChild( convertMeta( meta ) );
      result.addChild( convertModifiers( modifier ) );

      if ( tokIs( KeyWords.EXTENDS ) )
      {
         nextToken(); // extends
         result.addChild( NodeKind.EXTENDS,
                          tok.line,
                          tok.column,
                          parseQualifiedName() );
      }
      while ( tokIs( Operators.COMMA ) )
      {
         nextToken(); // comma
         result.addChild( NodeKind.EXTENDS,
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
      final Node result = new Node( NodeKind.LAMBDA, tok.line, tok.column );
      result.addChild( parseParameterList() );
      result.addChild( parseOptionalType() );
      result.addChild( parseBlock() );
      return result;
   }

   private IParserNode parseLines( final String filePath,
                                   final String[] lines ) throws TokenException
   {
      setFileName( filePath );
      scn = new AS3Scanner();
      scn.setLines( lines );
      return parseCompilationUnit();
   }

   /**
    * tok is [ [id] [id ("test")] [id (name="test",type="a.b.c.Event")] exit
    * token is the first token after ]
    * 
    * @throws TokenException
    */
   private Node parseMetaData() throws TokenException
   {
      final StringBuffer buffer = new StringBuffer();

      consume( Operators.LEFT_SQUARE_BRACKET );
      while ( !tokIs( Operators.RIGHT_SQUARE_BRACKET ) )
      {
         if ( buffer.length() > 0 )
         {
            buffer.append( ' ' );
         }
         buffer.append( tok.text );
         nextToken();
      }
      skip( Operators.RIGHT_SQUARE_BRACKET );
      return new Node( NodeKind.META, tok.line, tok.column, buffer.toString() );
   }

   private IParserNode parseMultiplicativeExpression() throws TokenException
   {
      final Node result = new Node( NodeKind.MULTIPLICATION, tok.line, tok.column, parseUnaryExpression() );
      while ( tokIs( Operators.TIMES )
            || tokIs( Operators.SLASH ) || tokIs( Operators.MODULO ) )
      {
         result.addChild( new Node( NodeKind.OP, tok.line, tok.column, tok.text ) );
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
      final Node result = new Node( NodeKind.NAME_TYPE_INIT, tok.line, tok.column );
      result.addChild( NodeKind.NAME,
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

      final Node result = new Node( NodeKind.NEW, tok.line, tok.column );
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
      final Node result = new Node( NodeKind.OBJECT, tok.line, tok.column );
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
      final Node result = new Node( NodeKind.PROP, tok.line, tok.column );
      final Node name = new Node( NodeKind.NAME, tok.line, tok.column );
      name.setStringValue( tok.text );
      result.addChild( name );
      nextToken(); // name
      consume( Operators.COLUMN );
      result.addChild( NodeKind.VALUE,
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
      if ( tokIs( Operators.EQUAL ) )
      {
         nextToken();
         result = new Node( NodeKind.INIT, tok.line, tok.column, parseExpression() );
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
      final Node result = new Node( NodeKind.TYPE, tok.line, tok.column, "" );
      if ( tokIs( Operators.COLUMN ) )
      {
         nextToken();
         result.setStringValue( tok.text );
         nextToken();
      }
      return result;
   }

   private IParserNode parseOrExpression() throws TokenException
   {
      final Node result = new Node( NodeKind.OR, tok.line, tok.column, parseAndExpression() );
      while ( tokIs( Operators.LOGICAL_OR ) )
      {
         result.addChild( new Node( NodeKind.OP, tok.line, tok.column, tok.text ) );
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

      final Node result = new Node( NodeKind.PACKAGE, tok.line, tok.column );
      final StringBuffer nameBuffer = new StringBuffer();

      while ( !tokIs( Operators.LEFT_CURLY_BRACKET ) )
      {
         nameBuffer.append( tok.text );
         nextToken();
      }
      result.addChild( NodeKind.NAME,
                       tok.line,
                       tok.column,
                       nameBuffer.toString() );
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
      final Node result = new Node( NodeKind.PARAMETER, tok.line, tok.column );
      if ( tokIs( Operators.REST_PARAMETERS ) )
      {
         nextToken(); // ...
         final Node rest = new Node( NodeKind.REST, tok.line, tok.column, tok.text );
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

      final Node result = new Node( NodeKind.PARAMETER_LIST, tok.line, tok.column );
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
      final StringBuffer buffer = new StringBuffer();

      buffer.append( tok.text );
      nextToken();
      while ( tokIs( Operators.DOT )
            || tokIs( Operators.DOUBLE_COLUMN ) )
      {
         buffer.append( tok.text );
         nextToken();
         buffer.append( tok.text );
         nextToken(); // name
      }
      return buffer.toString();
   }

   private IParserNode parseRelationalExpression() throws TokenException
   {
      final Node result = new Node( NodeKind.RELATION, tok.line, tok.column, parseShiftExpression() );
      while ( tokIs( Operators.INFERIOR )
            || tokIs( Operators.INFERIOR_OR_EQUAL ) || tokIs( Operators.SUPERIOR )
            || tokIs( Operators.SUPERIOR_OR_EQUAL ) || tokIs( KeyWords.IS ) || tokIs( KeyWords.AS )
            || tokIs( KeyWords.INSTANCE_OF ) )
      {
         result.addChild( new Node( NodeKind.OP, tok.line, tok.column, tok.text ) );
         nextToken();
         result.addChild( parseShiftExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private IParserNode parseReturnStatement() throws TokenException
   {
      Node result;

      nextTokenAllowNewLine();
      if ( tokIs( NEW_LINE )
            || tokIs( Operators.SEMI_COLUMN ) )
      {
         nextToken();
         result = new Node( NodeKind.RETURN, tok.line, tok.column, "" );
      }
      else
      {
         result = new Node( NodeKind.RETURN, tok.line, tok.column, parseExpression() );
         skip( Operators.SEMI_COLUMN );
      }
      return result;
   }

   private IParserNode parseShiftExpression() throws TokenException
   {
      final Node result = new Node( NodeKind.SHIFT, tok.line, tok.column, parseAdditiveExpression() );
      while ( tokIs( Operators.DOUBLE_SHIFT_LEFT )
            || tokIs( Operators.TRIPLE_SHIFT_LEFT ) || tokIs( Operators.DOUBLE_SHIFT_RIGHT )
            || tokIs( Operators.TRIPLE_SHIFT_RIGHT ) )
      {
         result.addChild( new Node( NodeKind.OP, tok.line, tok.column, tok.text ) );
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
      final Node result = new Node( NodeKind.SWITCH, tok.line, tok.column, parseCondition() );
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
      final Node result = new Node( NodeKind.SWITCH_BLOCK, tok.line, tok.column );
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
      final Node result = new Node( NodeKind.CASES, tok.line, tok.column );
      for ( ;; )
      {
         if ( tokIs( Operators.RIGHT_CURLY_BRACKET ) )
         {
            break;
         }
         else if ( tokIs( KeyWords.CASE ) )
         {
            nextToken(); // case
            final Node caseNode = new Node( NodeKind.CASE, tok.line, tok.column, parseExpression() );
            consume( Operators.COLUMN );
            caseNode.addChild( parseSwitchBlock() );
            result.addChild( caseNode );
         }
         else if ( tokIs( KeyWords.DEFAULT ) )
         {
            nextToken(); // default
            consume( Operators.COLUMN );
            final Node caseNode = new Node( NodeKind.CASE,
                                            tok.line,
                                            tok.column,
                                            new Node( NodeKind.DEFAULT,
                                                      tok.line,
                                                      tok.column,
                                                      KeyWords.DEFAULT.toString() ) );
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

      final Node result = new Node( NodeKind.FOR, tok.line, tok.column );
      if ( !tokIs( Operators.SEMI_COLUMN ) )
      {
         if ( tokIs( KeyWords.VAR ) )
         {
            result.addChild( NodeKind.INIT,
                             tok.line,
                             tok.column,
                             parseVarList( null,
                                           null ) );
         }
         else
         {
            result.addChild( NodeKind.INIT,
                             tok.line,
                             tok.column,
                             parseExpressionList() );
         }
         if ( tokIs( NodeKind.IN.toString() ) )
         {
            return parseForIn( result );
         }
      }
      consume( Operators.SEMI_COLUMN );
      if ( !tokIs( Operators.SEMI_COLUMN ) )
      {
         result.addChild( NodeKind.COND,
                          tok.line,
                          tok.column,
                          parseExpression() );
      }
      consume( Operators.SEMI_COLUMN );
      if ( !tokIs( Operators.RIGHT_PARENTHESIS ) )
      {
         result.addChild( NodeKind.ITER,
                          tok.line,
                          tok.column,
                          parseExpressionList() );
      }
      consume( Operators.RIGHT_PARENTHESIS );
      result.addChild( parseStatement() );
      return result;
   }

   private Node parseTry() throws TokenException
   {
      Node result;
      nextToken();
      result = new Node( NodeKind.TRY, tok.line, tok.column, parseBlock() );
      return result;
   }

   private Node parseUnaryExpressionNotPlusMinus() throws TokenException
   {
      Node result;
      if ( tokIs( KeyWords.DELETE ) )
      {
         nextToken();
         result = new Node( NodeKind.DELETE, tok.line, tok.column, parseExpression() );
      }
      else if ( tokIs( KeyWords.VOID ) )
      {
         nextToken();
         result = new Node( NodeKind.VOID, tok.line, tok.column, parseExpression() );
      }
      else if ( tokIs( KeyWords.TYPEOF ) )
      {
         nextToken();
         result = new Node( NodeKind.TYPEOF, tok.line, tok.column, parseExpression() );
      }
      else if ( tokIs( "!" ) )
      {
         nextToken();
         result = new Node( NodeKind.NOT, tok.line, tok.column, parseExpression() );
      }
      else if ( tokIs( "~" ) )
      {
         nextToken();
         result = new Node( NodeKind.B_NOT, tok.line, tok.column, parseExpression() );
      }
      else
      {
         result = parseUnaryPostfixExpression();
      }
      return result;
   }

   private Node parseUnaryPostfixExpression() throws TokenException
   {
      Node node = parsePrimaryExpression();

      if ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
      {
         node = parseArrayAccessor( node );
      }
      else if ( tokIs( Operators.LEFT_PARENTHESIS ) )
      {
         node = parseFunctionCall( node );
      }
      if ( tokIs( Operators.INCREMENT ) )
      {
         node = parseIncrement( node );
      }
      else if ( tokIs( Operators.DECREMENT ) )
      {
         node = parseDecrement( node );
      }
      else if ( tokIs( Operators.DOT ) )
      {
         node = parseDot( node );
      }
      return node;
   }

   private Node parseUse() throws TokenException
   {
      consume( KeyWords.USE );
      return new Node( NodeKind.USE, tok.line, tok.column, parseNamespaceName() );
   }

   private Node parseVar() throws TokenException
   {
      Node result;
      result = parseVarList( null,
                             null );
      skip( Operators.SEMI_COLUMN );
      return result;
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
      final Node result = new Node( NodeKind.VAR_LIST, tok.line, tok.column );
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
      final Node result = new Node( NodeKind.WHILE, tok.line, tok.column );
      result.addChild( parseCondition() );
      result.addChild( parseStatement() );
      return result;
   }

   private void setFileName( final String fileNameToParse )
   {
      fileName = fileNameToParse;
   }

   private void skip( final Operators operator ) throws TokenException
   {
      skip( operator.toString() );
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

   private boolean tokIs( final KeyWords keyword )
   {
      return tok.text.equals( keyword.toString() );
   }

   private boolean tokIs( final Operators operator )
   {
      return tok.text.equals( operator.toString() );
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
