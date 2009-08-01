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
package com.adobe.ac.pmd.rules.unused;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.KeyWords;
import com.adobe.ac.pmd.parser.NodeKind;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UnusedLocalVariableRule extends AbstractUnusedVariableRule
{
   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected final void visitStatement( final IParserNode ast )
   {
      super.visitStatement( ast );
      tryToAddVariableNodeInChildren( ast );
   }

   @Override
   protected void visitVarOrConstList( final IParserNode ast,
                                       final KeyWords varOrConst )
   {
      super.visitVarOrConstList( ast,
                                 varOrConst );
      tryToAddVariableNodeInChildren( ast );
   }

   private boolean tryToAddVariableNode( final IParserNode ast )
   {
      boolean result = false;

      if ( ast.is( NodeKind.NAME_TYPE_INIT ) )
      {
         addVariable( ast.getChild( 0 ).getStringValue(),
                      ast );
         result = true;
      }
      return result;
   }

   private void tryToAddVariableNodeInChildren( final IParserNode ast )
   {
      if ( ast != null
            && !tryToAddVariableNode( ast ) && ast.is( NodeKind.VAR_LIST ) )
      {
         for ( final IParserNode child : ast.getChildren() )
         {
            tryToAddVariableNode( child );
         }
      }
   }
}