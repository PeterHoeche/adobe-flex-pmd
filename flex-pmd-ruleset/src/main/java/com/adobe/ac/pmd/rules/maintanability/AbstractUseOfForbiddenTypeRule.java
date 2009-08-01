package com.adobe.ac.pmd.rules.maintanability;

import java.util.List;
import java.util.Map.Entry;

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.IVariable;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;

public abstract class AbstractUseOfForbiddenTypeRule extends AbstractAstFlexRule
{
   @Override
   protected void findViolations( final IClass classNode )
   {
      super.findViolations( classNode );

      findViolationInVariableLists( classNode.getAttributes() );
      findViolationInVariableLists( classNode.getConstants() );
   }

   @Override
   protected void findViolations( final IFunction function )
   {
      tryToAddViolation( function.getReturnType().getInternalNode(),
                         function.getReturnType().toString() );

      findViolationInVariableLists( function.getParameters() );

      for ( final Entry< String, IParserNode > localVariableEntry : function.getLocalVariables().entrySet() )
      {
         final IParserNode type = getTypeFromFieldDeclaration( localVariableEntry.getValue() );

         tryToAddViolation( type,
                            type.getStringValue() );
      }
   }

   abstract protected String getForbiddenType();

   private < E extends IVariable > void findViolationInVariableLists( final List< E > variables )
   {
      for ( final IVariable variable : variables )
      {
         if ( variable.getType() != null )
         {
            tryToAddViolation( variable.getInternalNode(),
                               variable.getType().toString() );
         }
      }
   }

   private void tryToAddViolation( final IParserNode node,
                                   final String typeName )
   {
      if ( typeName.equals( getForbiddenType() ) )
      {
         addViolation( node );
      }
   }
}
