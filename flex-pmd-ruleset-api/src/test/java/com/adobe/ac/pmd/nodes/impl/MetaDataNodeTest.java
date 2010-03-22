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

import static org.junit.Assert.assertEquals;
import net.sourceforge.pmd.PMDException;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.nodes.IMetaDataListHolder;
import com.adobe.ac.pmd.nodes.MetaData;
import com.adobe.ac.pmd.parser.IParserNode;

public class MetaDataNodeTest extends FlexPmdTestBase
{
   private final IMetaDataListHolder modelLocator;
   private final IMetaDataListHolder unboundMetaData;

   public MetaDataNodeTest() throws PMDException
   {
      super();

      IParserNode ast = FileSetUtils.buildAst( getTestFiles().get( "cairngorm.BindableModelLocator.as" ) );
      modelLocator = NodeFactory.createPackage( ast ).getClassNode();
      ast = FileSetUtils.buildAst( getTestFiles().get( "UnboundMetadata.as" ) );
      unboundMetaData = NodeFactory.createPackage( ast ).getClassNode();
   }

   @Test
   public void testEmbed() throws PMDException
   {
      final IParserNode titleNode = FileSetUtils.buildAst( getTestFiles().get( "Title.as" ) );

      final IMetaDataListHolder show = NodeFactory.createPackage( titleNode )
                                                  .getClassNode()
                                                  .getConstants()
                                                  .get( 0 );
      assertEquals( MetaData.EMBED.toString(),
                    show.getMetaData( MetaData.EMBED ).get( 0 ).getName() );

   }

   @Test
   public void testGetAttributeNames()
   {
      assertEquals( 2,
                    unboundMetaData.getMetaData( MetaData.EVENT ).get( 0 ).getAttributeNames().size() );
      assertEquals( "name",
                    unboundMetaData.getMetaData( MetaData.EVENT ).get( 0 ).getAttributeNames().get( 0 ) );
      assertEquals( "type",
                    unboundMetaData.getMetaData( MetaData.EVENT ).get( 0 ).getAttributeNames().get( 1 ) );
   }

   @Test
   public void testGetDefaultValue()
   {
      assertEquals( "",
                    modelLocator.getMetaData( MetaData.BINDABLE ).get( 0 ).getDefaultValue() );
      assertEquals( "name = \"dayChange\" , type = \'mx.events.StateChangeEvent\'",
                    unboundMetaData.getMetaData( MetaData.EVENT ).get( 0 ).getDefaultValue() );
   }

   @Test
   public void testGetMetaDataName()
   {
      assertEquals( MetaData.BINDABLE.toString(),
                    modelLocator.getMetaData( MetaData.BINDABLE ).get( 0 ).getName() );
      assertEquals( MetaData.EVENT.toString(),
                    unboundMetaData.getMetaData( MetaData.EVENT ).get( 0 ).getName() );
   }

   @Test
   public void testGetProperty()
   {
      assertEquals( 1,
                    unboundMetaData.getMetaData( MetaData.EVENT ).get( 0 ).getProperty( "name" ).length );
      assertEquals( "dayChange",
                    unboundMetaData.getMetaData( MetaData.EVENT ).get( 0 ).getProperty( "name" )[ 0 ] );
      assertEquals( 1,
                    unboundMetaData.getMetaData( MetaData.EVENT ).get( 0 ).getProperty( "type" ).length );
      assertEquals( "mx.events.StateChangeEvent",
                    unboundMetaData.getMetaData( MetaData.EVENT ).get( 0 ).getProperty( "type" )[ 0 ] );
   }

   @Test
   public void testGetPropertyAsList()
   {
      assertEquals( 1,
                    unboundMetaData.getMetaData( MetaData.EVENT ).get( 0 ).getPropertyAsList( "name" ).size() );
      assertEquals( "dayChange",
                    unboundMetaData.getMetaData( MetaData.EVENT )
                                   .get( 0 )
                                   .getPropertyAsList( "name" )
                                   .get( 0 ) );
      assertEquals( 1,
                    unboundMetaData.getMetaData( MetaData.EVENT ).get( 0 ).getPropertyAsList( "type" ).size() );
      assertEquals( "mx.events.StateChangeEvent",
                    unboundMetaData.getMetaData( MetaData.EVENT )
                                   .get( 0 )
                                   .getPropertyAsList( "type" )
                                   .get( 0 ) );
   }
}
