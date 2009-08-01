package com.adobe.ac.pmd.nodes;

import java.util.List;

import com.adobe.ac.pmd.parser.IParserNode;

/**
 * Node representing a package. It contains the nested class node, the list of
 * imports, and the package name.
 *
 * @author xagnetti
 */
public interface IPackage extends INamable, INode
{
   IClass getClassNode();

   String getFullyQualifiedClassName();

   List< IParserNode > getImports();
}