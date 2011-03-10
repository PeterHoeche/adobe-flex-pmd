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
package com.adobe.ac.pmd.nodes.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.adobe.ac.pmd.nodes.IMetaData;
import com.adobe.ac.pmd.parser.IParserNode;

/**
 * @author xagnetti
 */
class MetaDataNode extends AbstractNode implements IMetaData
{
   private List< String >          attributeNames;
   private String                  name;
   private String                  parameter;
   private Map< String, String[] > parameters;

   /**
    * @param node
    */
   protected MetaDataNode( final IParserNode node )
   {
      super( node );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.impl.AbstractNode#compute()
    */
   @Override
   public MetaDataNode compute()
   {
      final String stringValue = getInternalNode().getStringValue();

      name = stringValue.indexOf( " ( " ) > -1 ? stringValue.substring( 0,
                                                                        stringValue.indexOf( " ( " ) )
                                              : stringValue;
      parameter = stringValue.indexOf( "( " ) > -1 ? stringValue.substring( stringValue.indexOf( "( " ) + 2,
                                                                            stringValue.lastIndexOf( " )" ) )
                                                  : "";

      computeAttributeNames();
      return this;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IMetaData#getAttributeNames()
    */
   public List< String > getAttributeNames()
   {
      return attributeNames;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IMetaData#getDefaultValue()
    */
   public String getDefaultValue()
   {
      return parameter;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.INamable#getName()
    */
   public String getName()
   {
      return name;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IMetaData#getProperty(java.lang.String)
    */
   public String[] getProperty( final String property )
   {
      return parameters.containsKey( property ) ? parameters.get( property )
                                               : new String[]
                                               {};
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.nodes.IMetaData#getPropertyAsList(java.lang.String)
    */
   public List< String > getPropertyAsList( final String property )
   {
      return Arrays.asList( getProperty( property ) );
   }

   private void computeAttributeNames()
   {
      attributeNames = new ArrayList< String >();
      parameters = new LinkedHashMap< String, String[] >();

      final String[] pairs = getPairs();

      for ( final String pair : pairs )
      {
         final String[] pairSplit = pair.split( "=" );

         if ( pairSplit.length == 2 )
         {
            attributeNames.add( pairSplit[ 0 ].trim() );
            parameters.put( pairSplit[ 0 ].trim(),
                            pairSplit[ 1 ].trim().replaceAll( "\'",
                                                              "" ).replaceAll( "\"",
                                                                               "" ).split( "," ) );
         }
      }
   }

   private String[] getPairs()
   {
      return parameter.split( "," );
   }
}
