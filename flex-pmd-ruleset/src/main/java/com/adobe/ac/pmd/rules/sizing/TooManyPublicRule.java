package com.adobe.ac.pmd.rules.sizing;

import java.util.List;

import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedAstFlexRule;

public class TooManyPublicRule extends AbstractMaximizedAstFlexRule
{
   private IFunction constructor;
   private int       publicCount;

   public final int getActualValueForTheCurrentViolation()
   {
      return publicCount;
   }

   public final int getDefaultThreshold()
   {
      return 10;
   }

   @Override
   protected final void findViolations( final IClass classNode )
   {
      publicCount = 0;
      constructor = classNode.getConstructor();

      super.findViolations( classNode );

      if ( publicCount > getThreshold() )
      {
         addViolation( classNode );
      }
   }

   @Override
   protected final void findViolations( final IFunction function )
   {
      if ( function.isPublic()
            && !function.equals( constructor ) && !function.isGetter() && !function.isSetter() )
      {
         publicCount++;
      }
   }

   @Override
   protected final void findViolationsFromAttributes( final List< IAttribute > variables )
   {
      for ( final IAttribute variable : variables )
      {
         if ( variable.isPublic() )
         {
            publicCount++;
         }
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }
}
