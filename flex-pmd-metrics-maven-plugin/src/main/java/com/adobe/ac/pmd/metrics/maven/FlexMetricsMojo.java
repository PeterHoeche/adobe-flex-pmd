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
package com.adobe.ac.pmd.metrics.maven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.site.renderer.SiteRenderer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.adobe.ac.pmd.metrics.maven.generators.NcssAggregateReportGenerator;
import com.adobe.ac.pmd.metrics.maven.generators.NcssReportGenerator;
import com.adobe.ac.pmd.metrics.maven.utils.ModuleReport;
import com.adobe.ac.pmd.metrics.maven.utils.NcssExecuter;

/**
 * @author xagnetti
 * @goal check
 */
public class FlexMetricsMojo extends AbstractMavenReport
{
   private static final String OUTPUT_NAME = "javancss";

   private static ResourceBundle getBundle( final Locale locale )
   {
      return ResourceBundle.getBundle( "flexMetrics",
                                       locale,
                                       FlexMetricsReportMojo.class.getClassLoader() ); // NOPMD
   }

   /**
    * CCN Limit, any code with a ccn greater than this number will generate a
    * violation
    * 
    * @parameter default-value="10"
    * @required
    */
   private int                  ccnLimit;

   /**
    * Whether to fail the build if the validation check fails.
    * 
    * @parameter default-value="false"
    * @required
    */
   private boolean              failOnViolation;

   /**
    * Specifies the maximum number of lines to take into account into the
    * reports.
    * 
    * @parameter default-value="5"
    * @required
    */
   private int                  lineThreshold;

   /**
    * ncss Limit, any code with a ncss greater than this number will generate a
    * violation
    * 
    * @parameter default-value="100"
    * @required
    */
   private int                  ncssLimit;

   /**
    * Specifies the directory where the HTML report will be generated.
    * 
    * @parameter expression="${project.reporting.outputDirectory}"
    * @required
    * @readonly
    */
   private File                 outputDirectory;

   /**
    * @parameter expression="${project}"
    * @required
    * @readonly
    */
   private MavenProject         project;

   /**
    * The projects in the reactor for aggregation report.
    * 
    * @parameter expression="${reactorProjects}"
    * @readonly
    */
   private List< MavenProject > reactorProjects;

   /**
    * @parameter 
    *            expression="${component.org.codehaus.doxia.site.renderer.SiteRenderer}"
    * @required
    * @readonly
    */
   private SiteRenderer         siteRenderer;

   /**
    * Specifies the location of the source files to be used.
    * 
    * @parameter expression="${project.build.sourceDirectory}"
    * @required
    * @readonly
    */
   private File                 sourceDirectory;

   /**
    * Name of the file holding the xml file generated by JavaNCSS
    * 
    * @parameter default-value="javancss-raw-report.xml"
    * @required
    */
   private String               tempFileName;

   /**
    * Specifies the directory where the XML report will be generated.
    * 
    * @parameter default-value="${project.build.directory}"
    * @required
    */
   private File                 xmlOutputDirectory;

   /**
    * @see org.apache.maven.reporting.MavenReport#canGenerateReport()
    */
   public boolean canGenerateReport()
   {
      return canGenerateSingleReport()
            || canGenerateAggregateReport();
   }

   @Override
   @SuppressWarnings("unchecked")
   public void executeReport( final Locale locale ) throws MavenReportException
   {
      generateReport( locale );
      final Set< String > ccnViolation = new HashSet< String >();
      final Set< String > ncssViolation = new HashSet< String >();
      try
      {
         final List< Node > methodList = loadDocument().selectNodes( "//javancss/functions/function" );

         for ( final Node node : methodList )
         {
            if ( Integer.valueOf( node.valueOf( "ccn" ) ) > ccnLimit )
            {
               ccnViolation.add( node.valueOf( "name" ) );
            }
            if ( Integer.valueOf( node.valueOf( "ncss" ) ) > ncssLimit )
            {
               ncssViolation.add( node.valueOf( "name" ) );
            }
         }
         reportViolation( "ccn",
                          ccnViolation,
                          ccnLimit );
         reportViolation( "ncss",
                          ncssViolation,
                          ncssLimit );
      }
      catch ( final MojoFailureException e )
      {
         throw new MavenReportException( e.getMessage(), e );
      }
   }

   /**
    * @see org.apache.maven.reporting.MavenReport#getDescription(java.util.Locale)
    */
   public String getDescription( final Locale locale )
   {
      return getBundle( locale ).getString( "report.ncss.description" );
   }

   /**
    * @see org.apache.maven.reporting.MavenReport#getName(java.util.Locale)
    */
   public String getName( final Locale locale )
   {
      return getBundle( locale ).getString( "report.ncss.name" );
   }

   /**
    * @see org.apache.maven.reporting.MavenReport#getOutputName()
    */
   public String getOutputName()
   {
      return OUTPUT_NAME;
   }

   public void setCcnLimit( final int ccnLimitToBeSet )
   {
      ccnLimit = ccnLimitToBeSet;
   }

   public void setFailOnViolation( final boolean failOnViolationToBeSet )
   {
      failOnViolation = failOnViolationToBeSet;
   }

   public void setNcssLimit( final int ncssLimitToBeSet )
   {
      ncssLimit = ncssLimitToBeSet;
   }

   public void setTempFileName( final String tempFileNameToBeSet )
   {
      tempFileName = tempFileNameToBeSet;
   }

   public void setXmlOutputDirectory( final File xmlOutputDirectoryToBeSet )
   {
      xmlOutputDirectory = xmlOutputDirectoryToBeSet;
   }

   /**
    * Build a path for the output filename.
    * 
    * @return A String representation of the output filename.
    */
   /* package */File buildOutputFile()
   {
      return new File( xmlOutputDirectory
            + File.separator + tempFileName );
   }

   @Override
   protected String getOutputDirectory()
   {
      return outputDirectory.getAbsolutePath();
   }

   @Override
   protected MavenProject getProject()
   {
      return project;
   }

   @Override
   protected SiteRenderer getSiteRenderer()
   {
      return siteRenderer;
   }

   private boolean canGenerateAggregateReport()
   {
      return !project.getModules().isEmpty();
   }

   private boolean canGenerateSingleReport()
   {
      return sourceDirectory != null
            && sourceDirectory.exists();
   }

   private void generateAggregateReport( final Locale locale ) throws MavenReportException
   {
      final String basedir = project.getBasedir().toString();
      final String output = xmlOutputDirectory.toString();
      if ( getLog().isDebugEnabled() )
      {
         getLog().debug( "basedir: "
               + basedir );
         getLog().debug( "output: "
               + output );
      }
      String relative = null;
      if ( output.startsWith( basedir ) )
      {
         relative = output.substring( basedir.length() + 1 );
      }
      else
      {
         getLog().error( "Unable to aggregate report because I can't "
               + "determine the relative location of the XML report" );
         return;
      }
      getLog().debug( "relative: "
            + relative );
      final List< ModuleReport > reports = new ArrayList< ModuleReport >();
      for ( final MavenProject mavenProject : reactorProjects )
      {
         final MavenProject child = mavenProject;
         final File xmlReport = new File( child.getBasedir() // NOPMD
               + File.separator + relative, tempFileName );
         if ( xmlReport.exists() )
         {
            reports.add( new ModuleReport( child, loadDocument( xmlReport ) ) ); // NOPMD
         }
         else
         {
            getLog().debug( "xml file not found: "
                  + xmlReport );
         }
      }
      getLog().debug( "Aggregating "
            + reports.size() + " JavaNCSS reports" );

      new NcssAggregateReportGenerator( getSink(), getBundle( locale ), getLog() ).doReport( locale,
                                                                                             reports,
                                                                                             lineThreshold );
   }

   private void generateReport( final Locale locale ) throws MavenReportException
   {
      if ( !canGenerateReport() )
      {
         throw new MavenReportException( "Cannot generate report " );
      }

      if ( canGenerateSingleReport() )
      {
         try
         {
            generateSingleReport( locale );
         }
         catch ( final DocumentException e )
         {
            throw new MavenReportException( e.getMessage(), e );
         }
         catch ( final IOException e )
         {
            throw new MavenReportException( e.getMessage(), e );
         }
      }
      if ( canGenerateAggregateReport() )
      {
         generateAggregateReport( locale );
      }
   }

   private void generateSingleReport( final Locale locale ) throws MavenReportException,
                                                           DocumentException,
                                                           IOException
   {
      if ( getLog().isDebugEnabled() )
      {
         getLog().debug( "Calling NCSSExecuter with src    : "
               + sourceDirectory );
         getLog().debug( "Calling NCSSExecuter with output : "
               + buildOutputFile() );
      }
      // run javaNCss and produce an temp xml file
      new NcssExecuter( sourceDirectory, buildOutputFile() ).execute();
      if ( !isTempReportGenerated() )
      {
         throw new MavenReportException( "Can't process temp ncss xml file." );
      }
      // parse the freshly generated file and write the report
      final NcssReportGenerator reportGenerator = new NcssReportGenerator( getSink(),
                                                                           getBundle( locale ),
                                                                           getLog() );
      reportGenerator.doReport( loadDocument(),
                                lineThreshold );
   }

   /**
    * Check that the expected temporary file generated by JavaNCSS exists.
    * 
    * @return <code>true</code> if the temporary report exists,
    *         <code>false</code> otherwise.
    */
   private boolean isTempReportGenerated()
   {
      return buildOutputFile().exists();
   }

   private Document loadDocument() throws MavenReportException
   {
      final File ncssXmlFile = new File( xmlOutputDirectory
            + File.separator + tempFileName );
      try
      {
         return new SAXReader().read( ncssXmlFile );
      }
      catch ( final DocumentException de )
      {
         throw new MavenReportException( "Can't read javancss xml output file : "
               + ncssXmlFile, de );
      }
   }

   /**
    * Load the xml file generated by javancss. It first tries to load it as is.
    * If this fails it tries to load it with the forceEncoding parameter which
    * defaults to the system property "file.encoding". If this latter fails, it
    * throws a MavenReportException.
    */
   private Document loadDocument( final File file ) throws MavenReportException
   {
      try
      {
         return loadDocument( file,
                              null );
      }
      catch ( final DocumentException ignored )
      {
         try
         {
            return loadDocument( file,
                                 System.getProperty( "file.encoding" ) );
         }
         catch ( final DocumentException de )
         {
            throw new MavenReportException( de.getMessage(), de );
         }
      }
   }

   private Document loadDocument( final File file,
                                  final String encoding ) throws DocumentException
   {
      final SAXReader saxReader = new SAXReader();
      if ( encoding != null )
      {
         saxReader.setEncoding( encoding );
         getLog().debug( "Loading xml file with encoding : "
               + encoding );
      }
      return saxReader.read( file );
   }

   private void reportViolation( final String statName,
                                 final Set< String > violationSet,
                                 final int limit ) throws MojoFailureException
   {
      getLog().debug( statName
            + " Violation = " + violationSet.size() );
      if ( !violationSet.isEmpty() )
      {
         final String violationString = "Your code has "
               + violationSet.size() + " method(s) with a " + statName + " greater than " + limit;
         getLog().warn( violationString );
         final Iterator< String > iterator = violationSet.iterator();
         while ( iterator.hasNext() )
         {
            getLog().warn( "    "
                  + iterator.next() );
         }
         if ( failOnViolation )
         {
            throw new MojoFailureException( violationString );
         }
      }
   }
}
