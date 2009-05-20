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
package com.adobe.ac.pmd.rules.as3.sizing;

import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.PropertyDescriptor;

import com.adobe.ac.pmd.nodes.ClassNode;
import com.adobe.ac.pmd.nodes.FieldNode;
import com.adobe.ac.pmd.nodes.PackageNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.IThresholdedRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class TooManyFieldsRule extends AbstractAstFlexRule implements IThresholdedRule
{
   private ClassNode classNode   = null;
   private int       variablesNb = 0;

   public int getActualValue()
   {
      return variablesNb;
   }

   public int getDefaultThreshold()
   {
      return 5;
   }

   public int getThreshold()
   {
      return getIntProperty( propertyDescriptorFor( getThresholdName() ) );
   }

   public String getThresholdName()
   {
      return MAXIMUM;
   }

   @Override
   protected void findViolationsFromPackageNode( final PackageNode rootNode )
   {
      classNode = rootNode.getClassNode();
   }

   @Override
   protected void findViolationsFromVariablesList( final List< FieldNode > variables )
   {
      variablesNb = variables.size();

      if ( variablesNb > getThreshold() )
      {
         addViolation( classNode.getInternalNode(),
                       classNode.getInternalNode() );
      }
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.WARNING;
   }

   @Override
   protected Map< String, PropertyDescriptor > propertiesByName()
   {
      return getRuleProperties( this );
   }
}
