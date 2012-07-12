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
package com.adobe.ac.pmd.rules.cairngorm;

import org.apache.commons.lang.StringUtils;

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IField;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

/**
 * @author xagnetti
 */
public class BadCairngormEventNameFormatRule extends AbstractAstFlexRule // NO_UCD
{
   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule#isConcernedByTheCurrentFile
    * ()
    */
   @Override
   public final boolean isConcernedByTheCurrentFile()
   {
      return getCurrentFile().getClassName().endsWith( "Event.as" );
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule#findViolations(com.adobe
    * .ac.pmd.nodes.IClass)
    */
   @Override
   protected final void findViolations( final IClass classNode )
   {
      if ( isExtendedClassCairngormEvent( classNode ) )
      {
         final String eventName = extractEventName( classNode );

         if ( StringUtils.isEmpty( eventName )
               || !eventName.contains( "." ) )
         {
            addViolation( classNode );
         }
      }
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.rules.core.AbstractFlexRule#getDefaultPriority()
    */
   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   private String extractEventName( final IClass classNode )
   {
      String eventName = "";

      for ( final IField constantNode : classNode.getConstants() )
      {
         if ( constantNode.getName().startsWith( "EVENT" ) )
         {
            eventName = extractEventNameFromConstant( constantNode.getInitializationExpression()
                                                                  .getInternalNode() );
         }
      }
      if ( StringUtils.isEmpty( eventName )
            && classNode.getConstructor() != null )
      {
         eventName = extractEventNameFromConstructor( classNode.getConstructor() );
      }
      return eventName;
   }

   private String extractEventNameFromConstant( final IParserNode initExpressionNode )
   {
      return initExpressionNode.getChild( 0 ).getStringValue();
   }

   private String extractEventNameFromConstructor( final IFunction constructor )
   {
      String eventName = "";
      final IParserNode superCall = constructor.getSuperCall();

      if ( superCall != null )
      {
         eventName = superCall.getChild( 1 ).getChild( 0 ).getStringValue();
      }
      return eventName;
   }

   private boolean isExtendedClassCairngormEvent( final IClass classNode )
   {
      return classNode.getExtensionName() != null
            && classNode.getExtensionName().contains( "Cairngorm" )
            && classNode.getExtensionName().contains( "Event" );
   }
}
