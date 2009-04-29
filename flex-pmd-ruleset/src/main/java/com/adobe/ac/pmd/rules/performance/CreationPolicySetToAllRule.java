package com.adobe.ac.pmd.rules.performance;

import com.adobe.ac.pmd.files.AbstractFlexFile;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class CreationPolicySetToAllRule
      extends AbstractRegexpBasedRule
{
   @Override
   public boolean isConcernedByTheGivenFile(
         final AbstractFlexFile file )
   {
      return true;
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.WARNING;
   }

   @Override
   protected String getRegexp()
   {
      return ".*creationPolicy.*";
   }

   @Override
   protected boolean isViolationDetectedOnThisMatchingLine(
         final String line, final AbstractFlexFile file )
   {
      return line.toLowerCase().contains(
            "all" );
   }
}
