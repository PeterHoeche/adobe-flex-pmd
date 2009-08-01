package com.adobe.ac.pmd.rules.mxml;

import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class MoreThanTwoEntryPointsInMxmlRule extends AbstractMoreThanEntryPointInMxmlRule
{
   @Override
   public final int getThreshold()
   {
      return 2;
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }
}
