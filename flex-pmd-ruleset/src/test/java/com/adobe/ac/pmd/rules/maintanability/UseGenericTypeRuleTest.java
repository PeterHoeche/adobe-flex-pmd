package com.adobe.ac.pmd.rules.maintanability;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class UseGenericTypeRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new UseGenericTypeRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "GenericType.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 39, 39 ),
                                   new ViolationPosition( 44, 44 ),
                                   new ViolationPosition( 46, 46 ),
                                   new ViolationPosition( 35, 35 ),
                                   new ViolationPosition( 36, 36 ),
                                   new ViolationPosition( 37, 37 ) } );
   }
}
