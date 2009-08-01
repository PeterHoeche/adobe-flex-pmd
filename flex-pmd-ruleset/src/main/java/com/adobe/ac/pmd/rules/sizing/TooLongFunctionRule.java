package com.adobe.ac.pmd.rules.sizing;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedAstFlexRule;

public class TooLongFunctionRule extends AbstractMaximizedAstFlexRule
{
   private int functionLength;

   public final int getActualValueForTheCurrentViolation()
   {
      return functionLength;
   }

   public final int getDefaultThreshold()
   {
      return 25;
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected final void visitFunction( final IParserNode functionNode,
                                       final String type )
   {
      super.visitFunction( functionNode,
                           type );

      final IParserNode block = functionNode.getLastChild();

      if ( block != null
            && block.numChildren() != 0 )
      {
         final int beginningLine = block.getLine();
         final int lastLine = block.getLastChild().getLine();

         functionLength = lastLine
               - beginningLine;
         if ( functionLength > getThreshold() )
         {
            final IParserNode nameNode = getNameFromFunctionDeclaration( functionNode );

            addViolation( nameNode );
         }
      }
   }
}
