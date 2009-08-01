package com.adobe.ac.pmd.nodes.impl;

import com.adobe.ac.pmd.nodes.IIdentifierNode;
import com.adobe.ac.pmd.parser.IParserNode;

class IdentifierNode extends AbstractNode implements IIdentifierNode
{
   protected IdentifierNode( final IParserNode node )
   {
      super( node );
   }

   @Override
   public String toString()
   {
      return getInternalNode().getStringValue();
   }

   @Override
   protected void compute()
   {
   }
}
