package com.adobe.ac.pmd.rules.mxml;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class CodeBehindInMxmlRuleTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
   {
      return new String[]
      { "<Script>",
                  "<mx:Script",
                  "<mx:Script source=",
                  "<Script source=" };
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new CodeBehindInMxmlRule();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      return new String[]
      { "Script" };
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "Main.mxml",
                       new ViolationPosition[]
                       { new ViolationPosition( 55, 55 ) } );
   }
}
