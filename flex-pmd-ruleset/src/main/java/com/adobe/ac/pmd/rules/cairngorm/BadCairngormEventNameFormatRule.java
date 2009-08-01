package com.adobe.ac.pmd.rules.cairngorm;

import org.apache.commons.lang.StringUtils;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IField;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class BadCairngormEventNameFormatRule extends AbstractAstFlexRule // NO_UCD
{
   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return file.getClassName().endsWith( "Event.as" );
   }

   @Override
   protected final void findViolations( final IClass classNode )
   {
      if ( isExtendedClassCairngormEvent( classNode ) )
      {
         final String eventName = extractEventName( classNode );

         if ( StringUtils.isEmpty( eventName )
               || !eventName.contains( "." ) )
         {
            addViolation( classNode );
         }
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   private String extractEventName( final IClass classNode )
   {
      String eventName = "";

      for ( final IField constantNode : classNode.getConstants() )
      {
         if ( constantNode.getName().startsWith( "EVENT" ) )
         {
            eventName = extractEventNameFromConstant( constantNode.getInitializationExpression()
                                                                  .getInternalNode() );
         }
      }
      if ( StringUtils.isEmpty( eventName )
            && classNode.getConstructor() != null )
      {
         eventName = extractEventNameFromConstructor( classNode.getConstructor() );
      }
      return eventName;
   }

   private String extractEventNameFromConstant( final IParserNode initExpressionNode )
   {
      return initExpressionNode.getChild( 0 ).getStringValue();
   }

   private String extractEventNameFromConstructor( final IFunction constructor )
   {
      String eventName = "";
      final IParserNode superCall = constructor.getSuperCall();

      if ( superCall != null )
      {
         eventName = superCall.getChild( 1 ).getChild( 0 ).getStringValue();
      }
      return eventName;
   }

   private boolean isExtendedClassCairngormEvent( final IClass classNode )
   {
      return classNode.getExtensionName() != null
            && classNode.getExtensionName().contains( "Cairngorm" )
            && classNode.getExtensionName().contains( "Event" );
   }
}
