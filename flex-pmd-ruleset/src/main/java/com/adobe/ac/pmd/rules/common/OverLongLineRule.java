/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.rules.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adobe.ac.pmd.Violation;
import com.adobe.ac.pmd.files.AbstractFlexFile;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.AbstractMaximizedFlexRule;
import com.adobe.ac.pmd.rules.core.IThresholdedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class OverLongLineRule extends AbstractMaximizedFlexRule implements IThresholdedRule
{
   private int currentLine;

   public int getActualValue()
   {
      return currentLine;
   }

   public int getDefaultThreshold()
   {
      return 120;
   }

   public boolean isConcernedByTheGivenFile( final AbstractFlexFile file )
   {
      return true;
   }

   @Override
   final public List< Violation > processFileBody( final IPackage rootNode,
                                                   final AbstractFlexFile file,
                                                   final Map< String, AbstractFlexFile > files )
   {
      final List< Violation > violations = new ArrayList< Violation >();

      if ( isConcernedByTheGivenFile( file ) )
      {
         for ( int i = 0; i < file.getLines().size(); i++ )
         {
            final String line = file.getLines().get( i );

            if ( !line.trim().startsWith( "import" )
                  && line.length() >= getThreshold() )
            {
               currentLine = line.length();
               final ViolationPosition position = new ViolationPosition( i + 1, i + 1, 0, line.length() );

               addViolation( violations,
                             file,
                             position );
            }
         }
      }
      return violations;
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.INFO;
   }
}
