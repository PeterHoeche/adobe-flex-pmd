package de.bokelberg.flex.parser;

import java.util.ArrayList;
import java.util.List;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;

class NestedNode
{
   private List< IParserNode > children;
   private NodeKind            nodeId;

   protected NestedNode( final NodeKind idToBeSet )
   {
      nodeId = idToBeSet;
   }

   protected NestedNode( final NodeKind idToBeSet,
                         final IParserNode childToBeSet )
   {
      this( idToBeSet );
      addChild( childToBeSet );
   }

   final public int computeCyclomaticComplexity()
   {
      int cyclomaticComplexity = 0;

      if ( is( NodeKind.FOREACH )
            || is( NodeKind.FORIN ) || is( NodeKind.CASE ) || is( NodeKind.DEFAULT ) )
      {
         cyclomaticComplexity++;
      }
      else if ( is( NodeKind.IF )
            || is( NodeKind.WHILE ) || is( NodeKind.FOR ) )
      {
         cyclomaticComplexity++;
         cyclomaticComplexity += getChild( 0 ).countNodeFromType( NodeKind.AND );
         cyclomaticComplexity += getChild( 0 ).countNodeFromType( NodeKind.OR );
      }

      if ( numChildren() > 0 )
      {
         for ( final IParserNode child : getChildren() )
         {
            cyclomaticComplexity += child.computeCyclomaticComplexity();
         }
      }

      return cyclomaticComplexity;
   }

   final public int countNodeFromType( final NodeKind type )
   {
      int count = 0;

      if ( is( type ) )
      {
         count++;
      }
      if ( numChildren() > 0 )
      {
         for ( final IParserNode child : getChildren() )
         {
            count += child.countNodeFromType( type );
         }
      }
      return count;
   }

   final public IParserNode getChild( final int index )
   {
      return getChildren() == null
            || getChildren().size() <= index ? null
                                            : getChildren().get( index );
   }

   public List< IParserNode > getChildren()
   {
      return children;
   }

   public NodeKind getId()
   {
      return nodeId;
   }

   public IParserNode getLastChild()
   {
      return getChild( numChildren() - 1 );
   }

   final public boolean is( final NodeKind expectedType )
   {
      return getId() == null
            && expectedType == null || expectedType.equals( getId() );
   }

   final public int numChildren()
   {
      return getChildren() == null ? 0
                                  : getChildren().size();
   }

   final void addChild( final IParserNode child )
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

   final void addChild( final NodeKind childId,
                        final int childLine,
                        final int childColumn,
                        final IParserNode nephew )
   {
      addChild( Node.create( childId,
                             childLine,
                             childColumn,
                             nephew ) );
   }

   final void addChild( final NodeKind childId,
                        final int childLine,
                        final int childColumn,
                        final String value )
   {
      addChild( Node.create( childId,
                             childLine,
                             childColumn,
                             value ) );
   }

   final void setId( final NodeKind idToBeSet )
   {
      nodeId = idToBeSet;
   }
}
