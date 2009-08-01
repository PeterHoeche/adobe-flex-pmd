package com.adobe.ac.pmd.rules.event;

import java.util.List;

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UntypedEventMetadataRule extends AbstractAstFlexRule
{
   @Override
   protected final void findViolations( final IClass classNode )
   {
      final List< IMetaData > eventMetaData = classNode.getMetaData( "Event" );

      if ( eventMetaData != null )
      {
         findViolationsInMetaDataNode( eventMetaData );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }

   private void findViolationsInMetaDataNode( final List< IMetaData > eventMetaDatas )
   {
      for ( final IMetaData metaData : eventMetaDatas )
      {
         final String metaDataValue = metaData.getInternalNode().getStringValue();

         if ( !metaDataValue.contains( "type = \"" ) )
         {
            addViolation( metaData );
         }
      }
   }
}
