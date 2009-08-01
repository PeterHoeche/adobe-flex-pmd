package com.adobe.ac.pmd.rules.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public abstract class AbstractRegExpBasedRuleTest extends AbstractFlexRuleTest
{
   @Test
   public void testDoesCurrentLineMacthCorrectLine()
   {
      final AbstractRegexpBasedRule rule = getRegexpBasedRule();

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