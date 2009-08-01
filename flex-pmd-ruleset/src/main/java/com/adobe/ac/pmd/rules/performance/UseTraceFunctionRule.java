package com.adobe.ac.pmd.rules.performance;

import java.util.List;

import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UseTraceFunctionRule extends AbstractAstFlexRule
{
   @Override
   protected final void findViolations( final IFunction function )
   {
      final List< IParserNode > traceStatements = function.findPrimaryStatementsInBody( "trace" );

      for ( final IParserNode statement : traceStatements )
      {
         addViolation( statement );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }
}
