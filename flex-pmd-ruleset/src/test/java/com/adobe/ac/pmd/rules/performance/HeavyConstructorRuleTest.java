package com.adobe.ac.pmd.rules.performance;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class HeavyConstructorRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new HeavyConstructorRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                           "PngEncoder.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 130, 130 ) } ),
                                 "Looping.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 39, 39 ) } ),
                       "RadonDataGrid.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 53, 53 ) } );
   }
}
