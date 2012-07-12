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

import com.adobe.ac.pmd.nodes.IAsDocHolder;
import com.adobe.ac.pmd.nodes.ICommentHolder;
import com.adobe.ac.pmd.parser.IParserNode;

public final class MetricUtils
{
   public static int computeAsDocs( final IAsDocHolder attribute )
   {
      return attribute.getAsDoc() == null ? 0
                                         : computeNbOfLines( attribute.getAsDoc().getStringValue() );
   }

   public static int computeMultiLineComments( final ICommentHolder commentHolder )
   {
      int lines = 0;

      for ( final IParserNode comment : commentHolder.getMultiLinesComment() )
      {
         lines += comment.getStringValue() == null ? 0
                                                  : MetricUtils.computeNbOfLines( comment.getStringValue() );
      }
      return lines;
   }

   public static int computeNbOfLines( final String lines )
   {
      return lines.split( "\\n" ).length;
   }

   public static String getQualifiedName( final File sourceDirectory,
                                          final File file )
   {
      final String qualifiedName = file.getAbsolutePath().replace( sourceDirectory.getAbsolutePath(),
                                                                   "" ).replace( "/",
                                                                                 "." ).replace( "\\",
                                                                                                "." ).trim();

      if ( qualifiedName.length() > 0
            && qualifiedName.charAt( 0 ) == '.' )
      {
         return qualifiedName.substring( 1 );
      }

      return qualifiedName;
   }

   private MetricUtils()
   {
   }
}
