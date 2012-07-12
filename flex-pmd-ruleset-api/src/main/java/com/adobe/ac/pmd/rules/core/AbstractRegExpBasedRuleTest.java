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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import junit.framework.Assert;

import org.junit.Test;

public abstract class AbstractRegExpBasedRuleTest extends AbstractFlexRuleTest
{
   @Test
   public void testDoesCurrentLineMacthCorrectLine()
   {
      final AbstractRegexpBasedRule rule = getRegexpBasedRule();

      if ( getMatchableLines().length == 0 )
      {
         Assert.fail( "the getMatchableLines() is empty" );
      }
      for ( int i = 0; i < getMatchableLines().length; i++ )
      {
         final String correctLine = getMatchableLines()[ i ];

         assertTrue( "This line (\""
                           + correctLine + "\") should be matched",
                     rule.doesCurrentLineMacthes( correctLine ) );
      }
   }

   @Test
   public void testDoesCurrentLineMacthIncorrectLine()
   {
      final AbstractRegexpBasedRule rule = getRegexpBasedRule();

      if ( getUnmatchableLines().length == 0 )
      {
         Assert.fail( "the getUnmatchableLines() is empty" );
      }
      for ( int i = 0; i < getUnmatchableLines().length; i++ )
      {
         final String incorrectLine = getUnmatchableLines()[ i ];

         assertFalse( "This line  (\""
                            + incorrectLine + "\") should not be matched",
                      rule.doesCurrentLineMacthes( incorrectLine ) );
      }
   }

   protected abstract String[] getMatchableLines();

   protected abstract AbstractRegexpBasedRule getRegexpBasedRule();

   @Override
   protected AbstractFlexRule getRule()
   {
      return getRegexpBasedRule();
   }

   protected abstract String[] getUnmatchableLines();
}