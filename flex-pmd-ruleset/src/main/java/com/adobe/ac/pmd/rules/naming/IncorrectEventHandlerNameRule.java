/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
 *    All rights reserved.
 *
 *    Redistribution  and  use  in  source  and  binary  forms, with or without
 *    modification,  are  permitted  provided  that  the  following  conditions
 *    are met:
 *
 *      * Redistributions  of  source  code  must  retain  the  above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions  in  binary  form  must reproduce the above copyright
 *        notice,  this  list  of  conditions  and  the following disclaimer in
 *        the    documentation   and/or   other  materials  provided  with  the
 *        distribution.
 *      * Neither the name of the Adobe Systems, Incorporated. nor the names of
 *        its  contributors  may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS  SOFTWARE  IS  PROVIDED  BY THE  COPYRIGHT  HOLDERS AND CONTRIBUTORS
 *    "AS IS"  AND  ANY  EXPRESS  OR  IMPLIED  WARRANTIES,  INCLUDING,  BUT NOT
 *    LIMITED  TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,  INCIDENTAL,  SPECIAL,
 *    EXEMPLARY,  OR  CONSEQUENTIAL  DAMAGES  (INCLUDING,  BUT  NOT  LIMITED TO,
 *    PROCUREMENT  OF  SUBSTITUTE   GOODS  OR   SERVICES;  LOSS  OF  USE,  DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY,  WHETHER  IN  CONTRACT,  STRICT  LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE  OR  OTHERWISE)  ARISING  IN  ANY  WAY  OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.rules.naming;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sourceforge.pmd.PropertyDescriptor;
import net.sourceforge.pmd.properties.StringProperty;

import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class IncorrectEventHandlerNameRule extends AbstractAstFlexRule
{
   private static final String DEFAULT_PREFIX = "on";
   private static final String DEFAULT_SUFFIX = "";
   private static final String PREFIX_NAME    = "prefix";
   private static final String SUFFIX_NAME    = "suffix";
   private final String        prefix;
   private final String        suffix;

   public IncorrectEventHandlerNameRule()
   {
      super();
      prefix = getStringProperty( propertyDescriptorFor( PREFIX_NAME ) );
      suffix = getStringProperty( propertyDescriptorFor( SUFFIX_NAME ) );
   }

   @Override
   protected void findViolations( final IFunction function )
   {
      if ( function.isEventHandler()
            && !( function.getName().startsWith( prefix ) && function.getName().endsWith( suffix ) ) )
      {
         final IParserNode name = getNameFromFunctionDeclaration( function.getInternalNode() );

         addViolation( name,
                       name.getStringValue(),
                       prefix,
                       suffix );
      }
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }

   @Override
   protected final Map< String, PropertyDescriptor > propertiesByName()
   {
      final Map< String, PropertyDescriptor > properties = new LinkedHashMap< String, PropertyDescriptor >();

      properties.put( PREFIX_NAME,
                      new StringProperty( PREFIX_NAME, "", DEFAULT_PREFIX, properties.size() ) );
      properties.put( SUFFIX_NAME,
                      new StringProperty( SUFFIX_NAME, "", DEFAULT_SUFFIX, properties.size() ) );

      return properties;
   }
}
