package com.adobe.ac.pmd.rules.unused;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class UnusedPrivateMethodRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new UnusedPrivateMethodRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                                     "Sorted.as",
                                                     new ViolationPosition[]
                                                     { new ViolationPosition( 71, 71 ) } ),
                                           "cairngorm.LightController.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 191, 191 ) } ),
                                 "RadonDataGrid.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 208, 208 ) } ),
                       "com.adobe.ac.ncss.BigModel.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 94, 94 ),
                                   new ViolationPosition( 86, 86 ),
                                   new ViolationPosition( 98, 98 ),
                                   new ViolationPosition( 90, 90 ) } );
   }
}
