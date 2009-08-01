package com.adobe.ac.pmd.maven;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.codehaus.doxia.site.renderer.SiteRenderer;

abstract class AbstractFlexPmdMojo extends AbstractMavenReport
{
   protected static final Logger LOGGER      = Logger.getLogger( AbstractFlexPmdMojo.class.getName() );

   private static final String   OUTPUT_NAME = "flexpmd";

   protected static ResourceBundle getBundle( final Locale locale )
   {
      return ResourceBundle.getBundle( "flexPmd",
                                       locale,
                                       FlexPmdReportMojo.class.getClassLoader() ); // NOPMD
   }

   /**
    * Location of the file.
    * 
    * @parameter
    */
   private final String excludePackage = "";

   /**
    * Location of the file.
    * 
    * @parameter expression="${project.build.directory}"
    * @required
    */
   private File         outputDirectory;

   /**
    * @parameter expression="${project}"
    * @required
    * @readonly
    */
   private MavenProject project;

   /**
    * Location of the file.
    * 
    * @parameter
    */
   private File         ruleSet;

   /**
    * @parameter 
    *            expression="${component.org.codehaus.doxia.site.renderer.SiteRenderer}"
    * @required
    * @readonly
    */
   private SiteRenderer siteRenderer;

   /**
    * Specifies the location of the source files to be used.
    * 
    * @parameter expression="${project.build.sourceDirectory}"
    * @required
    * @readonly
    */
   private File         sourceDirectory;

   public AbstractFlexPmdMojo()
   {
      super();

   }

   public AbstractFlexPmdMojo( final File outputDirectoryToBeSet,
                               final MavenProject projectToBeSet,
                               final File ruleSetToBeSet,
                               final File sourceDirectoryToBeSet )
   {
      super();

      outputDirectory = outputDirectoryToBeSet;
      project = projectToBeSet;
      ruleSet = ruleSetToBeSet;
      sourceDirectory = sourceDirectoryToBeSet;
   }

   public final String getDescription( final Locale locale )
   {
      return getBundle( locale ).getString( "report.flexPmd.description" );
   }

   public final String getName( final Locale locale )
   {
      return getBundle( locale ).getString( "report.flexPmd.name" );
   }

   public final String getOutputName()
   {
      return OUTPUT_NAME;
   }

   protected final String getExcludePackage()
   {
      return excludePackage;
   }

   @Override
   protected final String getOutputDirectory()
   {
      return outputDirectory.getAbsolutePath();
   }

   protected final File getOutputDirectoryFile()
   {
      return outputDirectory;
   }

   @Override
   protected final MavenProject getProject()
   {
      return project;
   }

   protected final File getRuleSet()
   {
      return ruleSet;
   }

   @Override
   protected final SiteRenderer getSiteRenderer()
   {
      return siteRenderer;
   }

   protected final File getSourceDirectory()
   {
      return sourceDirectory;
   }

   protected final void setSiteRenderer( final SiteRenderer siteRendererToBeSet ) // NO_UCD
   {
      this.siteRenderer = siteRendererToBeSet;
   }
}