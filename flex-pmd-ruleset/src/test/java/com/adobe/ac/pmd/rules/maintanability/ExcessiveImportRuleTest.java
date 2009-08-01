package com.adobe.ac.pmd.rules.maintanability;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class ExcessiveImportRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new ExcessiveImportRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                           "cairngorm.LightController.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( -1, -1 ) } ),
                                 "cairngorm.FatController.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( -1, -1 ) } ),
                       "com.adobe.ac.ncss.BigImporterModel.as",
                       new ViolationPosition[]
                       { new ViolationPosition( -1, -1 ) } );
   }
}
