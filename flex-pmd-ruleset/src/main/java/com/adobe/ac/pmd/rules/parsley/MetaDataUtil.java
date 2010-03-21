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
import java.util.Arrays;
import java.util.List;

import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.nodes.IMetaDataListHolder;
import com.adobe.ac.pmd.nodes.MetaData;

public final class MetaDataUtil
{
   public static List< String > getAttributeNames( final IMetaData metaData )
   {
      final List< String > names = new ArrayList< String >();

      final String[] pairs = getPairs( metaData );

      for ( final String pair : pairs )
      {
         final String[] pairSplit = pair.split( "=" );

         if ( pairSplit.length == 2 )
         {
            names.add( pairSplit[ 0 ].trim() );
         }
      }

      return names;
   }

   public static List< IMetaData > getCustomMetaData( final IMetaDataListHolder holder,
                                                      final String name )
   {
      final List< IMetaData > results = new ArrayList< IMetaData >();
      final List< IMetaData > other = holder.getMetaData( MetaData.OTHER );

      for ( final IMetaData metaData : other )
      {
         if ( metaData.getName().equals( name ) )
         {
            results.add( metaData );
         }
      }

      return results;
   }

   public static String getPropertyString( final IMetaData data,
                                           final String property )
   {
      final String[] pairs = getPairs( data );

      for ( final String pair : pairs )
      {
         final String[] pairSplit = pair.split( "=" );

         if ( pairSplit[ 0 ].trim().equals( property ) )
         {
            String firstPart = pairSplit[ 1 ];
            firstPart = firstPart.trim();

            if ( firstPart.indexOf( '"' ) == 0 )
            {
               firstPart = firstPart.substring( 1 );
            }

            if ( firstPart.lastIndexOf( '"' ) == firstPart.length() - 1 )
            {
               firstPart = firstPart.substring( 0,
                                                firstPart.length() - 1 );
            }

            return firstPart;
         }
      }
      return null;
   }

   public static String[] getPropertyStringArray( final IMetaData data,
                                                  final String property )
   {
      final String value = getPropertyString( data,
                                              property );
      return value == null ? new String[]
                          {}
                          : value.split( "," );
   }

   public static List< String > getPropertyStringList( final IMetaData data,
                                                       final String property )
   {
      return Arrays.asList( getPropertyStringArray( data,
                                                    property ) );
   }

   private static String[] getPairs( final IMetaData data )
   {
      String value = data.getInternalNode().getStringValue();

      if ( value.contains( "(" )
            && value.contains( ")" ) )
      {
         value = value.substring( value.indexOf( '(' ) + 1,
                                  value.lastIndexOf( ')' ) );
      }
      return value.split( "," );
   }

   private MetaDataUtil()
   {
   }
}