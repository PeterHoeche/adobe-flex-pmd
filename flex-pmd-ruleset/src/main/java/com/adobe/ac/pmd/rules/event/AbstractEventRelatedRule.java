package com.adobe.ac.pmd.rules.event;

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;

public abstract class AbstractEventRelatedRule extends AbstractAstFlexRule
{

   @Override
   public final boolean isConcernedByTheCurrentFile()
   {
      return !getCurrentFile().isMxml();
   }

   @Override
   protected void findViolations( final IClass classNode )
   {
      if ( classNode.getExtensionName() != null
            && classNode.getExtensionName().contains( "Event" ) )
      {
         super.findViolations( classNode );
      }
   }

}