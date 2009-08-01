package com.adobe.ac.pmd.rules.as3;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class ViewComponentReferencedInModelRuleTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
   {
      return new String[]
      { "import lala.view.MyObject;",
                  "import MyObject   ",
                  "   import lala.view.MyObject" };
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new ViewComponentReferencedInModelRule();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      return new String[]
      { "mport lala.view.MyObject",
                  " text=\"{ vfrfr().frfr.frf.lala }\"/>",
                  " text=\"{vfrfr().frfr.frf.lala}\"/>",
                  "public dynamic class DynamicObject {",
                  "dynamic public class DynamicObject" };
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "com.adobe.ac.ncss.BigImporterModel.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 35, 35 ) } );
   }
}
