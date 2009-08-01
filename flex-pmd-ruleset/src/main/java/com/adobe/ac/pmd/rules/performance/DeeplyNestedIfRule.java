package com.adobe.ac.pmd.rules.performance;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedAstFlexRule;

public class DeeplyNestedIfRule extends AbstractMaximizedAstFlexRule
{
   private int ifLevel = 0;

   public final int getActualValueForTheCurrentViolation()
   {
      return ifLevel;
   }

   public final int getDefaultThreshold()
   {
      return 2;
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected final void visitElse( final IParserNode ast )
   {
      beforeVisitingIfBlock();

      super.visitElse( ast );

      afterVisitingIfBlock( ast );
   }

   @Override
   protected final void visitFunction( final IParserNode ast,
                                       final String type )
   {
      ifLevel = 0;

      super.visitFunction( ast,
                           type );
   }

   @Override
   protected final void visitThen( final IParserNode ast )
   {
      beforeVisitingIfBlock();

      super.visitThen( ast );

      afterVisitingIfBlock( ast );
   }

   private void afterVisitingIfBlock( final IParserNode ast )
   {
      ifLevel--;
      if ( ifLevel >= getThreshold() )
      {
         addViolation( ast );
      }
   }

   private void beforeVisitingIfBlock()
   {
      ifLevel++;
   }
}
