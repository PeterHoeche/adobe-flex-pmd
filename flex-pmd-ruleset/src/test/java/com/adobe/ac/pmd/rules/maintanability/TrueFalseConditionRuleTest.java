package com.adobe.ac.pmd.rules.maintanability;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class TrueFalseConditionRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new TrueFalseConditionRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      final HashMap< String, ViolationPosition[] > violatingFiles = new HashMap< String, ViolationPosition[] >();

      addToMap( addToMap( addToMap( violatingFiles,
                                    "PngEncoder.as",
                                    new ViolationPosition[]
                                    { new ViolationPosition( 574, 574 ),
                                                new ViolationPosition( 576, 576 ),
                                                new ViolationPosition( 578, 578 ),
                                                new ViolationPosition( 584, 584 ) } ),
                          "Looping.as",
                          new ViolationPosition[]
                          { new ViolationPosition( 44, 44 ),
                                      new ViolationPosition( 57, 57 ),
                                      new ViolationPosition( 66, 66 ),
                                      new ViolationPosition( 69, 69 ) } ),
                "RadonDataGrid.as",
                new ViolationPosition[]
                { new ViolationPosition( 63, 63 ),
                            new ViolationPosition( 190, 190 ),
                            new ViolationPosition( 194, 194 ) } );

      return addToMap( addToMap( addToMap( violatingFiles,
                                           "AbstractRowData.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 106, 106 ),
                                                       new ViolationPosition( 113, 113 ) } ),
                                 "com.adobe.ac.AbstractRowData.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 59, 59 ) } ),
                       "com.adobe.ac.ncss.TestResult.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 206, 206 ) } );
   }
}
