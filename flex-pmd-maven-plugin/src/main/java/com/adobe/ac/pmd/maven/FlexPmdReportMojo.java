package com.adobe.ac.pmd.maven;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;

import net.sourceforge.pmd.PMDException;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.MavenReportException;

import com.adobe.ac.pmd.FlexPmdViolations;
import com.adobe.ac.pmd.engines.FlexPmdXmlEngine;

/**
 * @author xagnetti
 * @goal report
 * @phase site
 */
public class FlexPmdReportMojo extends AbstractFlexPmdMojo
{
   /**
    * Specifies the location of the source files to be used.
    * 
    * @parameter default-value="false"
    * @required
    * @readonly
    */
   private boolean aggregate;

   public FlexPmdReportMojo()
   {
      super();
   }

   public FlexPmdReportMojo( final File outputDirectoryToBeSet,
                             final MavenProject projectToBeSet,
                             final File ruleSetToBeSet,
                             final File sourceDirectoryToBeSet )
   {
      super( outputDirectoryToBeSet, projectToBeSet, ruleSetToBeSet, sourceDirectoryToBeSet );
   }

   @Override
   protected final void executeReport( final Locale locale ) throws MavenReportException
   {
      LOGGER.info( "FlexPmdReportMojo starts" );

      try
      {
         final FlexPmdViolations pmd = new FlexPmdViolations();

         final FlexPmdXmlEngine engine = new FlexPmdXmlEngine( getSourceDirectory(),
                                                               getOutputDirectoryFile(),
                                                               getExcludePackage() );

         engine.executeReport( pmd,
                               getRuleSet() );

         final FlexPmdHtmlEngine flexPmdHtmlEngine = new FlexPmdHtmlEngine( getSink(),
                                                                            getBundle( locale ),
                                                                            aggregate,
                                                                            getProject(),
                                                                            getSourceDirectory(),
                                                                            getOutputDirectoryFile(),
                                                                            getExcludePackage() );

         flexPmdHtmlEngine.executeReport( pmd,
                                          getRuleSet() );
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
