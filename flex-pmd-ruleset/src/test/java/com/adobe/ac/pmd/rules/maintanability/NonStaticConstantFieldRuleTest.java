package com.adobe.ac.pmd.rules.maintanability;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class NonStaticConstantFieldRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new NonStaticConstantFieldRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                                     "AbstractRowData.as",
                                                     new ViolationPosition[]
                                                     { new ViolationPosition( 45, 45 ),
                                                                 new ViolationPosition( 46, 46 ),
                                                                 new ViolationPosition( 47, 47 ),
                                                                 new ViolationPosition( 48, 48 ),
                                                                 new ViolationPosition( 50, 50 ) } ),
                                           "Sorted.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 43, 43 ),
                                                       new ViolationPosition( 44, 44 ),
                                                       new ViolationPosition( 45, 45 ),
                                                       new ViolationPosition( 46, 46 ) } ),
                                 "GenericType.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 36, 36 ) } ),
                       "com.adobe.ac.ncss.ArrayVO.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 39, 39 ) } );
   }
}
