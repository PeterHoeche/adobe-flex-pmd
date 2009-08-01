package com.adobe.ac.pmd.rules.maintanability;

import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UseOfObjectTypeRule extends AbstractUseOfForbiddenTypeRule // NO_UCD
{
   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   @Override
   protected String getForbiddenType()
   {
      return "Object";
   }
}
