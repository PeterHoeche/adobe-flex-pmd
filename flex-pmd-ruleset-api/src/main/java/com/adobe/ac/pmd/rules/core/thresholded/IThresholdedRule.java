package com.adobe.ac.pmd.rules.core.thresholded;

import com.adobe.ac.pmd.rules.core.IFlexRule;

public interface IThresholdedRule extends IFlexRule
{
   int getActualValueForTheCurrentViolation();

   int getDefaultThreshold();

   int getThreshold();

   String getThresholdName();
}
