/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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