package com.adobe.ac.pmd.rules.naming;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IConstant;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.IVariable;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class WronglyNamedVariableRule extends AbstractAstFlexRule
{
   private static final String[] FORBIDDEN_NAMES =
                                                 { "tmp",
               "temp",
               "foo",
               "bar"                            };

   @Override
   protected void findViolations( final List< IFunction > functions )
   {
      for ( final IFunction function : functions )
      {
         findViolationsInVariables( function.getParameters() );

         for ( final Entry< String, IParserNode > variableNameEntrySet : function.getLocalVariables()
                                                                                 .entrySet() )
         {
            checkWronglyNamedVariable( variableNameEntrySet.getKey(),
                                       variableNameEntrySet.getValue() );
         }
      }
   }

   @Override
   protected void findViolationsFromAttributes( final List< IAttribute > attributes )
   {
      findViolationsInVariables( attributes );
   }

   @Override
   protected void findViolationsFromConstants( final List< IConstant > constants )
   {
      findViolationsInVariables( constants );
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   private void checkWronglyNamedVariable( final String variableName,
                                           final IParserNode variableNode )
   {
      for ( final String forbiddenName : FORBIDDEN_NAMES )
      {
         if ( variableName.startsWith( forbiddenName ) )
         {
            addViolation( variableNode,
                          variableName );
         }
      }
   }

   private void findViolationsInVariables( final Collection< ? extends IVariable > variables )
   {
      for ( final IVariable variable : variables )
      {
         checkWronglyNamedVariable( variable.getName(),
                                    variable.getInternalNode() );
      }
   }
}
