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

import java.text.MessageFormat;

public abstract class AbstractPackagedMetrics implements IMetrics
{
   private final int    ccn;
   private final int    nonCommentStatements;
   private final String packageName;

   protected AbstractPackagedMetrics( final int nonCommentStatementsToBeSet,
                                      final String packageNameToBeSet,
                                      final int ccnToBeSet )
   {
      super();
      nonCommentStatements = nonCommentStatementsToBeSet;
      packageName = packageNameToBeSet;
      ccn = ccnToBeSet;
   }

   abstract public String getFullName();

   abstract public String getMetricsName();

   public int getNonCommentStatements()
   {
      return nonCommentStatements;
   }

   public String getPackageName()
   {
      return packageName;
   }

   public String toXmlString()
   {
      return new StringBuffer().append( MessageFormat.format( "<{0}><name>{1}</name><ccn>{2}</ccn><ncss>{3}</ncss>"
                                                                    + "<javadoc_lines>0</javadoc_lines>"
                                                                    + "<single_comment_lines>0</single_comment_lines>"
                                                                    + "<multi_comment_lines>0</multi_comment_lines>"
                                                                    + "{4}</{5}>",
                                                              getMetricsName(),
                                                              getFullName(),
                                                              String.valueOf( ccn ),
                                                              String.valueOf( nonCommentStatements ),
                                                              getContreteXml(),
                                                              getMetricsName() ) )
                               .toString();
   }
}
