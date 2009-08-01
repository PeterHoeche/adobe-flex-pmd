package com.adobe.ac.pmd.rules.switchrules;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class SwitchStatementsShouldHaveDefaultRule extends AbstractAstFlexRule
{
   private boolean defaultStatementFound = false;

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   @Override
   protected final void visitSwitch( final IParserNode ast )
   {
      super.visitSwitch( ast );

      if ( !defaultStatementFound )
      {
         ast.getChild( 1 );

         addViolation( ast );
      }
   }

   @Override
   protected final void visitSwitchDefaultCase( final IParserNode child )
   {
      super.visitSwitchDefaultCase( child );

      defaultStatementFound = true;
   }
}
