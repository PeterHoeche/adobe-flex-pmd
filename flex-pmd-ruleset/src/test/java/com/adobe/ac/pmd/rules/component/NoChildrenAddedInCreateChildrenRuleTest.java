package com.adobe.ac.pmd.rules.component;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class NoChildrenAddedInCreateChildrenRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new NoChildrenAddedInCreateChildrenRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "AbstractRowData.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 137, 137 ) } ),
                       "BadComponent.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 48, 48 ) } );
   }
}
