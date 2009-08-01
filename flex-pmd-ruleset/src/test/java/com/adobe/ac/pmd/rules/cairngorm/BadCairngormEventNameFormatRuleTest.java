package com.adobe.ac.pmd.rules.cairngorm;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class BadCairngormEventNameFormatRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new BadCairngormEventNameFormatRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      final ViolationPosition[] positions =
      { new ViolationPosition( 38, 38 ) };

      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "cairngorm.events.UncorrectConstructorEvent.as",
                                 positions ),
                       "cairngorm.events.UncorrectConstantEvent.as",
                       positions );
   }
}
