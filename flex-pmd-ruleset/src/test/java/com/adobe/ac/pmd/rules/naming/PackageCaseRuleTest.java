package com.adobe.ac.pmd.rules.naming;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class PackageCaseRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new PackageCaseRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                                     "schedule_internal.as",
                                                     new ViolationPosition[]
                                                     { new ViolationPosition( -1, -1 ) } ),
                                           "cairngorm.FatController.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( -1, -1 ) } ),
                                 "GenericType.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( -1, -1 ) } ),
                       "AbstractRowData.as",
                       new ViolationPosition[]
                       { new ViolationPosition( -1, -1 ) } );
   }
}
