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
   private static final String       NAME_DAY_CHANGE_EVENT_NAME_EVENT = "name = \"dayChange\" ,"
                                                                            + " type = \"DefaultNameEvent\"";
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
   public void testGetMetaDataName()
   {
      assertEquals( MetaData.BINDABLE.toString(),
                    modelLocator.getMetaData( MetaData.BINDABLE ).get( 0 ).getName() );
      assertEquals( MetaData.EVENT.toString(),
                    unboundMetaData.getMetaData( MetaData.EVENT ).get( 0 ).getName() );
   }

   @Test
   public void testGetMetaDataParameter()
   {
      assertEquals( "",
                    modelLocator.getMetaData( MetaData.BINDABLE ).get( 0 ).getParameter() );
      assertEquals( NAME_DAY_CHANGE_EVENT_NAME_EVENT,
                    unboundMetaData.getMetaData( MetaData.EVENT ).get( 0 ).getParameter() );
   }
}
