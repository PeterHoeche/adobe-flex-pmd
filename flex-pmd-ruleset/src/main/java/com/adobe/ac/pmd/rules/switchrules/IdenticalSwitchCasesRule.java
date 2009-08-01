package com.adobe.ac.pmd.rules.switchrules;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class IdenticalSwitchCasesRule extends AbstractAstFlexRule
{
   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }

   @Override
   protected final void visitSwitch( final IParserNode ast )
   {
      super.visitSwitch( ast );

      if ( ast.numChildren() > 0 )
      {
         final Map< String, IParserNode > cases = new HashMap< String, IParserNode >();

         for ( final IParserNode caseStatement : ast.getChild( 1 ).getChildren() )
         {
            final String label = caseStatement.getChild( 0 ).toString();

            if ( cases.containsKey( label ) )
            {
               addViolation( caseStatement );
               break;
            }
            else
            {
               cases.put( label,
                          caseStatement );
            }
         }
      }
   }
}
