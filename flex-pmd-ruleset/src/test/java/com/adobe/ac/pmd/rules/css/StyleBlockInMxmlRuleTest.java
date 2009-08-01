package com.adobe.ac.pmd.rules.css;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class StyleBlockInMxmlRuleTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
   {
      return new String[]
      { "<mx:Style>",
                  "  <mx:Style>   ",
                  "<mx:Style>   ",
                  "  <mx:Style>" };
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new StyleBlockInMxmlRule();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      return new String[]
      { "<mx:VBox",
                  "<Box",
                  "<Canvas",
                  "<VBox",
                  "<mx:HBox",
                  "<Accordion",
                  "<Form",
                  "<FormItem",
                  "<LayoutContainer",
                  "<Panel",
                  "<ViewStack",
                  "<mx:Style/>" };
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "Main.mxml",
                       new ViolationPosition[]
                       { new ViolationPosition( 49, 49 ) } );
   }
}
