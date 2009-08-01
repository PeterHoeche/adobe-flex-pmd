package com.adobe.ac.pmd.impl;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.rules.core.IFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public final class ViolationFactory
{
   public static IFlexViolation create( final ViolationPosition position,
                                        final IFlexRule violatedRule,
                                        final IFlexFile violatedFile )
   {
      return new Violation( position, violatedRule, violatedFile );
   }

   private ViolationFactory()
   {
   }
}
