package com.adobe.ac.pmd.rules.mxml;

import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class MoreThanOneEntryPointInMxmlRule extends AbstractMoreThanEntryPointInMxmlRule
{
   @Override
   public final int getThreshold()
   {
      return 1;
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }
}
