package com.adobe.ac.pmd.rules.event;

import java.util.List;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class EventMissingCloneFunctionRule extends AbstractAstFlexRule
{
   private IClass classNode = null;

   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return file.getClassName().endsWith( "Event.as" );
   }

   @Override
   protected final void findViolations( final IClass classNodeToBeSet )
   {
      classNode = classNodeToBeSet;
      if ( "Event".equals( classNode.getExtensionName() ) )
      {
         super.findViolations( classNode );
      }
   }

   @Override
   protected final void findViolations( final List< IFunction > functions )
   {
      boolean cloneFound = false;

      for ( final IFunction functionNode : functions )
      {
         if ( "clone".equals( functionNode.getName() ) )
         {
            cloneFound = true;
         }
      }
      if ( !cloneFound )
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
