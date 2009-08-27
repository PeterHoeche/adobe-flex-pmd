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
package com.adobe.ac.pmd.rules.unused;

import java.util.HashMap;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UnusedParameterRule extends AbstractUnusedVariableRule
{
   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   @Override
   protected void visitFunction( final IParserNode ast,
                                 final FunctionType type )
   {
      variablesUnused = new HashMap< String, IParserNode >();

      super.visitFunction( ast,
                           type );

      if ( !functionIsEventHandler( ast ) )
      {
         for ( final String variableName : variablesUnused.keySet() )
         {
            final IParserNode variable = variablesUnused.get( variableName );

            addViolation( variable,
                          variable,
                          variableName );
         }
      }
   }

   @Override
   protected final void visitParameters( final IParserNode ast )
   {
      super.visitParameters( ast );

      if ( ast.numChildren() != 0 )
      {
         for ( final IParserNode parameterNode : ast.getChildren() )
         {
            if ( !isParameterAnEvent( parameterNode )
                  && parameterNode.numChildren() > 0 && parameterNode.getChild( 0 ).numChildren() > 0 )
            {
               addVariable( parameterNode.getChild( 0 ).getChild( 0 ).getStringValue(),
                            parameterNode );
            }
         }
      }
   }

   private String extractFunctionName( final IParserNode ast )
   {
      if ( ast.numChildren() != 0 )
      {
         for ( final IParserNode node : ast.getChildren() )
         {
            if ( node.is( NodeKind.NAME ) )
            {
               return node.getStringValue();
            }
         }
      }
      return "";
   }

   private boolean functionIsEventHandler( final IParserNode ast )
   {
      final String functionName = extractFunctionName( ast );

      return functionName.startsWith( "on" )
            || functionName.startsWith( "handle" ) || functionName.endsWith( "handler" );
   }

   private boolean isParameterAnEvent( final IParserNode parameterNode )
   {
      final IParserNode parameterType = getTypeFromFieldDeclaration( parameterNode );

      return parameterType != null
            && parameterType.getStringValue() != null && parameterType.getStringValue().contains( "Event" );
   }
}
