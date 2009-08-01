package com.adobe.ac.pmd.rules.maintanability;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class ClassAndExtensionAreIdenticalRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new ClassAndExtensionAreIdenticalRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "GenericType.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 33, 33 ) } );
   }
}
