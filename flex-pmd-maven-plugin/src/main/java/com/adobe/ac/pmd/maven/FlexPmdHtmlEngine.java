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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.renderers.HTMLRenderer;

import org.apache.maven.plugin.pmd.PmdFileInfo;
import org.apache.maven.plugin.pmd.PmdReportListener;
import org.apache.maven.project.MavenProject;
import org.codehaus.doxia.sink.Sink;

import com.adobe.ac.pmd.FlexPmdViolations;
import com.adobe.ac.pmd.Violation;
import com.adobe.ac.pmd.engines.AbstractFlexPmdEngine;
import com.adobe.ac.pmd.files.AbstractFlexFile;

public class FlexPmdHtmlEngine extends AbstractFlexPmdEngine
{
   public static final String   PMD_HTML = "flexPmd.html";

   final private boolean        aggregate;
   final private ResourceBundle bundle;
   final private MavenProject   project;
   final private Sink           sink;

   public FlexPmdHtmlEngine( final Sink sinkToBeSet,
                             final ResourceBundle bundleToBeSet,
                             final boolean aggregateToBeSet,
                             final MavenProject projectToBeSet )
   {
      super();

      sink = sinkToBeSet;
      bundle = bundleToBeSet;
      aggregate = aggregateToBeSet;
      project = projectToBeSet;
   }

   @Override
   protected String getReportType()
   {
      return "html";
   }

   @Override
   protected void writeReport( final FlexPmdViolations pmd,
                               final File outputDirectory ) throws PMDException
   {
      writeReport( outputDirectory,
                   computeReport( pmd ) );
   }

   private Report computeReport( final FlexPmdViolations pmd )
   {
      final Report report = new Report();
      final RuleContext ruleContext = new RuleContext();
      final PmdReportListener reportSink = new PmdReportListener( sink, bundle, aggregate );

      report.addListener( reportSink );
      ruleContext.setReport( report );
      reportSink.beginDocument();
      report.start();

      for ( final AbstractFlexFile file : pmd.getViolations().keySet() )
      {
         final File javaFile = new File( file.getFilePath() );
         final List< Violation > violations = pmd.getViolations().get( file );

         reportSink.beginFile( javaFile,
                               new PmdFileInfo( project, javaFile.getParentFile(), "" ) );
         ruleContext.setSourceCodeFilename( javaFile.getAbsolutePath() );

         for ( final Violation violation : violations )
         {
            reportSink.ruleViolationAdded( violation );
         }
         reportSink.endFile( javaFile );
      }

      reportSink.endDocument();
      report.end();

      return report;
   }

   private void writeReport( final File outputDirectory,
                             final Report report ) throws PMDException
   {
      final HTMLRenderer renderer = new HTMLRenderer( "", "" );

      try
      {
         renderer.render( new FileWriter( new File( outputDirectory.getAbsolutePath()
                                + "/" + PMD_HTML ) ),
                          report );
         renderer.getWriter().flush();
         renderer.getWriter().close();
      }
      catch ( final IOException e )
      {
         throw new PMDException( "unable to write the HTML report", e );
      }
   }
}
