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
package com.adobe.ac.pmd.rules.cairngorm;

import com.adobe.ac.pmd.files.AbstractFlexFile;
import com.adobe.ac.pmd.nodes.ClassNode;
import com.adobe.ac.pmd.nodes.FieldNode;
import com.adobe.ac.pmd.nodes.FunctionNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

import de.bokelberg.flex.parser.Node;

public class BadCairngormEventNameFormatRule extends AbstractAstFlexRule
{
   @Override
   public boolean isConcernedByTheGivenFile( final AbstractFlexFile file )
   {
      return file.getClassName().endsWith( "Event.as" );
   }

   @Override
   protected void findViolationsFromClassNode( final ClassNode classNode )
   {
      if ( classNode.getExtensionName() != null
            && classNode.getExtensionName().contains( "Cairngorm" ) && classNode.getExtensionName().contains( "Event" ) )
      {
         String eventName = "";

         for ( final FieldNode constantNode : classNode.getConstants() )
         {
            if ( constantNode.getName().startsWith( "EVENT" ) )
            {
               eventName = extractEventNameFromConstant( constantNode.getInitializationExpression().getInternalNode() );
            }
         }
         if ( eventName.compareTo( "" ) == 0
               && classNode.getConstructor() != null )
         {
            eventName = extractEventNameFromConstructor( classNode.getConstructor() );
         }
         if ( !eventName.contains( "." ) )
         {
            addViolation( classNode.getInternalNode(),
                          classNode.getInternalNode() );
         }
      }
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.WARNING;
   }

   private String extractEventNameFromConstant( final Node initExpressionNode )
   {
      return initExpressionNode.children.get( 0 ).stringValue;
   }

   private String extractEventNameFromConstructor( final FunctionNode constructor )
   {
      String eventName = "";
      final Node superCall = constructor.getSuperCall();

      if ( superCall != null )
      {
         eventName = superCall.children.get( 1 ).children.get( 0 ).stringValue;
      }
      return eventName;
   }
}
