package com.adobe.ac.pmd.rules.performance;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class CreationPolicySetToAllRuleTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
   {
      return new String[]
      { "creationPolicy = Policy.ALL",
                  " creationPolicy=\"all\"" };
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

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "Main.mxml",
                       new ViolationPosition[]
                       { new ViolationPosition( 37, 37 ) } );
   }
}
