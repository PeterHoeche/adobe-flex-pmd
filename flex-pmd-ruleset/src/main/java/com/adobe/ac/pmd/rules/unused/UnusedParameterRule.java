package com.adobe.ac.pmd.rules.unused;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UnusedParameterRule extends AbstractUnusedVariableRule
{
   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   @Override
   protected final void visitParameters( final IParserNode ast )
   {
      super.visitParameters( ast );

      if ( ast.numChildren() != 0 )
      {
         for ( final IParserNode parameterNode : ast.getChildren() )
         {
            if ( !isParameterAnEvent( parameterNode )
                  && parameterNode.numChildren() > 0 && parameterNode.getChild( 0 ).numChildren() > 0 )
            {
               addVariable( parameterNode.getChild( 0 ).getChild( 0 ).getStringValue(),
                            parameterNode );
            }
         }
      }
   }

   private boolean isParameterAnEvent( final IParserNode parameterNode )
   {
      final IParserNode parameterType = getTypeFromFieldDeclaration( parameterNode );

      return parameterType != null
            && parameterType.getStringValue() != null && parameterType.getStringValue().contains( "Event" );
   }
}
