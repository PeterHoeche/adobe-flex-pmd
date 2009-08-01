package com.adobe.ac.pmd.rules.maintanability;

import java.util.List;

import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class AvoidProtectedFieldInFinalClassRule extends AbstractAstFlexRule
{
   @Override
   protected final void findViolations( final IClass classNode )
   {
      final boolean isClassFinal = classNode.isFinal();

      findProtectedAttributes( classNode.getAttributes(),
                               isClassFinal );
      findProtectedMethods( classNode.getFunctions(),
                            isClassFinal );
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }

   private void findProtectedAttributes( final List< IAttribute > atributes,
                                         final boolean isClassFinal )
   {
      if ( atributes != null )
      {
         for ( final IAttribute field : atributes )
         {
            if ( field.is( Modifier.PROTECTED )
                  && isClassFinal )
            {
               addViolation( field,
                             field.getName() );
            }
         }
      }
   }

   private void findProtectedMethods( final List< IFunction > functions,
                                      final boolean isClassFinal )
   {
      if ( functions != null )
      {
         for ( final IFunction function : functions )
         {
            if ( function.is( Modifier.PROTECTED )
                  && !function.isOverriden() && isClassFinal )
            {
               addViolation( function );
            }
         }
      }
   }
}
