package com.adobe.ac.pmd.rules.event;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class DispatchHardCodedEventNameRuleTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
   {
      return new String[]
      { "dispatchEvent(new Event(\"change\" ));",
                  "dispatchEvent( new Event('change') );",
                  "dispatchEvent(new Event(\"change\"));",
                  "dispatchEvent( new Event( 'selectedGroupFieldsChange' ) )" };
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new DispatchHardCodedEventNameRule();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      return new String[]
      { "var i : int = 0;",
                  "lala();",
                  "dispatchEvent( new Event( CONST ) );",
                  "dispatchEvent(new Event(Rule.CONST));" };
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "com.adobe.ac.ncss.BigImporterModel.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 58, 58 ) } ),
                       "AbstractRowData.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 110, 110 ),
                                   new ViolationPosition( 111, 111 ) } );
   }
}
