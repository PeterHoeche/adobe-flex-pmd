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
package com.adobe.ac.pmd.engines;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.TestCase;
import net.sourceforge.pmd.PMDException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.adobe.ac.pmd.FlexPmdViolations;

public abstract class AbstractTestFlexPmdEngine extends TestCase
{
   static protected final String OUTPUT_DIRECTORY_URL = "target/report/";

   protected int                 violationsFound      = 0;

   public AbstractTestFlexPmdEngine( final String name )
   {
      super( name );
   }

   @Test
   public void testExecuteReport() throws PMDException,
                                  SAXException,
                                  URISyntaxException,
                                  IOException
   {
      final AbstractFlexPmdEngine engine = getFlexPmdEngine();
      final URL sourceDirectoryResource = getClass().getResource( "/test" );

      assertNotNull( "Source directory is not found as a resource",
                     sourceDirectoryResource );

      final File sourceDirectory = new File( sourceDirectoryResource.toURI().getPath() );
      final URL ruleSetUrl = getClass().getResource( "/com/adobe/ac/pmd/rulesets/all_flex.xml" );

      assertNotNull( "RuleSet has not been found",
                     ruleSetUrl );

      assertNotNull( "RuleSet has not been found",
                     ruleSetUrl.toURI() );

      assertNotNull( "RuleSet has not been found",
                     ruleSetUrl.toURI().getPath() );

      final File outputDirectory = new File( OUTPUT_DIRECTORY_URL );
      final File ruleSetFile = new File( ruleSetUrl.toURI().getPath() );

      violationsFound = engine.executeReport( sourceDirectory,
                                              outputDirectory,
                                              ruleSetFile,
                                              new FlexPmdViolations() );

      assertEquals( "Number of violations found is not correct",
                    272,
                    violationsFound );
   }

   protected abstract AbstractFlexPmdEngine getFlexPmdEngine();
}