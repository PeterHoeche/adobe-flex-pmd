package com.adobe.ac.pmd.rules.naming;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class WronglyNamedVariableRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new WronglyNamedVariableRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                           "PngEncoder.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 388, 388 ),
                                                       new ViolationPosition( 340, 340 ),
                                                       new ViolationPosition( 353, 353 ),
                                                       new ViolationPosition( 347, 347 ),
                                                       new ViolationPosition( 346, 346 ),
                                                       new ViolationPosition( 341, 341 ),
                                                       new ViolationPosition( 394, 394 ),
                                                       new ViolationPosition( 352, 352 ),
                                                       new ViolationPosition( 342, 342 ),
                                                       new ViolationPosition( 350, 350 ),
                                                       new ViolationPosition( 387, 387 ),
                                                       new ViolationPosition( 391, 391 ),
                                                       new ViolationPosition( 345, 345 ),
                                                       new ViolationPosition( 389, 389 ),
                                                       new ViolationPosition( 392, 392 ),
                                                       new ViolationPosition( 400, 400 ),
                                                       new ViolationPosition( 351, 351 ),
                                                       new ViolationPosition( 399, 399 ),
                                                       new ViolationPosition( 398, 398 ),
                                                       new ViolationPosition( 390, 390 ),
                                                       new ViolationPosition( 343, 343 ),
                                                       new ViolationPosition( 344, 344 ),
                                                       new ViolationPosition( 393, 393 ),
                                                       new ViolationPosition( 397, 397 ) } ),
                                 "GenericType.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 44, 44 ),
                                             new ViolationPosition( 46, 46 ) } ),
                       "com.adobe.ac.ncss.ConfigProxy.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 42, 42 ) } );
   }
}
