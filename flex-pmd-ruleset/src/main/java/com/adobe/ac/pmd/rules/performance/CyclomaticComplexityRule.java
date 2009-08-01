package com.adobe.ac.pmd.rules.performance;

import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedAstFlexRule;

public class CyclomaticComplexityRule extends AbstractMaximizedAstFlexRule
{
   private IFunction currentFunction = null;

   public final int getActualValueForTheCurrentViolation()
   {
      return currentFunction.getCyclomaticComplexity();
   }

   public final int getDefaultThreshold()
   {
      return 10;
   }

   @Override
   protected final void findViolations( final IFunction function )
   {
      currentFunction = function;
      if ( function.getCyclomaticComplexity() > getThreshold() )
      {
         addViolation( function );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }
}
