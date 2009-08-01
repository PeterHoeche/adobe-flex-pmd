package com.adobe.ac.pmd.rules.event;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UnboundTypeInMetadataRule extends AbstractAstFlexRule
{
   private static final String QUOTE      = "\"";
   private static final String TYPE_EQUAL = "type = \"";

   @Override
   protected final void findViolations( final IClass classNode )
   {
      final List< IMetaData > eventMetaDatas = classNode.getMetaData( "Event" );

      if ( eventMetaDatas != null )
      {
         findViolationsInMetaDataNode( eventMetaDatas,
                                       getFilesInSourcePath() );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   private void findViolationsInMetaDataNode( final List< IMetaData > eventMetaDatas,
                                              final Map< String, IFlexFile > files )
   {
      for ( final IMetaData metaData : eventMetaDatas )
      {
         final String metaDataValue = metaData.getParameter();
         final int startIndex = metaDataValue.indexOf( TYPE_EQUAL );

         if ( startIndex > -1 )
         {
            final String type = StringUtils.substringBefore( StringUtils.substringAfter( metaDataValue,
                                                                                         TYPE_EQUAL ),
                                                             QUOTE );

            if ( !files.containsKey( type
                  + ".as" ) )
            {
               addViolation( metaData );
            }
         }
      }
   }
}
