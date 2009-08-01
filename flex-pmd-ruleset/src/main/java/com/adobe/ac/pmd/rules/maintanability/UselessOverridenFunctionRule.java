package com.adobe.ac.pmd.rules.maintanability;

import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.parser.KeyWords;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UselessOverridenFunctionRule extends AbstractAstFlexRule
{
   @Override
   protected final void findViolations( final IFunction function )
   {
      final int statementNbAtFirstLevelInBody = function.getStatementNbInBody();

      if ( function.getBody() != null
            && function.isOverriden() && statementNbAtFirstLevelInBody == 1
            && function.findPrimaryStatementsInBody( KeyWords.SUPER.toString() ).size() != 0 )
      {
         addViolation( function );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }
}
