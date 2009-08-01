package com.adobe.ac.pmd.rules.style;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class ConstructorNonEmptyReturnTypeRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new ConstructorNonEmptyReturnTypeRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "com.adobe.ac.ncss.VoidConstructor.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 37, 37 ) } );
   }
}
