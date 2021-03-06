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
package de.bokelberg.flex.parser;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.adobe.ac.pmd.files.impl.FileUtils;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestAS3Parser extends AbstractAs3ParserTest
{
   @Test
   public void testBuildAst() throws IOException,
                             URISyntaxException,
                             TokenException
   {
      asp.buildAst( getClass().getResource( "/examples/unformatted/IContext.as" ).toURI().getPath() );
      asp.buildAst( getClass().getResource( "/examples/FlexPMD115.as" ).toURI().getPath() );
      asp.buildAst( getClass().getResource( "/examples/JPEGEncoder.as" ).toURI().getPath() );
      asp.buildAst( getClass().getResource( "/examples/JPEGEncoder2.as" ).toURI().getPath() );
      asp.buildAst( getClass().getResource( "/examples/FisheyeBase.as" ).toURI().getPath() );
      asp.buildAst( getClass().getResource( "/examples/FlexPMD98.as" ).toURI().getPath() );
      asp.buildAst( getClass().getResource( "/examples/FlexPMD195.as" ).toURI().getPath() );
      final String titlePath = getClass().getResource( "/examples/unformatted/Title.as" ).toURI().getPath();

      asp.buildAst( titlePath );
      asp.buildAst( titlePath,
                    FileUtils.readLines( new File( titlePath ) ) );
   }

   @Test
   public void testBuildAst_AS2() throws IOException,
                                 URISyntaxException,
                                 TokenException
   {
      asp.buildAst( getClass().getResource( "/examples/toAS2/src/fw/data/request/ResultListener.as" )
                              .toURI()
                              .getPath() );

      asp.buildAst( getClass().getResource( "/examples/toAS2/src/epg/StateExit_AS2.as" ).toURI().getPath() );
   }

   @Test
   public void testBuildAst2() throws IOException,
                              TokenException,
                              URISyntaxException
   {
      final IParserNode flexPmd62 = asp.buildAst( getClass().getResource( "/examples/FlexPMD62.as" )
                                                            .toURI()
                                                            .getPath() );

      assertEquals( "com.test.testy.ui.components",
                    flexPmd62.getChild( 0 ).getChild( 0 ).getStringValue() );

   }
}
