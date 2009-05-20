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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adobe.ac.pmd.nodes.FunctionNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

import de.bokelberg.flex.parser.Node;

public class UnusedPrivateMethodRule extends AbstractAstFlexRule
{
   private Map< String, FunctionNode > privateFunctions = null;

   @Override
   protected void findViolationsFromFunctionsList( final List< FunctionNode > functions )
   {
      super.findViolationsFromFunctionsList( functions );

      privateFunctions = new HashMap< String, FunctionNode >();

      for ( final FunctionNode function : functions )
      {
         if ( function.isPrivate() )
         {
            privateFunctions.put( function.getName(),
                                  function );
         }
      }

      for ( final FunctionNode function : functions )
      {
         findUnusedFunction( function.getContentBlock() );
      }

      for ( final String functionName : privateFunctions.keySet() )
      {
         final FunctionNode function = privateFunctions.get( functionName );

         addViolation( function.getInternalNode(),
                       function.getInternalNode().getLastChild(),
                       functionName );
      }
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.WARNING;
   }

   private void findUnusedFunction( final Node body )
   {
      if ( body.stringValue != null
            && !privateFunctions.isEmpty() )
      {
         for ( final String functionName : privateFunctions.keySet() )
         {
            if ( body.stringValue.compareTo( functionName ) == 0 )
            {
               privateFunctions.remove( functionName );
               break;
            }
         }
      }
      if ( body.children != null )
      {
         for ( final Node child : body.children )
         {
            findUnusedFunction( child );
         }
      }
   }
}
