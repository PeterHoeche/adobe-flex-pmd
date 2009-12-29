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
import java.util.List;

public class TotalPackageMetrics implements IMetrics
{
   public static TotalPackageMetrics create( final List< PackageMetrics > packageMetrics )
   {
      int nonCommentStatement = 0;
      int functions = 0;
      int classes = 0;
      int asDocs = 0;
      int multipleLineComments = 0;

      for ( final PackageMetrics metrics : packageMetrics )
      {
         nonCommentStatement += metrics.getNonCommentStatements();
         functions += metrics.getFunctions();
         classes += metrics.getClasses();
         asDocs += metrics.getAsDocs();
         multipleLineComments += metrics.getMultiLineComments();
      }
      return new TotalPackageMetrics( nonCommentStatement, functions, classes, asDocs, multipleLineComments );
   }

   private final int totalAsDocs;
   private final int totalClasses;
   private final int totalFunctions;
   private final int totalMultiLineComment;
   private final int totalStatements;

   public TotalPackageMetrics( final int totalStatementsToBeSet,
                               final int totalFunctionsToBeSet,
                               final int totalClassesToBeSet,
                               final int totalAsDocsToBeSet,
                               final int totalMultiLineCommentToBeSet )
   {
      super();

      totalStatements = totalStatementsToBeSet;
      totalFunctions = totalFunctionsToBeSet;
      totalClasses = totalClassesToBeSet;
      totalAsDocs = totalAsDocsToBeSet;
      totalMultiLineComment = totalMultiLineCommentToBeSet;
   }

   public String getContreteXml()
   {
      return new StringBuffer().append( MessageFormat.format( "<total>"
                                                                    + "<classes>{0}</classes>"
                                                                    + "<functions>{1}</functions>"
                                                                    + "<ncss>{2}</ncss>"
                                                                    + "<javadocs>{3}</javadocs>"
                                                                    + "<javadoc_lines>0</javadoc_lines>"
                                                                    + "<single_comment_lines>0</single_comment_lines>"
                                                                    + "<multi_comment_lines>{4}</multi_comment_lines>"
                                                                    + "</total>",
                                                              String.valueOf( totalClasses ),
                                                              String.valueOf( totalFunctions ),
                                                              String.valueOf( totalStatements ),
                                                              String.valueOf( totalAsDocs ),
                                                              String.valueOf( totalMultiLineComment ) ) )
                               .toString();
   }

   public int getTotalAsDocs()
   {
      return totalAsDocs;
   }

   public int getTotalClasses()
   {
      return totalClasses;
   }

   public int getTotalFunctions()
   {
      return totalFunctions;
   }

   public int getTotalMultiLineComment()
   {
      return totalMultiLineComment;
   }

   public int getTotalStatements()
   {
      return totalStatements;
   }
}
