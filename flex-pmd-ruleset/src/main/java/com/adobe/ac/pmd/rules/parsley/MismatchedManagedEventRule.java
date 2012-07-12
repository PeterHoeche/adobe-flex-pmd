/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
 *    All rights reserved.
 *
 *    Redistribution  and  use  in  source  and  binary  forms, with or without
 *    modification,  are  permitted  provided  that  the  following  conditions
 *    are met:
 *
 *      * Redistributions  of  source  code  must  retain  the  above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions  in  binary  form  must reproduce the above copyright
 *        notice,  this  list  of  conditions  and  the following disclaimer in
 *        the    documentation   and/or   other  materials  provided  with  the
 *        distribution.
 *      * Neither the name of the Adobe Systems, Incorporated. nor the names of
 *        its  contributors  may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS  SOFTWARE  IS  PROVIDED  BY THE  COPYRIGHT  HOLDERS AND CONTRIBUTORS
 *    "AS IS"  AND  ANY  EXPRESS  OR  IMPLIED  WARRANTIES,  INCLUDING,  BUT NOT
 *    LIMITED  TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,  INCIDENTAL,  SPECIAL,
 *    EXEMPLARY,  OR  CONSEQUENTIAL  DAMAGES  (INCLUDING,  BUT  NOT  LIMITED TO,
 *    PROCUREMENT  OF  SUBSTITUTE   GOODS  OR   SERVICES;  LOSS  OF  USE,  DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY,  WHETHER  IN  CONTRACT,  STRICT  LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE  OR  OTHERWISE)  ARISING  IN  ANY  WAY  OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.rules.parsley;

import java.util.ArrayList;
import java.util.List;

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.nodes.MetaData;
import com.adobe.ac.pmd.rules.core.AbstractFlexMetaDataRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.parsley.utils.ParsleyMetaData;

/**
 * @author xagnetti
 */
public final class MismatchedManagedEventRule extends AbstractFlexMetaDataRule
{
   /*
    * (non-Javadoc)
    * @seecom.adobe.ac.pmd.rules.core.AbstractFlexMetaDataRule#
    * findViolationsFromClassMetaData(com.adobe.ac.pmd.nodes.IClass)
    */
   @Override
   protected void findViolationsFromClassMetaData( final IClass classNode )
   {
      final List< IMetaData > managedEvents = ParsleyMetaData.MANAGED_EVENTS.getMetaDataList( classNode );

      final List< String > eventTypes = getEventTypes( classNode );

      for ( final IMetaData data : managedEvents )
      {
         final List< String > types = data.getPropertyAsList( "names" );

         for ( final String type : types )
         {
            if ( !eventTypes.contains( type ) )
            {
               addViolation( data,
                             type );
            }
         }
      }
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.rules.core.AbstractFlexRule#getDefaultPriority()
    */
   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   private List< String > getEventTypes( final IClass classNode )
   {
      final ArrayList< String > types = new ArrayList< String >();

      for ( final IMetaData data : classNode.getMetaData( MetaData.EVENT ) )
      {
         final String[] name = data.getProperty( "name" );
         final String eventName = name.length == 0 ? data.getDefaultValue()
                                                  : name[ 0 ];

         types.add( eventName );
      }

      return types;
   }
}