package com.adobe.ac.pmd.rules.sizing;

import java.util.List;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedAstFlexRule;

public class TooManyFieldsRule extends AbstractMaximizedAstFlexRule
{
   private int    attributesNb = 0;
   private IClass classNode    = null;

   public final int getActualValueForTheCurrentViolation()
   {
      return attributesNb;
   }

   public int getDefaultThreshold()
   {
      return 5;
   }

   @Override
   public boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return !file.getClassName().endsWith( "VO.as" );
   }

   @Override
   protected final void findViolations( final IClass classNodeToBeSet )
   {
      classNode = classNodeToBeSet;
      super.findViolations( classNodeToBeSet );
   }

   @Override
   protected final void findViolationsFromAttributes( final List< IAttribute > attributes )
   {
      attributesNb = attributes.size();

      if ( attributesNb > getThreshold() )
      {
         addViolation( classNode );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }
}
