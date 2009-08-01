package com.adobe.ac.pmd.nodes.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IConstant;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.nodes.utils.MetaDataUtils;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;

class ClassNode extends AbstractNode implements IClass
{
   private List< IAttribute >               attributes;
   private List< IConstant >                constants;
   private IFunction                        constructor;
   private String                           extensionName;
   private List< IFunction >                functions;
   private List< IParserNode >              implementations;
   private Map< String, List< IMetaData > > metadatas;
   private Set< Modifier >                  modifiers;
   private IdentifierNode                   name;

   protected ClassNode( final IParserNode node )
   {
      super( node );
   }

   public void add( final IMetaData metaData )
   {
      if ( !metadatas.containsKey( metaData.getName() ) )
      {
         metadatas.put( metaData.getName(),
                        new ArrayList< IMetaData >() );
      }
      metadatas.get( metaData.getName() ).add( metaData );
   }

   public void add( final Modifier modifier )
   {
      modifiers.add( modifier );
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

   public List< IMetaData > getMetaData( final String metaDataName )
   {
      return metadatas.get( metaDataName );
   }

   public int getMetaDataCount()
   {
      return metadatas.size();
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.INamable#getName()
    */
   public String getName()
   {
      return name.toString();
   }

   public boolean is( final Modifier modifier )
   {
      return modifiers.contains( modifier );
   }

   public boolean isBindable()
   {
      return metadatas.get( "Bindable" ) != null;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IClass#isFinal()
    */
   public boolean isFinal()
   {
      return is( Modifier.FINAL );
   }

   public boolean isPublic()
   {
      return is( Modifier.PUBLIC );
   }

   @Override
   protected void compute()
   {
      modifiers = new HashSet< Modifier >();
      metadatas = new HashMap< String, List< IMetaData > >();
      implementations = new ArrayList< IParserNode >();

      if ( getInternalNode().numChildren() != 0 )
      {
         for ( final IParserNode node : getInternalNode().getChildren() )
         {
            if ( node.is( NodeKind.CONTENT ) )
            {
               computeClassContent( node );
            }
            else if ( node.is( NodeKind.MOD_LIST ) )
            {
               computeModifierList( this,
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
