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
package com.adobe.ac.pmd.maven;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.site.renderer.SiteRenderer;

import com.adobe.ac.pmd.FlexPmdParameters;
import com.adobe.ac.pmd.FlexPmdViolations;
import com.adobe.ac.pmd.engines.AbstractFlexPmdEngine;
import com.adobe.ac.pmd.engines.FlexPmdXmlEngine;
import com.adobe.ac.pmd.engines.PmdEngineUtils;

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
   private final String excludePackage;

   /**
    * Build fails if an violation error occurs.
    * 
    * @parameter
    */
   private boolean      failOnError;

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
    * @parameter expression="${flexpmd.ruleset}"
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
      excludePackage = "";
   }

   public AbstractFlexPmdMojo( final MavenProject projectToBeSet,
                               final FlexPmdParameters parameters )
   {
      super();

      project = projectToBeSet;
      outputDirectory = parameters.getOutputDirectory();
      ruleSet = parameters.getRuleSet();
      sourceDirectory = parameters.getSourceDirectory();
      failOnError = parameters.isFailOnError();
      excludePackage = parameters.getExcludePackage();
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

   @Override
   protected final void executeReport( final Locale locale ) throws MavenReportException
   {
      LOGGER.info( "FlexPmdMojo starts" );
      try
      {
         final AbstractFlexPmdEngine engine = new FlexPmdXmlEngine( new FlexPmdParameters( excludePackage,
                                                                                           failOnError,
                                                                                           outputDirectory,
                                                                                           ruleSet,
                                                                                           sourceDirectory,
                                                                                           null ) );
         final FlexPmdViolations violations = new FlexPmdViolations();
         engine.executeReport( violations );

         onXmlReportExecuted( violations,
                              locale );
      }
      catch ( final Exception e )
      {
         throw new MavenReportException( "A system exception has been thrown", e );
      }
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

   protected void onXmlReportExecuted( final FlexPmdViolations violations,
                                       final Locale locale ) throws MavenReportException
   {
      if ( failOnError )
      {
         final String message = PmdEngineUtils.findFirstViolationError( violations );

         if ( message.length() > 0 )
         {
            throw new MavenReportException( message );
         }
      }
   }

   protected final void setSiteRenderer( final SiteRenderer siteRendererToBeSet ) // NO_UCD
   {
      this.siteRenderer = siteRendererToBeSet;
   }
}