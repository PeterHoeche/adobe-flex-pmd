package com.adobe.ac.pmd.rules.naming;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class VariableNameEndingWithNumericRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new VariableNameEndingWithNumericRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                                     "PngEncoder.as",
                                                     new ViolationPosition[]
                                                     { new ViolationPosition( 405, 405 ),
                                                                 new ViolationPosition( 441, 441 ),
                                                                 new ViolationPosition( 459, 459 ) } ),
                                           "com.adobe.ac.ncss.BigModel.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 82, 82 ),
                                                       new ViolationPosition( 86, 86 ),
                                                       new ViolationPosition( 90, 90 ),
                                                       new ViolationPosition( 94, 94 ),
                                                       new ViolationPosition( 98, 98 ) } ),
                                 "cairngorm.LightController.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 115, 115 ),
                                             new ViolationPosition( 134, 134 ),
                                             new ViolationPosition( 153, 153 ),
                                             new ViolationPosition( 172, 172 ),
                                             new ViolationPosition( 191, 191 ) } ),
                       "com.adobe.ac.ncss.BigImporterModel.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 62, 62 ),
                                   new ViolationPosition( 62, 62 ),
                                   new ViolationPosition( 62, 62 ),
                                   new ViolationPosition( 62, 62 ),
                                   new ViolationPosition( 62, 62 ),
                                   new ViolationPosition( 64, 64 ) } );
   }
}
