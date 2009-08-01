package com.adobe.ac.pmd.rules.mxml;

import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class MoreThanOneEntryPointInMxmlRuleTest extends AbstractMoreThanEntryPointInMxmlRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new MoreThanOneEntryPointInMxmlRule();
   }

   @Override
   protected ViolationPosition[] iterationsList2Violations()
   {
      return new ViolationPosition[]
      {};
   }

   @Override
   protected ViolationPosition[] iterationsListViolations()
   {
      return new ViolationPosition[]
      { new ViolationPosition( 74, 74 ) };
   }
}
