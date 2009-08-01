package com.adobe.ac.pmd.rules.sizing;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class TooManyFieldsRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new TooManyFieldsRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                                     "com.adobe.ac.ncss.TestResult.as",
                                                     new ViolationPosition[]
                                                     { new ViolationPosition( 44, 44 ) } ),
                                           "Sorted.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 37, 37 ) } ),
                                 "PngEncoder.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 42, 42 ) } ),
                       "com.adobe.ac.ncss.BigModel.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 35, 35 ) } );
   }
}
