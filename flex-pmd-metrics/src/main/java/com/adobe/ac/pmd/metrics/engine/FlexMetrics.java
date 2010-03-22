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

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.sourceforge.pmd.PMDException;

import com.adobe.ac.ncss.filters.FlexFilter;
import com.adobe.ac.ncss.utils.FileUtils;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.metrics.AverageClassMetrics;
import com.adobe.ac.pmd.metrics.AverageFunctionMetrics;
import com.adobe.ac.pmd.metrics.ClassMetrics;
import com.adobe.ac.pmd.metrics.InternalFunctionMetrics;
import com.adobe.ac.pmd.metrics.MetricUtils;
import com.adobe.ac.pmd.metrics.PackageMetrics;
import com.adobe.ac.pmd.metrics.ProjectMetrics;
import com.adobe.ac.pmd.metrics.TotalPackageMetrics;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IPackage;

public final class FlexMetrics extends AbstractMetrics
{
   private static final FlexFilter       FLEX_FILTER = new FlexFilter();
   private static final Logger           LOGGER      = Logger.getLogger( FlexMetrics.class.getName() );

   private final Map< String, IPackage > asts;

   public FlexMetrics( final File sourceDirectoryPath )
   {
      super( sourceDirectoryPath );

      asts = initAst();
   }

   @Override
   public ProjectMetrics loadMetrics()
   {
      final ProjectMetrics metrics = new ProjectMetrics();

      for ( final File directory : getNonEmptyDirectories() )
      {
         final Collection< File > classesInPackage = FileUtils.listFiles( directory,
                                                                          FLEX_FILTER,
                                                                          false );

         if ( directory.isDirectory()
               && !classesInPackage.isEmpty() )
         {
            final String packageFullName = MetricUtils.getQualifiedName( getSourceDirectory(),
                                                                         directory );
            int functionsInPackage = 0;
            int ncssInPackage = 0;
            int asDocsInPackage = 0;
            int multipleLineCommentInPackage = 0;
            final int importsInPackage = 0;

            for ( final File fileInPackage : classesInPackage )
            {
               IClass classNode = null;
               InternalFunctionMetrics functionMetrics = null;
               final IFlexFile file = com.adobe.ac.pmd.files.impl.FileUtils.create( fileInPackage,
                                                                                    getSourceDirectory() );
               if ( asts.containsKey( file.getFullyQualifiedName() )
                     && asts.get( file.getFullyQualifiedName() ).getClassNode() != null )
               {
                  classNode = asts.get( file.getFullyQualifiedName() ).getClassNode();
                  functionsInPackage += classNode.getFunctions().size();
                  functionMetrics = InternalFunctionMetrics.create( metrics,
                                                                    packageFullName,
                                                                    classNode );
                  asDocsInPackage += functionMetrics.getAsDocsInClass();
                  multipleLineCommentInPackage += functionMetrics.getMultipleLineCommentInClass();
                  ncssInPackage += functionMetrics.getNcssInClass();
               }
               final ClassMetrics classMetrics = ClassMetrics.create( packageFullName,
                                                                      fileInPackage,
                                                                      functionMetrics,
                                                                      classNode );
               asDocsInPackage += classMetrics.getAsDocs();
               multipleLineCommentInPackage += classMetrics.getMultiLineComments();
               metrics.getClassMetrics().add( classMetrics );
            }
            metrics.getPackageMetrics().add( PackageMetrics.create( classesInPackage,
                                                                    packageFullName,
                                                                    functionsInPackage,
                                                                    ncssInPackage,
                                                                    asDocsInPackage,
                                                                    multipleLineCommentInPackage,
                                                                    importsInPackage ) );
         }
      }
      setFinalMetrics( metrics );

      return metrics;
   }

   private Map< String, IPackage > initAst()
   {
      Map< String, IPackage > result = new HashMap< String, IPackage >();
      try
      {
         result = FileSetUtils.computeAsts( com.adobe.ac.pmd.files.impl.FileUtils.computeFilesList( getSourceDirectory(),
                                                                                                    null,
                                                                                                    "",
                                                                                                    null ) );
      }
      catch ( final PMDException e )
      {
         LOGGER.warning( e.getMessage() );
      }
      return result;
   }

   private void setFinalMetrics( final ProjectMetrics metrics )
   {
      metrics.setTotalPackages( TotalPackageMetrics.create( metrics.getPackageMetrics() ) );
      metrics.setAverageFunctions( AverageFunctionMetrics.create( metrics.getFunctionMetrics(),
                                                                  metrics.getTotalPackages() ) );
      metrics.setAverageObjects( AverageClassMetrics.create( metrics.getClassMetrics(),
                                                             metrics.getTotalPackages() ) );
   }
}