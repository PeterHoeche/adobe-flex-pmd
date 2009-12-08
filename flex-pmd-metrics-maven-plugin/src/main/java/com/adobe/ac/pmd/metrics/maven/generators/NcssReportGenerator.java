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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.doxia.sink.Sink;
import org.dom4j.Document;
import org.dom4j.Node;

import com.adobe.ac.pmd.metrics.maven.utils.NumericNodeComparator;

/**
 * Generates the javaNcss maven report.
 * 
 * @author <a href="jeanlaurentATgmail.com">Jean-Laurent de Morlhon</a>
 * @version $Id: NcssReportGenerator.java 3286 2007-02-08 20:18:51Z jeanlaurent
 *          $
 */
public class NcssReportGenerator extends NcssReportGeneratorBase
{
   private static final String CLASSES   = "classes";
   private static final String FUNCTIONS = "functions";
   private static final String NAME      = "name";
   private static final String NCSS      = "ncss";
   private int                 lineThreshold;

   /**
    * build a new NcssReportGenerator.
    * 
    * @param sink the sink which will be used for reporting.
    * @param bundle the correct RessourceBundle to be used for reporting.
    */
   public NcssReportGenerator( final Sink sink,
                               final ResourceBundle bundle,
                               final Log log )
   {
      super( sink, bundle, log );
   }

   /**
    * Generates the JavaNcss reports.
    * 
    * @param document the javaNcss raw report as an XML document.
    * @param lineThresholdToBeSet the maximum number of lines to keep in major
    *           reports.
    */
   public void doReport( final Document document,
                         final int lineThresholdToBeSet )
   {
      lineThreshold = lineThresholdToBeSet;
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
      startSection( "report.javancss.package.link",
                    "report.javancss.package.title" );
      doMainPackageAnalysis( document );
      doTotalPackageAnalysis( document );
      endSection();
      // Objects
      startSection( "report.javancss.object.link",
                    "report.javancss.object.title" );
      doTopObjectNcss( document );
      doTopObjectFunctions( document );
      doObjectAverage( document );
      endSection();
      // Functions
      startSection( "report.javancss.function.link",
                    "report.javancss.function.title" );
      doFunctionAnalysis( document );
      doFunctionAverage( document );
      endSection();
      // Explanation
      startSection( "report.javancss.explanation.link",
                    "report.javancss.explanation.title" );
      doExplanation();
      endSection();
      getSink().body_();
   }

   // sink helper to start a section
   @Override
   protected void startSection( final String link,
                                final String title )
   {
      super.startSection( link,
                          title );
      navigationBar();
   }

   private void doExplanation()
   {
      subtitleHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.title" ) );
      paragraphHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.paragraph1" ) );
      paragraphHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.paragraph2" ) );
      getSink().table();

      getSink().tableRow();
      headerCellHelper( "" );
      headerCellHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.table.examples" ) );
      getSink().tableRow_();

      getSink().tableRow();
      tableCellHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.table.package" ) );
      codeCellHelper( "package com.adobe.ac {}" );
      getSink().tableRow_();

      getSink().tableRow();
      tableCellHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.table.import" ) );
      codeCellHelper( "import java.awt.*;" );
      getSink().tableRow_();

      getSink().tableRow();
      tableCellHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.table.class" ) );
      getSink().tableCell();
      getSink().list();
      codeItemListHelper( "public class Foo {" );
      codeItemListHelper( "public class Foo extends Bla {" );
      getSink().list_();
      getSink().tableCell_();
      getSink().tableRow_();

      getSink().tableRow();
      tableCellHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.table.interface" ) );
      codeCellHelper( "public interface Able {" );
      getSink().tableRow_();

      getSink().tableRow();
      tableCellHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.table.field" ) );
      getSink().tableCell();
      getSink().list();
      codeItemListHelper( "var a : Number;" );
      codeItemListHelper( "var a : Number = 1;" );
      getSink().list_();
      getSink().tableCell_();
      getSink().tableRow_();

      getSink().tableRow();
      tableCellHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.table.method" ) );
      getSink().tableCell();
      getSink().list();
      codeItemListHelper( "public function cry() : void{}" );
      codeItemListHelper( "public function gib() : DeadResult {}" );
      getSink().list_();
      getSink().tableCell_();
      getSink().tableRow_();

      getSink().tableRow();
      tableCellHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.table.constructorD" ) );
      codeCellHelper( "public Foo() {" );
      getSink().tableRow_();

      getSink().tableRow();
      tableCellHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.table.constructorI" ) );
      getSink().tableCell();
      getSink().list();
      codeItemListHelper( "super();" );
      getSink().list_();
      getSink().tableCell_();
      getSink().tableRow_();

      getSink().tableRow();
      tableCellHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.table.statement" ) );
      getSink().tableCell();
      getSink().list();
      codeItemListHelper( "i = 0;" );
      codeItemListHelper( "if (ok)" );
      codeItemListHelper( "if (exit) {" );
      codeItemListHelper( "if (3 == 4);" );
      codeItemListHelper( "if (4 == 4) { ;" );
      codeItemListHelper( "} else {" );
      getSink().list_();
      getSink().tableCell_();
      getSink().tableRow_();
      getSink().table_();

      paragraphHelper( getResourceBundle().getString( "report.javancss.explanation.ncss.paragraph3" ) );

      // CCN Explanation
      subtitleHelper( getResourceBundle().getString( "report.javancss.explanation.ccn.title" ) );
      paragraphHelper( getResourceBundle().getString( "report.javancss.explanation.ccn.paragraph1" ) );
      paragraphHelper( getResourceBundle().getString( "report.javancss.explanation.ccn.paragraph2" ) );
      getSink().list();
      codeItemListHelper( "if" );
      codeItemListHelper( "for" );
      codeItemListHelper( "while" );
      codeItemListHelper( "case" );
      codeItemListHelper( "catch" );
      getSink().list_();
      paragraphHelper( getResourceBundle().getString( "report.javancss.explanation.ccn.paragraph3" ) );
      getSink().list();
      codeItemListHelper( "if" );
      codeItemListHelper( "for" );
      getSink().list_();
      paragraphHelper( getResourceBundle().getString( "report.javancss.explanation.ccn.paragraph4" ) );
      paragraphHelper( getResourceBundle().getString( "report.javancss.explanation.ccn.paragraph5" ) );
   }

   private void doFunctionAnalysis( final Document document )
   {
      subtitleHelper( getResourceBundle().getString( "report.javancss.top" )
            + " " + lineThreshold + " " + getResourceBundle().getString( "report.javancss.function.byncss" ) );
      getSink().paragraph();
      getSink().table();
      getSink().tableRow();
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.function" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.ncss" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.ccn" ) );
      getSink().tableRow_();
      final List list = document.selectNodes( "//javancss/functions/function" );
      Collections.sort( list,
                        new NumericNodeComparator( NCSS ) );
      final Iterator nodeIterator = list.iterator();
      int currentIndex = 0;
      while ( nodeIterator.hasNext()
            && currentIndex++ < lineThreshold )
      {
         final Node node = ( Node ) nodeIterator.next();
         getSink().tableRow();
         // getSink().tableCell();
         // getSink().tableCell_();
         tableCellHelper( node.valueOf( "name" ) );
         tableCellHelper( node.valueOf( NCSS ) );
         tableCellHelper( node.valueOf( "ccn" ) );
         getSink().tableRow_();
      }
      getSink().table_();
      getSink().paragraph_();
   }

   private void doFunctionAverage( final Document document )
   {
      subtitleHelper( getResourceBundle().getString( "report.javancss.averages" ) );
      getSink().paragraph();
      getSink().table();
      getSink().tableRow();
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.programncss" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.ncssaverage" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.ccnaverage" ) );
      getSink().tableRow_();
      final Node node = document.selectSingleNode( "//javancss/functions/function_averages" );
      final String totalNcss = document.selectSingleNode( "//javancss/functions/ncss" ).getText();
      getSink().tableRow();
      tableCellHelper( totalNcss );
      tableCellHelper( node.valueOf( NCSS ) );
      tableCellHelper( node.valueOf( "ccn" ) );
      getSink().tableRow_();
      getSink().table_();
      getSink().paragraph_();
   }

   private void doIntro()
   {
      getSink().section1();
      getSink().sectionTitle1();
      getSink().text( getResourceBundle().getString( "report.javancss.main.title" ) );
      getSink().sectionTitle1_();
      getSink().paragraph();
      navigationBar();
      getSink().text( getResourceBundle().getString( "report.javancss.main.text" )
            + " " );
      getSink().lineBreak();
      getSink().link( "http://www.kclee.de/clemens/java/javancss/" );
      getSink().text( "JavaNCSS web site." );
      getSink().link_();
      getSink().paragraph_();
      getSink().section1_();
   }

   private void doMainPackageAnalysis( final Document document )
   {
      subtitleHelper( getResourceBundle().getString( "report.javancss.package.text" ) );
      getSink().table();
      getSink().tableRow();
      // HEADER
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.package" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.classe" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.function" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.ncss" ) );
      getSink().tableRow_();
      // DATA
      final List list = document.selectNodes( "//javancss/packages/package" );
      Collections.sort( list,
                        new NumericNodeComparator( NCSS ) );
      final Iterator nodeIterator = list.iterator();
      while ( nodeIterator.hasNext() )
      {
         final Node node = ( Node ) nodeIterator.next();
         getSink().tableRow();
         tableCellHelper( node.valueOf( NAME ) );
         tableCellHelper( node.valueOf( CLASSES ) );
         tableCellHelper( node.valueOf( FUNCTIONS ) );
         tableCellHelper( node.valueOf( NCSS ) );
         getSink().tableRow_();
      }
      getSink().table_();
   }

   private void doObjectAverage( final Document document )
   {
      subtitleHelper( getResourceBundle().getString( "report.javancss.averages" ) );
      getSink().table();
      getSink().tableRow();
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.ncssaverage" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.classeaverage" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.functionaverage" ) );
      getSink().tableRow_();
      final Node node = document.selectSingleNode( "//javancss/objects/averages" );
      getSink().tableRow();
      tableCellHelper( node.valueOf( NCSS ) );
      tableCellHelper( node.valueOf( CLASSES ) );
      tableCellHelper( node.valueOf( FUNCTIONS ) );
      getSink().tableRow_();
      getSink().table_();
   }

   private void doTopObjectFunctions( final Document document )
   {
      subtitleHelper( getResourceBundle().getString( "report.javancss.top" )
            + " " + lineThreshold + " " + getResourceBundle().getString( "report.javancss.object.byfunction" ) );
      final List nodeList = document.selectNodes( "//javancss/objects/object" );
      Collections.sort( nodeList,
                        new NumericNodeComparator( FUNCTIONS ) );
      doTopObjectGeneric( nodeList );
   }

   // generic method called by doTopObjectFunctions & doTopObjectNCss
   private void doTopObjectGeneric( final List nodeList )
   {
      getSink().table();
      getSink().tableRow();
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.object" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.ncss" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.function" ) );
      getSink().tableRow_();
      final Iterator nodeIterator = nodeList.iterator();
      int currentIndex = 0;
      while ( nodeIterator.hasNext()
            && currentIndex++ < lineThreshold )
      {
         final Node node = ( Node ) nodeIterator.next();
         getSink().tableRow();
         tableCellHelper( node.valueOf( NAME ) );
         tableCellHelper( node.valueOf( NCSS ) );
         tableCellHelper( node.valueOf( FUNCTIONS ) );
         getSink().tableRow_();
      }
      getSink().table_();
   }

   private void doTopObjectNcss( final Document document )
   {
      subtitleHelper( getResourceBundle().getString( "report.javancss.top" )
            + " " + lineThreshold + " " + getResourceBundle().getString( "report.javancss.object.byncss" ) );
      final List nodeList = document.selectNodes( "//javancss/objects/object" );
      Collections.sort( nodeList,
                        new NumericNodeComparator( NCSS ) );
      doTopObjectGeneric( nodeList );
   }

   private void doTotalPackageAnalysis( final Document document )
   {
      getSink().table();
      getSink().tableRow();
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.classetotal" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.functiontotal" ) );
      headerCellHelper( getResourceBundle().getString( "report.javancss.header.ncsstotal" ) );
      getSink().tableRow_();
      final Node node = document.selectSingleNode( "//javancss/packages/total" );
      getSink().tableRow();
      tableCellHelper( node.valueOf( CLASSES ) );
      tableCellHelper( node.valueOf( FUNCTIONS ) );
      tableCellHelper( node.valueOf( NCSS ) );
      getSink().tableRow_();
      getSink().table_();
   }

   // print out the navigation bar
   private void navigationBar()
   {
      getSink().paragraph();
      getSink().text( "[ " );
      getSink().link( "#"
            + getResourceBundle().getString( "report.javancss.package.link" ) );
      getSink().text( getResourceBundle().getString( "report.javancss.package.link" ) );
      getSink().link_();
      getSink().text( " ] [ " );
      getSink().link( "#"
            + getResourceBundle().getString( "report.javancss.object.link" ) );
      getSink().text( getResourceBundle().getString( "report.javancss.object.link" ) );
      getSink().link_();
      getSink().text( " ] [ " );
      getSink().link( "#"
            + getResourceBundle().getString( "report.javancss.function.link" ) );
      getSink().text( getResourceBundle().getString( "report.javancss.function.link" ) );
      getSink().link_();
      getSink().text( " ] [ " );
      getSink().link( "#"
            + getResourceBundle().getString( "report.javancss.explanation.link" ) );
      getSink().text( getResourceBundle().getString( "report.javancss.explanation.link" ) );
      getSink().link_();
      getSink().text( " ]" );
      getSink().paragraph_();
   }

}
