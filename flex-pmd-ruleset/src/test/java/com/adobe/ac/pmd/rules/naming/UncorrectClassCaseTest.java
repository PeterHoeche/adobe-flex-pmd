package com.adobe.ac.pmd.rules.naming;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.AbstractFlexRuleTest;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class UncorrectClassCaseTest extends AbstractFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new UncorrectClassCase();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                           "schedule_internal.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 1, 34 ) } ),
                                 "sprintf.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 1, 358 ) } ),
                       "com.adobe.ac.foo.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 1, 34 ) } );
   }
}
