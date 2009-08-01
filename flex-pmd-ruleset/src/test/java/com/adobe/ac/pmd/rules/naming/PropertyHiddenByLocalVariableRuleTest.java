package com.adobe.ac.pmd.rules.naming;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class PropertyHiddenByLocalVariableRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new PropertyHiddenByLocalVariableRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "com.adobe.ac.ncss.VoidConstructor.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 40, 40 ) } );
   }
}
