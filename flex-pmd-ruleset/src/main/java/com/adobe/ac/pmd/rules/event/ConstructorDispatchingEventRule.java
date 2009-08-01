package com.adobe.ac.pmd.rules.event;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class ConstructorDispatchingEventRule extends AbstractAstFlexRule
{
   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return !file.isMxml();
   }

   @Override
   protected final void findViolationsFromConstructor( final IFunction constructor )
   {
      for ( final IParserNode statement : constructor.findPrimaryStatementsInBody( "dispatchEvent" ) )
      {
         addViolation( statement );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }
}
