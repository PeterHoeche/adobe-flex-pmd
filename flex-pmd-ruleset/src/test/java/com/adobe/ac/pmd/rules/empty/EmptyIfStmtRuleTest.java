package com.adobe.ac.pmd.rules.empty;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class EmptyIfStmtRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new EmptyIfStmtRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                           "PngEncoder.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 578, 578 ),
                                                       new ViolationPosition( 584, 584 ) } ),
                                 "RadonDataGrid.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 63, 63 ),
                                             new ViolationPosition( 190, 190 ),
                                             new ViolationPosition( 194, 194 ) } ),
                       "AbstractRowData.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 106, 106 ),
                                   new ViolationPosition( 113, 113 ) } );
   }
}
