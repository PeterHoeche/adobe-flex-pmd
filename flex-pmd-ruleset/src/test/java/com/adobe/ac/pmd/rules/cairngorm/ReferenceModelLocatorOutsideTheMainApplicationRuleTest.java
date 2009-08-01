package com.adobe.ac.pmd.rules.cairngorm;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class ReferenceModelLocatorOutsideTheMainApplicationRuleTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
   {
      return new String[]
      { "ModelLocator",
                  "import com.my.MyModelLocator;" };
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new ReferenceModelLocatorOutsideTheMainApplicationRule();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      return new String[]
      { "ModfrelLocator",
                  "import com.my.MyModelLocafrtor;" };
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                           "UnboundMetadata.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 33, 33 ) } ),
                                 "com.adobe.ac.ncss.mxml.IterationsList.mxml",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 41, 41 ) } ),
                       "AbstractRowData.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 33, 33 ) } );
   }
}
