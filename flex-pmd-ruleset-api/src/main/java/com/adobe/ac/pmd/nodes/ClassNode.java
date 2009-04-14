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
 * Node representing a class.
 * 
 * It contains different lists (constants, variables, functions, implementations, ...), but also
 * a reference to its constructor (if any), the extension name (if any), and its name.
 * 
 * @author xagnetti
 */
public class ClassNode
      extends AbstractNode implements IModifiersHolder, IMetaDataListHolder, INamable
{
   private List< FieldNode > constants;
   private FunctionNode constructor;
   private String extensionName;
   private List< FunctionNode > functions;
   private List< Node > implementations;
   private List< MetaDataNode > metadata;
   private List< Modifier > modifiers;
   private IdentifierNode name;
   private List< FieldNode > variables;

   public ClassNode(
         final Node node )
   {
      super( node );
   }

   public List< FieldNode > getConstants()
   {
      return constants;
   }

   public FunctionNode getConstructor()
   {
      return constructor;
   }

   public String getExtensionName()
   {
      return extensionName;
   }

   public List< FunctionNode > getFunctions()
   {
      return functions;
   }

   public List< Node > getImplementations()
   {
      return implementations;
   }

   public List< MetaDataNode > getMetaDataList()
   {
      return metadata;
   }

   public List< Modifier > getModifiers()
   {
      return modifiers;
   }

   /* (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.INamable#getName()
    */
   public String getName()
   {
      return name.toString();
   }

   public List< FieldNode > getVariables()
   {
      return variables;
   }

   public boolean isPublic()
   {
      return ModifierUtils.isPublic( this );
   }

   public void setMetaDataList(
         final List< MetaDataNode > metaDataList )
   {
      this.metadata = metaDataList;
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
            if ( node.is( Node.CONTENT ) )
            {
               computeClassContent( node );
            }
            else if ( node.is( Node.MOD_LIST ) )
            {
               ModifierUtils.computeModifierList(
                     this, node );
            }
            else if ( node.is( Node.NAME ) )
            {
               name = new IdentifierNode( node );
            }
            else if ( node.is( Node.META_LIST ) )
            {
               MetaDataUtils.computeMetaDataList(
                     this, node );
            }

            detectImplementations( node );
            detectExtensions( node );
         }
         for ( final FunctionNode function : functions )
         {
            if ( function.getName().toString().equals(
                  name.toString() ) )
            {
               constructor = function;
            }
         }
      }
   }

   private void computeClassContent(
         final Node classContentNode )
   {
      constants = new ArrayList< FieldNode >();
      variables = new ArrayList< FieldNode >();
      functions = new ArrayList< FunctionNode >();
      if ( classContentNode.children != null )
      {
         for ( final Node node : classContentNode.children )
         {
            detectFunction( node );
            detectVariable( node );
            detectConstant( node );
         }
      }
   }

   private void detectConstant(
         final Node node )
   {
      if ( node.is( Node.CONST_LIST ) )
      {
         constants.add( new FieldNode( node ) );
      }
   }

   private void detectExtensions(
         final Node node )
   {
      if ( node.is( KeyWords.EXTENDS ) )
      {
         extensionName = node.stringValue;
      }
   }

   private void detectFunction(
         final Node node )
   {
      if ( node.is( KeyWords.FUNCTION ) || node.is( KeyWords.GET ) || node.is( KeyWords.SET ) )
      {
         functions.add( new FunctionNode( node ) );
      }
   }

   private void detectImplementations(
         final Node node )
   {
      if ( node.is( Node.IMPLEMENTS_LIST ) )
      {
         implementations = node.children;
      }
   }

   private void detectVariable(
         final Node node )
   {
      if ( node.is( Node.VAR_LIST ) )
      {
         variables.add( new FieldNode( node ) );
      }
   }
}
