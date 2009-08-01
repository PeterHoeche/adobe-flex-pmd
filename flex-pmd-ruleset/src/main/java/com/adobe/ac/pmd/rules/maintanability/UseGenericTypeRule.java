package com.adobe.ac.pmd.rules.maintanability;

import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UseGenericTypeRule extends AbstractUseOfForbiddenTypeRule // NO_UCD
{
   private static final String STAR = "*";

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   @Override
   protected String getForbiddenType()
   {
      return STAR;
   }
}
