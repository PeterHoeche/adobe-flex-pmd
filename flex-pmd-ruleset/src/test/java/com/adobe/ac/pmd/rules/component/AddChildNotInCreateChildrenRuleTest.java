package com.adobe.ac.pmd.rules.component;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class AddChildNotInCreateChildrenRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new AddChildNotInCreateChildrenRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "RadonDataGrid.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 132, 132 ) } ),
                       "BadComponent.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 42, 42 ),
                                   new ViolationPosition( 43, 43 ) } );
   }
}
