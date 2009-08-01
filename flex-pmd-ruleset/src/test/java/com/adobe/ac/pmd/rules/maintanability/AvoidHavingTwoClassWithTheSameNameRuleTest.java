package com.adobe.ac.pmd.rules.maintanability;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.AbstractFlexRuleTest;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class AvoidHavingTwoClassWithTheSameNameRuleTest extends AbstractFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new AvoidHavingTwoClassWithTheSameNameRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      final ViolationPosition[] positions = new ViolationPosition[]
      { new ViolationPosition( -1, -1 ) };

      return addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                           "AbstractRowData.mxml",
                                           positions ),
                                 "AbstractRowData.as",
                                 positions ),
                       "com.adobe.ac.AbstractRowData.as",
                       positions );
   }
}
