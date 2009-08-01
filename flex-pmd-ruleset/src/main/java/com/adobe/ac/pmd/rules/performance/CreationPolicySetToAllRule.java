package com.adobe.ac.pmd.rules.performance;

import java.util.Locale;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class CreationPolicySetToAllRule extends AbstractRegexpBasedRule
{
   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return true;
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected final String getRegexp()
   {
      return ".*creationPolicy.*";
   }

   @Override
   protected final boolean isViolationDetectedOnThisMatchingLine( final String line,
                                                                  final IFlexFile file )
   {
      return line.toLowerCase( Locale.FRANCE ).contains( "all" );
   }
}
