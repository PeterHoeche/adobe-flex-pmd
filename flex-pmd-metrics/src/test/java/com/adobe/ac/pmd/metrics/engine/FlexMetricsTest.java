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
package com.adobe.ac.pmd.metrics.engine;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.dom4j.DocumentException;
import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.metrics.ProjectMetrics;

public class FlexMetricsTest extends FlexPmdTestBase
{
   private final FlexMetrics    flexMetrics;
   private final ProjectMetrics projectMetrics;

   public FlexMetricsTest()
   {
      super();

      flexMetrics = new FlexMetrics( getTestDirectory() );
      projectMetrics = flexMetrics.loadMetrics();
   }

   @Test
   public void execute() throws DocumentException,
                        IOException
   {
      final File outputFile = new File( "target/javancss.xml" );

      flexMetrics.execute( outputFile );

      assertTrue( outputFile.exists() );
   }

   @Test
   public void loadAverageMetrics()
   {
      assertEquals( 5,
                    Math.round( projectMetrics.getAverageFunctions().getAverageStatements() ) );
      assertEquals( 2,
                    Math.round( projectMetrics.getAverageObjects().getAverageFunctions() ) );
      assertEquals( 13,
                    Math.round( projectMetrics.getAverageObjects().getAverageStatements() ) );
      assertEquals( 2,
                    Math.round( projectMetrics.getAverageObjects().getAverageDocs() ) );
      assertEquals( 2,
                    Math.round( projectMetrics.getAverageObjects().getAverageMultipleComments() + 0.95 ) );
   }

   @Test
   public void loadClassMetrics()
   {
      assertEquals( 93,
                    projectMetrics.getClasses().size() );
      assertEquals( 0,
                    projectMetrics.getClasses().get( 3 ).getFunctions() );
      assertEquals( "bug.FlexPMD60",
                    projectMetrics.getClasses().get( 7 ).getFullName() );
      assertEquals( 7,
                    projectMetrics.getClasses().get( 7 ).getMultiLineComments() );
      assertEquals( "bug.FlexPMD61",
                    projectMetrics.getClasses().get( 8 ).getFullName() );
      assertEquals( 3,
                    projectMetrics.getClasses().get( 8 ).getFunctions() );
      assertEquals( 9,
                    projectMetrics.getClasses().get( 8 ).getNonCommentStatements() );
      assertEquals( "cairngorm.FatController",
                    projectMetrics.getClasses().get( 14 ).getFullName() );
      assertEquals( 3,
                    projectMetrics.getClasses().get( 14 ).getAsDocs() );
   }

   @Test
   public void loadFunctionMetrics()
   {
      assertEquals( 228,
                    projectMetrics.getFunctions().size() );
      assertEquals( "TestEvent",
                    projectMetrics.getFunctions().get( 15 ).getName() );
      assertEquals( 3,
                    projectMetrics.getFunctions().get( 15 ).getNonCommentStatements() );
      assertEquals( "clone",
                    projectMetrics.getFunctions().get( 16 ).getName() );
      assertEquals( 2,
                    projectMetrics.getFunctions().get( 16 ).getNonCommentStatements() );
      assertEquals( "BugDemo",
                    projectMetrics.getFunctions().get( 19 ).getName() );
      assertEquals( 10,
                    projectMetrics.getFunctions().get( 19 ).getNonCommentStatements() );
   }

   @Test
   public void loadPackageMetrics()
   {
      assertEquals( 26,
                    projectMetrics.getPackages().size() );
      assertEquals( "",
                    projectMetrics.getPackages()
                                  .get( projectMetrics.getPackages().size() - 1 )
                                  .getPackageName() );
      assertEquals( 11,
                    projectMetrics.getPackages().get( 1 ).getClasses() );
      assertEquals( "bug",
                    projectMetrics.getPackages().get( 1 ).getFullName() );
      assertEquals( 15,
                    projectMetrics.getPackages().get( 1 ).getFunctions() );
      assertEquals( 54,
                    projectMetrics.getPackages().get( 1 ).getNonCommentStatements() );
      assertEquals( "bug",
                    projectMetrics.getPackages().get( 1 ).getPackageName() );
   }

   @Test
   public void loadTotalPackageMetrics()
   {
      assertEquals( 93,
                    projectMetrics.getTotalPackages().getTotalClasses() );
      assertEquals( 228,
                    projectMetrics.getTotalPackages().getTotalFunctions() );
      assertEquals( 1220,
                    projectMetrics.getTotalPackages().getTotalStatements() );
      assertEquals( 227,
                    projectMetrics.getTotalPackages().getTotalAsDocs() );
      assertEquals( 94,
                    projectMetrics.getTotalPackages().getTotalMultiLineComment() );
   }

   @Test
   public void testBug157()
   {
      assertEquals( "org.as3commons.concurrency.thread",
                    projectMetrics.getPackageMetrics().get( 23 ).getFullName() );

   }
}
