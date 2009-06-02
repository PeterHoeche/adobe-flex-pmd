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
package com.adobe.ac.pmd.rules.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.adobe.ac.pmd.StackTraceUtils;
import com.adobe.ac.pmd.Violation;
import com.adobe.ac.pmd.files.AbstractFlexFile;
import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.nodes.utils.FunctionUtils;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.KeyWords;
import com.adobe.ac.pmd.parser.NodeKind;

/**
 * Abstract class for AST-based rule Extends this class if your rule is only
 * detectable in an AS file, which can be converted into an Abstract Synthax
 * Tree. Then you will be able to either use the visitor pattern, or to iterate
 * from the package node, i in order to find your violation(s).
 * 
 * @author xagnetti
 */
public abstract class AbstractAstFlexRule extends AbstractFlexRule
{
   private static final Logger             LOGGER = Logger.getLogger( AbstractAstFlexRule.class.getName() );
   private AbstractFlexFile                currentFile;
   private Map< String, AbstractFlexFile > filesInSourcePath;
   private final List< Violation >         violations;

   public AbstractAstFlexRule()
   {
      super();

      violations = new ArrayList< Violation >();
   }

   public boolean isConcernedByTheGivenFile( final AbstractFlexFile file )
   {
      return true;
   }

   protected final void addViolation( final IFunction function )
   {
      final IParserNode name = FunctionUtils.extractNameNode( function.getInternalNode() );

      addViolation( name,
                    name );
   }

   /**
    * @param beginningNode
    * @param endNode
    * @return the added violation replacing the threshold value in the message
    *         if any.
    */
   final protected Violation addViolation( final IParserNode beginningNode,
                                           final IParserNode endNode )
   {
      final Violation violation = new Violation( new ViolationPosition( beginningNode.getLine(),
                                                                        endNode.getLine(),
                                                                        beginningNode.getColumn(),
                                                                        endNode.getColumn() ),
                                                 this,
                                                 currentFile );

      prettyPrintMessage( violation );
      violations.add( violation );

      return violation;
   }

   /**
    * @param beginningNode
    * @param endNode
    * @param messageToReplace
    * @return the add violation replacing the {0} token by the specified message
    */
   final protected Violation addViolation( final IParserNode beginningNode,
                                           final IParserNode endNode,
                                           final String messageToReplace )
   {
      final Violation violation = new Violation( new ViolationPosition( beginningNode.getLine(),
                                                                        endNode.getLine(),
                                                                        beginningNode.getColumn(),
                                                                        endNode.getColumn() ),
                                                 this,
                                                 currentFile );

      violation.replacePlaceholderInMessage( messageToReplace );
      violations.add( violation );

      return violation;
   }

   protected void findViolationsFromAttributesList( final List< IAttribute > variables )
   {
   }

   protected void findViolationsFromClassNode( final IClass classNode )
   {
      if ( classNode.getAttributes() != null )
      {
         findViolationsFromAttributesList( classNode.getAttributes() );
      }
      if ( classNode.getConstants() != null )
      {
         findViolationsFromConstantsList( classNode.getConstants() );
      }
      if ( classNode.getFunctions() != null )
      {
         findViolationsFromFunctionsList( classNode.getFunctions() );
      }
      if ( classNode.getConstructor() != null )
      {
         findViolationsFromConstructor( classNode.getConstructor() );
      }
   }

   protected void findViolationsFromConstantsList( final List< IConstant > constants )
   {
   }

   protected void findViolationsFromConstructor( final IFunction constructor )
   {
   }

   protected void findViolationsFromFunctionsList( final List< IFunction > functions )
   {
   }

   /**
    * Override this method if you need to find violations from the package ( or
    * any subsequent node like class or function)
    * 
    * @param packageNode
    */
   protected void findViolationsFromPackageNode( final IPackage packageNode )
   {
   }

   protected AbstractFlexFile getCurrentFile()
   {
      return currentFile;
   }

   protected Map< String, AbstractFlexFile > getFilesInSourcePath()
   {
      return filesInSourcePath;
   }

   @Override
   final protected List< Violation > processFileBody( final IPackage packageNode,
                                                      final AbstractFlexFile file,
                                                      final Map< String, AbstractFlexFile > files )
   {
      currentFile = file;
      filesInSourcePath = files;
      try
      {
         if ( packageNode != null )
         {
            visitNodes( packageNode.getInternalNode() );
            findViolationsFromPackageNode( packageNode );
            final IClass classNode = packageNode.getClassNode();

            if ( classNode != null )
            {
               findViolationsFromClassNode( classNode );
            }
         }
      }
      catch ( final Exception e )
      {
         LOGGER.warning( StackTraceUtils.print( e ) );
      }
      final List< Violation > copy = new ArrayList< Violation >( violations );

      violations.clear();

      return copy;
   }

   protected void visitAdditiveExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.ADD ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            final IParserNode node = iterator.next();
            visitMultiplicativeExpression( node );
            while ( iterator.hasNext() )
            {
               iterator.next();
               visitMultiplicativeExpression( iterator.next() );
            }
         }
         else
         {
            visitMultiplicativeExpression( ast );
         }
      }
   }

   protected void visitAndExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.AND ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            final IParserNode node = iterator.next();
            visitBitwiseOrExpression( node );
            while ( iterator.hasNext() )
            {
               iterator.next();
               visitBitwiseOrExpression( iterator.next() );
            }
         }
         else
         {
            visitBitwiseOrExpression( ast );
         }
      }
   }

   protected void visitArrayAccessor( final IParserNode ast )
   {
      final Iterator< IParserNode > iterator = ast.getChildren().iterator();
      visitExpression( iterator.next() );
      do
      {
         visitExpression( iterator.next() );
      }
      while ( iterator.hasNext() );
   }

   protected void visitBitwiseAndExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.B_AND ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            final IParserNode node = iterator.next();
            visitEqualityExpression( node );
            while ( iterator.hasNext() )
            {
               iterator.next();
               visitEqualityExpression( iterator.next() );
            }
         }
         else
         {
            visitEqualityExpression( ast );
         }
      }
   }

   protected void visitBitwiseOrExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.B_OR ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            final IParserNode node = iterator.next();
            visitBitwiseXorExpression( node );
            while ( iterator.hasNext() )
            {
               iterator.next();
               visitBitwiseXorExpression( iterator.next() );
            }
         }
         else
         {
            visitBitwiseXorExpression( ast );
         }
      }
   }

   protected void visitBitwiseXorExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.B_OR ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            final IParserNode node = iterator.next();
            visitBitwiseAndExpression( node );
            while ( iterator.hasNext() )
            {
               iterator.next();
               visitBitwiseAndExpression( iterator.next() );
            }
         }
         else
         {
            visitBitwiseAndExpression( ast );
         }
      }
   }

   protected void visitBlock( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final IParserNode node : ast.getChildren() )
         {
            visitStatement( node );
         }
      }
   }

   protected void visitCatch( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitNameTypeInit( ast.getChild( 0 ) );
         visitBlock( ast.getChild( 1 ) );
      }
   }

   protected void visitClass( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         IParserNode modifiers = null;
         IParserNode implement = null;
         IParserNode content = null;
         for ( final IParserNode node : ast.getChildren() )
         {
            if ( node.is( NodeKind.MOD_LIST ) )
            {
               modifiers = node;
            }
            else if ( node.is( NodeKind.IMPLEMENTS_LIST ) )
            {
               implement = node;
            }
            else if ( node.is( NodeKind.CONTENT ) )
            {
               content = node;
            }
         }
         visitModifiers( modifiers );
         if ( implement != null )
         {
            visitImplementsList( implement );
         }
         visitClassContent( content );
      }
   }

   protected void visitClassContent( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final IParserNode node : ast.getChildren() )
         {
            if ( node.is( NodeKind.VAR_LIST ) )
            {
               visitVarOrConstList( node,
                                    KeyWords.VAR );
            }
            else if ( node.is( NodeKind.CONST_LIST ) )
            {
               visitVarOrConstList( node,
                                    KeyWords.CONST );
            }
            else if ( node.is( NodeKind.FUNCTION ) )
            {
               visitFunction( node,
                              "" );
            }
            else if ( node.is( NodeKind.SET ) )
            {
               visitFunction( node,
                              "set " );
            }
            else if ( node.is( NodeKind.GET ) )
            {
               visitFunction( node,
                              "get " );
            }
         }
      }
   }

   protected void visitCompilationUnit( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final IParserNode node : ast.getChildren() )
         {
            if ( node.is( NodeKind.PACKAGE ) )
            {
               if ( node.numChildren() >= 2 )
               {
                  visitPackageContent( node.getChild( 1 ) );
               }
            }
            else
            {
               if ( node.numChildren() > 0 )
               {
                  visitPackageContent( node );
               }
            }
         }
      }
   }

   protected void visitConditionalExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.CONDITIONAL ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            final IParserNode node = iterator.next();
            visitOrExpression( node );
            while ( iterator.hasNext() )
            {
               visitExpression( iterator.next() );
               visitExpression( iterator.next() );
            }
         }
         else
         {
            visitOrExpression( ast );
         }
      }
   }

   protected void visitDo( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitBlock( ast.getChild( 0 ) );
         visitExpression( ast.getChild( 1 ) );
      }
   }

   protected void visitEqualityExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.EQUALITY ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            final IParserNode node = iterator.next();
            visitRelationalExpression( node );
            while ( iterator.hasNext() )
            {
               iterator.next();
               visitRelationalExpression( iterator.next() );
            }
         }
         else
         {
            visitRelationalExpression( ast );
         }
      }
   }

   protected void visitExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.ASSIGN ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            final IParserNode node = iterator.next();
            visitConditionalExpression( node );
            while ( iterator.hasNext() )
            {
               iterator.next();
               visitConditionalExpression( iterator.next() );
            }
         }
         else
         {
            visitConditionalExpression( ast );
         }
      }
   }

   protected void visitExpressionList( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final IParserNode node : ast.getChildren() )
         {
            visitExpression( node );
         }
      }
   }

   protected void visitFinally( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitBlock( ast.getChild( 0 ) );
      }
   }

   protected void visitFor( final IParserNode ast )
   {
      if ( ast.numChildren() > 3 )
      {
         visitBlock( ast.getChild( 3 ) );
      }
   }

   protected void visitForEach( final IParserNode ast )
   {
      if ( ast.numChildren() > 2 )
      {
         visitBlock( ast.getChild( 2 ) );
      }
   }

   protected void visitForIn( final IParserNode ast )
   {
   }

   protected void visitFunction( final IParserNode ast,
                                 final String type )
   {
      if ( isNodeNavigable( ast ) )
      {
         final Iterator< IParserNode > iterator = ast.getChildren().iterator();
         IParserNode node = iterator.next();

         if ( node.is( NodeKind.META_LIST ) )
         {
            visitMetaData( node );
            node = iterator.next();
         }
         if ( node.is( NodeKind.MOD_LIST ) )
         {
            visitModifiers( node );
            node = iterator.next();
         }
         node = iterator.next();
         visitParameters( node );
         node = iterator.next();
         visitFunctionReturnType( node );
         if ( iterator.hasNext() )
         {
            node = iterator.next();
            visitFunctionBody( node );
         }
      }
   }

   protected void visitFunctionBody( final IParserNode node )
   {
      visitBlock( node );
   }

   protected void visitFunctionReturnType( final IParserNode node )
   {
      visitBlock( node );
   }

   protected void visitIf( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitExpression( ast.getChild( 0 ) );
         visitStatement( ast.getChild( 1 ) );
         if ( ast.numChildren() == 2 )
         {
            visitStatement( ast.getChild( 2 ) );
         }
      }
   }

   protected void visitImplementsList( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final IParserNode node : ast.getChildren() )
         {
            visitImplementsListChildren( node );
         }
      }
   }

   /**
    * Overrides it if you need to visit each implementation
    * 
    * @param next
    */
   protected void visitImplementsListChildren( final IParserNode next )
   {
   }

   protected void visitInterface( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         IParserNode modifiers = null;
         for ( final IParserNode node : ast.getChildren() )
         {
            if ( node.is( NodeKind.MOD_LIST ) )
            {
               modifiers = node;
            }
         }

         visitModifiers( modifiers );
      }
   }

   /**
    * Overrides it if you need to visit a metadata node
    * 
    * @param node
    */
   protected void visitMetaData( final IParserNode node )
   {
   }

   /**
    * Overrides it if you need to visit a modifier
    * 
    * @param ast
    */
   protected void visitModifiers( final IParserNode ast )
   {
   }

   protected void visitMultiplicativeExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.MULTIPLICATION ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            final IParserNode node = iterator.next();
            visitUnaryExpression( node );
            while ( iterator.hasNext() )
            {
               iterator.next();
               visitUnaryExpression( iterator.next() );
            }
         }
         else
         {
            visitUnaryExpression( ast );
         }
      }
   }

   protected void visitNodes( final IParserNode ast )
   {
      visitCompilationUnit( ast );
   }

   protected void visitOrExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.OR ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            final IParserNode node = iterator.next();
            visitAndExpression( node );
            while ( iterator.hasNext() )
            {
               iterator.next();
               visitAndExpression( iterator.next() );
            }
         }
         else
         {
            visitAndExpression( ast );
         }
      }
   }

   protected void visitPackageContent( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final IParserNode node : ast.getChildren() )
         {
            if ( node.is( NodeKind.CLASS ) )
            {
               visitClass( node );
            }
            else if ( node.is( NodeKind.INTERFACE ) )
            {
               visitInterface( node );
            }
         }
      }
   }

   protected void visitParameters( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final IParserNode node2 : ast.getChildren() )
         {
            final IParserNode node = node2.getChild( 0 );

            if ( node.is( NodeKind.NAME_TYPE_INIT ) )
            {
               visitNameTypeInit( node );
            }
         }
      }
   }

   protected void visitPrimaryExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.numChildren() != 0
               && ast.is( NodeKind.ARRAY ) )
         {
            visitExpressionList( ast );
         }
         else if ( ast.is( NodeKind.OBJECT ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            while ( iterator.hasNext() )
            {
               final IParserNode node = iterator.next();
               visitExpression( node.getChild( 1 ) );
            }
         }
         else if ( ast.is( NodeKind.NEW ) )
         {
            visitExpression( ast.getChild( 0 ) );
            visitExpressionList( ast.getChild( 1 ) );
         }
         else if ( ast.is( NodeKind.ENCAPSULATED ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( NodeKind.E4X_ATTR ) )
         {
            final IParserNode node = ast.getChild( 0 );

            if ( !node.is( NodeKind.NAME )
                  && !node.is( NodeKind.STAR ) )
            {
               visitExpression( node );
            }
         }
      }
   }

   protected void visitRelationalExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.RELATION ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            final IParserNode node = iterator.next();
            visitShiftExpression( node );
            while ( iterator.hasNext() )
            {
               iterator.next();
               visitShiftExpression( iterator.next() );
            }
         }
         else
         {
            visitShiftExpression( ast );
         }
      }
   }

   protected void visitReturn( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitExpression( ast.getChild( 0 ) );
      }
   }

   protected void visitShiftExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.SHIFT ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            final IParserNode node = iterator.next();
            visitAdditiveExpression( node );
            while ( iterator.hasNext() )
            {
               iterator.next();
               visitAdditiveExpression( iterator.next() );
            }
         }
         else
         {
            visitAdditiveExpression( ast );
         }
      }
   }

   protected void visitStatement( final IParserNode ast )
   {
      if ( ast == null )
      {
         return;
      }
      if ( ast.is( NodeKind.FOR ) )
      {
         visitFor( ast );
      }
      else if ( ast.is( NodeKind.FORIN ) )
      {
         visitForIn( ast );
      }
      else if ( ast.is( NodeKind.FOREACH ) )
      {
         visitForEach( ast );
      }
      else if ( ast.is( NodeKind.DO ) )
      {
         visitDo( ast );
      }
      else if ( ast.is( NodeKind.WHILE ) )
      {
         visitWhile( ast );
      }
      else if ( ast.is( NodeKind.IF ) )
      {
         visitIf( ast );
      }
      else if ( ast.is( NodeKind.SWITCH ) )
      {
         visitSwitch( ast );
      }
      else if ( ast.is( NodeKind.TRY ) )
      {
         visitTry( ast );
      }
      else if ( ast.is( NodeKind.CATCH ) )
      {
         visitCatch( ast );
      }
      else if ( ast.is( NodeKind.FINALLY ) )
      {
         visitFinally( ast );
      }
      else if ( ast.is( NodeKind.LEFT_CURLY_BRACKET ) )
      {
         visitBlock( ast );
      }
      else if ( ast.is( NodeKind.VAR ) )
      {
         visitVarOrConstList( ast,
                              KeyWords.VAR );
      }
      else if ( ast.is( NodeKind.CONST ) )
      {
         visitVarOrConstList( ast,
                              KeyWords.CONST );
      }
      else if ( ast.is( NodeKind.RETURN ) )
      {
         visitReturn( ast );
      }
      else if ( !ast.is( NodeKind.STMT_EMPTY ) )
      {
         visitExpressionList( ast );
      }
   }

   protected void visitSwitch( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         final Iterator< IParserNode > iterator = ast.getChildren().iterator();
         visitExpression( iterator.next() );

         if ( iterator.hasNext() )
         {
            final IParserNode cases = iterator.next();

            if ( cases.getChildren() != null )
            {
               final Iterator< IParserNode > caseIterator = cases.getChildren().iterator();
               while ( caseIterator.hasNext() )
               {
                  final IParserNode node = caseIterator.next();
                  final IParserNode child = node.getChild( 0 );
                  if ( child.is( NodeKind.DEFAULT ) )
                  {
                     visitSwitchDefaultCase( node.getChild( 1 ) );
                  }
                  else
                  {
                     visitSwitchCase( node.getChild( 1 ) );
                     visitExpression( child );
                  }
               }
            }
         }
      }
   }

   protected void visitSwitchCase( final IParserNode child )
   {
      visitBlock( child );
   }

   protected void visitSwitchDefaultCase( final IParserNode child )
   {
      visitBlock( child );
   }

   protected void visitTry( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitBlock( ast.getChild( 0 ) );
      }
   }

   protected void visitUnaryExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.PRE_INC ) )
         {
            visitUnaryExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( NodeKind.PRE_DEC ) )
         {
            visitUnaryExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( NodeKind.MINUS ) )
         {
            visitUnaryExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( NodeKind.PLUS ) )
         {
            visitUnaryExpression( ast.getChild( 0 ) );
         }
         else
         {
            visitUnaryExpressionNotPlusMinus( ast );
         }
      }
   }

   protected void visitUnaryExpressionNotPlusMinus( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.DELETE ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( NodeKind.VOID ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( NodeKind.TYPEOF ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( NodeKind.NOT ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( NodeKind.B_NOT ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else
         {
            visitUnaryPostfixExpression( ast );
         }
      }
   }

   protected void visitUnaryPostfixExpression( final IParserNode ast )
   {
      if ( ast != null )
      {
         if ( ast.is( NodeKind.ARRAY_ACCESSOR ) )
         {
            visitArrayAccessor( ast );
         }
         else if ( ast.is( NodeKind.DOT ) )
         {
            visitExpression( ast.getChild( 0 ) );
            visitExpression( ast.getChild( 1 ) );
         }
         else if ( ast.is( NodeKind.CALL ) )
         {
            final Iterator< IParserNode > iterator = ast.getChildren().iterator();
            visitExpression( iterator.next() );
            do
            {
               visitExpressionList( iterator.next() );
            }
            while ( iterator.hasNext() );
         }
         else if ( ast.is( NodeKind.POST_INC ) )
         {
            visitPrimaryExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( NodeKind.POST_DEC ) )
         {
            visitPrimaryExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( NodeKind.E4X_FILTER ) )
         {
            visitExpression( ast.getChild( 0 ) );
            visitExpression( ast.getChild( 1 ) );
         }
         else if ( ast.is( NodeKind.E4X_STAR ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else
         {
            visitPrimaryExpression( ast );
         }
      }
   }

   protected void visitVarOrConstList( final IParserNode ast,
                                       final KeyWords varOrConst )
   {
      if ( isNodeNavigable( ast ) )
      {
         final Iterator< IParserNode > iterator = ast.getChildren().iterator();
         IParserNode node = iterator.next();
         if ( node.is( NodeKind.META_LIST ) )
         {
            visitMetaData( node );
            node = iterator.next();
         }
         if ( node.is( NodeKind.MOD_LIST ) )
         {
            visitModifiers( node );
            node = iterator.next();
         }
         while ( node != null )
         {
            visitNameTypeInit( node );
            node = iterator.hasNext() ? iterator.next()
                                     : null;
         }
      }
   }

   protected void visitWhile( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitExpression( ast.getChild( 0 ) );
         visitBlock( ast.getChild( 1 ) );
      }
   }

   final private boolean isNodeNavigable( final IParserNode node )
   {
      return node != null
            && node.numChildren() != 0;
   }

   private void visitNameTypeInit( final IParserNode ast )
   {
      if ( ast != null
            && ast.numChildren() != 0 )
      {
         final Iterator< IParserNode > iterator = ast.getChildren().iterator();
         IParserNode node;

         iterator.next();
         if ( iterator.hasNext() )
         {
            node = iterator.next();
         }
         if ( iterator.hasNext() )
         {
            node = iterator.next();
            if ( node.is( NodeKind.INIT ) )
            {
               visitExpression( node );
            }
         }
      }
   }
}
