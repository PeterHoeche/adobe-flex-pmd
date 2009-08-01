package com.adobe.ac.pmd.rules.switchrules;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class NestedSwitchRule extends AbstractAstFlexRule
{
   private int switchLevel = 0;

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected final void visitSwitch( final IParserNode ast )
   {
      switchLevel++;
      if ( switchLevel > 1 )
      {
         addViolation( ast );
      }
      super.visitSwitch( ast );

      switchLevel--;
   }
}
