package com.adobe.ac.pmd.rules.unused;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class UnusedParameterRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new UnusedParameterRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                                     "cairngorm.NonBindableModelLocator.as",
                                                     new ViolationPosition[]
                                                     { new ViolationPosition( 43, 43 ) } ),
                                           "GenericType.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 44, 44 ) } ),
                                 "com.adobe.ac.ncss.BigImporterModel.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 62, 62 ),
                                             new ViolationPosition( 62, 62 ),
                                             new ViolationPosition( 62, 62 ),
                                             new ViolationPosition( 62, 62 ) } ),
                       "Sorted.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 58, 58 ),
                                   new ViolationPosition( 67, 67 ) } );
   }
}
