package com.adobe.ac.pmd.rules.component;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class UpdateChildrenNumberInUpdateDisplayListRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new UpdateChildrenNumberInUpdateDisplayListRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "BadComponent.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 42, 42 ),
                                   new ViolationPosition( 43, 43 ),
                                   new ViolationPosition( 44, 44 ),
                                   new ViolationPosition( 45, 45 ) } );
   }
}
