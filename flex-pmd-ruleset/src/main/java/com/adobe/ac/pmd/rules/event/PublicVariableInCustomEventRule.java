package com.adobe.ac.pmd.rules.event;

import java.util.List;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class PublicVariableInCustomEventRule extends AbstractAstFlexRule
{
   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return file.getClassName().endsWith( "Event.as" );
   }

   @Override
   protected final void findViolationsFromAttributes( final List< IAttribute > variables )
   {
      for ( final IAttribute attribute : variables )
      {
         if ( attribute.isPublic() )
         {
            addViolation( attribute,
                          attribute.getName() );
         }
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }
}
