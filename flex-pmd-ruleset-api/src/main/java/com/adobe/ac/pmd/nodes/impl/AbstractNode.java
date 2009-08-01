package com.adobe.ac.pmd.nodes.impl;

import java.util.logging.Logger;

import com.adobe.ac.pmd.nodes.IModifiersHolder;
import com.adobe.ac.pmd.nodes.INode;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.parser.IParserNode;

abstract class AbstractNode implements INode
{
   protected static final Logger LOGGER = Logger.getLogger( "Node" );

   protected static final void computeModifierList( final IModifiersHolder modifiable,
                                                    final IParserNode child )
   {
      if ( child.numChildren() != 0 )
      {
         for ( final IParserNode modifierNode : child.getChildren() )
         {
            final Modifier modifier = Modifier.create( modifierNode.getStringValue() );

            modifiable.add( modifier );
         }
      }
   }

   private final IParserNode internalNode;

   protected AbstractNode( final IParserNode node )
   {
      internalNode = node;

      compute();
   }

   public IParserNode getInternalNode()
   {
      return internalNode;
   }

   protected abstract void compute();
}