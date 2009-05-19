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
package com.adobe.ac.pmd.maven;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

import net.sourceforge.pmd.PMDException;

import org.apache.maven.reporting.sink.SinkFactory;
import org.codehaus.doxia.sink.Sink;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.adobe.ac.pmd.engines.AbstractFlexPmdEngine;
import com.adobe.ac.pmd.engines.AbstractTestFlexPmdEngine;

public class FlexPmdHtmlEngineTest extends AbstractTestFlexPmdEngine
{
   public FlexPmdHtmlEngineTest( final String name )
   {
      super( name );
   }

   @Test
   @Override
   public void testExecuteReport() throws PMDException,
                                  SAXException,
                                  URISyntaxException,
                                  IOException
   {
      // super.testExecuteReport();
      //
      // final org.xml.sax.XMLReader reader = new ParserAdapter(
      // new XMLReaderAdapter() );
      //
      // try
      // {
      // reader.parse( new InputSource( new FileInputStream( new File(
      // OUTPUT_DIRECTORY_URL
      // + FlexPmdHtmlEngine.PMD_HTML ) ) ) );
      // }
      // catch ( final SAXParseException e )
      // {
      // fail( e.getMessage() );
      // }
   }

   @Override
   protected AbstractFlexPmdEngine getFlexPmdEngine()
   {
      try
      {
         final Sink sink = new SinkFactory().getSink( OUTPUT_DIRECTORY_URL
               + FlexPmdHtmlEngine.PMD_HTML );

         return new FlexPmdHtmlEngine( sink, ResourceBundle.getBundle( "flexPmd" ), false, null );
      }
      catch ( final Exception exception )
      {
         exception.printStackTrace();

         return null;
      }
   }
}
