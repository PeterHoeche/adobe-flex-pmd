package com.adobe.ac.pmd.rules.cairngorm;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class BindableModelLocatorRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new BindableModelLocatorRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "cairngorm.BindableModelLocator.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 33, 33 ) } );
   }
}
