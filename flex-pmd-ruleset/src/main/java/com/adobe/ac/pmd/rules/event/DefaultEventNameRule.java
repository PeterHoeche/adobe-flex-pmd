package com.adobe.ac.pmd.rules.event;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class DefaultEventNameRule extends AbstractAstFlexRule
{
   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return file.getClassName().endsWith( "Event.as" );
   }

   @Override
   protected final void findViolationsFromConstructor( final IFunction constructor )
   {
      if ( constructor.getParameters().size() > 0
            && constructor.getParameters().get( 0 ).getType().toString().equals( "String" )
            && constructor.getParameters().get( 0 ).getInitializationExpression() != null )
      {
         addViolation( constructor.getParameters().get( 0 ) );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }
}
