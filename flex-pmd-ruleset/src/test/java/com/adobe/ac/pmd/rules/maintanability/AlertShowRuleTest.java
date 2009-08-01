package com.adobe.ac.pmd.rules.maintanability;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class AlertShowRuleTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
   {
      return new String[]
      { " Alert.show(",
                  " Alert.show( ",
                  " Alert.show( \"something\" );",
                  " Alert.show(\"something\");" };
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new AlertShowRule();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      return new String[]
      { "var i : Number",
                  "Alert.sho( ",
                  "lert.show( \"something\" );",
                  "Alrt.show(\"something\");",
                  "ScoyoAlert.show(" };
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "com.adobe.ac.ncss.ConfigProxy.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 48, 48 ) } );
   }
}
