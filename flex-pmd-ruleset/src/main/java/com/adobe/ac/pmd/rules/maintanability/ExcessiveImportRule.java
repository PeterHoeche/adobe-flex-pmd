package com.adobe.ac.pmd.rules.maintanability;

import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedAstFlexRule;

public class ExcessiveImportRule extends AbstractMaximizedAstFlexRule
{
   private int importNumber;

   public final int getActualValueForTheCurrentViolation()
   {
      return importNumber;
   }

   public final int getDefaultThreshold()
   {
      return 15;
   }

   @Override
   protected final void findViolations( final IPackage packageNode )
   {
      importNumber = packageNode.getImports().size();

      if ( importNumber > getThreshold() )
      {
         addViolation( packageNode );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }
}
