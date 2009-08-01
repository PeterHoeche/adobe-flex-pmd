package com.adobe.ac.pmd.rules.naming;

import java.util.List;

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.INamableNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class VariableNameEndingWithNumericRule extends AbstractAstFlexRule
{
   @Override
   protected void findViolations( final IClass classNode )
   {
      super.findViolations( classNode );

      findViolationsInNamableList( classNode.getAttributes() );
      findViolationsInNamableList( classNode.getConstants() );
      findViolationsInNamableList( classNode.getFunctions() );
   }

   @Override
   protected void findViolations( final IFunction function )
   {
      findViolationsInNamableList( function.getParameters() );

      for ( final String variableName : function.getLocalVariables().keySet() )
      {
         if ( isNameEndsWithNumeric( variableName ) )
         {
            addViolation( function.getLocalVariables().get( variableName ),
                          variableName );
         }
      }
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   private void findViolationsInNamableList( final List< ? extends INamableNode > namables )
   {
      for ( final INamableNode namable : namables )
      {
         if ( isNameEndsWithNumeric( namable.getName() ) )
         {
            if ( namable instanceof IFunction )
            {
               final IFunction function = ( IFunction ) namable;

               addViolation( function,
                             function.getName() );
            }
            else
            {
               addViolation( namable,
                             namable.getName() );
            }
         }
      }
   }

   private boolean isNameEndsWithNumeric( final String name )
   {
      final char lastCharacter = name.charAt( name.length() - 1 );

      return Character.isDigit( lastCharacter );
   }
}
