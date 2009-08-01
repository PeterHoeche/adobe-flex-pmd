package com.adobe.ac.pmd.rules.switchrules;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class TooFewBrancheInSwitchStatementRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new TooFewBrancheInSwitchStatementRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "com.adobe.ac.ncss.LongSwitch.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 53, 53 ) } ),
                       "com.adobe.ac.ncss.NestedSwitch.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 43, 43 ) } );
   }
}
