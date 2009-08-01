package com.adobe.ac.pmd.nodes.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.IIdentifierNode;
import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.nodes.IParameter;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.nodes.utils.MetaDataUtils;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.KeyWords;
import com.adobe.ac.pmd.parser.NodeKind;

class FunctionNode extends AbstractNode implements IFunction
{
   private IParserNode                      body;
   private int                              cyclomaticComplexity;
   private Map< String, IParserNode >       localVariables;
   private Map< String, List< IMetaData > > metaDataList;
   private Set< Modifier >                  modifiers;
   private IdentifierNode                   name;
   private List< IParameter >               parameters;
   private IIdentifierNode                  returnType;

   protected FunctionNode( final IParserNode node )
   {
      super( node );
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
    * @see com.adobe.ac.pmd.nodes.IFunction#findPrimaryStatementInBody(java.lang
    * .String[])
    */
   public List< IParserNode > findPrimaryStatementInBody( final String[] primaryNames )
   {
      return body == null ? null
                         : body.findPrimaryStatementsFromNameInChildren( primaryNames );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#findPrimaryStatementInBody(java.lang
    * .String)
    */
   public List< IParserNode > findPrimaryStatementsInBody( final String primaryName )
   {
      return body == null ? new ArrayList< IParserNode >()
                         : body.findPrimaryStatementsFromNameInChildren( new String[]
                         { primaryName } );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#getBody()
    */
   public IParserNode getBody()
   {
      return body;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#getCyclomaticComplexity()
    */
   public int getCyclomaticComplexity()
   {
      return cyclomaticComplexity;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#getLocalVariables()
    */
   public Map< String, IParserNode > getLocalVariables()
   {
      return localVariables;
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
    * @see com.adobe.ac.pmd.nodes.IFunction#getParameters()
    */
   public List< IParameter > getParameters()
   {
      return parameters;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#getReturnType()
    */
   public IIdentifierNode getReturnType()
   {
      return returnType;
   }

   public int getStatementNbInBody()
   {
      return 1 + getStatementInNode( body );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#getSuperCall()
    */
   public IParserNode getSuperCall()
   {
      if ( body != null
            && body.numChildren() != 0 )
      {
         for ( final IParserNode childContent : body.getChildren() )
         {
            if ( NodeKind.CALL.equals( childContent.getId() )
                  || NodeKind.DOT.equals( childContent.getId() ) )
            {
               for ( final IParserNode childCall : childContent.getChildren() )
               {
                  if ( KeyWords.SUPER.toString().equals( childCall.getStringValue() ) )
                  {
                     return childContent;
                  }
               }
            }
         }
      }
      return null;
   }

   public boolean is( final Modifier modifier )
   {
      return modifiers.contains( modifier );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#isGetter()
    */
   public boolean isGetter()
   {
      return getInternalNode().is( NodeKind.GET );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#isOverriden()
    */
   public boolean isOverriden()
   {
      return is( Modifier.OVERRIDE );
   }

   public boolean isPublic()
   {
      return is( Modifier.PUBLIC );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#isSetter()
    */
   public boolean isSetter()
   {
      return getInternalNode().is( NodeKind.SET );
   }

   @Override
   protected void compute()
   {
      modifiers = new HashSet< Modifier >();
      metaDataList = new HashMap< String, List< IMetaData > >();
      localVariables = new HashMap< String, IParserNode >();
      parameters = new ArrayList< IParameter >();

      if ( getInternalNode().numChildren() != 0 )
      {
         for ( final IParserNode node : getInternalNode().getChildren() )
         {
            if ( node.is( NodeKind.BLOCK ) )
            {
               computeFunctionContent( node );
            }
            else if ( node.is( NodeKind.NAME ) )
            {
               name = new IdentifierNode( node );
            }
            else if ( node.is( NodeKind.MOD_LIST ) )
            {
               computeModifierList( this,
                                    node );
            }
            else if ( node.is( NodeKind.PARAMETER_LIST ) )
            {
               computeParameterList( node );
            }
            else if ( node.is( NodeKind.TYPE ) )
            {
               returnType = new IdentifierNode( node );
            }
            else if ( node.is( NodeKind.META_LIST ) )
            {
               MetaDataUtils.computeMetaDataList( this,
                                                  node );
            }
         }
      }
   }

   private void computeCyclomaticComplexity()
   {
      cyclomaticComplexity = 1 + body.computeCyclomaticComplexity();
   }

   private void computeFunctionContent( final IParserNode functionBodyNode )
   {
      body = functionBodyNode;

      computeCyclomaticComplexity();
      computeVariableList( body );
   }

   private void computeParameterList( final IParserNode node )
   {
      if ( node.numChildren() != 0 )
      {
         for ( final IParserNode parameterNode : node.getChildren() )
         {
            parameters.add( new FormalNode( parameterNode ) );
         }
      }
   }

   private void computeVariableList( final IParserNode node )
   {
      if ( node.is( NodeKind.VAR_LIST ) )
      {
         localVariables.put( node.getChild( 0 ).getChild( 0 ).getStringValue(),
                             node );
      }
      else if ( node.numChildren() > 0 )
      {
         for ( final IParserNode child : node.getChildren() )
         {
            computeVariableList( child );
         }
      }
   }

   private int getStatementInNode( final IParserNode node )
   {
      int statementNb = 0;

      if ( node != null
            && node.numChildren() != 0 )
      {
         int lastLine = node.getChild( 0 ).getLine();
         for ( final IParserNode childContent : node.getChildren() )
         {
            if ( childContent.getLine() != lastLine )
            {
               lastLine = childContent.getLine();
               statementNb++;
            }
            statementNb += getStatementInNode( childContent );
         }
      }

      return statementNb;
   }
}
