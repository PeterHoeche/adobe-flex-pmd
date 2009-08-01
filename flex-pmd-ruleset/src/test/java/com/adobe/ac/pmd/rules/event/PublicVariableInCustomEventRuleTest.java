package com.adobe.ac.pmd.rules.event;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class PublicVariableInCustomEventRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new PublicVariableInCustomEventRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "com.adobe.ac.ncss.event.DynamicCustomEvent.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 35, 35 ) } ),
                       "com.adobe.ac.ncss.event.FirstCustomEvent.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 35, 35 ) } );
   }
}
