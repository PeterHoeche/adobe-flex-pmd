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
package com.adobe.ac.pmd.rules.performance;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

/**
 * @author xagnetti
 */
public class AvoidInstanciationInLoopRule extends AbstractAstFlexRule
{
   private int loopLevel = 0;

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.rules.core.AbstractFlexRule#getDefaultPriority()
    */
   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule#visitFor(com.adobe.ac.
    * pmd.parser.IParserNode)
    */
   @Override
   protected final void visitFor( final IParserNode ast )
   {
      loopLevel++;
      super.visitFor( ast );
      loopLevel--;
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule#visitForEach(com.adobe
    * .ac.pmd.parser.IParserNode)
    */
   @Override
   protected final void visitForEach( final IParserNode ast )
   {
      loopLevel++;
      super.visitForEach( ast );
      loopLevel--;
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule#visitStatement(com.adobe
    * .ac.pmd.parser.IParserNode)
    */
   @Override
   protected final void visitStatement( final IParserNode ast )
   {
      super.visitStatement( ast );

      if ( ast != null
            && !ast.is( NodeKind.WHILE ) && !ast.is( NodeKind.FOR ) && !ast.is( NodeKind.FOREACH )
            && !ast.is( NodeKind.FOR ) )
      {
         searchNewNode( ast );
      }
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule#visitWhile(com.adobe.ac
    * .pmd.parser.IParserNode)
    */
   @Override
   protected final void visitWhile( final IParserNode ast )
   {
      loopLevel++;
      super.visitWhile( ast );
      loopLevel--;
   }

   private void searchNewNode( final IParserNode ast )
   {
      if ( ast.numChildren() > 0 )
      {
         for ( final IParserNode child : ast.getChildren() )
         {
            searchNewNode( child );
         }
      }
      if ( ast.getId() != null
            && ast.is( NodeKind.NEW ) && loopLevel != 0 )
      {
         addViolation( ast );
      }
   }
}
