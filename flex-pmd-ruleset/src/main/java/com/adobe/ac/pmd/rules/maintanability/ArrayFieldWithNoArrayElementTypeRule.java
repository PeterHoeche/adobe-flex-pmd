package com.adobe.ac.pmd.rules.maintanability;

import java.util.List;

import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class ArrayFieldWithNoArrayElementTypeRule extends AbstractAstFlexRule
{
   private static final String ARRAY_ELEMENT_TYPE_BINDING = "ArrayElementType";
   private static final String ARRAY_TYPE                 = "Array";

   @Override
   protected final void findViolationsFromAttributes( final List< IAttribute > variables )
   {
      for ( final IAttribute variable : variables )
      {
         if ( ARRAY_TYPE.equals( variable.getType().toString() )
               && variable.getMetaData( ARRAY_ELEMENT_TYPE_BINDING ) == null )
         {
            addViolation( variable,
                          variable.getName() );
         }
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }
}
