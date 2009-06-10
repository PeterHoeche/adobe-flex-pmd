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
package com.adobe.ac.pmd.nodes.impl;

import com.adobe.ac.pmd.nodes.IField;
import com.adobe.ac.pmd.nodes.utils.ModifierUtils;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;

/**
 * Base class for AttributeNode and for ConstantNode
 * 
 * @author xagnetti
 */
class FieldNode extends VariableNode implements IField
{
   protected FieldNode( final IParserNode rootNode )
   {
      super( rootNode );
   }

   public boolean isPrivate()
   {
      return ModifierUtils.isPrivate( this );
   }

   public boolean isProtected()
   {
      return ModifierUtils.isProtected( this );
   }

   public boolean isPublic()
   {
      return ModifierUtils.isPublic( this );
   }

   public boolean isStatic()
   {
      return ModifierUtils.isStatic( this );
   }

   @Override
   protected void compute()
   {
      super.compute();

      if ( getInternalNode().numChildren() != 0 )
      {
         for ( final IParserNode child : getInternalNode().getChildren() )
         {
            if ( child.is( NodeKind.MOD_LIST ) )
            {
               ModifierUtils.computeModifierList( this,
                                                  child );
            }
         }
      }
   }
}
