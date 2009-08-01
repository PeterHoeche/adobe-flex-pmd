package com.adobe.ac.pmd.nodes.impl;

import com.adobe.ac.pmd.nodes.IParameter;
import com.adobe.ac.pmd.parser.IParserNode;

class FormalNode extends VariableNode implements IParameter
{
   protected FormalNode( final IParserNode node )
   {
      super( node );
   }
}
