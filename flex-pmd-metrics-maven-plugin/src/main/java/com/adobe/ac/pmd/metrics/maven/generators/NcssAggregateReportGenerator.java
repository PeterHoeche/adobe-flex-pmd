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
package com.adobe.ac.pmd.metrics.maven.generators;

/*
 * Copyright 2004-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.doxia.sink.Sink;
import org.dom4j.Document;
import org.dom4j.Node;

import com.adobe.ac.pmd.metrics.maven.utils.ModuleReport;

/**
 * Generates the JavaNCSS aggregate report.
 * 
 * @author <a href="mperham AT gmail.com">Mike Perham</a>
 * @version $Id: NcssAggregateReportGenerator.java 3286 2007-02-08 20:18:51Z
 *          jeanlaurent $
 */
public class NcssAggregateReportGenerator extends NcssReportGeneratorBase
{

   /**
    * @param getSink() the getSink() which will be used for reporting.
    * @param bundle the correct RessourceBundle to be used for reporting.
    * @param log
    */
   public NcssAggregateReportGenerator( final Sink sink,
                                        final ResourceBundle bundle,
                                        final Log log )
   {
      super( sink, bundle, log );
   }

   /**
    * Generates the JavaNCSS report.
    * 
    * @param locale the Locale used for this report.
    * @param moduleReports the javancss raw reports to aggregate, List of
    *           ModuleReport.
    * @param lineThreshold the maximum number of lines to keep in major reports.
    */
   public void doReport( final Locale locale,
                         final List< ModuleReport > moduleReports,
                         final int lineThreshold )
   {
      // HEADER
      getSink().head();
      getSink().title();
      getSink().text( getResourceBundle().getString( "report.javancss.title" ) );
      getSink().title_();
      getSink().head_();
      // BODY
      getSink().body();
      doIntro();
      // packages
      startSection( "report.javancss.module.link",
                    "report.javancss.module.title" );
      doModuleAnalysis( moduleReports );
      endSection();
      getSink().body_();
      getSink().close();
   }

   private void doIntro()
   {
      getSink().section1();
      getSink().sectionTitle1();
      getSink().text( getResourceBundle().getString( "report.javancss.main.title" ) );
      getSink().sectionTitle1_();
      getSink().paragraph();
      getSink().text( getResourceBundle().getString( "report.javancss.main.text" )
            + " " );
      getSink().lineBreak();
      getSink().link( "http://www.kclee.de/clemens/java/javancss/" );
      getSink().text( "JavaNCSS web site." );
      getSink().link_();
      getSink().paragraph_();
      getSink().section1_();
   }

   private void doModuleAnalysis( final List< ModuleReport > reports )
   {
      getSink().table();
      getSink().tableRow();
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.module" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.packages" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.classetotal" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.functiontotal" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.ncsstotal" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.javadoc" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.javadoc_line" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.single_comment" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.multi_comment" ) );
      getSink().tableRow_();

      int packages = 0;
      int classes = 0;
      int methods = 0;
      int ncss = 0;
      int javadocs = 0;
      int jdlines = 0;
      int single = 0;
      int multi = 0;
      for ( final Iterator< ModuleReport > it = reports.iterator(); it.hasNext(); )
      {
         final ModuleReport report = ( ModuleReport ) it.next();
         final Document document = report.getJavancssDocument();
         getSink().tableRow();
         getLog().debug( "Aggregating "
               + report.getModule().getArtifactId() );
         tableCellHelper( report.getModule().getArtifactId() );
         final int packageSize = document.selectNodes( "//javancss/packages/package" ).size();
         packages += packageSize;
         tableCellHelper( String.valueOf( packageSize ) );

         final Node node = document.selectSingleNode( "//javancss/packages/total" );

         final String classSize = node.valueOf( "classes" );
         tableCellHelper( classSize );
         classes += Integer.parseInt( classSize );

         final String methodSize = node.valueOf( "functions" );
         tableCellHelper( methodSize );
         methods += Integer.parseInt( methodSize );

         final String ncssSize = node.valueOf( "ncss" );
         tableCellHelper( ncssSize );
         ncss += Integer.parseInt( ncssSize );

         final String javadocSize = node.valueOf( "javadocs" );
         tableCellHelper( javadocSize );
         javadocs += Integer.parseInt( javadocSize );

         final String jdlineSize = node.valueOf( "javadoc_lines" );
         tableCellHelper( jdlineSize );
         jdlines += Integer.parseInt( jdlineSize );

         final String singleSize = node.valueOf( "single_comment_lines" );
         tableCellHelper( singleSize );
         single += Integer.parseInt( singleSize );

         final String multiSize = node.valueOf( "multi_comment_lines" );
         tableCellHelper( multiSize );
         multi += Integer.parseInt( multiSize );

         getSink().tableRow_();
      }

      // Totals row
      getSink().tableRow();
      tableCellHelper( getResourceBundle().getString( "report.javancss.header.totals" ) );
      tableCellHelper( String.valueOf( packages ) );
      tableCellHelper( String.valueOf( classes ) );
      tableCellHelper( String.valueOf( methods ) );
      tableCellHelper( String.valueOf( ncss ) );
      tableCellHelper( String.valueOf( javadocs ) );
      tableCellHelper( String.valueOf( jdlines ) );
      tableCellHelper( String.valueOf( single ) );
      tableCellHelper( String.valueOf( multi ) );
      getSink().tableRow_();

      getSink().table_();
   }

}
