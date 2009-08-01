package com.adobe.ac.pmd.rules.event;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class UntypedEventMetadataRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new UntypedEventMetadataRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "UnboundMetadata.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 41, 41 ) } );
   }
}
