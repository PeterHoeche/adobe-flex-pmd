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
import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.engines.AbstractFlexPmdEngine;
import com.adobe.ac.pmd.engines.FlexPMDFormat;
import com.adobe.ac.pmd.files.IFlexFile;

class FlexPmdHtmlEngine extends AbstractFlexPmdEngine
{
   private final boolean        aggregate;
   private final ResourceBundle bundle;
   private final MavenProject   project;
   private final Sink           sink;

   protected FlexPmdHtmlEngine( final Sink sinkToBeSet,
                                final ResourceBundle bundleToBeSet,
                                final boolean aggregateToBeSet,
                                final MavenProject projectToBeSet,
                                final File sourceDirectoryToBeSet,
                                final File outputDirectoryToBeSet,
                                final String packageToExcludeToBeSet )
   {
      super( sourceDirectoryToBeSet, outputDirectoryToBeSet, packageToExcludeToBeSet );

      sink = sinkToBeSet;
      bundle = bundleToBeSet;
      aggregate = aggregateToBeSet;
      project = projectToBeSet;
   }

   @Override
   protected final void writeReport( final FlexPmdViolations pmd ) throws PMDException
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

      for ( final IFlexFile file : pmd.getViolations().keySet() )
      {
         final File javaFile = new File( file.getFilePath() ); // NOPMD
         final List< IFlexViolation > violations = pmd.getViolations().get( file );

         reportSink.beginFile( javaFile,
                               new PmdFileInfo( project, javaFile.getParentFile(), "" ) ); // NOPMD
         ruleContext.setSourceCodeFilename( javaFile.getAbsolutePath() );

         for ( final IFlexViolation violation : violations )
         {
            report.addRuleViolation( violation );
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
                                + "/" + FlexPMDFormat.HTML.toString() ) ),
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
