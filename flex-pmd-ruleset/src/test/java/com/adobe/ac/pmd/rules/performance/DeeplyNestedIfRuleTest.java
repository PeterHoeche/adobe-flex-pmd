package com.adobe.ac.pmd.rules.performance;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class DeeplyNestedIfRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new DeeplyNestedIfRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "com.adobe.ac.AbstractRowData.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 59, 59 ) } ),
                       "PngEncoder.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 578, 578 ),
                                   new ViolationPosition( 576, 576 ),
                                   new ViolationPosition( 584, 584 ),
                                   new ViolationPosition( 576, 576 ) } );
   }
}
