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
package com.adobe.ac.pmd.rules.parsley;

import java.util.List;

import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.nodes.IParameter;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractFlexMetaDataRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.parsley.utils.ParsleyMetaData;

/**
 * @author xagnetti
 */
public final class MessageInterceptorSignatureRule extends AbstractFlexMetaDataRule
{
   /*
    * (non-Javadoc)
    * @seecom.adobe.ac.pmd.rules.core.AbstractFlexMetaDataRule#
    * findViolationsFromFunctionMetaData(com.adobe.ac.pmd.nodes.IFunction)
    */
   @Override
   protected void findViolationsFromFunctionMetaData( final IFunction function )
   {
      final List< IMetaData > interceptors = ParsleyMetaData.MESSAGE_INTERCEPTOR.getMetaDataList( function );
      final IParserNode name = getNameFromFunctionDeclaration( function.getInternalNode() );

      if ( !interceptors.isEmpty() )
      {

         if ( !function.isPublic() )
         {
            addViolation( name,
                          name,
                          name.getStringValue(),
                          "It is not public" );
         }
         if ( function.getParameters().size() == 1 )
         {
            if ( !hasMessageProcessorParameter( function ) )
            {
               addViolation( name,
                             name,
                             name.getStringValue(),
                             "The argument type should be MessageProcessor" );
            }
         }
         else
         {
            addViolation( name,
                          name,
                          name.getStringValue(),
                          "Its argument number is not 1" );
         }
      }
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.rules.core.AbstractFlexRule#getDefaultPriority()
    */
   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   private boolean hasMessageProcessorParameter( final IFunction function )
   {
      final IParameter param = function.getParameters().get( 0 );
      final String type = param.getType().toString();
      return "MessageProcessor".equals( type );
   }
}