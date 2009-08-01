package com.adobe.ac.pmd.rules.maintanability;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class DynamicClassRule extends AbstractAstFlexRule
{
   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return !file.isMxml();
   }

   @Override
   protected final void findViolations( final IClass classNode )
   {
      if ( classNode.is( Modifier.DYNAMIC ) )
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
