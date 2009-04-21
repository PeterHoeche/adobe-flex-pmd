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
package com.adobe.ac.pmd.nodes;

import java.util.ArrayList;
import java.util.List;

import de.bokelberg.flex.parser.KeyWords;
import de.bokelberg.flex.parser.Node;

/**
 * Node representing a package.
 *
 * It contains the nested class node, the list of imports, and the package name.
 *
 * @author xagnetti
 */
public class PackageNode
      extends AbstractNode implements INamable
{
   private ClassNode classNode;
   private List< Node > imports;
   private String name;

   public PackageNode(
         final Node node )
   {
      super( node );
   }

   public ClassNode getClassNode()
   {
      return classNode;
   }

   public String getFullyQualifiedClassName()
   {
      return name + "." + classNode.getName().toString();
   }

   public List< Node > getImports()
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
      final Node classWrapperNode = getClassNodeFromCompilationUnitNode(
            internalNode, 3 );

      name = internalNode.getChild( 0 ).getChild( 0 ).stringValue;
      imports = new ArrayList< Node >();
      classNode = new ClassNode( classWrapperNode );
      try
      {
      for ( final Node node : internalNode.getChild(
            0 ).getChild(
            1 ).children )
      {
         if ( node.is( KeyWords.IMPORT ) )
         {
            imports.add( node );
         }
      }
      }
      catch ( final NullPointerException e )
      {
      }
   }

   private Node getClassNodeFromCompilationUnitNode(
         final Node node, final int depth )
   {
      if ( depth == 0
            || node.children == null )
      {
         return null;
      }
      for ( final Node child : node.children )
      {
         if ( child.is( KeyWords.CLASS ) || child.is( KeyWords.INTERFACE ) )
         {
            return child;
         }
         final Node localClassNode = getClassNodeFromCompilationUnitNode(
               child, depth - 1 );

         if ( localClassNode != null )
         {
            return localClassNode;
         }
      }
      return null;
   }
}
