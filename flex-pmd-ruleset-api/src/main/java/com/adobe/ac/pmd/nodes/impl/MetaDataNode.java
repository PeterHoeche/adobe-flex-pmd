package com.adobe.ac.pmd.nodes.impl;

import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.parser.IParserNode;

class MetaDataNode extends AbstractNode implements IMetaData
{
   private String name;
   private String parameter;

   protected MetaDataNode( final IParserNode node )
   {
      super( node );
   }

   public String getName()
   {
      return name;
   }

   public String getParameter()
   {
      return parameter;
   }

   @Override
   protected void compute()
   {
      final String stringValue = getInternalNode().getStringValue();

      name = stringValue.indexOf( " ( " ) > -1 ? stringValue.substring( 0,
                                                                        stringValue.indexOf( " ( " ) )
                                              : stringValue;
      parameter = stringValue.indexOf( "( " ) > -1 ? stringValue.substring( stringValue.indexOf( "( " ) + 2,
                                                                            stringValue.lastIndexOf( " )" ) )
                                                  : "";
   }
}
