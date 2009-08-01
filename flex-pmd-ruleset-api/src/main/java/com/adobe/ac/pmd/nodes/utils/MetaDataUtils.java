package com.adobe.ac.pmd.nodes.utils;

import com.adobe.ac.pmd.nodes.IMetaDataListHolder;
import com.adobe.ac.pmd.nodes.impl.NodeFactory;
import com.adobe.ac.pmd.parser.IParserNode;

public final class MetaDataUtils
{
   public static void computeMetaDataList( final IMetaDataListHolder metaDataHolder,
                                           final IParserNode child )
   {
      if ( child.numChildren() != 0 )
      {
         for ( final IParserNode metadataNode : child.getChildren() )
         {
            metaDataHolder.add( NodeFactory.createMetaData( metadataNode ) );
         }
      }
   }

   private MetaDataUtils()
   {
   }
}
