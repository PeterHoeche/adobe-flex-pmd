package com.adobe.ac.pmd.rules.sizing;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class TooManyParametersRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new TooManyParametersRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                           "RadonDataGrid.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 68, 68 ),
                                                       new ViolationPosition( 84, 84 ),
                                                       new ViolationPosition( 117, 117 ) } ),
                                 "PngEncoder.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 548, 548 ) } ),
                       "com.adobe.ac.ncss.BigImporterModel.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 62, 62 ) } );
   }
}
