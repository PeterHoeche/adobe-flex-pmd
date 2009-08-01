package com.adobe.ac.pmd.parser;

import java.util.List;

public interface IParserNode
{
   /**
    * @return the cyclomatic complexity of the current node
    */
   int computeCyclomaticComplexity();

   /**
    * @param type
    * @return count recursivly the number of children which are of type "type"
    */
   int countNodeFromType( final NodeKind type );

   /**
    * @param names
    * @return the list of IParserNode which names is contained in the given
    *         names array
    */
   List< IParserNode > findPrimaryStatementsFromNameInChildren( final String[] names );

   /**
    * @param index
    * @return the indexth child
    */
   IParserNode getChild( final int index );

   /**
    * @return the entire list of chilren
    */
   List< IParserNode > getChildren();

   /**
    * @return node's column
    */
   int getColumn();

   /**
    * @return node's type
    */
   NodeKind getId();

   /**
    * @return the node's last child
    */
   IParserNode getLastChild();

   /**
    * @return nodes's line
    */
   int getLine();

   /**
    * @return node's string value
    */
   String getStringValue();

   /**
    * @param expectedType
    * @return true if the node's type is identical to the given name
    */
   boolean is( final NodeKind expectedType );

   /**
    * @return the children number
    */
   int numChildren();
}