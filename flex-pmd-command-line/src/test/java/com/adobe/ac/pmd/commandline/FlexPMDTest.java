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
package com.adobe.ac.pmd.commandline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import net.sourceforge.pmd.PMDException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.adobe.ac.pmd.CommandLineOptions;
import com.adobe.ac.pmd.FlexPmdTestBase;
import com.martiansoftware.jsap.JSAPException;

public class FlexPMDTest extends FlexPmdTestBase
{
   @Test
   public void testAreCommandLineOptionsCorrect() throws FileNotFoundException,
                                                 JSAPException,
                                                 PMDException,
                                                 URISyntaxException
   {
      assertFalse( FlexPMD.areCommandLineOptionsCorrect( new String[]
      {} ) );

      assertFalse( FlexPMD.areCommandLineOptionsCorrect( new String[]
      { "-y",
                  "sourceDirectory",
                  "-p",
                  "outPutDirectory" } ) );

      assertTrue( FlexPMD.areCommandLineOptionsCorrect( new String[]
      { "-s",
                  "sourceDirectory",
                  "-o",
                  "target",
                  "-r",
                  "valid.xml" } ) );

      assertTrue( FlexPMD.areCommandLineOptionsCorrect( new String[]
      { "-s",
                  "sourceDirectory",
                  "-o",
                  "target" } ) );
   }

   @Test
   public void testFlexPMD114() throws JSAPException,
                               PMDException,
                               URISyntaxException,
                               IOException,
                               DocumentException
   {
      final String[] args = new String[]
      { "-s",
                  getTestDirectory().getAbsolutePath()
                        + File.separatorChar + "flexpmd114",
                  "-o",
                  new File( "target/test2" ).getAbsolutePath() };

      FlexPMD.startFlexPMD( args );

      assertEquals( 3,
                    loadDocument( new File( "target/test2/pmd.xml" ) ).selectNodes( "//pmd/file" ).size() );
   }

   @Test
   public void testFlexPMD88() throws JSAPException,
                              PMDException,
                              URISyntaxException,
                              IOException,
                              DocumentException
   {
      final String[] args = new String[]
      { "-s",
                  getTestDirectory().getAbsolutePath()
                        + File.separatorChar + "bug" + File.separatorChar + "FlexPMD88.as",
                  "-o",
                  new File( "target/test3" ).getAbsolutePath() };

      FlexPMD.startFlexPMD( args );

      assertEquals( 21,
                    loadDocument( new File( "target/test3/pmd.xml" ) ).selectNodes( "//pmd/file[1]/violation" )
                                                                      .size() );
   }

   @Test
   public void testGetCommandLineValue() throws JSAPException
   {
      FlexPMD.areCommandLineOptionsCorrect( new String[]
      { "-s",
                  "sourceDirectory",
                  "-o",
                  "target",
                  "-r",
                  "valid.xml" } );

      assertEquals( "sourceDirectory",
                    FlexPMD.getParameterValue( CommandLineOptions.SOURCE_DIRECTORY ) );
      assertEquals( "target",
                    FlexPMD.getParameterValue( CommandLineOptions.OUTPUT ) );
      assertEquals( "valid.xml",
                    FlexPMD.getParameterValue( CommandLineOptions.RULE_SET ) );
   }

   @Test
   public void testStartFlexPMD() throws JSAPException,
                                 PMDException,
                                 URISyntaxException,
                                 IOException
   {
      final String[] args = new String[]
      { "-s",
                  getTestDirectory().getAbsolutePath(),
                  "-o",
                  new File( "target/test" ).getAbsolutePath(),
                  "--excludePackage",
                  "cairngorm." };

      FlexPMD.startFlexPMD( args );
   }

   @Test
   public void testStartFlexPMDOnAFile() throws JSAPException,
                                        PMDException,
                                        URISyntaxException,
                                        IOException
   {
      final String filePath = getTestFiles().get( "AbstractRowData.as" ).getFilePath();

      final String[] args = new String[]
      { "-s",
                  filePath,
                  "-o",
                  new File( "target/test" ).getAbsolutePath(),
                  "--excludePackage",
                  "cairngorm." };

      FlexPMD.startFlexPMD( args );
   }

   @Test
   public void testStartFlexPMDOnSeveralFolders() throws JSAPException,
                                                 PMDException,
                                                 URISyntaxException,
                                                 IOException
   {
      final String[] args = new String[]
      { "-s",
                  new File( "target/test/bug" ).getAbsolutePath()
                        + "," + new File( "target/test/cairngorm" ).getAbsolutePath(),
                  "-o",
                  new File( "target/test" ).getAbsolutePath(), };

      FlexPMD.startFlexPMD( args );
   }

   private Document loadDocument( final File outputFile ) throws DocumentException
   {
      return new SAXReader().read( outputFile );
   }
}
