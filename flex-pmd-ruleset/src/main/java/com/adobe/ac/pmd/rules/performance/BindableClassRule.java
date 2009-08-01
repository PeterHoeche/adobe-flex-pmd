package com.adobe.ac.pmd.rules.performance;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class BindableClassRule extends AbstractAstFlexRule
{
   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return !file.isMxml()
            && !file.getClassName().endsWith( "VO.as" );
   }

   @Override
   protected final void findViolations( final IClass classNode )
   {
      if ( classNode.isBindable() )
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
