package com.adobe.ac.pmd.rules.style;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.AbstractFlexRuleTest;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class CopyrightMissingRuleTest extends AbstractFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new CopyrightMissingRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "Simple.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( -1, -1 ) } ),
                       "MainWithNoCopyright.mxml",
                       new ViolationPosition[]
                       { new ViolationPosition( -1, -1 ) } );
   }
}
