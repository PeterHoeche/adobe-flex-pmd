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

import java.util.ArrayList;
import java.util.List;

import com.adobe.ac.pmd.nodes.IFieldInitialization;
import com.adobe.ac.pmd.nodes.IIdentifierNode;
import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.nodes.IVariable;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.nodes.utils.MetaDataUtils;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;

/**
 * Node representing a variable (var i : int = 0) It contains the variable name,
 * its type name, the list of modifiers, the list of metadata, and the
 * initialization expression (if any).
 *
 * @author xagnetti
 */
public class VariableNode extends AbstractNode implements IVariable
{
   private IFieldInitialization initializationExpression;
   private List< IMetaData >    metaDataList;
   private List< Modifier >     modifiers;
   private IdentifierNode       name;
   private IdentifierNode       type;

   public VariableNode( final IParserNode rootNode )
   {
      super( rootNode );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IVariable#getInitializationExpression()
    */
   public IFieldInitialization getInitializationExpression()
   {
      return initializationExpression;
   }

   public List< IMetaData > getMetaDataList()
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

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IVariable#getType()
    */
   public IIdentifierNode getType()
   {
      return type;
   }

   public void setMetaDataList( final List< IMetaData > metaDataListToBeSet )
   {
      metaDataList = metaDataListToBeSet;
   }

   public void setModifiers( final List< Modifier > modifiersToBeSet )
   {
      modifiers = modifiersToBeSet;
   }

   @Override
   protected void compute()
   {
      metaDataList = new ArrayList< IMetaData >();

      if ( internalNode.is( NodeKind.NAME_TYPE_INIT ) )
      {
         computeNameTypeInit( internalNode );
      }
      else
      {
         if ( internalNode.numChildren() != 0 )
         {
            for ( final IParserNode child : internalNode.getChildren() )
            {
               if ( child.is( NodeKind.NAME_TYPE_INIT ) )
               {
                  computeNameTypeInit( child );
               }
               else if ( child.is( NodeKind.META_LIST ) )
               {
                  MetaDataUtils.computeMetaDataList( this,
                                                     child );
               }
            }
         }
      }
   }

   private void computeNameTypeInit( final IParserNode nameTypeInit )
   {
      if ( nameTypeInit.numChildren() != 0 )
      {
         for ( final IParserNode child : nameTypeInit.getChildren() )
         {
            if ( child.is( NodeKind.NAME ) )
            {
               name = new IdentifierNode( child );
            }
            else if ( child.is( NodeKind.TYPE ) )
            {
               type = new IdentifierNode( child );
            }
            else if ( child.is( NodeKind.INIT ) )
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
