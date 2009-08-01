package com.adobe.ac.pmd.rules.mxml;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.AbstractFlexRuleTest;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class TooLongScriptBlockRuleTest extends AbstractFlexRuleTest
{
   @Override
   public AbstractFlexRule getRule()
   {
      return new TooLongScriptBlockRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "DeleteButtonRenderer.mxml",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 49, 103 ) } ),
                       "com.adobe.ac.ncss.mxml.IterationsList.mxml",
                       new ViolationPosition[]
                       { new ViolationPosition( 40, 90 ) } );
   }
}
