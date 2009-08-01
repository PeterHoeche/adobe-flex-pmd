package com.adobe.ac.pmd.rules.performance;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class AvoidInstanciationInLoopRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new AvoidInstanciationInLoopRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "PngEncoder.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 255, 255 ),
                                             new ViolationPosition( 282, 282 ),
                                             new ViolationPosition( 289, 289 ) } ),
                       "Looping.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 43, 43 ),
                                   new ViolationPosition( 46, 46 ),
                                   new ViolationPosition( 50, 50 ),
                                   new ViolationPosition( 56, 56 ),
                                   new ViolationPosition( 59, 59 ),
                                   new ViolationPosition( 63, 63 ),
                                   new ViolationPosition( 68, 68 ),
                                   new ViolationPosition( 71, 71 ),
                                   new ViolationPosition( 75, 75 ) } );
   }
}
