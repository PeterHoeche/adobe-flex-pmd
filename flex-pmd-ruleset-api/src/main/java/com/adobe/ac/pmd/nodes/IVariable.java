package com.adobe.ac.pmd.nodes;

public interface IVariable extends IMetaDataListHolder, INamableNode
{
   IFieldInitialization getInitializationExpression();

   IIdentifierNode getType();
}