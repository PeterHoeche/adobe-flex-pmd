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

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;

public final class FunctionMetrics extends AbstractNamedMetrics
{
   static FunctionMetrics create( final String packageFullName,
                                  final IClass classNode,
                                  final IFunction function,
                                  final int asDocs,
                                  final int multipleDoc )
   {
      return new FunctionMetrics( function.getStatementNbInBody(), // NOPMD
                                  function.getName(),
                                  packageFullName.compareTo( "" ) == 0 ? classNode.getName()
                                                                      : packageFullName
                                                                            + "." + classNode.getName(),
                                  function.getCyclomaticComplexity(),
                                  asDocs,
                                  multipleDoc );
   }

   private FunctionMetrics( final int nonCommentStatements,
                            final String name,
                            final String packageName,
                            final int ccn,
                            final int asDocs,
                            final int multiLineCommentsToBeSet )
   {
      super( nonCommentStatements, name, packageName, ccn, asDocs, multiLineCommentsToBeSet );
   }

   public String getContreteXml()
   {
      return "";
   }

   @Override
   public String getFullName()
   {
      return getPackageName().compareTo( "" ) == 0 ? getName()
                                                  : getPackageName()
                                                        + "::" + getName();
   }

   @Override
   public String getMetricsName()
   {
      return "function";
   }

   @Override
   protected int getNcss()
   {
      return 1;
   }
}
