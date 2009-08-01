package com.adobe.ac.pmd.rules.sizing;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedAstFlexRule;

public class TooLongSwitchCaseRule extends AbstractMaximizedAstFlexRule
{
   private int length;

   public final int getActualValueForTheCurrentViolation()
   {
      return length;
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
   protected final void visitSwitchCase( final IParserNode caseBlock )
   {
      if ( caseBlock.getLastChild() != null )
      {
         length = caseBlock.getLastChild().getLine()
               - caseBlock.getLine();
         if ( length > getThreshold() )
         {
            addViolation( caseBlock );
         }
      }
   }
}
