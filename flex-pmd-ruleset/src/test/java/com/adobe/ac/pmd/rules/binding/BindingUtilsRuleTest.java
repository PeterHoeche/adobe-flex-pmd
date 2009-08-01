package com.adobe.ac.pmd.rules.binding;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class BindingUtilsRuleTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
   {
      return new String[]
      { " BindingUtils.",
                  " BindingUtils.bindSetter",
                  "         BindingUtils.bindSetter(setContent, value, \"content\");" };
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new BindingUtilsRule();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      return new String[]
      { " MyBindingUtils.",
                  " var i : int = 0;",
                  ".BindingUtils",
                  " BindingUtilsT" };
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "cairngorm.FatController.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 99, 99 ) } );
   }
}
