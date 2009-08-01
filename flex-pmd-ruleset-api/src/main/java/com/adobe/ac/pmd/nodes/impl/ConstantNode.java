package com.adobe.ac.pmd.nodes.impl;

import com.adobe.ac.pmd.nodes.IConstant;
import com.adobe.ac.pmd.parser.IParserNode;

class ConstantNode extends FieldNode implements IConstant
{
   protected ConstantNode( final IParserNode rootNode )
   {
      super( rootNode );
   }
}
