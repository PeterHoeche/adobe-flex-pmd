package com.adobe.ac.pmd.rules.performance;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class CyclomaticComplexityRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new CyclomaticComplexityRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "PngEncoder.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 548, 548 ) } ),
                       "RadonDataGrid.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 160, 160 ) } );
   }
}
