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
package com.adobe.ac.pmd.rules.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IConstant;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.INode;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;
import com.adobe.ac.utils.StackTraceUtils;

/**
 * Abstract class for AST-based rule Extends this class if your rule is only
 * detectable in an AS script block, which can be converted into an Abstract
 * Synthax Tree. Then you will be able to either use the visitor pattern, or to
 * iterate from the package node, in order to find your violation(s).
 * 
 * @author xagnetti
 */
public abstract class AbstractAstFlexRule extends AbstractFlexRule implements IFlexAstRule
{
   protected enum FunctionType
   {
      GETTER, NORMAL, SETTER
   }

   protected enum VariableOrConstant
   {
      CONSTANT, VARIABLE
   }

   protected enum VariableScope
   {
      IN_CLASS, IN_FUNCTION
   }

   private interface ExpressionVisitor
   {
      void visitExpression( final IParserNode ast );
   }

   private static final Logger LOGGER = Logger.getLogger( AbstractAstFlexRule.class.getName() );

   protected static IParserNode getNameFromFunctionDeclaration( final IParserNode functionNode )
   {
      IParserNode nameChild = null;

      for ( final IParserNode child : functionNode.getChildren() )
      {
         if ( child.is( NodeKind.NAME ) )
         {
            nameChild = child;
            break;
         }
      }
      return nameChild;
   }

   protected static IParserNode getTypeFromFieldDeclaration( final IParserNode fieldNode )
   {
      return fieldNode.getChild( 0 ).getChild( 1 );
   }

   private final List< IFlexViolation > violations;

   public AbstractAstFlexRule()
   {
      super();

      violations = new ArrayList< IFlexViolation >();
   }

   @Override
   public boolean isConcernedByTheCurrentFile()
   {
      return true;
   }

   /**
    * @param function
    * @return the added violation positioned on the given function node
    */
   protected final IFlexViolation addViolation( final IFunction function )
   {
      final IParserNode name = getNameFromFunctionDeclaration( function.getInternalNode() );

      return addViolation( name,
                           name,
                           name.getStringValue() );
   }

   protected final IFlexViolation addViolation( final IFunction function,
                                                final String messageToReplace )
   {
      final IParserNode name = getNameFromFunctionDeclaration( function.getInternalNode() );

      return addViolation( name,
                           name,
                           messageToReplace );
   }

   /**
    * @param violatingNode
    * @return the added violation replacing the threshold value in the message
    *         if any.
    */
   protected final IFlexViolation addViolation( final INode violatingNode )
   {
      return addViolation( violatingNode.getInternalNode(),
                           violatingNode.getInternalNode() );
   }

   /**
    * @param violatingNode
    * @return the added violation replacing the threshold value in the message
    *         if any.
    */
   protected final IFlexViolation addViolation( final INode violatingNode,
                                                final String... messageToReplace )
   {
      return addViolation( violatingNode.getInternalNode(),
                           violatingNode.getInternalNode(),
                           messageToReplace );
   }

   /**
    * @param violatingNode
    * @param endNode
    * @return the added violation replacing the threshold value in the message
    *         if any.
    */
   protected final IFlexViolation addViolation( final IParserNode violatingNode )
   {
      return addViolation( violatingNode,
                           violatingNode );
   }

   /**
    * @param beginningNode
    * @param endNode
    * @param messageToReplace
    * @return the add violation replacing the {0} token by the specified message
    */
   protected final IFlexViolation addViolation( final IParserNode beginningNode,
                                                final IParserNode endNode,
                                                final String... messageToReplace )
   {
      final IFlexViolation violation = addViolation( ViolationPosition.create( beginningNode.getLine(),
                                                                               endNode.getLine(),
                                                                               beginningNode.getColumn(),
                                                                               endNode.getColumn() ) );

      for ( int i = 0; i < messageToReplace.length; i++ )
      {
         violation.replacePlaceholderInMessage( messageToReplace[ i ],
                                                i );
      }

      return violation;
   }

   /**
    * @param violatingNode
    * @param endNode
    * @param messageToReplace
    * @return the add violation replacing the {0} token by the specified message
    */
   protected final IFlexViolation addViolation( final IParserNode violatingNode,
                                                final String... messageToReplace )
   {
      return addViolation( violatingNode,
                           violatingNode,
                           messageToReplace );
   }

   /**
    * @param violationPosition
    * @return the added violation positioned at the given position
    */
   protected final IFlexViolation addViolation( final ViolationPosition violationPosition )
   {
      return addViolation( violations,
                           violationPosition );
   }

   /**
    * find the violations list from the given class node
    * 
    * @param classNode
    */
   protected void findViolations( final IClass classNode )
   {
      findViolationsFromAttributes( classNode.getAttributes() );
      findViolationsFromConstants( classNode.getConstants() );
      findViolations( classNode.getFunctions() );
      if ( classNode.getConstructor() != null )
      {
         findViolationsFromConstructor( classNode.getConstructor() );
      }
   }

   protected void findViolations( final IFunction function )
   {
   }

   /**
    * Override this method if you need to find violations from the package ( or
    * any subsequent node like class or function)
    * 
    * @param packageNode
    */
   protected void findViolations( final IPackage packageNode )
   {
      final IClass classNode = packageNode.getClassNode();

      if ( classNode != null )
      {
         findViolations( classNode );
      }
   }

   /**
    * find the violations list from the given functions list
    * 
    * @param functions
    */
   protected void findViolations( final List< IFunction > functions )
   {
      for ( final IFunction function : functions )
      {
         findViolations( function );
      }
   }

   /**
    * find the violations list from the given class variables list
    * 
    * @param variables
    */
   protected void findViolationsFromAttributes( final List< IAttribute > variables )
   {
   }

   /**
    * find the violations list from the given class constants list
    * 
    * @param constants
    */
   protected void findViolationsFromConstants( final List< IConstant > constants )
   {
   }

   /**
    * find the violations list from the given class constructor node
    * 
    * @param constructor
    */
   protected void findViolationsFromConstructor( final IFunction constructor )
   {
   }

   /**
    * Find violations in the current file
    */
   @Override
   protected final List< IFlexViolation > findViolationsInCurrentFile()
   {
      try
      {
         if ( getCurrentPackageNode() != null )
         {
            visitCompilationUnit( getCurrentPackageNode().getInternalNode() );
            findViolations( getCurrentPackageNode() );
         }
      }
      catch ( final Exception e )
      {
         LOGGER.warning( "on "
               + getCurrentFile().getFilePath() );
         LOGGER.warning( StackTraceUtils.print( e ) );
      }
      final List< IFlexViolation > copy = new ArrayList< IFlexViolation >( violations );

      violations.clear();

      return copy;
   }

   protected void visitCatch( final IParserNode catchNode )
   {
      visitNameTypeInit( catchNode.getChild( 0 ) );
      visitBlock( catchNode.getChild( 1 ) );
   }

   protected void visitClass( final IParserNode classNode )
   {
      IParserNode content = null;
      for ( final IParserNode node : classNode.getChildren() )
      {
         if ( node.is( NodeKind.CONTENT ) )
         {
            content = node;
            break;
         }
      }
      visitClassContent( content );
   }

   /**
    * Visit the condition of a if, while, ...
    * 
    * @param condition
    */
   protected void visitCondition( final IParserNode condition )
   {
      visitExpression( condition );
   }

   protected void visitDo( final IParserNode doNode )
   {
      visitBlock( doNode.getChild( 0 ) );
      visitCondition( doNode.getChild( 1 ) );
   }

   protected void visitElse( final IParserNode elseNode )
   {
      visitBlock( elseNode.getChild( 2 ) );
   }

   protected void visitEmptyStatetement( final IParserNode statementNode )
   {
   }

   protected void visitFinally( final IParserNode finallyNode )
   {
      if ( isNodeNavigable( finallyNode ) )
      {
         visitBlock( finallyNode.getChild( 0 ) );
      }
   }

   protected void visitFor( final IParserNode forNode )
   {
      visitBlock( forNode.getChild( 3 ) );
   }

   protected void visitForEach( final IParserNode foreachNode )
   {
      visitBlock( foreachNode.getChild( 2 ) );
   }

   protected void visitFunction( final IParserNode functionNode,
                                 final FunctionType type )
   {
      final Iterator< IParserNode > iterator = functionNode.getChildren().iterator();
      IParserNode currentNode = iterator.next();

      while ( currentNode.is( NodeKind.META_LIST )
            || currentNode.is( NodeKind.MOD_LIST ) )
      {
         currentNode = iterator.next();
      }
      currentNode = iterator.next();
      visitParameters( currentNode );
      currentNode = iterator.next();
      visitFunctionReturnType( currentNode );
      currentNode = iterator.next();
      visitFunctionBody( currentNode );
   }

   protected void visitFunctionReturnType( final IParserNode functionReturnTypeNode )
   {
      visitBlock( functionReturnTypeNode );
   }

   protected void visitIf( final IParserNode ifNode )
   {
      visitCondition( ifNode.getChild( 0 ) );
      visitThen( ifNode );
      if ( ifNode.numChildren() == 3 )
      {
         visitElse( ifNode );
      }
   }

   protected void visitInterface( final IParserNode interfaceNode )
   {
   }

   protected void visitMethodCall( final IParserNode methodCallNode )
   {
      final Iterator< IParserNode > iterator = methodCallNode.getChildren().iterator();
      visitExpression( iterator.next() );
      do
      {
         visitExpressionList( iterator.next() );
      }
      while ( iterator.hasNext() );
   }

   protected void visitNewExpression( final IParserNode newExpression )
   {
      visitExpression( newExpression.getChild( 0 ) );
      visitExpressionList( newExpression.getChild( 1 ) );
   }

   protected void visitParameters( final IParserNode functionParametersNode )
   {
      if ( isNodeNavigable( functionParametersNode ) )
      {
         for ( final IParserNode node2 : functionParametersNode.getChildren() )
         {
            visitNameTypeInit( node2.getChild( 0 ) );
         }
      }
   }

   protected void visitStatement( final IParserNode statementNode )
   {
      if ( statementNode.is( NodeKind.FOR ) )
      {
         visitFor( statementNode );
      }
      else if ( statementNode.is( NodeKind.FOREACH ) )
      {
         visitForEach( statementNode );
      }
      else if ( statementNode.is( NodeKind.DO ) )
      {
         visitDo( statementNode );
      }
      else if ( statementNode.is( NodeKind.WHILE ) )
      {
         visitWhile( statementNode );
      }
      else if ( statementNode.is( NodeKind.IF ) )
      {
         visitIf( statementNode );
      }
      else if ( statementNode.is( NodeKind.SWITCH ) )
      {
         visitSwitch( statementNode );
      }
      else if ( statementNode.is( NodeKind.TRY ) )
      {
         visitTry( statementNode );
      }
      else if ( statementNode.is( NodeKind.CATCH ) )
      {
         visitCatch( statementNode );
      }
      else if ( statementNode.is( NodeKind.FINALLY ) )
      {
         visitFinally( statementNode );
      }
      else if ( statementNode.is( NodeKind.LEFT_CURLY_BRACKET ) )
      {
         visitBlock( statementNode );
      }
      else if ( statementNode.is( NodeKind.RETURN ) )
      {
         visitReturn( statementNode );
      }
      else if ( statementNode.is( NodeKind.STMT_EMPTY ) )
      {
         visitEmptyStatetement( statementNode );
      }
      else
      {
         visitExpressionList( statementNode );
      }
   }

   protected void visitSwitch( final IParserNode switchNode )
   {
      final Iterator< IParserNode > iterator = switchNode.getChildren().iterator();

      visitExpression( iterator.next() );

      final IParserNode cases = iterator.next();

      for ( final IParserNode caseNode : cases.getChildren() )
      {
         final IParserNode child = caseNode.getChild( 0 );

         if ( child.is( NodeKind.DEFAULT ) )
         {
            visitSwitchDefaultCase( caseNode.getChild( 1 ) );
         }
         else
         {
            visitSwitchCase( caseNode.getChild( 1 ) );
            visitExpression( child );
         }
      }
   }

   protected void visitSwitchCase( final IParserNode switchCaseNode )
   {
      visitBlock( switchCaseNode );
   }

   protected void visitSwitchDefaultCase( final IParserNode defaultCaseNode )
   {
      visitBlock( defaultCaseNode );
   }

   protected void visitThen( final IParserNode ast )
   {
      visitBlock( ast.getChild( 1 ) );
   }

   protected void visitTry( final IParserNode ast )
   {
      visitBlock( ast.getChild( 0 ) );
   }

   protected void visitVariableInitialization( final IParserNode node )
   {
      visitExpression( node );
   }

   protected void visitVarOrConstList( final IParserNode variableNode,
                                       final VariableOrConstant varOrConst,
                                       final VariableScope scope )
   {
      final Iterator< IParserNode > iterator = variableNode.getChildren().iterator();

      IParserNode node = iterator.next();
      while ( node.is( NodeKind.META_LIST )
            || node.is( NodeKind.MOD_LIST ) )
      {
         node = iterator.next();
      }
      while ( node != null )
      {
         visitNameTypeInit( node );
         node = iterator.hasNext() ? iterator.next()
                                  : null;
      }
   }

   protected void visitWhile( final IParserNode whileNode )
   {
      visitCondition( whileNode.getChild( 0 ) );
      visitBlock( whileNode.getChild( 1 ) );
   }

   private boolean isNodeNavigable( final IParserNode node )
   {
      return node != null
            && node.numChildren() != 0;
   }

   private void visitAdditiveExpression( final IParserNode ast )
   {
      visitExpression( ast,
                       NodeKind.ADD,
                       new ExpressionVisitor()
                       {
                          public void visitExpression( final IParserNode ast )
                          {
                             visitMultiplicativeExpression( ast );
                          }
                       } );
   }

   private void visitAndExpression( final IParserNode ast )
   {
      visitExpression( ast,
                       NodeKind.AND,
                       new ExpressionVisitor()
                       {
                          public void visitExpression( final IParserNode ast )
                          {
                             visitBitwiseOrExpression( ast );
                          }
                       } );
   }

   private void visitArrayAccessor( final IParserNode ast )
   {
      final Iterator< IParserNode > iterator = ast.getChildren().iterator();
      visitExpression( iterator.next() );
      do
      {
         visitExpression( iterator.next() );
      }
      while ( iterator.hasNext() );
   }

   private void visitBitwiseAndExpression( final IParserNode ast )
   {
      visitExpression( ast,
                       NodeKind.B_AND,
                       new ExpressionVisitor()
                       {
                          public void visitExpression( final IParserNode ast )
                          {
                             visitEqualityExpression( ast );
                          }
                       } );
   }

   private void visitBitwiseOrExpression( final IParserNode ast )
   {
      visitExpression( ast,
                       NodeKind.B_OR,
                       new ExpressionVisitor()
                       {
                          public void visitExpression( final IParserNode ast )
                          {
                             visitBitwiseXorExpression( ast );
                          }
                       } );
   }

   private void visitBitwiseXorExpression( final IParserNode ast )
   {
      visitExpression( ast,
                       NodeKind.B_XOR,
                       new ExpressionVisitor()
                       {
                          public void visitExpression( final IParserNode ast )
                          {
                             visitBitwiseAndExpression( ast );
                          }
                       } );
   }

   private void visitBlock( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final IParserNode node : ast.getChildren() )
         {
            visitStatement( node );
         }
      }
   }

   private void visitClassContent( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final IParserNode node : ast.getChildren() )
         {
            if ( node.is( NodeKind.VAR_LIST ) )
            {
               visitVarOrConstList( node,
                                    VariableOrConstant.VARIABLE,
                                    VariableScope.IN_CLASS );
            }
            else if ( node.is( NodeKind.CONST_LIST ) )
            {
               visitVarOrConstList( node,
                                    VariableOrConstant.CONSTANT,
                                    VariableScope.IN_CLASS );
            }
            else if ( node.is( NodeKind.FUNCTION ) )
            {
               visitFunction( node,
                              FunctionType.NORMAL );
            }
            else if ( node.is( NodeKind.SET ) )
            {
               visitFunction( node,
                              FunctionType.SETTER );
            }
            else if ( node.is( NodeKind.GET ) )
            {
               visitFunction( node,
                              FunctionType.GETTER );
            }
         }
      }
   }

   private void visitCompilationUnit( final IParserNode ast )
   {
      for ( final IParserNode node : ast.getChildren() )
      {
         if ( node.is( NodeKind.PACKAGE )
               && node.numChildren() >= 2 )
         {
            visitPackageContent( node.getChild( 1 ) );
         }
         if ( !node.is( NodeKind.PACKAGE )
               && node.numChildren() > 0 )
         {
            visitPackageContent( node );
         }
      }
   }

   private void visitConditionalExpression( final IParserNode ast )
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

   private void visitEqualityExpression( final IParserNode ast )
   {
      visitExpression( ast,
                       NodeKind.EQUALITY,
                       new ExpressionVisitor()
                       {
                          public void visitExpression( final IParserNode ast )
                          {
                             visitRelationalExpression( ast );
                          }
                       } );
   }

   private void visitExpression( final IParserNode ast )
   {
      visitExpression( ast,
                       NodeKind.ASSIGN,
                       new ExpressionVisitor()
                       {
                          public void visitExpression( final IParserNode ast )
                          {
                             visitConditionalExpression( ast );
                          }
                       } );
   }

   private void visitExpression( final IParserNode ast,
                                 final NodeKind kind,
                                 final ExpressionVisitor visitor )
   {
      if ( ast.is( kind ) )
      {
         final Iterator< IParserNode > iterator = ast.getChildren().iterator();
         final IParserNode node = iterator.next();

         visitor.visitExpression( node );

         while ( iterator.hasNext() )
         {
            iterator.next();
            visitor.visitExpression( iterator.next() );
         }
      }
      else
      {
         visitor.visitExpression( ast );
      }
   }

   private void visitExpressionList( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final IParserNode node : ast.getChildren() )
         {
            visitExpression( node );
         }
      }
   }

   private void visitFunctionBody( final IParserNode node )
   {
      visitBlock( node );
   }

   private void visitMultiplicativeExpression( final IParserNode ast )
   {
      visitExpression( ast,
                       NodeKind.MULTIPLICATION,
                       new ExpressionVisitor()
                       {
                          public void visitExpression( final IParserNode ast )
                          {
                             visitUnaryExpression( ast );
                          }
                       } );
   }

   private void visitNameTypeInit( final IParserNode ast )
   {
      if ( ast.numChildren() != 0 )
      {
         final Iterator< IParserNode > iterator = ast.getChildren().iterator();
         IParserNode node;

         iterator.next();
         iterator.next();

         if ( iterator.hasNext() )
         {
            node = iterator.next();
            visitVariableInitialization( node );
         }
      }
   }

   private void visitObjectInitialization( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final IParserNode node : ast.getChildren() )
         {
            visitExpression( node.getChild( 1 ) );
         }
      }
   }

   private void visitOrExpression( final IParserNode ast )
   {
      visitExpression( ast,
                       NodeKind.OR,
                       new ExpressionVisitor()
                       {
                          public void visitExpression( final IParserNode ast )
                          {
                             visitAndExpression( ast );
                          }
                       } );
   }

   private void visitPackageContent( final IParserNode ast )
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

   private void visitPrimaryExpression( final IParserNode ast )
   {
      if ( ast.is( NodeKind.NEW ) )
      {
         visitNewExpression( ast );
      }
      else if ( ast.numChildren() != 0
            && ast.is( NodeKind.ARRAY ) )
      {
         visitExpressionList( ast );
      }
      else if ( ast.is( NodeKind.OBJECT ) )
      {
         visitObjectInitialization( ast );
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

   private void visitRelationalExpression( final IParserNode ast )
   {
      visitExpression( ast,
                       NodeKind.RELATION,
                       new ExpressionVisitor()
                       {
                          public void visitExpression( final IParserNode ast )
                          {
                             visitShiftExpression( ast );
                          }
                       } );
   }

   private void visitReturn( final IParserNode ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitExpression( ast.getChild( 0 ) );
      }
   }

   private void visitShiftExpression( final IParserNode ast )
   {
      visitExpression( ast,
                       NodeKind.SHIFT,
                       new ExpressionVisitor()
                       {
                          public void visitExpression( final IParserNode ast )
                          {
                             visitAdditiveExpression( ast );
                          }
                       } );
   }

   private void visitUnaryExpression( final IParserNode ast )
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

   private void visitUnaryExpressionNotPlusMinus( final IParserNode ast )
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

   private void visitUnaryPostfixExpression( final IParserNode ast )
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
         visitMethodCall( ast );
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
