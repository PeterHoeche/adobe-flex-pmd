package com.adobe.ac.pmd.rules.sizing;

import java.util.List;

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedAstFlexRule;

public class TooManyFunctionRule extends AbstractMaximizedAstFlexRule
{
   private IClass classNode;
   private int    functionNb;

   public final int getActualValueForTheCurrentViolation()
   {
      return functionNb;
   }

   public final int getDefaultThreshold()
   {
      return 10;
   }

   @Override
   protected final void findViolations( final IClass classNodeToSet )
   {
      functionNb = 0;
      classNode = classNodeToSet;

      super.findViolations( classNodeToSet );
   }

   @Override
   protected final void findViolations( final List< IFunction > functions )
   {
      for ( final IFunction functionNode : functions )
      {
         if ( !functionNode.isGetter()
               && !functionNode.isSetter() && functionNode != classNode.getConstructor() )
         {
            functionNb++;
         }
      }
      if ( functionNb > getThreshold() )
      {
         addViolation( classNode );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }
}
