package com.adobe.ac.pmd.nodes.impl;

import java.util.ArrayList;
import java.util.List;

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;

class PackageNode extends AbstractNode implements IPackage
{
   private IClass              classNode;
   private List< IParserNode > imports;
   private String              name;

   protected PackageNode( final IParserNode node )
   {
      super( node );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IPackage#getClassNode()
    */
   public IClass getClassNode()
   {
      return classNode;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IPackage#getFullyQualifiedClassName()
    */
   public String getFullyQualifiedClassName()
   {
      if ( !"".equals( name ) )
      {
         return name
               + "." + classNode.getName();
      }
      return classNode == null ? ""
                              : classNode.getName();
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IPackage#getImports()
    */
   public List< IParserNode > getImports()
   {
      return imports;
   }

   public String getName()
   {
      return name;
   }

   @Override
   protected void compute()
   {
      final IParserNode classWrapperNode = getClassNodeFromCompilationUnitNode( getInternalNode(),
                                                                                3 );
      final IParserNode firstChild = getInternalNode().getChild( 0 );

      imports = new ArrayList< IParserNode >();

      if ( firstChild.numChildren() > 0
            && firstChild.getChild( 0 ).getStringValue() != null )
      {
         name = firstChild.getChild( 0 ).getStringValue();
      }
      else
      {
         name = "";
      }
      if ( classWrapperNode != null )
      {
         classNode = new ClassNode( classWrapperNode );
      }

      if ( firstChild.numChildren() > 1
            && firstChild.getChild( 1 ).numChildren() != 0 )
      {
         final List< IParserNode > children = firstChild.getChild( 1 ).getChildren();

         for ( final IParserNode node : children )
         {
            if ( node.is( NodeKind.IMPORT ) )
            {
               imports.add( node );
            }
         }
      }
   }

   private IParserNode getClassNodeFromCompilationUnitNode( final IParserNode node,
                                                            final int depth )
   {
      if ( depth == 0
            || node.numChildren() == 0 )
      {
         return null;
      }
      for ( final IParserNode child : node.getChildren() )
      {
         if ( child.is( NodeKind.CLASS )
               || child.is( NodeKind.INTERFACE ) )
         {
            return child;
         }
         final IParserNode localClassNode = getClassNodeFromCompilationUnitNode( child,
                                                                                 depth - 1 );

         if ( localClassNode != null )
         {
            return localClassNode;
         }
      }
      return null;
   }
}
