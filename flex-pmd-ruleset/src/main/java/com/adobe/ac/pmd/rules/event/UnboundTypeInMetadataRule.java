/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
