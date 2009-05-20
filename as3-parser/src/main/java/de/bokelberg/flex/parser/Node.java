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
package de.bokelberg.flex.parser;

import java.util.ArrayList;
import java.util.List;

import com.adobe.ac.pmd.parser.IParserNode;

/**
 * A single node of the ast
 * 
 * @author rbokel
 */
public class Node implements IParserNode
{
   private List< IParserNode > children;
   private final int           column;
   private String              id;
   private final int           line;
   private String              stringValue;

   public Node( final String idToBeSet,
                final int lineToBeSet,
                final int columnToBeSet )
   {
      id = idToBeSet;
      line = lineToBeSet;
      column = columnToBeSet;
   }

   public Node( final String idToBeSet,
                final int lineToBeSet,
                final int columnToBeSet,
                final IParserNode childToBeSet )
   {
      this( idToBeSet, lineToBeSet, columnToBeSet );
      addChild( childToBeSet );
   }

   public Node( final String idToBeSet,
                final int lineToBeSet,
                final int columnToBeSet,
                final String valueToBeSet )
   {
      this( idToBeSet, lineToBeSet, columnToBeSet );
      stringValue = valueToBeSet;
   }

   final public void addChild( final IParserNode child )
   {
      if ( child == null )
      {
         return; // skip optional children
      }

      if ( children == null )
      {
         children = new ArrayList< IParserNode >();
      }
      children.add( child );
   }

   public void addChild( final String childId,
                         final int childLine,
                         final int childColumn,
                         final IParserNode nephew )
   {
      addChild( new Node( childId, childLine, childColumn, nephew ) );
   }

   public void addChild( final String childId,
                         final int childLine,
                         final int childColumn,
                         final String value )
   {
      addChild( new Node( childId, childLine, childColumn, value ) );
   }

   public boolean findChildFromTokenType( final String tokenTypeToFind )
   {
      if ( numChildren() == 0 )
      {
         return false;
      }

      for ( final IParserNode node : getChildren() )
      {
         if ( node.is( tokenTypeToFind ) )
         {
            return true;
         }
      }
      return false;
   }

   /*
    * (non-Javadoc)
    * @see de.bokelberg.flex.parser.IParserNode#getChild(int)
    */
   public IParserNode getChild( final int index )
   {
      return getChildren() == null
            || getChildren().size() <= index ? null
                                            : getChildren().get( index );
   }

   public List< IParserNode > getChildren()
   {
      return children;
   }

   public int getColumn()
   {
      return column;
   }

   public String getId()
   {
      return id;
   }

   /*
    * (non-Javadoc)
    * @see de.bokelberg.flex.parser.IParserNode#getLastChild()
    */
   public IParserNode getLastChild()
   {
      return getChild( numChildren() - 1 );
   }

   public int getLine()
   {
      return line;
   }

   public String getStringValue()
   {
      return stringValue;
   }

   /*
    * (non-Javadoc)
    * @see de.bokelberg.flex.parser.IParserNode#is(java.lang.String)
    */
   public boolean is( final String expectedType )
   {
      return getId() == null
            && expectedType == null || expectedType.equals( getId() );
   }

   public boolean isValueEquals( final String value )
   {
      return getStringValue() == null
            && value == null || value.equals( getStringValue() );
   }

   /*
    * (non-Javadoc)
    * @see de.bokelberg.flex.parser.IParserNode#numChildren()
    */
   public int numChildren()
   {
      return getChildren() == null ? 0
                                  : getChildren().size();
   }

   public void setId( final String idToBeSet )
   {
      id = idToBeSet;
   }

   public void setStringValue( final String text )
   {
      stringValue = text;
   }

   @Override
   public String toString()
   {
      final StringBuffer buffer = new StringBuffer();

      if ( getStringValue() == null )
      {
         buffer.append( getId() );
      }
      else
      {
         buffer.append( getStringValue() );
      }

      buffer.append( ' ' );

      if ( getChildren() != null )
      {
         for ( final IParserNode child : getChildren() )
         {
            buffer.append( child.toString() );
            buffer.append( ' ' );
         }
      }
      return buffer.toString();
   }
}