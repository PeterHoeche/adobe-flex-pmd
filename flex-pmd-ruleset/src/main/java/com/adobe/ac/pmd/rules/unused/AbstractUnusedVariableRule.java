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

import java.util.Map;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;

/**
 * @author xagnetti
 */
abstract class AbstractUnusedVariableRule extends AbstractAstFlexRule
{
   private Map< String, IParserNode > variablesUnused;

   /**
    * @param variableName
    * @param ast
    */
   protected final void addVariable( final String variableName,
                                     final IParserNode ast )
   {
      variablesUnused.put( variableName,
                           ast );
   }

   /**
    * @return
    */
   protected Map< String, IParserNode > getVariablesUnused()
   {
      return variablesUnused;
   }

   /**
    * @param variablesUnusedToBeSet
    */
   protected void setVariablesUnused( final Map< String, IParserNode > variablesUnusedToBeSet )
   {
      variablesUnused = variablesUnusedToBeSet;
   }

   /**
    * @param ast
    */
   protected final void tryToAddVariableNodeInChildren( final IParserNode ast )
   {
      if ( ast != null
            && !tryToAddVariableNode( ast ) && ast.is( NodeKind.VAR_LIST ) )
      {
         for ( final IParserNode child : ast.getChildren() )
         {
            tryToAddVariableNode( child );
         }
      }
   }

   /**
    * @param ast
    */
   protected final void tryToMarkVariableAsUsed( final IParserNode ast )
   {
      if ( variablesUnused != null
            && ast != null )
      {
         markVariableAsUsed( ast );
      }
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule#visitStatement(com.adobe
    * .ac.pmd.parser.IParserNode)
    */
   @Override
   protected void visitStatement( final IParserNode ast )
   {
      super.visitStatement( ast );

      tryToMarkVariableAsUsed( ast );
   }

   private void markVariableAsUsed( final IParserNode ast )
   {
      if ( ast.numChildren() == 0 )
      {
         if ( variablesUnused.containsKey( ast.getStringValue() ) )
         {
            variablesUnused.remove( ast.getStringValue() );
         }
      }
      else
      {
         for ( final IParserNode child : ast.getChildren() )
         {
            markVariableAsUsed( child );
         }
      }
   }

   private boolean tryToAddVariableNode( final IParserNode ast )
   {
      boolean result = false;

      if ( ast.is( NodeKind.NAME_TYPE_INIT ) )
      {
         addVariable( ast.getChild( 0 ).getStringValue(),
                      ast );
         result = true;
      }
      return result;
   }
}
