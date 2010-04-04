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
package com.adobe.ac.cpd.maven;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.cpd.CPD;
import net.sourceforge.pmd.cpd.FileReporter;
import net.sourceforge.pmd.cpd.Renderer;
import net.sourceforge.pmd.cpd.ReportException;
import net.sourceforge.pmd.cpd.XMLRenderer;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.site.renderer.SiteRenderer;

import com.adobe.ac.cpd.FlexLanguage;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.files.impl.FileUtils;

/**
 * @author xagnetti
 * @goal check
 * @phase verify
 */
public class FlexCpdMojo extends AbstractMavenReport
{
   private static final String OUTPUT_NAME = "cpd";

   protected static ResourceBundle getBundle( final Locale locale )
   {
      return ResourceBundle.getBundle( "flexPmd",
                                       locale,
                                       FlexCpdMojo.class.getClassLoader() ); // NOPMD
   }

   private final String encoding = System.getProperty( "file.encoding" );

   /**
    * Location of the file.
    * 
    * @parameter expression="25"
    * @required
    */
   private int          minimumTokenCount;

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

   public FlexCpdMojo()
   {
      super();
   }

   public FlexCpdMojo( final File outputDirectoryToBeSet,
                       final File sourceDirectoryToBeSet,
                       final MavenProject projectToBeSet )
   {
      super();
      outputDirectory = outputDirectoryToBeSet;
      sourceDirectory = sourceDirectoryToBeSet;
      project = projectToBeSet;
      minimumTokenCount = 25;
   }

   public String getDescription( final Locale locale )
   {
      return getBundle( locale ).getString( "report.flexCpd.description" );
   }

   public final String getName( final Locale locale )
   {
      return getBundle( locale ).getString( "report.flexCpd.name" );
   }

   public final String getOutputName()
   {
      return OUTPUT_NAME;
   }

   void setSiteRenderer( final SiteRenderer site )
   {
      siteRenderer = site;
   }

   @Override
   protected void executeReport( final Locale locale ) throws MavenReportException
   {
      final CPD cpd = new CPD( minimumTokenCount, new FlexLanguage() );

      try
      {
         final Map< String, IFlexFile > files = FileUtils.computeFilesList( sourceDirectory,
                                                                            null,
                                                                            "",
                                                                            null );

         for ( final Entry< String, IFlexFile > fileEntry : files.entrySet() )
         {
            cpd.add( new File( fileEntry.getValue().getFilePath() ) ); // NOPMD
         }

         cpd.go();

         report( cpd );
      }
      catch ( final PMDException e )
      {
         throw new MavenReportException( "", e );
      }
      catch ( final IOException e )
      {
         throw new MavenReportException( "", e );
      }
      catch ( final ReportException e )
      {
         throw new MavenReportException( "", e );
      }
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

   private void report( final CPD cpd ) throws ReportException
   {
      final Renderer renderer = new XMLRenderer( encoding );
      final FileReporter reporter = new FileReporter( new File( outputDirectory.getAbsolutePath(),
                                                                OUTPUT_NAME
                                                                      + ".xml" ), encoding );
      reporter.report( renderer.render( cpd.getMatches() ) );
   }

}
