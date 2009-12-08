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
import java.util.List;
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
import com.adobe.ac.pmd.metrics.FunctionMetrics;
import com.adobe.ac.pmd.metrics.PackageMetrics;
import com.adobe.ac.pmd.metrics.ProjectMetrics;
import com.adobe.ac.pmd.metrics.TotalPackageMetrics;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.IPackage;

public class FlexMetrics extends AbstractMetrics
{
   private static final Logger LOGGER = Logger.getLogger( FlexMetrics.class.getName() );

   private static String getQualifiedName( final File sourceDirectory,
                                           final File file )
   {
      final String qualifiedName = file.getAbsolutePath()
                                       .replace( sourceDirectory.getAbsolutePath(),
                                                 "" )
                                       .replace( "/",
                                                 "." )
                                       .replace( "\\",
                                                 "." )
                                       .replace( ".as",
                                                 "" );

      if ( qualifiedName.charAt( 0 ) == '.' )
      {
         return qualifiedName.substring( 1 );
      }

      return qualifiedName;
   }

   public FlexMetrics( final File sourceDirectoryPath )
   {
      super( sourceDirectoryPath );
   }

   @Override
   public ProjectMetrics loadMetrics()
   {
      final ProjectMetrics metrics = new ProjectMetrics();
      final FlexFilter flexFilter = new FlexFilter();

      try
      {
         final Map< String, IFlexFile > files = com.adobe.ac.pmd.files.impl.FileUtils.computeFilesList( sourceDirectory,
                                                                                                        "" );
         final Map< String, IPackage > asts = FileSetUtils.computeAsts( files );

         for ( final File directory : nonEmptyDirectories )
         {
            final Collection< File > classesInPackage = FileUtils.listFiles( directory,
                                                                             flexFilter,
                                                                             false );

            if ( directory.isDirectory()
                  && !classesInPackage.isEmpty() )
            {
               final String packageFullName = getQualifiedName( sourceDirectory,
                                                                directory );
               int functionsInPackage = 0;
               int ncssInPackage = 0;

               for ( final File fileInPackage : classesInPackage )
               {
                  int ncssInClass = 0;
                  IClass classNode = null;
                  final IFlexFile file = com.adobe.ac.pmd.files.impl.FileUtils.create( fileInPackage,
                                                                                       sourceDirectory );
                  if ( asts.containsKey( file.getFullyQualifiedName() )
                        && asts.get( file.getFullyQualifiedName() ).getClassNode() != null )
                  {
                     classNode = asts.get( file.getFullyQualifiedName() ).getClassNode();
                     functionsInPackage += classNode.getFunctions().size();
                     ncssInClass = computeFunctionMetrics( metrics,
                                                           packageFullName,
                                                           classNode );
                     ncssInPackage += ncssInClass;
                  }
                  metrics.getClassMetrics().add( createClassMetrics( packageFullName,
                                                                     fileInPackage,
                                                                     ncssInClass,
                                                                     classNode ) );
               }
               metrics.getPackageMetrics().add( new PackageMetrics( ncssInPackage,// NOPMD
                                                                    functionsInPackage,
                                                                    classesInPackage.size(),
                                                                    packageFullName ) );
            }
         }
         metrics.setTotalPackages( getTotalPackages( metrics.getPackageMetrics() ) );
         metrics.setAverageFunctions( getAverageFunctions( metrics.getFunctionMetrics() ) );
         metrics.setAverageObjects( getAverageObjects( metrics.getClassMetrics() ) );
      }
      catch ( final PMDException e )
      {
         LOGGER.warning( e.getMessage() );
      }

      return metrics;
   }

   private int computeFunctionMetrics( final ProjectMetrics metrics,
                                       final String packageFullName,
                                       final IClass classNode )
   {
      int ncssInClass = 0;
      for ( final IFunction function : classNode.getFunctions() )
      {
         ncssInClass += function.getStatementNbInBody();
         metrics.getFunctionMetrics()
                .add( new FunctionMetrics( function.getStatementNbInBody(), // NOPMD
                                           function.getName(),
                                           packageFullName.compareTo( "" ) == 0 ? classNode.getName()
                                                                               : packageFullName
                                                                                     + "."
                                                                                     + classNode.getName(),
                                           function.getCyclomaticComplexity() ) );
      }
      return ncssInClass;
   }

   private ClassMetrics createClassMetrics( final String packageFullName,
                                            final File fileInPackage,
                                            final int ncssInClass,
                                            final IClass classNode )
   {
      final int averageClassComplexity = classNode == null ? 0
                                                          : ( int ) Math.round( classNode.getAverageCyclomaticComplexity() );
      final ClassMetrics classMetrics = new ClassMetrics( ncssInClass, // NOPMD
                                                          classNode == null ? 0
                                                                           : classNode.getFunctions().size(),
                                                          fileInPackage.getName().replace( ".as",
                                                                                           "" ),
                                                          packageFullName,
                                                          averageClassComplexity );
      return classMetrics;
   }

   private AverageFunctionMetrics getAverageFunctions( final List< FunctionMetrics > functionMetrics )
   {
      int nonCommentStatement = 0;

      for ( final FunctionMetrics metrics : functionMetrics )
      {
         nonCommentStatement += metrics.getNonCommentStatements();
      }

      return new AverageFunctionMetrics( nonCommentStatement, functionMetrics.size() );
   }

   private AverageClassMetrics getAverageObjects( final List< ClassMetrics > classMetrics )
   {
      int nonCommentStatement = 0;
      int functions = 0;

      for ( final ClassMetrics metrics : classMetrics )
      {
         nonCommentStatement += metrics.getNonCommentStatements();
         functions += metrics.getFunctions();
      }
      return new AverageClassMetrics( nonCommentStatement, functions, classMetrics.size() );
   }

   private TotalPackageMetrics getTotalPackages( final List< PackageMetrics > packageMetrics )
   {
      int nonCommentStatement = 0;
      int functions = 0;
      int classes = 0;

      for ( final PackageMetrics metrics : packageMetrics )
      {
         nonCommentStatement += metrics.getNonCommentStatements();
         functions += metrics.getFunctions();
         classes += metrics.getClasses();
      }
      return new TotalPackageMetrics( nonCommentStatement, functions, classes );
   }
}