package com.adobe.ac.pmd.rules.binding;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class TooLongBindingExpressionRuleTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
   {
      return new String[]
      { " text=\"{ vfrfr.frfr.frf.lala }\"/>",
                  " text=\"{ vfrfr().frfr.frf.lala }\"/>",
                  " text=\"{vfrfr().frfr.frf.lala}\"/>" };
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new TooLongBindingExpressionRule();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      return new String[]
      { " text=\"\"/>",
                  "lala()" };
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "com.adobe.ac.ncss.mxml.IterationsList2.mxml",
                       new ViolationPosition[]
                       { new ViolationPosition( 54, 54 ) } );
   }
}
