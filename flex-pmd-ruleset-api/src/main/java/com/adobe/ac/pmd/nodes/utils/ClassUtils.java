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
package com.adobe.ac.pmd.nodes.utils;

import de.bokelberg.flex.parser.KeyWords;
import de.bokelberg.flex.parser.Node;

final public class ClassUtils
{
   public static Node getClassExtension( final Node classNode )
   {
      Node content = null;

      if ( classNode != null
            && classNode.children != null )
      {
         for ( final Node child : classNode.children )
         {
            if ( KeyWords.EXTENDS.equals( child.id ) )
            {
               content = child;
               break;
            }
         }
      }
      return content;
   }

   public static String getClassNameFromClassNode( final Node classNode )
   {
      return classNode.getChild( 0 ).stringValue;
   }

   public static Node getTypeFromFieldDeclaration( final Node fieldNode )
   {
      Node typeNode = null;

      for ( final Node node : fieldNode.children )
      {
         if ( node.is( Node.NAME_TYPE_INIT )
               && node.numChildren() > 1 )
         {
            typeNode = node.getChild( 1 );
            break;
         }
      }
      return typeNode;
   }

   private ClassUtils()
   {
   }
}
