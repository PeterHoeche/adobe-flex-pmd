package com.adobe.ac.pmd.nodes;

import java.util.List;

import com.adobe.ac.pmd.parser.IParserNode;

/**
 * Node representing a class. It contains different lists (constants, variables,
 * functions, implementations, ...), but also a reference to its constructor (if
 * any), the extension name (if any), and its name.
 * 
 * @author xagnetti
 */
public interface IClass extends IVisible, IMetaDataListHolder, INamableNode
{
   List< IAttribute > getAttributes();

   List< IConstant > getConstants();

   IFunction getConstructor();

   String getExtensionName();

   List< IFunction > getFunctions();

   List< IParserNode > getImplementations();

   boolean isBindable();

   boolean isFinal();
}