package com.adobe.ac.pmd.rules.sizing;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedAstFlexRule;

public class TooManyParametersRule extends AbstractMaximizedAstFlexRule
{
   private int paramsNb;

   public final int getActualValueForTheCurrentViolation()
   {
      return paramsNb;
   }

   public final int getDefaultThreshold()
   {
      return 4;
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }

   @Override
   protected final void visitFunction( final IParserNode ast,
                                       final String type )
   {
      super.visitFunction( ast,
                           type );

      final IParserNode paramList = ast.getChild( 2 );

      paramsNb = paramList.numChildren();

      if ( paramsNb > getThreshold() )
      {
         addViolation( paramList );
      }
   }
}
