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

import java.util.ArrayList;
import java.util.List;

import com.adobe.ac.pmd.nodes.utils.MetaDataUtils;
import com.adobe.ac.pmd.nodes.utils.ModifierUtils;

import de.bokelberg.flex.parser.Node;

/**
 * Node representing a variable (var i : int = 0)
 *
 * It contains the variable name, its type name, the list of modifiers, the list of metadata,
 * and the initialization expression (if any).
 *
 * @author xagnetti
 */
public class VariableNode
      extends AbstractNode implements IModifiersHolder, IMetaDataListHolder, INamable
{
   private FieldInitializationNode initializationExpression;
   private List< MetaDataNode > metaDataList;
   private List< Modifier > modifiers;
   private IdentifierNode name;
   private IdentifierNode type;

   public VariableNode(
         final Node rootNode )
   {
      super( rootNode );
   }

   public FieldInitializationNode getInitializationExpression()
   {
      return initializationExpression;
   }

   public List< MetaDataNode > getMetaDataList()
   {
      return metaDataList;
   }

   public List< Modifier > getModifiers()
   {
      return modifiers;
   }

   public String getName()
   {
      return name.toString();
   }

   public IdentifierNode getType()
   {
      return type;
   }

   public boolean isPublic()
   {
      return ModifierUtils.isPublic( this );
   }

   public void setMetaDataList(
         final List< MetaDataNode > metaDataListToBeSet )
   {
      metaDataList = metaDataListToBeSet;
   }

   public void setModifiers(
         final List< Modifier > modifiersToBeSet )
   {
      modifiers = modifiersToBeSet;
   }

   @Override
   protected void compute()
   {
      metaDataList = new ArrayList< MetaDataNode >();

      if ( internalNode.is( Node.NAME_TYPE_INIT ) )
      {
         computeNameTypeInit( internalNode );
      }
      else
      {
         if ( internalNode.children != null )
         {
            for ( final Node child : internalNode.children )
            {
               if ( child.is( Node.NAME_TYPE_INIT ) )
               {
                  computeNameTypeInit( child );
               }
               else if ( child.is( Node.MOD_LIST ) )
               {
                  ModifierUtils.computeModifierList(
                        this, child );
               }
               else if ( child.is( Node.META_LIST ) )
               {
                  MetaDataUtils.computeMetaDataList(
                        this, child );
               }
               else if ( !child.is( Node.REST ) )
               {
                  LOGGER.warning( "unknown node type "
                        + child.id );
               }
            }
         }
      }
   }

   private void computeNameTypeInit(
         final Node nameTypeInit )
   {
      if ( nameTypeInit.children != null )
      {
         for ( final Node child : nameTypeInit.children )
         {
            if ( child.is( Node.NAME ) )
            {
               name = new IdentifierNode( child );
            }
            else if ( child.is( Node.TYPE ) )
            {
               type = new IdentifierNode( child );
            }
            else if ( child.is( Node.INIT ) )
            {
               initializationExpression = new FieldInitializationNode( child );
            }
            else
            {
               LOGGER.warning( "unknown node type "
                     + child.toString() );
            }
         }
      }
   }
}
