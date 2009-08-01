package com.adobe.ac.pmd.rules.mxml;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class StaticMethodInMxmlRule extends AbstractAstFlexRule
{
   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return file.isMxml();
   }

   @Override
   protected final void findViolations( final IFunction function )
   {
      if ( function.is( Modifier.STATIC ) )
      {
         addViolation( function );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }

}
