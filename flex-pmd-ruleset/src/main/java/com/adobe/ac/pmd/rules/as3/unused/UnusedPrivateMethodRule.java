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
package com.adobe.ac.pmd.rules.as3.unused;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adobe.ac.pmd.nodes.FunctionNode;
import com.adobe.ac.pmd.nodes.utils.ModifierUtils;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

import de.bokelberg.flex.parser.Node;

public class UnusedPrivateMethodRule
      extends AbstractAstFlexRule
{
   private List< Node > functionCalls;
   private Map< String, FunctionNode > unvisitedPrivateFunctions;

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.WARNING;
   }

   @Override
   protected void visitClassContent(
         final Node ast )
   {
      unvisitedPrivateFunctions = new HashMap< String, FunctionNode >();
      functionCalls = new ArrayList< Node >();

      super.visitClassContent( ast );

      for ( final Node call : functionCalls )
      {
         if ( unvisitedPrivateFunctions.containsKey( call.stringValue ) )
         {
            unvisitedPrivateFunctions.remove( call.stringValue );
         }
      }
      for ( final String functionName : unvisitedPrivateFunctions.keySet() )
      {
         final FunctionNode function = unvisitedPrivateFunctions
               .get( functionName );

         addViolation(
               function.getInternalNode(), function.getInternalNode().getChild(
                     function.getInternalNode().numChildren() - 1 ) );
      }
   }

   @Override
   protected void visitExpression(
         final Node ast )
   {
      super.visitExpression( ast );

      if ( ast.is( Node.PRIMARY ) )
      {
         functionCalls.add( ast );
      }
   }

   @Override
   protected void visitFunction(
         final Node ast, final String type )
   {
      super.visitFunction(
            ast, type );
      final FunctionNode function = new FunctionNode( ast );
      if ( ModifierUtils.isPrivate( function ) )
      {
         unvisitedPrivateFunctions.put(
               function.getName(), function );
      }
   }
}
