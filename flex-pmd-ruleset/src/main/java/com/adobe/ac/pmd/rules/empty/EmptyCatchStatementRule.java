package com.adobe.ac.pmd.rules.empty;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class EmptyCatchStatementRule extends AbstractAstFlexRule
{
   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected final void visitCatch( final IParserNode ast )
   {
      super.visitCatch( ast );

      if ( ast.getChild( 2 ).numChildren() == 0 )
      {
         addViolation( ast );
      }
   }
}
