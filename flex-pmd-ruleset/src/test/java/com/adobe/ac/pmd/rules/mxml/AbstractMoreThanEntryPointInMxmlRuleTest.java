package com.adobe.ac.pmd.rules.mxml;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public abstract class AbstractMoreThanEntryPointInMxmlRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      return addToMap( addToMap( new HashMap< String, ViolationPosition[] >(),
                                 "com.adobe.ac.ncss.mxml.IterationsList.mxml",
                                 iterationsListViolations() ),
                       "com.adobe.ac.ncss.mxml.IterationsList2.mxml",
                       iterationsList2Violations() );
   }

   abstract protected ViolationPosition[] iterationsList2Violations();

   abstract protected ViolationPosition[] iterationsListViolations();
}