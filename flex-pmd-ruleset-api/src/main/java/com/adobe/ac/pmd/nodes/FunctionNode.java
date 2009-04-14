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

import de.bokelberg.flex.parser.KeyWords;
import de.bokelberg.flex.parser.Node;

/**
 * Node representing a Function It contains the function name, its parameters,
 * its return type, its modifiers, its metadata
 *
 * @author xagnetti
 */
public class FunctionNode
      extends AbstractNode implements IModifiersHolder, IMetaDataListHolder,
      INamable
{
   private Node contentBlock;
   private List< MetaDataNode > metaDataList;
   private List< Modifier > modifiers;
   private IdentifierNode name;
   private List< FormalNode > parameters;
   private IdentifierNode returnType;

   public FunctionNode(
         final Node node )
   {
      super( node );
   }

   /**
    * Finds recursivly a statement in the function body from its name
    *
    * @param name statement name
    * @return corresponding node
    */
   public Node findPrimaryStatementFromName(
         final String name )
   {
      return getPrimaryStatementFromName(
            name, getContentBlock() );
   }

   public Node getContentBlock()
   {
      return contentBlock;
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

   public List< FormalNode > getParameters()
   {
      return parameters;
   }

   public IdentifierNode getReturnType()
   {
      return returnType;
   }

   /**
    * @return Extracts the super call node (if any) from the function content
    *         block
    */
   public Node getSuperCall()
   {
      // TODO return findPrimaryStatementFromName( "super" )
      if ( contentBlock != null
            && contentBlock.children != null )
      {
         for ( final Node childContent : contentBlock.children )
         {
            if ( Node.CALL.equals( childContent.id ) )
            {
               for ( final Node childCall : childContent.children )
               {
                  if ( childCall.stringValue != null
                        && KeyWords.SUPER.equals( childCall.stringValue ) )
                  {
                     return childContent;
                  }
               }
            }
         }
      }
      return null;
   }

   public boolean isPublic()
   {
      return ModifierUtils.isPublic( this );
   }

   public void setMetaDataList(
         final List< MetaDataNode > metaDataList )
   {
      this.metaDataList = metaDataList;
   }

   public void setModifiers(
         final List< Modifier > modifiers )
   {
      this.modifiers = modifiers;
   }

   @Override
   protected void compute()
   {
      modifiers = new ArrayList< Modifier >();
      if ( internalNode.children != null )
      {
         for ( final Node node : internalNode.children )
         {
            if ( node.is( Node.BLOCK ) )
            {
               computeFunctionContent( node );
            }
            else if ( node.is( Node.NAME ) )
            {
               name = new IdentifierNode( node );
            }
            else if ( node.is( Node.MOD_LIST ) )
            {
               ModifierUtils.computeModifierList(
                     this, node );
            }
            else if ( node.is( Node.PARAMETER_LIST ) )
            {
               computeParameterList( node );
            }
            else if ( node.is( Node.TYPE ) )
            {
               returnType = new IdentifierNode( node );
            }
            else if ( node.is( Node.META_LIST ) )
            {
               MetaDataUtils.computeMetaDataList(
                     this, node );
            }
            else
            {
               LOGGER.warning( "unknow type "
                     + node.id );
            }
         }
      }
   }

   private void computeFunctionContent(
         final Node node )
   {
      contentBlock = node;
   }

   private void computeParameterList(
         final Node node )
   {
      parameters = new ArrayList< FormalNode >();

      if ( node.children != null )
      {
         for ( final Node parameterNode : node.children )
         {
            parameters.add( new FormalNode( parameterNode ) );
         }
      }
   }

   private Node getPrimaryStatementFromName(
         final String name, final Node content )
   {
      Node dispatchNode = null;

      if ( content.stringValue != null
            && name.compareTo( content.stringValue ) == 0 )
      {
         dispatchNode = content;
      }
      else if ( content.numChildren() > 0 )
      {
         for ( final Node child : content.children )
         {
            dispatchNode = getPrimaryStatementFromName(
                  name, child );
            if ( dispatchNode != null )
            {
               break;
            }
         }
      }
      return dispatchNode;
   }
}
