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
package com.adobe.ac.pmd.rules.core;

import java.util.List;

import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.parser.IParserNode;

/**
 * Abstract rule which find a primary (or a couple of primaries) in a body
 * function.
 * 
 * @author xagnetti
 */
public abstract class AbstractPrimaryAstRule extends AbstractAstFlexRule
{
   /**
    * @param statement
    * @param function
    */
   protected abstract void addViolation( IParserNode statement,
                                         IFunction function );

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule#findViolations(com.adobe
    * .ac.pmd.nodes.IFunction)
    */
   @Override
   protected final void findViolations( final IFunction function )
   {
      final List< IParserNode > firstStatements = function.findPrimaryStatementsInBody( getFirstPrimaryToFind() );
      if ( !firstStatements.isEmpty() )
      {
         for ( final IParserNode firstStatement : firstStatements )
         {
            if ( getSecondPrimaryToFind() == null )
            {
               addViolation( firstStatement,
                             function );
            }
            else
            {
               final List< IParserNode > secondStatements = function.findPrimaryStatementsInBody( getSecondPrimaryToFind() );
               if ( !secondStatements.isEmpty() )
               {
                  for ( final IParserNode secondStatement : secondStatements )
                  {
                     addViolation( secondStatement,
                                   function );
                  }
               }
            }
         }
      }
   }

   /**
    * @return
    */
   protected abstract String getFirstPrimaryToFind();

   /**
    * @return
    */
   protected String getSecondPrimaryToFind()
   {
      return null;
   }
}