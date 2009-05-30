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
package com.adobe.ac.pmd.nodes;

import java.util.List;
import java.util.Map;

import com.adobe.ac.pmd.parser.IParserNode;

/**
 * Node representing a Function It contains the function name, its parameters,
 * its return type, its modifiers, its metadata
 *
 * @author xagnetti
 */
public interface IFunction extends IModifiersHolder, IMetaDataListHolder, INamable, INode
{
   /**
    * Finds recursivly a statement in the function body from its name
    *
    * @param primaryName statement name
    * @return corresponding node
    */
   IParserNode findPrimaryStatementFromName( final String primaryName );

   /**
    * Finds recursivly a statement in the function body from a list of names
    *
    * @param primaryNames statement name
    * @return corresponding node
    */
   IParserNode findPrimaryStatementFromName( final String[] primaryNames );

   IParserNode getBody();

   int getCyclomaticComplexity();

   Map< String, IParserNode > getLocalVariables();

   List< IFormal > getParameters();

   IIdentifierNode getReturnType();

   /**
    * @return Extracts the super call node (if any) from the function content
    *         block
    */
   IParserNode getSuperCall();

   boolean isGetter();

   boolean isOverriden();

   boolean isSetter();
}