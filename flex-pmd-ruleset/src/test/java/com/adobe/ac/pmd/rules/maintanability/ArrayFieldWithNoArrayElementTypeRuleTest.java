package com.adobe.ac.pmd.rules.maintanability;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class ArrayFieldWithNoArrayElementTypeRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new ArrayFieldWithNoArrayElementTypeRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "PngEncoder.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 47, 47 ),
                                             new ViolationPosition( 49, 49 ),
                                             new ViolationPosition( 60, 60 ),
                                             new ViolationPosition( 61, 61 ),
                                             new ViolationPosition( 62, 62 ),
                                             new ViolationPosition( 63, 63 ),
                                             new ViolationPosition( 64, 64 ),
                                             new ViolationPosition( 65, 65 ),
                                             new ViolationPosition( 66, 66 ),
                                             new ViolationPosition( 67, 67 ),
                                             new ViolationPosition( 68, 68 ),
                                             new ViolationPosition( 69, 69 ),
                                             new ViolationPosition( 70, 70 ),
                                             new ViolationPosition( 71, 71 ),
                                             new ViolationPosition( 95, 95 ),
                                             new ViolationPosition( 96, 96 ),
                                             new ViolationPosition( 97, 97 ),
                                             new ViolationPosition( 98, 98 ),
                                             new ViolationPosition( 121, 121 ),
                                             new ViolationPosition( 122, 122 ),
                                             new ViolationPosition( 126, 126 ),
                                             new ViolationPosition( 127, 127 ),
                                             new ViolationPosition( 128, 128 ) } ),
                       "com.adobe.ac.ncss.ArrayVO.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 34, 34 ) } );
   }
}
