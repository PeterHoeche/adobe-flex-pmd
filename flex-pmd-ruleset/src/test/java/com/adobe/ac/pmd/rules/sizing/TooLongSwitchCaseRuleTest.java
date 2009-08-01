package com.adobe.ac.pmd.rules.sizing;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class TooLongSwitchCaseRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new TooLongSwitchCaseRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "com.adobe.ac.ncss.NestedSwitch.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 38, 38 ),
                                             new ViolationPosition( 58, 58 ) } ),
                       "com.adobe.ac.ncss.LongSwitch.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 47, 47 ),
                                   new ViolationPosition( 52, 52 ) } );
   }
}
