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
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.ResourceBundle;

import net.sourceforge.pmd.PMDException;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.site.renderer.SiteRenderer;

import com.adobe.ac.pmd.FlexPmdViolations;
import com.adobe.ac.pmd.engines.FlexPmdHtmlEngine;
import com.adobe.ac.pmd.engines.FlexPmdXmlEngine;

/**
 * @goal check
 * @phase verify
 */
public class FlexPmdMojo
      extends AbstractMavenReport
{
   private static final String OUTPUT_NAME = "flexPmd";

   private static ResourceBundle getBundle(
         final Locale locale )
   {
      return ResourceBundle.getBundle(
            "flexPmd", locale, FlexPmdMojo.class.getClassLoader() );
   }

   /**
    * Location of the file.
    *
    * @parameter expression="${project.build.directory}"
    * @required
    */
   private File outputDirectory;

   /**
    * @parameter expression="${project}"
    * @required
    * @readonly
    */
   private final MavenProject project = null; // NOPMD by xagnetti on 4/20/09 10:46 PM

   /**
    * Location of the file.
    *
    * @parameter
    * @required
    */
   private final File ruleSet = null; // NOPMD by xagnetti on 4/20/09 10:46 PM

   /**
    * @parameter
    *            expression="${component.org.codehaus.doxia.site.renderer.SiteRenderer}"
    * @required
    * @readonly
    */
   private final SiteRenderer siteRenderer = null; // NOPMD by xagnetti on 4/20/09 10:46 PM

   /**
    * Specifies the location of the source files to be used.
    *
    * @parameter expression="${project.build.sourceDirectory}"
    * @required
    * @readonly
    */
   private File sourceDirectory;

   public final String getDescription(
         final Locale locale )
   {
      return getBundle(
            locale ).getString(
            "report.flexPmd.description" );
   }

   public final String getName(
         final Locale locale )
   {
      return getBundle(
            locale ).getString(
            "report.flexPmd.name" );
   }

   public final String getOutputName()
   {
      return OUTPUT_NAME;
   }

   public void setOutputDirectory(
         final File outputDirectory )
   {
      this.outputDirectory = outputDirectory;
   }

   public void setSourceDirectory(
         final File sourceDirectory )
   {
      this.sourceDirectory = sourceDirectory;
   }

   @Override
   protected void executeReport(
         final Locale locale ) throws MavenReportException
   {
      try
      {
         final FlexPmdViolations pmd = new FlexPmdViolations();
         
         new FlexPmdXmlEngine().executeReport(
               sourceDirectory, outputDirectory, ruleSet, pmd  );
         new FlexPmdHtmlEngine().executeReport(
               sourceDirectory, new File( outputDirectory + "/site" ), ruleSet, pmd );
      }
      catch ( final PMDException e )
      {
         throw new MavenReportException(
               "An error has been thrown while executing the PMD report", e );
      }
      catch ( final FileNotFoundException e )
      {
         throw new MavenReportException( "The Ruleset url has not been found",
               e );
      }
   }

   @Override
   protected final String getOutputDirectory()
   {
      return outputDirectory.getAbsolutePath();
   }

   @Override
   protected final MavenProject getProject()
   {
      return project;
   }

   @Override
   protected final SiteRenderer getSiteRenderer()
   {
      return siteRenderer;
   }
}
