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
package com.adobe.ac.pmd.rules.as3;

import java.util.Map;

import com.adobe.ac.pmd.files.AbstractFlexFile;
import com.adobe.ac.pmd.nodes.FieldInitializationNode;
import com.adobe.ac.pmd.nodes.FieldNode;
import com.adobe.ac.pmd.nodes.PackageNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

import de.bokelberg.flex.parser.Node;

public class BadFormatLogerRule
      extends AbstractAstFlexRule
{
   public boolean isConcernedByTheGivenFile(
         final AbstractFlexFile file )
   {
      return !file.isMxml();
   }

   @Override
   protected void findViolationsFromPackageNode(
         final PackageNode packageName, final Map< String, AbstractFlexFile > files )
   {
      super.findViolationsFromPackageNode( packageName, files );

      for ( final FieldNode field : packageName.getClassNode().getVariables() )
      {
         if ( field.getType().toString().compareTo(
               "ILogger" ) == 0 )
         {
            addViolation( field.getInternalNode(), field.getInternalNode(),
                  "A logger should be constant" );
         }
      }
      for ( final FieldNode field : packageName.getClassNode().getConstants() )
      {
         if ( field.getType().toString().compareTo(
               "ILogger" ) == 0 )
         {
            if ( field.getName().compareTo(
                  "LOG" ) != 0 )
            {
               addViolation( field.getInternalNode(), field.getInternalNode(),
                     "The logger name is not LOG" );
            }
            if ( field.getInitializationExpression() == null )
            {
               addViolation( field.getInternalNode(), field.getInternalNode(),
                     "The logger is not initialized" );
            }
            else
            {
               lookupStringMethodArguments(
                     field.getInitializationExpression(), packageName
                           .getFullyQualifiedClassName() );
            }
         }
      }
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.INFO;
   }

   private void lookupStringMethodArguments(
         final FieldInitializationNode initializationExpression,
         final String fullyQualifiedClassName )
   {
      visitNode(
            initializationExpression.getInternalNode(), fullyQualifiedClassName );
   }

   private void visitNode(
         final Node internalNode, final String fullyQualifiedClassName )
   {
      if ( internalNode.numChildren() > 0 )
      {
         for ( final Node child : internalNode.children )
         {
            visitNode(
                  child, fullyQualifiedClassName );
         }
      }
      if ( internalNode.is( Node.ARGUMENTS ) )
      {
         for ( final Node argumentNode : internalNode.children )
         {
            if ( argumentNode.stringValue != null
                  && argumentNode.stringValue.compareTo( "\""
                        + fullyQualifiedClassName + "\"" ) != 0 )
            {
               addViolation( internalNode, internalNode,
                     "The logger is not initialized with the fully qualified class name" );
            }
         }
      }
   }
}
