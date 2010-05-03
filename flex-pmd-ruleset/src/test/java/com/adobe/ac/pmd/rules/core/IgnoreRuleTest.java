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

import java.util.List;

import org.junit.Test;

import com.adobe.ac.pmd.IFlexViolation;

public class IgnoreRuleTest
{
   private class IgnoredRule extends AbstractFlexRule
   {
      @Override
      public String getName()
      {
         return "com.adobe.ac.Ignored";
      }

      @Override
      protected List< IFlexViolation > findViolationsInCurrentFile()
      {
         return null;
      }

      @Override
      protected ViolationPriority getDefaultPriority()
      {
         return ViolationPriority.LOW;
      }

      @Override
      protected boolean isConcernedByTheCurrentFile()
      {
         return true;
      }
   }

   @Test
   public final void testIsViolationIgnored()
   {
      assertTrue( new IgnoredRule().isViolationIgnored( "var i : int; // NO PMD" ) );
      assertFalse( new IgnoredRule().isViolationIgnored( "var i : int; // NO PMD AlertShow" ) );
      assertTrue( new IgnoredRule().isViolationIgnored( "var i : int; // NO PMD IgnoreTest$Ignored" ) );
      assertTrue( new IgnoredRule().isViolationIgnored( "var i : int; // NO PMD adobe.ac.pmd.rules.core.IgnoreTest$Ignored" ) );
      assertFalse( new IgnoredRule().isViolationIgnored( "var i : int;" ) );
   }

   @Test
   public final void testIsViolationIgnoredCollapsed()
   {
      assertTrue( new IgnoredRule().isViolationIgnored( "var i : int; // NOPMD" ) );
      assertFalse( new IgnoredRule().isViolationIgnored( "var i : int; // NOPMD AlertShow" ) );
      assertTrue( new IgnoredRule().isViolationIgnored( "var i : int; // NOPMD IgnoreTest$Ignored" ) );
      assertTrue( new IgnoredRule().isViolationIgnored( "var i : int; // NOPMD adobe.ac.pmd.rules.core.IgnoreTest$Ignored" ) );
      assertFalse( new IgnoredRule().isViolationIgnored( "var i : int;" ) );
   }

   @Test
   public final void testIsViolationIgnoredWithFullCollapsed()
   {
      assertTrue( new IgnoredRule().isViolationIgnored( "var i : int; //NOPMD" ) );
      assertFalse( new IgnoredRule().isViolationIgnored( "var i : int; //NOPMD AlertShow" ) );
      assertTrue( new IgnoredRule().isViolationIgnored( "var i : int; //NOPMD IgnoreTest$Ignored" ) );
      assertTrue( new IgnoredRule().isViolationIgnored( "var i : int; //NOPMD adobe.ac.pmd.rules.core.IgnoreTest$Ignored" ) );
      assertFalse( new IgnoredRule().isViolationIgnored( "var i : int;" ) );
   }

   @Test
   public final void testIsViolationIgnoredWithLowerCase()
   {
      assertTrue( new IgnoredRule().isViolationIgnored( "var i : int; // No PMD" ) );
      assertFalse( new IgnoredRule().isViolationIgnored( "var i : int; // No PMD AlertShow" ) );
      assertTrue( new IgnoredRule().isViolationIgnored( "var i : int; // No PMD IgnoreTest$Ignored" ) );
      assertTrue( new IgnoredRule().isViolationIgnored( "var i : int; // No PMD adobe.ac.pmd.rules.core.IgnoreTest$Ignored" ) );
      assertFalse( new IgnoredRule().isViolationIgnored( "var i : int;" ) );
   }

}
