package com.adobe.ac.pmd.nodes.impl;

import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.parser.IParserNode;

public final class NodeFactory
{
   public static IMetaData createMetaData( final IParserNode metadataNode )
   {
      return new MetaDataNode( metadataNode );
   }

   public static IPackage createPackage( final IParserNode packageNode )
   {
      return new PackageNode( packageNode );
   }

   private NodeFactory()
   {
   }
}
