package com.adobe.ac.pmd.rules.naming;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class TooShortVariableRuleTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
   {
      return new String[]
      { "  var toto : int = 0;",
                  "  var i : int = 0;",
                  "var ii : int = 0;",
                  "var iii : int = 0;",
                  "for ( var i : int = 0; i < 10; i++ ){}" };
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new TooShortVariableRule();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      return new String[]
      { "function lala() : Number",
                  "lala();" };
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                           "PngEncoder.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 47, 47 ) } ),
                                 "Looping.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 63, 63 ) } ),
                       "com.adobe.ac.ncss.mxml.IterationsList.mxml",
                       new ViolationPosition[]
                       { new ViolationPosition( 86, 86 ) } );
   }
}
