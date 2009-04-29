package com.adobe.ac.pmd.rules.performance;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.adobe.ac.pmd.rules.common.AbstractCommonRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class CreationPolicySetToAllRuleTest
      extends AbstractCommonRegExpBasedRuleTest
{
   @Override
   @Test
   public void testProcessConcernedButNonViolatingFiles()
         throws FileNotFoundException, URISyntaxException
   {
      assertEmptyViolations( "MainWithModelLocator.mxml" );
   }

   @Override
   @Test
   public void testProcessViolatingFiles() throws FileNotFoundException,
         URISyntaxException
   {
      assertViolations(
            "Main.mxml", new ViolationPosition[]
            { new ViolationPosition( 37, 37 ) } );
   }

   @Override
   protected String[] getMatchableLines()
   {
      return new String[] { "creationPolicy = Policy.ALL", " creationPolicy=\"all\"" };
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new CreationPolicySetToAllRule();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      return new String[]
      { "creationPolic=" };
   }
}
