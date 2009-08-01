package com.adobe.ac.pmd.rules.binding;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class ChangeWatcherRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new ChangeWatcherRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "Title.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 41, 41 ) } );
   }
}
