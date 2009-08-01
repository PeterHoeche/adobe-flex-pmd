package com.adobe.ac.pmd.rules.switchrules;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class SwitchStatementsShouldHaveDefaultRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new SwitchStatementsShouldHaveDefaultRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "com.adobe.ac.ncss.LongSwitch.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 53, 53 ),
                                             new ViolationPosition( 41, 41 ) } ),
                       "com.adobe.ac.ncss.NestedSwitch.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 43, 43 ) } );
   }
}
