package com.adobe.ac.pmd.rules.maintanability;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class UselessOverridenFunctionRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new UselessOverridenFunctionRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "GoodComponent.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 38, 38 ) } ),
                       "AbstractRowData.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 137, 137 ) } );
   }
}
