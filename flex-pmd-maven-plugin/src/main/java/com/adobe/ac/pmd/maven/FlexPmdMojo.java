package com.adobe.ac.pmd.maven;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;

import net.sourceforge.pmd.PMDException;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.MavenReportException;

import com.adobe.ac.pmd.engines.FlexPmdXmlEngine;

/**
 * @goal check
 * @phase verify
 */
public class FlexPmdMojo extends AbstractFlexPmdMojo // NO_UCD
{
   public FlexPmdMojo()
   {
      super();
   }

   public FlexPmdMojo( final File outputDirectoryToBeSet,
                       final MavenProject projectToBeSet,
                       final File ruleSetToBeSet,
                       final File sourceDirectoryToBeSet )
   {
      super( outputDirectoryToBeSet, projectToBeSet, ruleSetToBeSet, sourceDirectoryToBeSet );
   }

   @Override
   protected final void executeReport( final Locale locale ) throws MavenReportException
   {
      LOGGER.info( "FlexPmdMojo starts" );
      try
      {
         final FlexPmdXmlEngine engine = new FlexPmdXmlEngine( getSourceDirectory(),
                                                               getOutputDirectoryFile(),
                                                               getExcludePackage() );
         engine.executeReport( getRuleSet() );
      }
      catch ( final PMDException e )
      {
         throw new MavenReportException( "An error has been thrown while executing the PMD report", e );
      }
      catch ( final FileNotFoundException e )
      {
         throw new MavenReportException( "The Ruleset url has not been found", e );
      }
      catch ( final URISyntaxException e )
      {
         throw new MavenReportException( "The Ruleset url has not been found", e );
      }
      catch ( final IOException e )
      {
         throw new MavenReportException( "The Ruleset url has not been found", e );
      }
   }
}
