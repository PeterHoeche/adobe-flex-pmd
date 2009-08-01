package com.adobe.ac.pmd.nodes.impl;

import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.parser.IParserNode;

class AttributeNode extends FieldNode implements IAttribute
{
   protected AttributeNode( final IParserNode rootNode )
   {
      super( rootNode );
   }
}
