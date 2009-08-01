package com.adobe.ac.pmd.rules.sizing;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class TooLongFunctionRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new TooLongFunctionRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                                                         "ErrorToltipSkin.as",
                                                                         new ViolationPosition[]
                                                                         { new ViolationPosition( 94, 94 ),
                                                                                     new ViolationPosition( 156,
                                                                                                            156 ) } ),
                                                               "PngEncoder.as",
                                                               new ViolationPosition[]
                                                               { new ViolationPosition( 150, 150 ),
                                                                           new ViolationPosition( 192, 192 ),
                                                                           new ViolationPosition( 335, 335 ),
                                                                           new ViolationPosition( 492, 492 ),
                                                                           new ViolationPosition( 548, 548 ) } ),
                                                     "RadonDataGrid.as",
                                                     new ViolationPosition[]
                                                     { new ViolationPosition( 84, 84 ),
                                                                 new ViolationPosition( 117, 117 ) } ),
                                           "cairngorm.FatController.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 97, 97 ) } ),
                                 "com.adobe.ac.ncss.TestResult.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 227, 227 ) } ),
                       "com.adobe.ac.ncss.LongSwitch.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 39, 39 ) } );
   }
}
