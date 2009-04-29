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
package com.adobe.ac.pmd.rules.as3.naming;

import com.adobe.ac.pmd.Violation;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

import de.bokelberg.flex.parser.Node;

public class PackageCaseRule
      extends AbstractAstFlexRule
{
   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.WARNING;
   }

   @Override
   protected void visitCompilationUnit(
         final Node ast )
   {
      super.visitCompilationUnit( ast );

      if ( ast.children.size() > 1 )
      {
         final Node packageNode = ast.getChild( 0 );

         if ( packageNode.children.size() > 1 )
         {
            final Node packageNameNode = packageNode.getChild( 0 );
            final String packageName = packageNameNode.stringValue;

            ifViolationDetected(
                  packageNode, packageName );
         }
      }
   }

   private boolean containsUpperCharacter(
         final String packageName )
   {
      boolean found = false;

      for ( int i = 0; i < packageName.length(); i++ )
      {
         final char currentChar = packageName.charAt( i );
         if ( currentChar != '.'
               && currentChar >= 'A' && currentChar <= 'Z' )
         {
            found = true;

            break;
         }
      }
      return found;
   }

   private void ifViolationDetected(
         final Node packageNode, final String packageName )
   {
      if ( containsUpperCharacter( packageName ) )
      {
         final Violation violation = addViolation(
               packageNode, packageNode );

         violation.setEndColumn( packageName.length()
               + violation.getBeginColumn() );
      }
   }
}
