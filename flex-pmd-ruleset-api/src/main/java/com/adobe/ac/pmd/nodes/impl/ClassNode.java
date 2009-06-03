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

import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IConstant;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.nodes.utils.MetaDataUtils;
import com.adobe.ac.pmd.nodes.utils.ModifierUtils;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;

class ClassNode extends AbstractNode implements IClass
{
   private List< IAttribute >  attributes;
   private List< IConstant >   constants;
   private IFunction           constructor;
   private String              extensionName;
   private List< IFunction >   functions;
   private List< IParserNode > implementations;
   private List< IMetaData >   metadata;
   private List< Modifier >    modifiers;
   private IdentifierNode      name;

   public ClassNode( final IParserNode node )
   {
      super( node );
   }

   public List< IAttribute > getAttributes()
   {
      return attributes;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IClass#getConstants()
    */
   public List< IConstant > getConstants()
   {
      return constants;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IClass#getConstructor()
    */
   public IFunction getConstructor()
   {
      return constructor;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IClass#getExtensionName()
    */
   public String getExtensionName()
   {
      return extensionName;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IClass#getFunctions()
    */
   public List< IFunction > getFunctions()
   {
      return functions;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IClass#getImplementations()
    */
   public List< IParserNode > getImplementations()
   {
      return implementations;
   }

   public List< IMetaData > getMetaDataList()
   {
      return metadata;
   }

   public List< Modifier > getModifiers()
   {
      return modifiers;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.INamable#getName()
    */
   public String getName()
   {
      return name.toString();
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IClass#isFinal()
    */
   public boolean isFinal()
   {
      return ModifierUtils.isFinal( this );
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

   public void setMetaDataList( final List< IMetaData > metaDataList )
   {
      metadata = metaDataList;
   }

   public void setModifiers( final List< Modifier > modifiersToBeSet )
   {
      modifiers = modifiersToBeSet;
   }

   @Override
   protected void compute()
   {
      modifiers = new ArrayList< Modifier >();
      implementations = new ArrayList< IParserNode >();
      metadata = new ArrayList< IMetaData >();

      if ( internalNode.numChildren() != 0 )
      {
         for ( final IParserNode node : internalNode.getChildren() )
         {
            if ( node.is( NodeKind.CONTENT ) )
            {
               computeClassContent( node );
            }
            else if ( node.is( NodeKind.MOD_LIST ) )
            {
               ModifierUtils.computeModifierList( this,
                                                  node );
            }
            else if ( node.is( NodeKind.NAME ) )
            {
               name = new IdentifierNode( node );
            }
            else if ( node.is( NodeKind.META_LIST ) )
            {
               MetaDataUtils.computeMetaDataList( this,
                                                  node );
            }
            detectImplementations( node );
            detectExtensions( node );
         }
         for ( final IFunction function : functions )
         {
            if ( name.toString().equals( function.getName() ) )
            {
               constructor = function;
            }
         }
      }
   }

   private void computeClassContent( final IParserNode classContentNode )
   {
      constants = new ArrayList< IConstant >();
      attributes = new ArrayList< IAttribute >();
      functions = new ArrayList< IFunction >();
      if ( classContentNode.numChildren() != 0 )
      {
         for ( final IParserNode node : classContentNode.getChildren() )
         {
            detectFunction( node );
            detectAttribute( node );
            detectConstant( node );
         }
      }
   }

   private void detectAttribute( final IParserNode node )
   {
      if ( node.is( NodeKind.VAR_LIST ) )
      {
         attributes.add( new AttributeNode( node ) );
      }
   }

   private void detectConstant( final IParserNode node )
   {
      if ( node.is( NodeKind.CONST_LIST ) )
      {
         constants.add( new ConstantNode( node ) );
      }
   }

   private void detectExtensions( final IParserNode node )
   {
      if ( node.is( NodeKind.EXTENDS ) )
      {
         extensionName = node.getStringValue();
      }
   }

   private void detectFunction( final IParserNode node )
   {
      if ( node.is( NodeKind.FUNCTION )
            || node.is( NodeKind.GET ) || node.is( NodeKind.SET ) )
      {
         functions.add( new FunctionNode( node ) );
      }
   }

   private void detectImplementations( final IParserNode node )
   {
      if ( node.is( NodeKind.IMPLEMENTS_LIST ) )
      {
         implementations = node.getChildren();
      }
   }
}
