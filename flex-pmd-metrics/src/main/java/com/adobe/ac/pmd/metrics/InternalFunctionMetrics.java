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
/**
 * 
 */
package com.adobe.ac.pmd.metrics;

import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.IMetaData;

public class InternalFunctionMetrics
{
   public static InternalFunctionMetrics create( final ProjectMetrics metrics,
                                                 final String packageFullName,
                                                 final IClass classNode )
   {
      int ncssInClass = 0;
      int asDocsInClass = 0;
      int multipleLineCommentInClass = 0;

      for ( final IFunction function : classNode.getFunctions() )
      {
         final int multipleDoc = MetricUtils.computeMultiLineComments( function );
         int asDocs = MetricUtils.computeAsDocs( function );

         ncssInClass += function.getStatementNbInBody();
         multipleLineCommentInClass += multipleDoc;

         for ( final IMetaData metaData : function.getAllMetaData() )
         {
            asDocs += MetricUtils.computeAsDocs( metaData );
         }

         asDocsInClass += asDocs;

         metrics.getFunctionMetrics().add( FunctionMetrics.create( packageFullName,
                                                                   classNode,
                                                                   function,
                                                                   asDocs,
                                                                   multipleDoc ) );
      }

      for ( final IAttribute attribute : classNode.getAttributes() )
      {
         asDocsInClass += MetricUtils.computeAsDocs( attribute );

         for ( final IMetaData metaData : attribute.getAllMetaData() )
         {
            asDocsInClass += MetricUtils.computeAsDocs( metaData );
         }
      }

      for ( final IMetaData metaData : classNode.getAllMetaData() )
      {
         asDocsInClass += MetricUtils.computeAsDocs( metaData );
      }
      return new InternalFunctionMetrics( ncssInClass, asDocsInClass, multipleLineCommentInClass );
   }

   private final int asDocsInClass;
   private final int multipleLineCommentInClass;
   private final int ncssInClass;

   public InternalFunctionMetrics( final int ncssInClassToBeSet,
                                   final int asDocsInClassToBeSet,
                                   final int multipleLineCommentInClassToBeSet )
   {
      ncssInClass = ncssInClassToBeSet;
      asDocsInClass = asDocsInClassToBeSet;
      multipleLineCommentInClass = multipleLineCommentInClassToBeSet;
   }

   public int getAsDocsInClass()
   {
      return asDocsInClass;
   }

   public int getMultipleLineCommentInClass()
   {
      return multipleLineCommentInClass;
   }

   public int getNcssInClass()
   {
      return ncssInClass;
   }
}