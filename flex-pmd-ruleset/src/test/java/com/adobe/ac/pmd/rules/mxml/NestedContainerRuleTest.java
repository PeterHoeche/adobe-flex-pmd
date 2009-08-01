package com.adobe.ac.pmd.rules.mxml;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class NestedContainerRuleTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
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
                  "<ViewStack" };
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new NestedContainerRule();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      return new String[]
      { "<Label",
                  "<Image",
                  "<mx:Image",
                  "<Label" };
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( new HashMap< String, ViolationPosition[] >(),
                       "com.adobe.ac.ncss.mxml.NestedComponent.mxml",
                       new ViolationPosition[]
                       { new ViolationPosition( 41, 41 ) } );
   }
}
