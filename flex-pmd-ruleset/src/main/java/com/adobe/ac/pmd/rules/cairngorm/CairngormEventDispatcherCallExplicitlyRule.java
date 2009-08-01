package com.adobe.ac.pmd.rules.cairngorm;

import java.util.List;

import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class CairngormEventDispatcherCallExplicitlyRule extends AbstractAstFlexRule // NO_UCD
{
   private static final String ADD_EVENT_LISTENER_MESSAGE = "The Cairngorm event is listened directly. "
                                                                + "The Controller is then avoided, and "
                                                                + "the MVC pattern is broken.";
   private static final String DISPATCH_EVENT             = "dispatchEvent";
   private static final String DISPATCH_EVENT_MESSAGE     = "Use cairngormEvent.dispatch instead";
   private static final String EVENT_DISPATCHER           = "CairngormEventDispatcher";

   @Override
   protected void findViolations( final IFunction function )
   {
      final List< IParserNode > primaries = function.findPrimaryStatementsInBody( EVENT_DISPATCHER );

      for ( final IParserNode primary : primaries )
      {
         addViolation( primary,
                       computeMessage( function ) );
      }
   }

   private String computeMessage( final IFunction function )
   {
      return function.findPrimaryStatementsInBody( DISPATCH_EVENT ).isEmpty() ? ADD_EVENT_LISTENER_MESSAGE
                                                                        : DISPATCH_EVENT_MESSAGE;
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }
}
