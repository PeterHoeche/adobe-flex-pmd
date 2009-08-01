package com.adobe.ac.pmd.nodes;

import java.util.List;

public interface IMetaDataListHolder
{
   void add( IMetaData metaData );

   List< IMetaData > getMetaData( String metaDataName );

   int getMetaDataCount();
}
