package com.adobe.ac.pmd.rules.style;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class BadFormatLogerRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new BadFormatLogerRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "com.adobe.cairngorm.work.SequenceWorkFlow.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 49, 49 ) } ),
                       "AbstractRowData.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 44, 44 ),
                                   new ViolationPosition( 45, 45 ),
                                   new ViolationPosition( 45, 45 ),
                                   new ViolationPosition( 46, 46 ),
                                   new ViolationPosition( 46, 46 ),
                                   new ViolationPosition( 47, 47 ),
                                   new ViolationPosition( 47, 47 ),
                                   new ViolationPosition( 48, 48 ),
                                   new ViolationPosition( 49, 49 ),
                                   new ViolationPosition( 50, 50 ) } );
   }
}
