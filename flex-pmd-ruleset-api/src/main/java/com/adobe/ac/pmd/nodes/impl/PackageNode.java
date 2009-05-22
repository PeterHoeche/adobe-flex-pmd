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

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;

class PackageNode extends AbstractNode implements IPackage
{
   private IClass              classNode;
   private List< IParserNode > imports;
   private String              name;

   public PackageNode( final IParserNode node )
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
      return name
            + "." + classNode.getName();
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
      final IParserNode classWrapperNode = getClassNodeFromCompilationUnitNode( internalNode,
                                                                                3 );
      final IParserNode firstChild = internalNode.getChild( 0 );

      imports = new ArrayList< IParserNode >();

      if ( firstChild.numChildren() > 0 )
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
