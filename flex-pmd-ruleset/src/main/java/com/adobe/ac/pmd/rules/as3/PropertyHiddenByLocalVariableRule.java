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

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adobe.ac.pmd.files.AbstractFlexFile;
import com.adobe.ac.pmd.nodes.FieldNode;
import com.adobe.ac.pmd.nodes.FunctionNode;
import com.adobe.ac.pmd.nodes.PackageNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class PropertyHiddenByLocalVariableRule
      extends AbstractAstFlexRule
{
   @Override
   protected void findViolationsFromPackageNode(
         final PackageNode packageNode,
         final Map< String, AbstractFlexFile > filesInSourcePath )
   {
      super.findViolationsFromPackageNode(
            packageNode, filesInSourcePath );

      final List< FieldNode > variables = packageNode.getClassNode()
            .getVariables();

      for ( final FunctionNode function : packageNode.getClassNode()
            .getFunctions() )
      {
         final Set< String > localVariables = function.getLocalVariables()
               .keySet();

         for ( final String localVariable : localVariables )
         {
            for ( final FieldNode field : variables )
            {
               if ( localVariable.compareTo( field.getName() ) == 0 )
               {
                  addViolation(
                        function.getLocalVariables().get(
                              localVariable ), function.getLocalVariables()
                              .get(
                                    localVariable ) );
               }
            }
         }
      }
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.WARNING;
   }
}
