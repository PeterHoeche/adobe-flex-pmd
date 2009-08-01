package com.adobe.ac.pmd.nodes;

import com.adobe.ac.pmd.parser.IParserNode;

/**
 * FlexPmdNode which wraps the parser node into a concrete type
 *
 * @author xagnetti
 */
public interface INode
{
   IParserNode getInternalNode();
}
