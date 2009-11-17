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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.adobe.ac.pmd.files.impl.FileUtils;
import com.adobe.ac.pmd.parser.IAS3Parser;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.KeyWords;
import com.adobe.ac.pmd.parser.NodeKind;
import com.adobe.ac.pmd.parser.Operators;
import com.adobe.ac.pmd.parser.exceptions.NullTokenException;
import com.adobe.ac.pmd.parser.exceptions.TokenException;
import com.adobe.ac.pmd.parser.exceptions.UnExpectedTokenException;

import de.bokelberg.flex.parser.AS3Scanner.Token;

public class AS3Parser implements IAS3Parser
{
   private static final String NEW_LINE = "\n";
   private String              fileName;
   private boolean             isInFor;
   private AS3Scanner          scn;
   private Token               tok;

   public AS3Parser()
   {
      this.scn = new AS3Scanner();
      isInFor = false;
   }

   /*
    * (non-Javadoc)
    * @see de.bokelberg.flex.parser.IAS3Parser#buildAst(java.lang.String)
    */
   public final IParserNode buildAst( final String filePath ) throws IOException,
                                                             TokenException
   {
      return parseLines( filePath,
                         FileUtils.readLines( new File( filePath ) ) );
   }

   public final IParserNode buildAst( final String filePath,
                                      final String[] scriptBlockLines ) throws TokenException
   {
      return parseLines( filePath,
                         scriptBlockLines );
   }

   final AS3Scanner getScn()
   {
      return scn;
   }

   /**
    * Get the next token Skip comments and newlines for now In the end we want
    * to keep them though.
    * 
    * @throws TokenException
    */
   final void nextToken() throws TokenException
   {
      do
      {
         nextTokenAllowNewLine();
      }
      while ( tok.getText().equals( NEW_LINE ) );
   }

   /**
    * tok is first content token
    * 
    * @throws TokenException
    */
   final Node parseClassContent() throws TokenException
   {
      final Node result = Node.create( NodeKind.CONTENT,
                                       tok.getLine(),
                                       tok.getColumn() );
      final List< Token > modifiers = new ArrayList< Token >();
      final List< Node > meta = new ArrayList< Node >();

      while ( !tokIs( Operators.RIGHT_CURLY_BRACKET ) )
      {
         if ( tokIs( Operators.LEFT_CURLY_BRACKET ) )
         {
            result.addChild( parseBlock() );
         }
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
   final Node parseCompilationUnit() throws TokenException
   {
      final Node result = Node.create( NodeKind.COMPILATION_UNIT,
                                       -1,
                                       -1 );

      nextToken();
      if ( tokIs( KeyWords.PACKAGE ) )
      {
         result.addChild( parsePackage() );
      }
      result.addChild( parsePackageContent() );
      return result;
   }

   final IParserNode parseExpression() throws TokenException
   {
      return parseAssignmentExpression();
   }

   /**
    * tok is first content token
    * 
    * @throws TokenException
    */
   final Node parseInterfaceContent() throws TokenException
   {
      final Node result = Node.create( NodeKind.CONTENT,
                                       tok.getLine(),
                                       tok.getColumn() );
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
         else if ( tokIs( KeyWords.INCLUDE ) )
         {
            result.addChild( parseIncludeExpression() );
         }
         else if ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
         {
            while ( !tokIs( Operators.RIGHT_SQUARE_BRACKET ) )
            {
               nextToken();
            }
            nextToken();
         }
         else
         {
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
   final Node parsePackageContent() throws TokenException
   {
      final Node result = Node.create( NodeKind.CONTENT,
                                       tok.getLine(),
                                       tok.getColumn() );
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

   final Node parsePrimaryExpression() throws TokenException
   {
      Node result = Node.create( NodeKind.PRIMARY,
                                 tok.getLine(),
                                 tok.getColumn(),
                                 tok.getText() );

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
      // else if ( tok.isNum()
      // || tokIs( KeyWords.TRUE ) || tokIs( KeyWords.FALSE ) || tokIs(
      // KeyWords.NULL )
      // || tok.getText().startsWith( Operators.DOUBLE_QUOTE.toString() )
      // || tok.getText().startsWith( Operators.SIMPLE_QUOTE.toString() )
      // || tok.getText().startsWith( Operators.SLASH.toString() )
      // || tok.getText().startsWith( Operators.INFERIOR.toString() ) || tokIs(
      // KeyWords.UNDEFINED ) )
      // {
      // nextToken();
      // }
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
   final IParserNode parseStatement() throws TokenException
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

   final Node parseUnaryExpression() throws TokenException
   {
      Node result;
      if ( tokIs( Operators.INCREMENT ) )
      {
         nextToken();
         result = Node.create( NodeKind.PRE_INC,
                               tok.getLine(),
                               tok.getColumn(),
                               parseUnaryExpression() );
      }
      else if ( tokIs( Operators.DECREMENT ) )
      {
         nextToken();
         result = Node.create( NodeKind.PRE_DEC,
                               tok.getLine(),
                               tok.getColumn(),
                               parseUnaryExpression() );
      }
      else if ( tokIs( Operators.MINUS ) )
      {
         nextToken();
         result = Node.create( NodeKind.MINUS,
                               tok.getLine(),
                               tok.getColumn(),
                               parseUnaryExpression() );
      }
      else if ( tokIs( Operators.PLUS ) )
      {
         nextToken();
         result = Node.create( NodeKind.PLUS,
                               tok.getLine(),
                               tok.getColumn(),
                               parseUnaryExpression() );
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
         throw new UnExpectedTokenException( tok.getText(), tok.getLine(), tok.getColumn(), fileName, text );
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

      final Node result = Node.create( NodeKind.META_LIST,
                                       tok.getLine(),
                                       tok.getColumn() );

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

      final Node result = Node.create( NodeKind.MOD_LIST,
                                       tok.getLine(),
                                       tok.getColumn() );

      for ( final Token modifierToken : modifierList )
      {
         result.addChild( NodeKind.MODIFIER,
                          tok.getLine(),
                          tok.getColumn(),
                          modifierToken.getText() );
      }
      return result;
   }

   private Node[] doParseSignature() throws TokenException
   {
      consume( KeyWords.FUNCTION );

      Node type = Node.create( NodeKind.TYPE,
                               tok.getLine(),
                               tok.getColumn(),
                               KeyWords.FUNCTION.toString() );
      if ( tokIs( KeyWords.SET )
            || tokIs( KeyWords.GET ) )
      {
         type = Node.create( NodeKind.TYPE,
                             tok.getLine(),
                             tok.getColumn(),
                             tok.getText() );
         nextToken(); // set or get
      }
      final Node name = Node.create( NodeKind.NAME,
                                     tok.getLine(),
                                     tok.getColumn(),
                                     tok.getText() );
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
            if ( node.getStringValue().equals( "set" ) )
            {
               return NodeKind.SET;
            }
            if ( node.getStringValue().equals( "get" ) )
            {
               return NodeKind.GET;
            }
            return NodeKind.FUNCTION;
         }
      }
      return NodeKind.FUNCTION;
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
          * st[ 1 ]; System.out.println( ste.getMethodName() + ":" +
          * tok.getText() ); }
          */
         if ( tok == null )
         {
            throw new NullTokenException( fileName );

         }
         if ( tok.getText() == null )
         {
            throw new NullTokenException( fileName );
         }
      }
      while ( tok.getText().startsWith( "//" )
            || tok.getText().startsWith( "/*" ) );
   }

   // ------------------------------------------------------------------------
   // language specific recursive descent parsing
   // ------------------------------------------------------------------------

   private IParserNode parseAdditiveExpression() throws TokenException
   {
      final Node result = Node.create( NodeKind.ADD,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseMultiplicativeExpression() );
      while ( tokIs( Operators.PLUS )
            || tokIs( Operators.MINUS ) )
      {
         result.addChild( Node.create( NodeKind.OP,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       tok.getText() ) );
         nextToken();
         result.addChild( parseMultiplicativeExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private IParserNode parseAndExpression() throws TokenException
   {
      final Node result = Node.create( NodeKind.AND,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseBitwiseOrExpression() );
      while ( tokIs( Operators.AND ) )
      {
         result.addChild( Node.create( NodeKind.OP,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       tok.getText() ) );
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
      final Node result = Node.create( NodeKind.ARGUMENTS,
                                       tok.getLine(),
                                       tok.getColumn() );
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
      final Node result = Node.create( NodeKind.ARRAY_ACCESSOR,
                                       tok.getLine(),
                                       tok.getColumn() );
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
      final Node result = Node.create( NodeKind.ARRAY,
                                       tok.getLine(),
                                       tok.getColumn() );
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
      final Node result = Node.create( NodeKind.ASSIGN,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseConditionalExpression() );
      while ( tokIs( Operators.EQUAL )
            || tokIs( Operators.PLUS_EQUAL ) || tokIs( Operators.MINUS_EQUAL )
            || tokIs( Operators.TIMES_EQUAL ) || tokIs( Operators.DIVIDED_EQUAL )
            || tokIs( Operators.MODULO_EQUAL ) || tokIs( Operators.AND_EQUAL ) || tokIs( Operators.OR_EQUAL )
            || tokIs( Operators.XOR_EQUAL ) )
      {
         result.addChild( Node.create( NodeKind.OP,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       tok.getText() ) );
         nextToken();
         result.addChild( parseExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private IParserNode parseBitwiseAndExpression() throws TokenException
   {
      final Node result = Node.create( NodeKind.B_AND,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseEqualityExpression() );
      while ( tokIs( Operators.B_AND ) )
      {
         result.addChild( Node.create( NodeKind.OP,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       tok.getText() ) );
         nextToken();
         result.addChild( parseEqualityExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private IParserNode parseBitwiseOrExpression() throws TokenException
   {
      final Node result = Node.create( NodeKind.B_OR,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseBitwiseXorExpression() );
      while ( tokIs( Operators.B_OR ) )
      {
         result.addChild( Node.create( NodeKind.OP,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       tok.getText() ) );
         nextToken();
         result.addChild( parseBitwiseXorExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private IParserNode parseBitwiseXorExpression() throws TokenException
   {
      final Node result = Node.create( NodeKind.B_XOR,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseBitwiseAndExpression() );
      while ( tokIs( Operators.B_XOR ) )
      {
         result.addChild( Node.create( NodeKind.OP,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       tok.getText() ) );
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

      final Node result = Node.create( NodeKind.BLOCK,
                                       tok.getLine(),
                                       tok.getColumn() );
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
      final Node result = Node.create( NodeKind.CATCH,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       Node.create( NodeKind.NAME,
                                                    tok.getLine(),
                                                    tok.getColumn(),
                                                    tok.getText() ) );
      nextToken(); // name
      if ( tokIs( Operators.COLUMN ) )
      {
         nextToken(); // :
         result.addChild( Node.create( NodeKind.TYPE,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       tok.getText() ) );
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

      final Node result = Node.create( NodeKind.CLASS,
                                       tok.getLine(),
                                       tok.getColumn() );
      result.addChild( NodeKind.NAME,
                       tok.getLine(),
                       tok.getColumn(),
                       tok.getText() );
      nextToken(); // name
      result.addChild( convertMeta( meta ) );
      result.addChild( convertModifiers( modifier ) );
      do
      {
         if ( tokIs( KeyWords.EXTENDS ) )
         {
            nextToken(); // extends
            result.addChild( NodeKind.EXTENDS,
                             tok.getLine(),
                             tok.getColumn(),
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
      final Node result = Node.create( NodeKind.CONDITION,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseExpression() );
      consume( Operators.RIGHT_PARENTHESIS );
      return result;
   }

   private IParserNode parseConditionalExpression() throws TokenException
   {
      final IParserNode result = parseOrExpression();
      if ( tokIs( Operators.QUESTION_MARK ) )
      {
         final Node conditional = Node.create( NodeKind.CONDITIONAL,
                                               tok.getLine(),
                                               tok.getColumn(),
                                               result );
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
      final Node result = Node.create( NodeKind.CONST_LIST,
                                       tok.getLine(),
                                       tok.getColumn() );
      result.addChild( convertMeta( meta ) );
      result.addChild( convertModifiers( modifiers ) );
      collectVarListContent( result );
      return result;
   }

   private Node parseDecrement( final Node node ) throws TokenException
   {
      nextToken();
      final Node result = Node.create( NodeKind.POST_DEC,
                                       tok.getLine(),
                                       tok.getColumn() );
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
      final Node result = Node.create( NodeKind.DO,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseStatement() );
      consume( KeyWords.WHILE );
      result.addChild( parseCondition() );
      if ( tokIs( Operators.SEMI_COLUMN ) )
      {
         nextToken();
      }
      return result;
   }

   private Node parseDot( final Node node ) throws TokenException
   {
      nextToken();
      if ( tokIs( Operators.LEFT_PARENTHESIS ) )
      {
         nextToken();
         final Node result = Node.create( NodeKind.E4X_FILTER,
                                          tok.getLine(),
                                          tok.getColumn() );
         result.addChild( node );
         result.addChild( parseExpression() );
         consume( Operators.RIGHT_PARENTHESIS );
         return result;
      }
      else if ( tokIs( Operators.TIMES ) )
      {
         final Node result = Node.create( NodeKind.E4X_STAR,
                                          tok.getLine(),
                                          tok.getColumn() );
         result.addChild( node );
         return result;
      }
      // else if ( tokIs( Operators.AT ) )
      // {
      // return parseE4XAttributeIdentifier();
      // }
      final Node result = Node.create( NodeKind.DOT,
                                       tok.getLine(),
                                       tok.getColumn() );
      result.addChild( node );
      result.addChild( parseExpression() );
      return result;
   }

   // private Node parseE4XAttributeIdentifier() throws TokenException
   // {
   // consume( Operators.AT );
   //
   // final Node result = Node.create( NodeKind.E4X_ATTR,
   // tok.getLine(),
   // tok.getColumn() );
   // if ( tokIs( Operators.LEFT_SQUARE_BRACKET ) )
   // {
   // nextToken();
   // result.addChild( parseExpression() );
   // consume( Operators.RIGHT_SQUARE_BRACKET );
   // }
   // else if ( tokIs( Operators.TIMES ) )
   // {
   // nextToken();
   // result.addChild( Node.create( NodeKind.STAR,
   // tok.getLine(),
   // tok.getColumn() ) );
   // }
   // else
   // {
   // result.addChild( Node.create( NodeKind.NAME,
   // tok.getLine(),
   // tok.getColumn(),
   // parseQualifiedName() ) );
   // }
   // return result;
   // }

   private Node parseEmptyStatement() throws TokenException
   {
      Node result;
      result = Node.create( NodeKind.STMT_EMPTY,
                            tok.getLine(),
                            tok.getColumn(),
                            Operators.SEMI_COLUMN.toString() );
      nextToken();
      return result;
   }

   private Node parseEncapsulatedExpression() throws TokenException
   {
      consume( Operators.LEFT_PARENTHESIS );
      final Node result = Node.create( NodeKind.ENCAPSULATED,
                                       tok.getLine(),
                                       tok.getColumn() );
      result.addChild( parseExpression() );
      consume( Operators.RIGHT_PARENTHESIS );
      return result;
   }

   private IParserNode parseEqualityExpression() throws TokenException
   {
      final Node result = Node.create( NodeKind.EQUALITY,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseRelationalExpression() );
      while ( tokIs( Operators.DOUBLE_EQUAL )
            || tokIs( Operators.STRICTLY_EQUAL ) || tokIs( Operators.NON_EQUAL )
            || tokIs( Operators.NON_STRICTLY_EQUAL ) )
      {
         result.addChild( Node.create( NodeKind.OP,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       tok.getText() ) );
         nextToken();
         result.addChild( parseRelationalExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private IParserNode parseExpressionList() throws TokenException
   {
      final Node result = Node.create( NodeKind.EXPR_LIST,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseAssignmentExpression() );
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
      result = Node.create( NodeKind.FINALLY,
                            tok.getLine(),
                            tok.getColumn(),
                            parseBlock() );
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

      final Node result = Node.create( NodeKind.FOREACH,
                                       tok.getLine(),
                                       tok.getColumn() );
      if ( tokIs( KeyWords.VAR ) )
      {
         final Node var = Node.create( NodeKind.VAR,
                                       tok.getLine(),
                                       tok.getColumn() );
         nextToken();
         var.addChild( parseNameTypeInit() );
         result.addChild( var );
      }
      else
      {
         result.addChild( NodeKind.NAME,
                          tok.getLine(),
                          tok.getColumn(),
                          tok.getText() );
         // names allowed?
         nextToken();
      }
      nextToken(); // in
      result.addChild( NodeKind.IN,
                       tok.getLine(),
                       tok.getColumn(),
                       parseExpression() );
      consume( Operators.RIGHT_PARENTHESIS );
      result.addChild( parseStatement() );
      return result;
   }

   private Node parseForIn( final Node result ) throws TokenException
   {
      nextToken();
      result.addChild( NodeKind.IN,
                       tok.getLine(),
                       tok.getColumn(),
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
      final Node result = Node.create( findFunctionTypeFromSignature( signature ),
                                       tok.getLine(),
                                       tok.getColumn(),
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
      final Node result = Node.create( NodeKind.CALL,
                                       tok.getLine(),
                                       tok.getColumn() );
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
      final Node result = Node.create( findFunctionTypeFromSignature( signature ),
                                       tok.getLine(),
                                       tok.getColumn(),
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
      final Node result = Node.create( NodeKind.IF,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseCondition() );
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

      final Node result = Node.create( NodeKind.IMPLEMENTS_LIST,
                                       tok.getLine(),
                                       tok.getColumn() );
      result.addChild( NodeKind.IMPLEMENTS,
                       tok.getLine(),
                       tok.getColumn(),
                       parseQualifiedName() );
      while ( tokIs( Operators.COMMA ) )
      {
         nextToken();
         result.addChild( NodeKind.IMPLEMENTS,
                          tok.getLine(),
                          tok.getColumn(),
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
      final Node result = Node.create( NodeKind.IMPORT,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseImportName() );
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

      result.append( tok.getText() );
      nextToken();
      while ( tokIs( Operators.DOT ) )
      {
         result.append( Operators.DOT );
         nextToken(); // .
         result.append( tok.getText() );
         nextToken(); // part of name
      }
      return result.toString();
   }

   private IParserNode parseIncludeExpression() throws TokenException
   {
      final Node result = Node.create( NodeKind.INCLUDE,
                                       tok.getLine(),
                                       tok.getColumn() );
      consume( KeyWords.INCLUDE );
      result.addChild( parseExpression() );
      return result;
   }

   private Node parseIncrement( final Node node ) throws TokenException
   {
      nextToken();
      final Node result = Node.create( NodeKind.POST_INC,
                                       tok.getLine(),
                                       tok.getColumn() );
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
      final Node result = Node.create( NodeKind.INTERFACE,
                                       tok.getLine(),
                                       tok.getColumn() );

      result.addChild( NodeKind.NAME,
                       tok.getLine(),
                       tok.getColumn(),
                       tok.getText() );
      nextToken(); // name
      result.addChild( convertMeta( meta ) );
      result.addChild( convertModifiers( modifier ) );

      if ( tokIs( KeyWords.EXTENDS ) )
      {
         nextToken(); // extends
         result.addChild( NodeKind.EXTENDS,
                          tok.getLine(),
                          tok.getColumn(),
                          parseQualifiedName() );
      }
      while ( tokIs( Operators.COMMA ) )
      {
         nextToken(); // comma
         result.addChild( NodeKind.EXTENDS,
                          tok.getLine(),
                          tok.getColumn(),
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
      final Node result = Node.create( NodeKind.LAMBDA,
                                       tok.getLine(),
                                       tok.getColumn() );
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
         buffer.append( tok.getText() );
         nextToken();
      }
      skip( Operators.RIGHT_SQUARE_BRACKET );
      return Node.create( NodeKind.META,
                          tok.getLine(),
                          tok.getColumn(),
                          buffer.toString() );
   }

   private IParserNode parseMultiplicativeExpression() throws TokenException
   {
      final Node result = Node.create( NodeKind.MULTIPLICATION,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseUnaryExpression() );
      while ( tokIs( Operators.TIMES )
            || tokIs( Operators.SLASH ) || tokIs( Operators.MODULO ) )
      {
         result.addChild( Node.create( NodeKind.OP,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       tok.getText() ) );
         nextToken();
         result.addChild( parseUnaryExpression() );
      }
      return result.numChildren() > 1 ? result
                                     : result.getChild( 0 );
   }

   private String parseNamespaceName() throws TokenException
   {
      final String name = tok.getText();
      nextToken(); // simple name for now
      return name;
   }

   private Node parseNameTypeInit() throws TokenException
   {
      final Node result = Node.create( NodeKind.NAME_TYPE_INIT,
                                       tok.getLine(),
                                       tok.getColumn() );
      result.addChild( NodeKind.NAME,
                       tok.getLine(),
                       tok.getColumn(),
                       tok.getText() );
      nextToken(); // name
      result.addChild( parseOptionalType() );
      result.addChild( parseOptionalInit() );
      return result;
   }

   private Node parseNewExpression() throws TokenException
   {
      consume( KeyWords.NEW );

      final Node result = Node.create( NodeKind.NEW,
                                       tok.getLine(),
                                       tok.getColumn() );
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
      final Node result = Node.create( NodeKind.OBJECT,
                                       tok.getLine(),
                                       tok.getColumn() );
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
      final Node result = Node.create( NodeKind.PROP,
                                       tok.getLine(),
                                       tok.getColumn() );
      final Node name = Node.create( NodeKind.NAME,
                                     tok.getLine(),
                                     tok.getColumn(),
                                     tok.getText() );
      result.addChild( name );
      nextToken(); // name
      consume( Operators.COLUMN );
      result.addChild( NodeKind.VALUE,
                       tok.getLine(),
                       tok.getColumn(),
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
         result = Node.create( NodeKind.INIT,
                               tok.getLine(),
                               tok.getColumn(),
                               parseExpression() );
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
      Node result = Node.create( NodeKind.TYPE,
                                 tok.getLine(),
                                 tok.getColumn(),
                                 "" );
      if ( tokIs( Operators.COLUMN ) )
      {
         nextToken();
         result = Node.create( NodeKind.TYPE,
                               tok.getLine(),
                               tok.getColumn(),
                               tok.getText() );
         nextToken();
      }
      return result;
   }

   private IParserNode parseOrExpression() throws TokenException
   {
      final Node result = Node.create( NodeKind.OR,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseAndExpression() );
      while ( tokIs( Operators.LOGICAL_OR ) )
      {
         result.addChild( Node.create( NodeKind.OP,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       tok.getText() ) );
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

      final Node result = Node.create( NodeKind.PACKAGE,
                                       tok.getLine(),
                                       tok.getColumn() );
      final StringBuffer nameBuffer = new StringBuffer();

      while ( !tokIs( Operators.LEFT_CURLY_BRACKET ) )
      {
         nameBuffer.append( tok.getText() );
         nextToken();
      }
      result.addChild( NodeKind.NAME,
                       tok.getLine(),
                       tok.getColumn(),
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
      final Node result = Node.create( NodeKind.PARAMETER,
                                       tok.getLine(),
                                       tok.getColumn() );
      if ( tokIs( Operators.REST_PARAMETERS ) )
      {
         nextToken(); // ...
         final Node rest = Node.create( NodeKind.REST,
                                        tok.getLine(),
                                        tok.getColumn(),
                                        tok.getText() );
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

      final Node result = Node.create( NodeKind.PARAMETER_LIST,
                                       tok.getLine(),
                                       tok.getColumn() );
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

      buffer.append( tok.getText() );
      nextToken();
      while ( tokIs( Operators.DOT )
            || tokIs( Operators.DOUBLE_COLUMN ) )
      {
         buffer.append( tok.getText() );
         nextToken();
         buffer.append( tok.getText() );
         nextToken(); // name
      }
      return buffer.toString();
   }

   private IParserNode parseRelationalExpression() throws TokenException
   {
      final Node result = Node.create( NodeKind.RELATION,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseShiftExpression() );
      while ( tokIs( Operators.INFERIOR )
            || tokIs( Operators.INFERIOR_OR_EQUAL ) || tokIs( Operators.SUPERIOR )
            || tokIs( Operators.SUPERIOR_OR_EQUAL ) || tokIs( KeyWords.IS ) || tokIs( KeyWords.IN )
            && !isInFor || tokIs( KeyWords.AS ) || tokIs( KeyWords.INSTANCE_OF ) )
      {
         result.addChild( Node.create( NodeKind.OP,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       tok.getText() ) );
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
         result = Node.create( NodeKind.RETURN,
                               tok.getLine(),
                               tok.getColumn(),
                               "" );
      }
      else
      {
         result = Node.create( NodeKind.RETURN,
                               tok.getLine(),
                               tok.getColumn(),
                               parseExpression() );
         skip( Operators.SEMI_COLUMN );
      }
      return result;
   }

   private IParserNode parseShiftExpression() throws TokenException
   {
      final Node result = Node.create( NodeKind.SHIFT,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseAdditiveExpression() );
      while ( tokIs( Operators.DOUBLE_SHIFT_LEFT )
            || tokIs( Operators.TRIPLE_SHIFT_LEFT ) || tokIs( Operators.DOUBLE_SHIFT_RIGHT )
            || tokIs( Operators.TRIPLE_SHIFT_RIGHT ) )
      {
         result.addChild( Node.create( NodeKind.OP,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       tok.getText() ) );
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
      final Node result = Node.create( NodeKind.SWITCH,
                                       tok.getLine(),
                                       tok.getColumn(),
                                       parseCondition() );
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
      final Node result = Node.create( NodeKind.SWITCH_BLOCK,
                                       tok.getLine(),
                                       tok.getColumn() );
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
      final Node result = Node.create( NodeKind.CASES,
                                       tok.getLine(),
                                       tok.getColumn() );
      for ( ;; )
      {
         if ( tokIs( Operators.RIGHT_CURLY_BRACKET ) )
         {
            break;
         }
         else if ( tokIs( KeyWords.CASE ) )
         {
            nextToken(); // case
            final Node caseNode = Node.create( NodeKind.CASE,
                                               tok.getLine(),
                                               tok.getColumn(),
                                               parseExpression() );
            consume( Operators.COLUMN );
            caseNode.addChild( parseSwitchBlock() );
            result.addChild( caseNode );
         }
         else if ( tokIs( KeyWords.DEFAULT ) )
         {
            nextToken(); // default
            consume( Operators.COLUMN );
            final Node caseNode = Node.create( NodeKind.CASE,
                                               tok.getLine(),
                                               tok.getColumn(),
                                               Node.create( NodeKind.DEFAULT,
                                                            tok.getLine(),
                                                            tok.getColumn(),
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

      final Node result = Node.create( NodeKind.FOR,
                                       tok.getLine(),
                                       tok.getColumn() );
      if ( !tokIs( Operators.SEMI_COLUMN ) )
      {
         if ( tokIs( KeyWords.VAR ) )
         {
            result.addChild( NodeKind.INIT,
                             tok.getLine(),
                             tok.getColumn(),
                             parseVarList( null,
                                           null ) );
         }
         else
         {
            isInFor = true;
            result.addChild( NodeKind.INIT,
                             tok.getLine(),
                             tok.getColumn(),
                             parseExpression() );
            isInFor = false;
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
                          tok.getLine(),
                          tok.getColumn(),
                          parseExpression() );
      }
      consume( Operators.SEMI_COLUMN );
      if ( !tokIs( Operators.RIGHT_PARENTHESIS ) )
      {
         result.addChild( NodeKind.ITER,
                          tok.getLine(),
                          tok.getColumn(),
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
      result = Node.create( NodeKind.TRY,
                            tok.getLine(),
                            tok.getColumn(),
                            parseBlock() );
      return result;
   }

   private Node parseUnaryExpressionNotPlusMinus() throws TokenException
   {
      Node result;
      if ( tokIs( KeyWords.DELETE ) )
      {
         nextToken();
         result = Node.create( NodeKind.DELETE,
                               tok.getLine(),
                               tok.getColumn(),
                               parseExpression() );
      }
      else if ( tokIs( KeyWords.VOID ) )
      {
         nextToken();
         result = Node.create( NodeKind.VOID,
                               tok.getLine(),
                               tok.getColumn(),
                               parseExpression() );
      }
      else if ( tokIs( KeyWords.TYPEOF ) )
      {
         nextToken();
         result = Node.create( NodeKind.TYPEOF,
                               tok.getLine(),
                               tok.getColumn(),
                               parseExpression() );
      }
      else if ( tokIs( "!" ) )
      {
         nextToken();
         result = Node.create( NodeKind.NOT,
                               tok.getLine(),
                               tok.getColumn(),
                               parseExpression() );
      }
      else if ( tokIs( "~" ) )
      {
         nextToken();
         result = Node.create( NodeKind.B_NOT,
                               tok.getLine(),
                               tok.getColumn(),
                               parseExpression() );
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
      else if ( tokIs( Operators.DOT )
            || tokIs( Operators.DOUBLE_COLUMN ) )
      {
         node = parseDot( node );
      }
      return node;
   }

   private Node parseUse() throws TokenException
   {
      consume( KeyWords.USE );
      return Node.create( NodeKind.USE,
                          tok.getLine(),
                          tok.getColumn(),
                          parseNamespaceName() );
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
      final Node result = Node.create( NodeKind.VAR_LIST,
                                       tok.getLine(),
                                       tok.getColumn() );
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
      final Node result = Node.create( NodeKind.WHILE,
                                       tok.getLine(),
                                       tok.getColumn() );
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
      return tok.getText().compareTo( keyword.toString() ) == 0;
   }

   private boolean tokIs( final Operators operator )
   {
      return tok.getText().compareTo( operator.toString() ) == 0;
   }

   /**
    * Compare the current token to the parameter
    * 
    * @param text
    * @return true, if tok's text property equals the parameter
    */
   private boolean tokIs( final String text )
   {
      return tok.getText().equals( text );
   }
}
