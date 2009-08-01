package com.adobe.ac.pmd.rules.performance;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class HeavyConstructorRule extends AbstractAstFlexRule
{
   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return !file.isMxml();
   }

   @Override
   protected final void findViolationsFromConstructor( final IFunction constructor )
   {
      if ( constructor.getCyclomaticComplexity() > 1 )
      {
         addViolation( constructor,
                       String.valueOf( constructor.getCyclomaticComplexity() ) );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }
}
