package com.adobe.ac.pmd.nodes.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adobe.ac.pmd.nodes.IFieldInitialization;
import com.adobe.ac.pmd.nodes.IIdentifierNode;
import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.nodes.IModifiersHolder;
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
class VariableNode extends AbstractNode implements IVariable, IModifiersHolder
{
   private IFieldInitialization             initializationExpression;
   private Map< String, List< IMetaData > > metaDataList;
   private Set< Modifier >                  modifiers;
   private IdentifierNode                   name;
   private IdentifierNode                   type;

   protected VariableNode( final IParserNode rootNode )
   {
      super( rootNode );
   }

   public void add( final IMetaData metaData )
   {
      if ( !metaDataList.containsKey( metaData.getName() ) )
      {
         metaDataList.put( metaData.getName(),
                           new ArrayList< IMetaData >() );
      }
      metaDataList.get( metaData.getName() ).add( metaData );
   }

   public void add( final Modifier modifier )
   {
      modifiers.add( modifier );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IVariable#getInitializationExpression()
    */
   public IFieldInitialization getInitializationExpression()
   {
      return initializationExpression;
   }

   public List< IMetaData > getMetaData( final String metaDataName )
   {
      return metaDataList.get( metaDataName );
   }

   public int getMetaDataCount()
   {
      return metaDataList.size();
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

   public boolean is( final Modifier modifier )
   {
      return modifiers.contains( modifier );
   }

   @Override
   protected void compute()
   {
      metaDataList = new HashMap< String, List< IMetaData > >();
      modifiers = new HashSet< Modifier >();

      if ( getInternalNode().is( NodeKind.NAME_TYPE_INIT ) )
      {
         computeNameTypeInit( getInternalNode() );
      }
      else
      {
         if ( getInternalNode().numChildren() != 0 )
         {
            for ( final IParserNode child : getInternalNode().getChildren() )
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
