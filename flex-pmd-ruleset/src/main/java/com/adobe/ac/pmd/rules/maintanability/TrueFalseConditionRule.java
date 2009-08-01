package com.adobe.ac.pmd.rules.maintanability;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class TrueFalseConditionRule extends AbstractAstFlexRule // NO_UCD
{
   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   @Override
   protected void visitCondition( final IParserNode condition )
   {
      super.visitCondition( condition );

      final String conditionStr = condition.toString();

      if ( conditionStr.contains( "true" )
            || conditionStr.contains( "false" ) )
      {
         addViolation( condition );
      }
   }
}
