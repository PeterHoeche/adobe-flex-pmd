package com.adobe.ac.pmd.rules.naming;

import java.util.List;
import java.util.Set;

import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.IVariable;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class PropertyHiddenByLocalVariableRule extends AbstractAstFlexRule
{
   @Override
   protected final void findViolations( final IClass classNode )
   {
      final List< IAttribute > variables = classNode.getAttributes();

      if ( classNode.getFunctions() != null )
      {
         for ( final IFunction function : classNode.getFunctions() )
         {
            final Set< String > localVariables = function.getLocalVariables().keySet();

            for ( final String localVariable : localVariables )
            {
               for ( final IVariable field : variables )
               {
                  if ( localVariable.equals( field.getName() ) )
                  {
                     addViolation( function.getLocalVariables().get( localVariable ),
                                   field.getName() );
                  }
               }
            }
         }
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }
}
