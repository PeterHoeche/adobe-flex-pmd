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
package com.adobe.ac.pmd.rules.parsley.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.nodes.IMetaDataListHolder;
import com.adobe.ac.pmd.nodes.MetaData;

/**
 * @author xagnetti
 */
public final class MetaDataTag
{
   /**
    * @author xagnetti
    */
   public enum Location
   {
      ATTRIBUTE, CLASS_DECLARATION, FUNCTION
   };

   private final String[]   attributes;

   private final String     name;

   private final Location[] placedOn;

   /**
    * @param nameToBeSet
    * @param attributesToBeSet
    * @param placedOnToBeSet
    */
   public MetaDataTag( final String nameToBeSet,
                       final String[] attributesToBeSet,
                       final Location[] placedOnToBeSet )
   {
      name = nameToBeSet;
      attributes = attributesToBeSet;
      placedOn = placedOnToBeSet;
   }

   /**
    * @return
    */
   public List< String > getAttributes()
   {
      return Arrays.asList( attributes );
   }

   /**
    * @param holder
    * @return
    */
   public List< IMetaData > getMetaDataList( final IMetaDataListHolder holder )
   {
      final List< IMetaData > list = new ArrayList< IMetaData >();

      for ( final IMetaData metaData : holder.getMetaData( MetaData.OTHER ) )
      {
         if ( metaData.getName().equals( name ) )
         {
            list.add( metaData );
         }
      }

      return list;
   }

   /**
    * @return
    */
   public String getName()
   {
      return name;
   }

   /**
    * @return
    */
   public List< Location > getPlacedOn()
   {
      return Arrays.asList( placedOn );
   }
}