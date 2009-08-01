package com.adobe.ac.pmd.rules.component;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class CallLaterDirectlyRuleTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
   {
      return new String[]
      { "callLater( myFunction)",
                  " callLater (myFunction)" };
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new CallLaterDirectlyRule();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      return new String[]
      { "callLate( myFunction)",
                  " allLater(myFunction)" };
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "Main.mxml",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 36, 36 ) } ),
                       "GenericType.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 41, 41 ) } );
   }
}
