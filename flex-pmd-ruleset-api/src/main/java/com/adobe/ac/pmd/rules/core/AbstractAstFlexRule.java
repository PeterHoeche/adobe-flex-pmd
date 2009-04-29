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
import com.adobe.ac.pmd.nodes.PackageNode;

import de.bokelberg.flex.parser.KeyWords;
import de.bokelberg.flex.parser.Node;

/**
 * Abstract class for AST-based rule
 * Extends this class if your rule is only detectable in an AS file, which can be converted
 * into an Abstract Synthax Tree.
 *
 * Then you will be able to either use the visitor pattern, or to iterate from the package node, i
 * in order to find your violation(s).
 *
 * @author xagnetti
 */
public abstract class AbstractAstFlexRule
      extends AbstractFlexRule
{
   private static final Logger LOGGER = Logger.getLogger(
         AbstractAstFlexRule.class.getName() );
   private static final String MOD_LIST = "mod-list";
   protected AbstractFlexFile currentFile;
   private final List< Violation > violations;

   public AbstractAstFlexRule()
   {
      super();

      violations = new ArrayList< Violation >();
   }

   public boolean isConcernedByTheGivenFile(
         final AbstractFlexFile file )
   {
      return !file.isMxml();
   }

   /**
    * @param beginningNode
    * @param endNode
    * @return the added violation replacing the threshold value in the message if any.
    */
   final protected Violation addViolation( final Node beginningNode, final Node endNode )
   {
      final Violation violation = new Violation( new ViolationPosition( beginningNode.line,
            endNode.line, beginningNode.column, endNode.column ), this, currentFile );

      if ( this instanceof IThresholdedRule )
      {
         final IThresholdedRule thresholdeRule = (IThresholdedRule ) this;

         violation.replacePlaceholderInMessage( String.valueOf( thresholdeRule.getThreshold() ) );
      }
      violations.add( violation );

      return violation;
   }

   /**
    *
    * @param beginningNode
    * @param endNode
    * @param messageToReplace
    * @return the add violation replacing the {0} token by the specified message
    */
   final protected Violation addViolation(
         final Node beginningNode, final Node endNode, final String messageToReplace )
   {
      final Violation violation = new Violation( new ViolationPosition( beginningNode.line,
            endNode.line, beginningNode.column, endNode.column ), this, currentFile );

      violation.replacePlaceholderInMessage( messageToReplace );
      violations.add( violation );

      return violation;
   }

   /**
    * Override this method if you need to find violations from the package (
    * or any subsequent node like class or function)
    *
    * @param packageNode
    * @param filesInSourcePath
    */
   protected void findViolationsFromPackageNode(
         final PackageNode packageNode, final Map< String, AbstractFlexFile > filesInSourcePath )
   {
   }

   @Override
   final protected List< Violation > processFileBody(
         final PackageNode rootNode, final AbstractFlexFile file, final Map< String, AbstractFlexFile > files )
   {
      currentFile = file;
      try
      {
         if ( rootNode != null )
         {
            visitNodes( rootNode.getInternalNode() );
            findViolationsFromPackageNode( rootNode, files );
         }
      }
      catch ( final Exception e )
      {
         LOGGER.warning(
               StackTraceUtils.print( e ) );
      }
      final List< Violation > copy = new ArrayList< Violation >( violations );

      violations.clear();

      return copy;
   }

   protected void visitAdditiveExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( Node.ADD ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            final Node node = iterator.next();
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

   protected void visitAndExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( Node.AND ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            final Node node = iterator.next();
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

   protected void visitBitwiseAndExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( Node.B_AND ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            final Node node = iterator.next();
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

   protected void visitBitwiseOrExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( Node.B_OR ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            final Node node = iterator.next();
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

   protected void visitBitwiseXorExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( Node.B_OR ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            final Node node = iterator.next();
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

   protected void visitBlock(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final Node node : ast.children )
         {
            visitStatement( node );
         }
      }
   }

   protected void visitCatch(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitNameTypeInit( ast.getChild( 0 ) );
         visitBlock( ast.getChild( 1 ) );
      }
   }

   protected void visitClass(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         Node modifiers = null;
         Node implement = null;
         Node content = null;
         for ( final Node node : ast.children )
         {
            if ( node.is( MOD_LIST ) )
            {
               modifiers = node;
            }
            else if ( node.is( Node.IMPLEMENTS_LIST ) )
            {
               implement = node;
            }
            else if ( node.is( Node.CONTENT ) )
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

   protected void visitClassContent(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final Node node : ast.children )
         {
            if ( node.is( Node.VAR_LIST ) )
            {
               visitVarOrConstList(
                     node, KeyWords.VAR );
            }
            else if ( node.is( Node.CONST_LIST ) )
            {
               visitVarOrConstList(
                     node, KeyWords.CONST );
            }
            else if ( node.is( KeyWords.FUNCTION ) )
            {
               visitFunction(
                     node, "" );
            }
            else if ( node.is( KeyWords.SET ) )
            {
               visitFunction(
                     node, "set " );
            }
            else if ( node.is( KeyWords.GET ) )
            {
               visitFunction(
                     node, "get " );
            }
         }
      }
   }

   protected void visitCompilationUnit(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final Node node : ast.children )
         {
            if ( node.is( "package" ) )
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

   protected void visitConditionalExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( Node.CONDITIONAL ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            final Node node = iterator.next();
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

   protected void visitDo(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitBlock( ast.getChild( 0 ) );
         visitExpression( ast.getChild( 1 ) );
      }
   }

   protected void visitEqualityExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( Node.EQUALITY ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            final Node node = iterator.next();
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

   protected void visitExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( Node.ASSIGN ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            final Node node = iterator.next();
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

   protected void visitExpressionList(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final Node node : ast.children )
         {
            visitExpression( node );
         }
      }
   }

   protected void visitFinally(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitBlock( ast.getChild( 0 ) );
      }
   }

   protected void visitFor(
         final Node ast )
   {
      if ( ast.numChildren() > 3 )
      {
         visitBlock( ast.getChild( 3 ) );
      }
   }

   protected void visitForEach(
         final Node ast )
   {
      if ( ast.numChildren() > 2 )
      {
         visitBlock( ast.getChild( 2 ) );
      }
   }

   protected void visitForIn(
         final Node ast )
   {
   }

   protected void visitFunction(
         final Node ast, final String type )
   {
      if ( isNodeNavigable( ast ) )
      {
         final Iterator< Node > iterator = ast.children.iterator();
         Node node = iterator.next();

         if ( node.is( Node.META_LIST ) )
         {
            visitMetaData( node );
            node = iterator.next();
         }
         if ( node.is( MOD_LIST ) )
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

   protected void visitFunctionBody(
         final Node node )
   {
      visitBlock( node );
   }

   protected void visitFunctionReturnType(
         final Node node )
   {
      visitBlock( node );
   }

   protected void visitIf(
         final Node ast )
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

   protected void visitImplementsList(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final Node node : ast.children )
         {
            visitImplementsListChildren( node );
         }
      }
   }

   /**
    * Overrides it if you need to visit each implementation
    * @param next
    */
   protected void visitImplementsListChildren(
         final Node next )
   {
   }

   protected void visitInterface(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         Node modifiers = null;
         for ( final Node node : ast.children )
         {
            if ( node.is( MOD_LIST ) )
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
   protected void visitMetaData(
         final Node node )
   {
   }

   /**
    * Overrides it if you need to visit a modifier
    * @param ast
    */
   protected void visitModifiers(
         final Node ast )
   {
   }

   protected void visitMultiplicativeExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( Node.MULTIPLICATION ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            final Node node = iterator.next();
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

   protected void visitNodes(
         final Node ast )
   {
      visitCompilationUnit( ast );
   }

   protected void visitOrExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( Node.OR ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            final Node node = iterator.next();
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

   protected void visitPackageContent(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final Node node : ast.children )
         {
            if ( node.is( KeyWords.CLASS ) )
            {
               visitClass( node );
            }
            else if ( node.is( KeyWords.INTERFACE ) )
            {
               visitInterface( node );
            }
         }
      }
   }

   protected void visitParameters(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         for ( final Node node2 : ast.children )
         {
            final Node node = node2.getChild(
                  0 );

            if ( node.is( Node.NAME_TYPE_INIT ) )
            {
               visitNameTypeInit( node );
            }
         }
      }
   }

   protected void visitPrimaryExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.numChildren() != 0
               && ast.is( Node.ARRAY ) )
         {
            visitExpressionList( ast );
         }
         else if ( ast.is( Node.OBJECT ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            while ( iterator.hasNext() )
            {
               final Node node = iterator.next();
               visitExpression( node.getChild( 1 ) );
            }
         }
         else if ( ast.is( KeyWords.NEW ) )
         {
            visitExpression( ast.getChild( 0 ) );
            visitExpressionList( ast.getChild( 1 ) );
         }
         else if ( ast.is( Node.ENCAPSULATED ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( Node.E4X_ATTR ) )
         {
            final Node node = ast.getChild( 0 );

            if ( !node.is( Node.NAME )
                  && !node.is( Node.STAR ) )
            {
               visitExpression( node );
            }
         }
      }
   }

   protected void visitRelationalExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( Node.RELATION ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            final Node node = iterator.next();
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

   protected void visitReturn(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitExpression( ast.getChild( 0 ) );
      }
   }

   protected void visitShiftExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( "shift" ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            final Node node = iterator.next();
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

   protected void visitStatement(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( KeyWords.FOR ) )
         {
            visitFor( ast );
         }
         else if ( ast.is( KeyWords.FORIN ) )
         {
            visitForIn( ast );
         }
         else if ( ast.is( KeyWords.FOREACH ) )
         {
            visitForEach( ast );
         }
         else if ( ast.is( KeyWords.IF ) )
         {
            visitIf( ast );
         }
         else if ( ast.is( KeyWords.SWITCH ) )
         {
            visitSwitch( ast );
         }
         else if ( ast.is( KeyWords.DO ) )
         {
            visitDo( ast );
         }
         else if ( ast.is( KeyWords.WHILE ) )
         {
            visitWhile( ast );
         }
         else if ( ast.is( KeyWords.TRY ) )
         {
            visitTry( ast );
         }
         else if ( ast.is( KeyWords.CATCH ) )
         {
            visitCatch( ast );
         }
         else if ( ast.is( KeyWords.FINALLY ) )
         {
            visitFinally( ast );
         }
         else if ( ast.is( "{" ) )
         {
            visitBlock( ast );
         }
         else if ( ast.is( KeyWords.VAR ) )
         {
            visitVarOrConstList(
                  ast, KeyWords.VAR );
         }
         else if ( ast.is( KeyWords.CONST ) )
         {
            visitVarOrConstList(
                  ast, KeyWords.CONST );
         }
         else if ( ast.is( KeyWords.RETURN ) )
         {
            visitReturn( ast );
         }
         else if ( !ast.is( Node.STMT_EMPTY ) )
         {
            visitExpressionList( ast );
         }
      }
   }

   protected void visitStatements(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         final Iterator< Node > iterator = ast.children.iterator();
         while ( iterator.hasNext() )
         {
            final Node node = iterator.next();
            visitStatement( node );
         }
      }
   }

   protected void visitSwitch(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         final Iterator< Node > iterator = ast.children.iterator();
         visitExpression( iterator.next() );

         if ( iterator.hasNext() )
         {
            final Node cases = iterator.next();

            if ( cases.children != null )
            {
               final Iterator< Node > caseIterator = cases.children.iterator();
               while ( caseIterator.hasNext() )
               {
                  final Node node = caseIterator.next();
                  final Node child = node.getChild( 0 );
                  if ( child.is( KeyWords.DEFAULT ) )
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

   protected void visitSwitchCase(
         final Node child )
   {
      visitStatements( child );
   }

   protected void visitSwitchDefaultCase(
         final Node child )
   {
      visitStatements( child );
   }

   protected void visitTry(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitBlock( ast.getChild( 0 ) );
      }
   }

   protected void visitUnaryExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( Node.PRE_INC ) )
         {
            visitUnaryExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( Node.PRE_DEC ) )
         {
            visitUnaryExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( Node.MINUS ) )
         {
            visitUnaryExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( Node.PLUS ) )
         {
            visitUnaryExpression( ast.getChild( 0 ) );
         }
         else
         {
            visitUnaryExpressionNotPlusMinus( ast );
         }
      }
   }

   protected void visitUnaryExpressionNotPlusMinus(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( KeyWords.DELETE ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( KeyWords.VOID ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( KeyWords.TYPEOF ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( Node.NOT ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( Node.B_NOT ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else
         {
            visitUnaryPostfixExpression( ast );
         }
      }
   }

   protected void visitUnaryPostfixExpression(
         final Node ast )
   {
      if ( ast != null )
      {
         if ( ast.is( Node.ARRAY_ACCESSOR ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            visitExpression( iterator.next() );
            do
            {
               visitExpression( iterator.next() );
            }
            while ( iterator.hasNext() );

         }
         else if ( ast.is( Node.DOT ) )
         {
            visitExpression( ast.getChild( 0 ) );
            visitExpression( ast.getChild( 1 ) );
         }
         else if ( ast.is( Node.CALL ) )
         {
            final Iterator< Node > iterator = ast.children.iterator();
            visitExpression( iterator.next() );
            do
            {
               visitExpressionList( iterator.next() );
            }
            while ( iterator.hasNext() );
         }
         else if ( ast.is( Node.POST_INC ) )
         {
            visitPrimaryExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( Node.POST_DEC ) )
         {
            visitPrimaryExpression( ast.getChild( 0 ) );
         }
         else if ( ast.is( Node.E4X_FILTER ) )
         {
            visitExpression( ast.getChild( 0 ) );
            visitExpression( ast.getChild( 1 ) );
         }
         else if ( ast.is( Node.E4X_STAR ) )
         {
            visitExpression( ast.getChild( 0 ) );
         }
         else
         {
            visitPrimaryExpression( ast );
         }
      }
   }

   protected void visitVarOrConstList(
         final Node ast, final String varOrConst )
   {
      if ( isNodeNavigable( ast ) )
      {
         final Iterator< Node > iterator = ast.children.iterator();
         Node node = iterator.next();
         if ( node.is( Node.META_LIST ) )
         {
            visitMetaData( node );
            node = iterator.next();
         }
         if ( node.is( MOD_LIST ) )
         {
            visitModifiers( node );
            node = iterator.next();
         }
         while ( node != null )
         {
            visitNameTypeInit( node );
            node = iterator.hasNext() ? iterator.next() : null;
         }
      }
   }

   protected void visitWhile(
         final Node ast )
   {
      if ( isNodeNavigable( ast ) )
      {
         visitExpression( ast.getChild( 0 ) );
         visitBlock( ast.getChild( 1 ) );
      }
   }

   final private boolean isNodeNavigable(
         final Node node )
   {
      return node != null
            && node.numChildren() != 0;
   }

   private void visitNameTypeInit(
         final Node ast )
   {
      if ( ast != null
            && ast.children != null )
      {
         final Iterator< Node > iterator = ast.children.iterator();
         Node node;

         iterator.next();
         if ( iterator.hasNext() )
         {
            node = iterator.next();
         }
         if ( iterator.hasNext() )
         {
            node = iterator.next();
            if ( node.is( Node.INIT ) )
            {
               visitExpression( node );
            }
         }
      }
   }
}
