package com.adobe.ac.pmd.rules.maintanability;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class UseOfObjectTypeRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new UseOfObjectTypeRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                                               "Looping.as",
                                                               new ViolationPosition[]
                                                               { new ViolationPosition( 63, 63 ) } ),
                                                     "com.adobe.ac.ncss.ConfigProxy.as",
                                                     new ViolationPosition[]
                                                     { new ViolationPosition( 42, 42 ) } ),
                                           "DeleteButtonRenderer.mxml",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 64, 64 ) } ),
                                 "Sorted.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 67, 67 ) } ),
                       "AbstractRowData.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 52, 52 ) } );
   }
}
