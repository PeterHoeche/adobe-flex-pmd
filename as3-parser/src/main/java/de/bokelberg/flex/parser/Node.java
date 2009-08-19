/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
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
 *      * Neither the name of the Adobe Systems, Inc. nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.bokelberg.flex.parser;

import java.util.ArrayList;
import java.util.List;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;

/**
 * A single node of the ast
 * 
 * @author rbokel
 */
final class Node extends NestedNode implements IParserNode
{
   protected static Node create( final NodeKind idToBeSet,
                                 final int lineToBeSet,
                                 final int columnToBeSet )
   {
      return new Node( idToBeSet, lineToBeSet, columnToBeSet );
   }

   protected static Node create( final NodeKind idToBeSet,
                                 final int lineToBeSet,
                                 final int columnToBeSet,
                                 final IParserNode childToBeSet )
   {
      return new Node( idToBeSet, lineToBeSet, columnToBeSet, childToBeSet );
   }

   protected static Node create( final NodeKind idToBeSet,
                                 final int lineToBeSet,
                                 final int columnToBeSet,
                                 final String valueToBeSet )
   {
      return new Node( idToBeSet, lineToBeSet, columnToBeSet, valueToBeSet );
   }

   private static boolean isNameInArray( final String[] strings,
                                         final String string )
   {
      for ( final String currentName : strings )
      {
         if ( currentName.equals( string ) )
         {
            return true;
         }
      }
      return false;
   }

   private final int column;

   private final int line;

   private String    stringValue;

   private Node( final NodeKind idToBeSet,
                 final int lineToBeSet,
                 final int columnToBeSet )
   {
      super( idToBeSet );

      line = lineToBeSet;
      column = columnToBeSet;
   }

   private Node( final NodeKind idToBeSet,
                 final int lineToBeSet,
                 final int columnToBeSet,
                 final IParserNode childToBeSet )
   {
      super( idToBeSet, childToBeSet );

      line = lineToBeSet;
      column = columnToBeSet;
   }

   private Node( final NodeKind idToBeSet,
                 final int lineToBeSet,
                 final int columnToBeSet,
                 final String valueToBeSet )
   {
      this( idToBeSet, lineToBeSet, columnToBeSet );

      stringValue = valueToBeSet;
   }

   public List< IParserNode > findPrimaryStatementsFromNameInChildren( final String[] names )
   {
      final List< IParserNode > foundNode = new ArrayList< IParserNode >();

      if ( getStringValue() != null
            && isNameInArray( names,
                              getStringValue() ) )
      {
         foundNode.add( this );
      }
      else if ( numChildren() != 0 )
      {
         for ( final IParserNode child : getChildren() )
         {
            foundNode.addAll( child.findPrimaryStatementsFromNameInChildren( names ) );
         }
      }
      return foundNode;
   }

   public int getColumn()
   {
      return column;
   }

   public int getLine()
   {
      return line;
   }

   public String getStringValue()
   {
      return stringValue;
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

   void setStringValue( final String text )
   {
      stringValue = text;
   }
}