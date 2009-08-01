package com.adobe.ac.pmd.rules.cairngorm;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class ReferenceModelLocatorOutsideTheMainApplicationRule extends AbstractRegexpBasedRule // NO_UCD
{
   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return !file.getClassName().endsWith( "ModelLocator.as" )
            && ( !file.isMxml() || !file.isMainApplication() );
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected final String getRegexp()
   {
      return ".*ModelLocator.*";
   }

   @Override
   protected final boolean isViolationDetectedOnThisMatchingLine( final String line,
                                                                  final IFlexFile file )
   {
      return true;
   }
}
