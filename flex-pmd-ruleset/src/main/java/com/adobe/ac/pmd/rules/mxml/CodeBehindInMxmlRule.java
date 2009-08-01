package com.adobe.ac.pmd.rules.mxml;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class CodeBehindInMxmlRule extends AbstractRegexpBasedRule
{
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
      return ".*<.*Script.*";
   }

   @Override
   protected final boolean isViolationDetectedOnThisMatchingLine( final String line,
                                                                  final IFlexFile file )
   {
      return line.contains( "source" );
   }
}
