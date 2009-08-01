package com.adobe.ac.pmd.rules.mxml;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedRegexpBasedRule;

public class NestedContainerRule extends AbstractMaximizedRegexpBasedRule
{
   private int     currentLevel   = 0;
   private boolean violationFound = false;

   public final int getActualValueForTheCurrentViolation()
   {
      return currentLevel;
   }

   public final int getDefaultThreshold()
   {
      return 2;
   }

   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return file.isMxml();
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   @Override
   protected final String getRegexp()
   {
      return ".*<(mx:)?(.*Box|Canvas|Accordion|Form|FormItem|LayoutContainer|Panel|Tile|ViewStack).*";
   }

   @Override
   protected final boolean isViolationDetectedOnThisMatchingLine( final String line,
                                                                  final IFlexFile file )
   {
      boolean result = false;

      if ( line.contains( "</" ) )
      {
         currentLevel--;
      }
      else
      {
         currentLevel++;
      }
      if ( !violationFound
            && currentLevel > getThreshold() )
      {
         violationFound = true;

         result = true;
      }
      return result;
   }
}
