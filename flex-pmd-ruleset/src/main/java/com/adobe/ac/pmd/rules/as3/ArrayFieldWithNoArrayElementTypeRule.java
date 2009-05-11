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

import com.adobe.ac.pmd.nodes.FieldNode;
import com.adobe.ac.pmd.nodes.MetaDataNode;
import com.adobe.ac.pmd.nodes.VariableNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class ArrayFieldWithNoArrayElementTypeRule
      extends AbstractAstFlexRule
{
   private static final String ARRAY_ELEMENT_TYPE_BINDING = "ArrayElementType";
   private static final String ARRAY_TYPE = "Array";

   @Override
   protected void findViolationsFromVariablesList(
         final List< FieldNode > variables )
   {
      for ( final VariableNode variable : variables )
      {
         if ( ARRAY_TYPE.equals( variable.getType().toString() )
               && !doesMetaDataContainArrayElementType( variable
                     .getMetaDataList() ) )
         {
            addViolation(
                  variable.getInternalNode(), variable.getType()
                        .getInternalNode() );
         }
      }
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.WARNING;
   }

   private boolean doesMetaDataContainArrayElementType(
         final List< MetaDataNode > metaDataList )
   {
      boolean arrayElementTypeFound = false;

      for ( final MetaDataNode metaDataNode : metaDataList )
      {
         if ( metaDataNode.getName().startsWith(
               ARRAY_ELEMENT_TYPE_BINDING ) )
         {
            arrayElementTypeFound = true;
            break;
         }
      }
      return arrayElementTypeFound;
   }
}
