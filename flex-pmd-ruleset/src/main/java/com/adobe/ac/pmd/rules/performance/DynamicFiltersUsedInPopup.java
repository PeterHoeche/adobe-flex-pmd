package com.adobe.ac.pmd.rules.performance;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class DynamicFiltersUsedInPopup extends AbstractRegexpBasedRule
{
   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return file.getClassName().contains( "Popup" );
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   @Override
   protected final String getRegexp()
   {
      return ".*Filter.*";
   }

   @Override
   protected final boolean isViolationDetectedOnThisMatchingLine( final String line,
                                                                  final IFlexFile file )
   {
      return true;
   }
}
