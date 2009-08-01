package com.adobe.ac.pmd.rules.performance;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class AvoidInstanciationInLoopRule extends AbstractAstFlexRule
{
   private int loopLevel = 0;

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected final void visitFor( final IParserNode ast )
   {
      loopLevel++;
      super.visitFor( ast );
      loopLevel--;
   }

   @Override
   protected final void visitForEach( final IParserNode ast )
   {
      loopLevel++;
      super.visitForEach( ast );
      loopLevel--;
   }

   @Override
   protected final void visitStatement( final IParserNode ast )
   {
      super.visitStatement( ast );

      if ( ast != null
            && !ast.is( NodeKind.WHILE ) && !ast.is( NodeKind.FOR ) && !ast.is( NodeKind.FOREACH )
            && !ast.is( NodeKind.FOR ) )
      {
         searchNewNode( ast );
      }
   }

   @Override
   protected final void visitWhile( final IParserNode ast )
   {
      loopLevel++;
      super.visitWhile( ast );
      loopLevel--;
   }

   private void searchNewNode( final IParserNode ast )
   {
      if ( ast.numChildren() > 0 )
      {
         for ( final IParserNode child : ast.getChildren() )
         {
            searchNewNode( child );
         }
      }
      if ( ast.getId() != null
            && ast.is( NodeKind.NEW ) && loopLevel != 0 )
      {
         addViolation( ast );
      }
   }
}
