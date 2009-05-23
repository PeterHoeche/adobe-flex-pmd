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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adobe.ac.pmd.nodes.IFormal;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.IIdentifierNode;
import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.nodes.utils.MetaDataUtils;
import com.adobe.ac.pmd.nodes.utils.ModifierUtils;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.KeyWords;
import com.adobe.ac.pmd.parser.NodeKind;

class FunctionNode extends AbstractNode implements IFunction
{
   private IParserNode                contentBlock;
   private int                        cyclomaticComplexity;
   private Map< String, IParserNode > localVariables;
   private List< IMetaData >          metaDataList;
   private List< Modifier >           modifiers;
   private IdentifierNode             name;

   private List< IFormal >            parameters;
   private IIdentifierNode            returnType;

   public FunctionNode( final IParserNode node )
   {
      super( node );
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.nodes.IFunction#findPrimaryStatementFromName(java.lang
    * .String)
    */
   public IParserNode findPrimaryStatementFromName( final String primaryName )
   {
      final String[] names =
      { primaryName };
      return getPrimaryStatementFromName( names,
                                          getBody() );
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.nodes.IFunction#findPrimaryStatementFromName(java.lang
    * .String[])
    */
   public IParserNode findPrimaryStatementFromName( final String[] primaryNames )
   {
      return getPrimaryStatementFromName( primaryNames,
                                          getBody() );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#getContentBlock()
    */
   public IParserNode getBody()
   {
      return contentBlock;
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
    * @see com.adobe.ac.pmd.nodes.IFunction#getParameters()
    */
   public List< IFormal > getParameters()
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

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#getSuperCall()
    */
   public IParserNode getSuperCall()
   {
      if ( contentBlock != null
            && contentBlock.numChildren() != 0 )
      {
         for ( final IParserNode childContent : contentBlock.getChildren() )
         {
            if ( NodeKind.CALL.equals( childContent.getId() )
                  || NodeKind.DOT.equals( childContent.getId() ) )
            {
               for ( final IParserNode childCall : childContent.getChildren() )
               {
                  if ( childCall.getStringValue() != null
                        && KeyWords.SUPER.toString().equals( childCall.getStringValue() ) )
                  {
                     return childContent;
                  }
               }
            }
         }
      }
      return null;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#isGetter()
    */
   public boolean isGetter()
   {
      return internalNode.is( NodeKind.GET );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#isOverriden()
    */
   public boolean isOverriden()
   {
      return ModifierUtils.isOverriden( this );
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

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IFunction#isSetter()
    */
   public boolean isSetter()
   {
      return internalNode.is( NodeKind.SET );
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
      modifiers = new ArrayList< Modifier >();
      if ( internalNode.numChildren() != 0 )
      {
         for ( final IParserNode node : internalNode.getChildren() )
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
               ModifierUtils.computeModifierList( this,
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
            else
            {
               LOGGER.warning( "unknow type "
                     + node.getId() );
            }
         }
      }
   }

   private void computeCyclomaticComplexity( final IParserNode node )
   {
      if ( node.is( NodeKind.FOREACH )
            || node.is( NodeKind.FORIN ) || node.is( NodeKind.CASE ) || node.is( NodeKind.DEFAULT ) )
      {
         cyclomaticComplexity++;
      }
      else if ( node.is( NodeKind.IF )
            || node.is( NodeKind.WHILE ) || node.is( NodeKind.FOR ) )
      {
         cyclomaticComplexity++;
         cyclomaticComplexity += countNodeFromType( node.getChild( 0 ),
                                                    NodeKind.AND );
         cyclomaticComplexity += countNodeFromType( node.getChild( 0 ),
                                                    NodeKind.OR );
      }

      if ( node.numChildren() > 0 )
      {
         for ( final IParserNode child : node.getChildren() )
         {
            computeCyclomaticComplexity( child );
         }
      }
   }

   private void computeFunctionContent( final IParserNode functionBodyNode )
   {
      localVariables = new HashMap< String, IParserNode >();
      contentBlock = functionBodyNode;
      cyclomaticComplexity = 1;

      computeCyclomaticComplexity( functionBodyNode );
      computeVariableList( functionBodyNode );
   }

   private void computeParameterList( final IParserNode node )
   {
      parameters = new ArrayList< IFormal >();

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

   private IParserNode getPrimaryStatementFromName( final String[] names,
                                                    final IParserNode content )
   {
      IParserNode dispatchNode = null;

      if ( content != null
            && content.getStringValue() != null && isNameInArray( names,
                                                                  content.getStringValue() ) )
      {
         dispatchNode = content;
      }
      else if ( content != null
            && content.numChildren() != 0 )
      {
         for ( final IParserNode child : content.getChildren() )
         {
            dispatchNode = getPrimaryStatementFromName( names,
                                                        child );
            if ( dispatchNode != null )
            {
               break;
            }
         }
      }
      return dispatchNode;
   }

   private boolean isNameInArray( final String[] strings,
                                  final String string )
   {
      for ( final String currentName : strings )
      {
         if ( currentName.compareTo( string ) == 0 )
         {
            return true;
         }
      }
      return false;
   }
}
