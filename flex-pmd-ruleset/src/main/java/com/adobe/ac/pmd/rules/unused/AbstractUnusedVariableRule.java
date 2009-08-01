package com.adobe.ac.pmd.rules.unused;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;

abstract class AbstractUnusedVariableRule extends AbstractAstFlexRule
{
   private Map< String, IParserNode > variablesUnused;

   protected final void addVariable( final String variableName,
                                     final IParserNode ast )
   {
      variablesUnused.put( variableName,
                           ast );
   }

   @Override
   protected void visitFunction( final IParserNode ast,
                                 final String type )
   {
      variablesUnused = new HashMap< String, IParserNode >();

      super.visitFunction( ast,
                           type );
      for ( final String variableName : variablesUnused.keySet() )
      {
         final IParserNode variable = variablesUnused.get( variableName );

         addViolation( variable,
                       variable,
                       variableName );
      }
   }

   @Override
   protected void visitStatement( final IParserNode ast )
   {
      super.visitStatement( ast );

      if ( variablesUnused != null
            && !variablesUnused.isEmpty() && ast != null )
      {
         markVariableAsUsed( ast );
      }
   }

   private void markVariableAsUsed( final IParserNode ast )
   {
      if ( ast.numChildren() == 0 )
      {
         if ( variablesUnused.containsKey( ast.getStringValue() ) )
         {
            variablesUnused.remove( ast.getStringValue() );
         }
      }
      else
      {
         for ( final IParserNode child : ast.getChildren() )
         {
            markVariableAsUsed( child );
         }
      }
   }
}
