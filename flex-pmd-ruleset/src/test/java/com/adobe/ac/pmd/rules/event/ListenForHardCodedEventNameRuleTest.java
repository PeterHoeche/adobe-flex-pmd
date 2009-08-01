package com.adobe.ac.pmd.rules.event;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class ListenForHardCodedEventNameRuleTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
   {
      return new String[]
      { "addEventListener( \"change\", handleChange );",
                  "addEventListener( 'change', handleChange );",
                  "addEventListener(\"change\",handleChange);",
                  "addEventListener( \"change\"," };
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new ListenForHardCodedEventNameRule();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      return new String[]
      { "addEventListener( CHANGE, handleChange );",
                  "addEventListener(CHANGE,handleChange);",
                  "addEventListener( CHANGE," };
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "AbstractRowData.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 109, 109 ) } );
   }
}
