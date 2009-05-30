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

import com.adobe.ac.pmd.files.As3File;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UnusedPrivateMethodRule extends AbstractAstFlexRule
{
   private Map< String, IFunction > privateFunctions = null;

   @Override
   protected void findViolationsFromFunctionsList( final List< IFunction > functions )
   {
      super.findViolationsFromFunctionsList( functions );

      privateFunctions = new HashMap< String, IFunction >();

      for ( final IFunction function : functions )
      {
         if ( function.isPrivate() )
         {
            privateFunctions.put( function.getName(),
                                  function );
         }
      }

      for ( final IFunction function : functions )
      {
         findUnusedFunction( function.getBody() );
      }

      for ( final String functionName : privateFunctions.keySet() )
      {
         final IFunction function = privateFunctions.get( functionName );

         if ( getCurrentFile() instanceof As3File
               || !getCurrentFile().contains( functionName,
                                              new int[]
                                              { function.getInternalNode().getLine() } ) )
         {
            addViolation( function.getInternalNode(),
                          function.getInternalNode().getLastChild(),
                          functionName );
         }
      }
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.WARNING;
   }

   private void findUnusedFunction( final IParserNode body )
   {
      if ( body != null )
      {
         if ( body.getStringValue() != null
               && !privateFunctions.isEmpty() )
         {
            for ( final String functionName : privateFunctions.keySet() )
            {
               if ( body.getStringValue().compareTo( functionName ) == 0 )
               {
                  privateFunctions.remove( functionName );
                  break;
               }
            }
         }
         if ( body.numChildren() != 0 )
         {
            for ( final IParserNode child : body.getChildren() )
            {
               findUnusedFunction( child );
            }
         }
      }
   }
}
