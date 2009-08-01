package com.adobe.ac.pmd.rules.maintanability;

import java.util.List;

import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class AvoidUsingPublicStaticFieldRule extends AbstractAstFlexRule
{
   @Override
   protected void findViolationsFromAttributes( final List< IAttribute > variables )
   {
      for ( final IAttribute attribute : variables )
      {
         if ( attribute.isPublic()
               && attribute.isStatic() && !attribute.getName().contains( "instance" ) )
         {
            addViolation( attribute );
         }
      }
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }
}
