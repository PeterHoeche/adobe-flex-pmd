package com.adobe.ac.pmd.nodes.impl;

import com.adobe.ac.pmd.nodes.IFieldInitialization;
import com.adobe.ac.pmd.parser.IParserNode;

class FieldInitializationNode extends AbstractNode implements IFieldInitialization
{
   protected FieldInitializationNode( final IParserNode node )
   {
      super( node );
   }

   @Override
   protected void compute()
   {
   }
}
