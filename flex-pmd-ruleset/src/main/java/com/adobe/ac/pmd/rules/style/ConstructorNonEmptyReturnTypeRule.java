package com.adobe.ac.pmd.rules.style;

import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class ConstructorNonEmptyReturnTypeRule extends AbstractAstFlexRule
{
   @Override
   protected final void findViolationsFromConstructor( final IFunction constructor )
   {
      if ( constructor.getReturnType() != null
            && !"".equals( constructor.getReturnType().toString() ) )
      {
         addViolation( constructor );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }
}
