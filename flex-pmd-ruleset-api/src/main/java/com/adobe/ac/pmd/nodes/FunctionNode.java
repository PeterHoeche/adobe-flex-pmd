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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   public static int countNodeFromType( final Node rootNode, final String type )
   {
      int count = 0;

      if ( rootNode.is( type ) )
      {
         count++;
      }
      if ( rootNode.numChildren() > 0 )
      {
         for ( final Node child : rootNode.children )
         {
            count += countNodeFromType( child, type );
         }
      }
      return count;
   }
   private Node contentBlock;
   private int cyclomaticComplexity;
   private Map< String, Node > localVariables;
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
    * @param primaryName statement name
    * @return corresponding node
    */
   public Node findPrimaryStatementFromName(
         final String primaryName )
   {
      final String[] names =
      { primaryName };
      return getPrimaryStatementFromName(
            names, getContentBlock() );
   }

   /**
    * Finds recursivly a statement in the function body from a list of names
    *
    * @param primaryNames statement name
    * @return corresponding node
    */
   public Node findPrimaryStatementFromName(
         final String[] primaryNames )
   {
      return getPrimaryStatementFromName(
            primaryNames, getContentBlock() );
   }

   public Node getContentBlock()
   {
      return contentBlock;
   }

   public int getCyclomaticComplexity()
   {
      return cyclomaticComplexity;
   }

   public Map< String, Node > getLocalVariables()
   {
      return localVariables;
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

   public boolean isOverriden()
   {
      return ModifierUtils.isOverriden( this );
   }

   public boolean isProtected()
   {
      return ModifierUtils.isProtected( this );
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

   private void computeCyclomaticComplexity(
         final Node node )
   {
      if ( node.is( KeyWords.FOREACH )
            || node.is( KeyWords.FORIN ) || node.is( KeyWords.CASE )
            || node.is( KeyWords.DEFAULT ) )
      {
         cyclomaticComplexity++;
      }
      else if ( node.is( KeyWords.IF )
            || node.is( KeyWords.WHILE ) || node.is( KeyWords.FOR ) )
      {
         cyclomaticComplexity++;
         cyclomaticComplexity += countNodeFromType(
               node.getChild( 0 ), Node.AND );
         cyclomaticComplexity += countNodeFromType(
               node.getChild( 0 ), Node.OR );
      }

      if ( node.numChildren() > 0 )
      {
         for ( final Node child : node.children )
         {
            computeCyclomaticComplexity( child );
         }
      }
   }

   private void computeFunctionContent(
         final Node functionBodyNode )
   {
      localVariables = new HashMap< String, Node >();
      contentBlock = functionBodyNode;
      cyclomaticComplexity = 1;

      computeCyclomaticComplexity( functionBodyNode );
      computeVariableList( functionBodyNode );
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

   private void computeVariableList(
         final Node node )
   {
      if ( node.is( Node.VAR_LIST ) )
      {
         localVariables.put(
               node.getChild(
                     0 ).getChild(
                     0 ).stringValue, node );
      }
      else if ( node.numChildren() > 0 )
      {
         for ( final Node child : node.children )
         {
            computeVariableList( child );
         }
      }
   }

   private Node getPrimaryStatementFromName(
         final String[] names, final Node content )
   {
      Node dispatchNode = null;

      if ( content != null
            && content.stringValue != null
            && isNameInArray(
                  names, content.stringValue ) )
      {
         dispatchNode = content;
      }
      else if ( content != null
            && content.numChildren() > 0 )
      {
         for ( final Node child : content.children )
         {
            dispatchNode = getPrimaryStatementFromName(
                  names, child );
            if ( dispatchNode != null )
            {
               break;
            }
         }
      }
      return dispatchNode;
   }

   private boolean isNameInArray(
         final String[] strings, final String string )
   {
      for ( int i = 0; i < strings.length; i++ )
      {
         final String currentName = strings[ i ];

         if ( currentName.compareTo( string ) == 0 )
         {
            return true;
         }
      }
      return false;
   }
}
