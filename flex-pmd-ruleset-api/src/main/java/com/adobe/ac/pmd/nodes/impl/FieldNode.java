package com.adobe.ac.pmd.nodes.impl;

import com.adobe.ac.pmd.nodes.IField;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;

/**
 * Base class for AttributeNode and for ConstantNode
 * 
 * @author xagnetti
 */
class FieldNode extends VariableNode implements IField
{
   protected FieldNode( final IParserNode rootNode )
   {
      super( rootNode );
   }

   public boolean isPublic()
   {
      return is( Modifier.PUBLIC );
   }

   public boolean isStatic()
   {
      return is( Modifier.STATIC );
   }

   @Override
   protected void compute()
   {
      super.compute();

      if ( getInternalNode().numChildren() != 0 )
      {
         for ( final IParserNode child : getInternalNode().getChildren() )
         {
            if ( child.is( NodeKind.MOD_LIST ) )
            {
               computeModifierList( this,
                                    child );
            }
         }
      }
   }
}
