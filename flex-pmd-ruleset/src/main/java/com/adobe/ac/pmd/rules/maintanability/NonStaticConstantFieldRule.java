package com.adobe.ac.pmd.rules.maintanability;

import java.util.List;

import com.adobe.ac.pmd.nodes.IConstant;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class NonStaticConstantFieldRule extends AbstractAstFlexRule
{
   @Override
   protected final void findViolationsFromConstants( final List< IConstant > constants )
   {
      for ( final IConstant field : constants )
      {
         if ( !field.isStatic() )
         {
            addViolation( field,
                          field.getName() );
         }
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }
}
