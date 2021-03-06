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
package com.adobe.ac.pmd.metrics;

import java.io.File;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IClass;

public final class ClassMetrics extends AbstractNamedMetrics
{
   public static ClassMetrics create( final String packageFullName,
                                      final File fileInPackage,
                                      final InternalFunctionMetrics functionMetrics,
                                      final IClass classNode,
                                      final IFlexFile file,
                                      final double mxmlFactor )
   {
      final int average = classNode == null ? 0
                                           : ( int ) Math.round( classNode.getAverageCyclomaticComplexity() );
      final int asDocs = ( classNode == null
            || classNode.getAsDoc() == null ? 0
                                           : MetricUtils.computeNbOfLines( classNode.getAsDoc()
                                                                                    .getStringValue() ) )
            + ( functionMetrics == null ? 0
                                       : functionMetrics.getAsDocsInClass() );
      final int multiLineComments = ( classNode == null ? 0
                                                       : MetricUtils.computeMultiLineComments( classNode ) )
            + ( functionMetrics == null ? 0
                                       : functionMetrics.getMultipleLineCommentInClass() );
      final int nonCommentStatements = computeStatements( functionMetrics,
                                                          file,
                                                          mxmlFactor );
      return new ClassMetrics( nonCommentStatements, // NOPMD
                               classNode == null ? 0
                                                : classNode.getFunctions().size(),
                               fileInPackage.getName().replace( ".as",
                                                                "" ).replace( ".mxml",
                                                                              "" ),
                               packageFullName,
                               average,
                               asDocs,
                               multiLineComments,
                               classNode );
   }

   private static int computeStatements( final InternalFunctionMetrics functionMetrics,
                                         final IFlexFile file,
                                         final double mxmlFactor )
   {
      int stts = functionMetrics == null ? 0
                                        : functionMetrics.getNcssInClass();
      if ( file.isMxml() )
      {
         stts += file.getLinesNb()
               * mxmlFactor;
      }
      return stts;
   }

   private final IClass classNode;
   private final int    functions;

   private ClassMetrics( final int nonCommentStatements,
                         final int functionsToBeSet,
                         final String name,
                         final String packageName,
                         final int ccn,
                         final int asDocs,
                         final int multiLineCommentsToBeSet,
                         final IClass classNodeToBeSet )
   {
      super( nonCommentStatements, name, packageName, ccn, asDocs, multiLineCommentsToBeSet );

      functions = functionsToBeSet;
      this.classNode = classNodeToBeSet;
   }

   public String getContreteXml()
   {
      return "<functions>"
            + functions + "</functions>";
   }

   @Override
   public String getFullName()
   {
      return getPackageName().compareTo( "" ) == 0 ? getName()
                                                  : getPackageName()
                                                        + "." + getName();
   }

   public int getFunctions()
   {
      return functions;
   }

   @Override
   public String getMetricsName()
   {
      return "object";
   }

   @Override
   protected int getNcss()
   {
      if ( classNode == null )
      {
         return 1;
      }
      return 1
            + classNode.getAttributes().size() + classNode.getConstants().size() + functions;
   }
}
