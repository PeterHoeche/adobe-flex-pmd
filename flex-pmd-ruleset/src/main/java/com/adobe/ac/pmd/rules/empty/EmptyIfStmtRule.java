package com.adobe.ac.pmd.rules.empty;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class EmptyIfStmtRule extends AbstractAstFlexRule
{
   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected final void visitIf( final IParserNode ast )
   {
      super.visitIf( ast );

      final IParserNode block = ast.getChild( 1 );

      if ( block.numChildren() == 0 )
      {
         addViolation( ast );
      }
   }
}
