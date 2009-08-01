package com.adobe.ac.pmd.rules.performance;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class UseTraceFunctionRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new UseTraceFunctionRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "Simple.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 1, 1 ) } ),
                       "com.adobe.ac.ncss.BigImporterModel.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 74, 74 ),
                                   new ViolationPosition( 75, 75 ) } );
   }
}
