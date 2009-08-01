package com.adobe.ac.pmd.rules.unused;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.KeyWords;
import com.adobe.ac.pmd.parser.NodeKind;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UnusedLocalVariableRule extends AbstractUnusedVariableRule
{
   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected final void visitStatement( final IParserNode ast )
   {
      super.visitStatement( ast );
      tryToAddVariableNodeInChildren( ast );
   }

   @Override
   protected void visitVarOrConstList( final IParserNode ast,
                                       final KeyWords varOrConst )
   {
      super.visitVarOrConstList( ast,
                                 varOrConst );
      tryToAddVariableNodeInChildren( ast );
   }

   private boolean tryToAddVariableNode( final IParserNode ast )
   {
      boolean result = false;

      if ( ast.is( NodeKind.NAME_TYPE_INIT ) )
      {
         addVariable( ast.getChild( 0 ).getStringValue(),
                      ast );
         result = true;
      }
      return result;
   }

   private void tryToAddVariableNodeInChildren( final IParserNode ast )
   {
      if ( ast != null
            && !tryToAddVariableNode( ast ) && ast.is( NodeKind.VAR_LIST ) )
      {
         for ( final IParserNode child : ast.getChildren() )
         {
            tryToAddVariableNode( child );
         }
      }
   }
}
