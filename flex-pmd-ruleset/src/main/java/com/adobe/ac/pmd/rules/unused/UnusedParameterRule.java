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

import java.util.LinkedHashMap;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.KeyWords;
import com.adobe.ac.pmd.parser.NodeKind;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

/**
 * @author xagnetti
 */
public class UnusedParameterRule extends AbstractUnusedVariableRule
{
   private static final String DATA_GRID_COLUMN         = "DataGridColumn";
   private static final String FAULT_FUNCTION_NAME      = "fault";
   private static final String RESPONDER_INTERFACE_NAME = "Responder";
   private static final String RESULT_FUNCTION_NAME     = "result";

   private static String computeFunctionName( final IParserNode functionAst )
   {
      String functionName = "";
      for ( final IParserNode node : functionAst.getChildren() )
      {
         if ( node.is( NodeKind.NAME ) )
         {
            functionName = node.getStringValue();
            break;
         }
      }
      return functionName;
   }

   private static boolean isClassImplementingIResponder( final IParserNode currentClass2 )
   {
      for ( final IParserNode node : currentClass2.getChildren() )
      {
         if ( node.is( NodeKind.IMPLEMENTS_LIST ) )
         {
            for ( final IParserNode implementation : node.getChildren() )
            {
               if ( implementation.getStringValue() != null
                     && implementation.getStringValue().contains( RESPONDER_INTERFACE_NAME ) )
               {
                  return true;
               }
            }
         }
      }
      return false;
   }

   private static boolean isResponderImplementation( final IParserNode currentClass,
                                                     final IParserNode functionAst )
   {
      if ( !isClassImplementingIResponder( currentClass ) )
      {
         return false;
      }
      final String functionName = computeFunctionName( functionAst );

      return RESULT_FUNCTION_NAME.compareTo( functionName ) == 0
            || FAULT_FUNCTION_NAME.compareTo( functionName ) == 0;
   }

   private IParserNode currentClass;

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.rules.core.AbstractFlexRule#getDefaultPriority()
    */
   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule#visitClass(com.adobe.ac
    * .pmd.parser.IParserNode)
    */
   @Override
   protected final void visitClass( final IParserNode classNode )
   {
      currentClass = classNode;
      super.visitClass( classNode );
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule#visitFunction(com.adobe
    * .ac.pmd.parser.IParserNode,
    * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule.FunctionType)
    */
   @Override
   protected final void visitFunction( final IParserNode functionAst,
                                       final FunctionType type )
   {
      setVariablesUnused( new LinkedHashMap< String, IParserNode >() );
      final boolean isOverriden = isFunctionOverriden( functionAst );

      if ( !isOverriden
            && !isResponderImplementation( currentClass,
                                           functionAst ) )
      {
         super.visitFunction( functionAst,
                              type );

         if ( !functionIsEventHandler( functionAst ) )
         {
            for ( final String variableName : getVariablesUnused().keySet() )
            {
               final IParserNode variable = getVariablesUnused().get( variableName );

               addViolation( variable,
                             variable,
                             variableName );
            }
         }
      }
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule#visitParameters(com.adobe
    * .ac.pmd.parser.IParserNode)
    */
   @Override
   protected final void visitParameters( final IParserNode ast )
   {
      super.visitParameters( ast );

      if ( ast.numChildren() != 0 )
      {
         for ( final IParserNode parameterNode : ast.getChildren() )
         {
            if ( !isParameterAnEvent( parameterNode )
                  && parameterNode.numChildren() > 0
                  && parameterNode.getChild( 0 ).numChildren() > 1
                  && parameterNode.getChild( 0 ).getChild( 1 ).getStringValue().compareTo( DATA_GRID_COLUMN ) != 0 )
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

   private boolean isFunctionOverriden( final IParserNode ast )
   {
      for ( final IParserNode child : ast.getChildren() )
      {
         if ( child.is( NodeKind.MOD_LIST ) )
         {
            for ( final IParserNode mod : child.getChildren() )
            {
               if ( mod.getStringValue().equals( KeyWords.OVERRIDE.toString() ) )
               {
                  return true;
               }
            }
         }
      }
      return false;
   }

   private boolean isParameterAnEvent( final IParserNode parameterNode )
   {
      final IParserNode parameterType = getTypeFromFieldDeclaration( parameterNode );

      return parameterType != null
            && parameterType.getStringValue() != null && parameterType.getStringValue().contains( "Event" );
   }
}
