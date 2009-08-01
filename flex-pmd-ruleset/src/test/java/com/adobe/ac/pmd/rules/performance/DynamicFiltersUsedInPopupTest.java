package com.adobe.ac.pmd.rules.performance;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractRegExpBasedRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class DynamicFiltersUsedInPopupTest extends AbstractRegExpBasedRuleTest
{
   @Override
   protected String[] getMatchableLines()
   {
      final String[] lines =
      { "new DropShadowFilter",
                  "new GlowFilter",
                  "mx:DropShadowFilter" };
      return lines;
   }

   @Override
   protected AbstractRegexpBasedRule getRegexpBasedRule()
   {
      return new DynamicFiltersUsedInPopup();
   }

   @Override
   protected String[] getUnmatchableLines()
   {
      final String[] lines =
      { ".filterFunction",
                  "DropShadowfilter(" };
      return lines;
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "filters.MyPopup.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 37, 37 ) } ),
                       "filters.MyPopup.mxml",
                       new ViolationPosition[]
                       { new ViolationPosition( 41, 41 ),
                                   new ViolationPosition( 44, 44 ) } );
   }
}
