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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IField;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.INode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.utils.comparators.ModifierHolderByVisibilityAndStaticityComparator;

public class WronglyOrderedClassElementRule extends AbstractAstFlexRule
{
   @Override
   protected final void findViolations( final IClass classNode )
   {
      final List< IField > constants = new ArrayList< IField >();
      final List< IAttribute > variables = new ArrayList< IAttribute >();
      final List< IFunction > functions = new ArrayList< IFunction >();

      constants.addAll( classNode.getConstants() );
      variables.addAll( classNode.getAttributes() );
      functions.addAll( classNode.getFunctions() );

      Collections.sort( constants,
                        new ModifierHolderByVisibilityAndStaticityComparator() );
      Collections.sort( variables,
                        new ModifierHolderByVisibilityAndStaticityComparator() );
      Collections.sort( functions,
                        new ModifierHolderByVisibilityAndStaticityComparator() );

      addViolationIfListNotOrderedByLine( constants );
      addViolationIfListNotOrderedByLine( variables );
      addViolationIfListNotOrderedByLine( functions );
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   private void addViolationIfListNotOrderedByLine( final List< ? extends INode > list )
   {
      int previousFieldLine = 0;
      for ( final INode node : list )
      {
         if ( previousFieldLine < node.getInternalNode().getLine() )
         {
            previousFieldLine = node.getInternalNode().getLine();
         }
         else
         {
            addViolation( node.getInternalNode(),
                          node.getInternalNode(),
                          node.getInternalNode().getId().toString() );
         }
      }
   }
}
