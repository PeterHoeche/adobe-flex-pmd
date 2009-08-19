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
package com.adobe.ac.pmd.rules.maintanability;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class AvoidHavingTwoClassWithTheSameNameRule extends AbstractFlexRule
{
   @Override
   public final boolean isConcernedByTheCurrentFile()
   {
      return true;
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   @Override
   protected final List< IFlexViolation > findViolationsInCurrentFile()
   {
      final List< IFlexViolation > violations = new ArrayList< IFlexViolation >();
      final ViolationPosition position = new ViolationPosition( -1, -1 );

      for ( final Entry< String, IFlexFile > currentFileEntry : getFilesInSourcePath().entrySet() )
      {
         final IFlexFile currentFile = currentFileEntry.getValue();

         if ( classNamesEqualsIgnoringExtension( currentFile.getClassName(),
                                                 getCurrentFile().getClassName() )
               && !currentFile.getPackageName().equals( getCurrentFile().getPackageName() ) )
         {
            addViolation( violations,
                          position );
            break;
         }
      }
      return violations;
   }

   private boolean classNamesEqualsIgnoringExtension( final String firstClassName,
                                                      final String secondClassName )
   {
      return extractingClassName( firstClassName ).equals( extractingClassName( secondClassName ) );
   }

   private String extractingClassName( final String name )
   {
      final String[] split = name.split( "\\." );

      return split[ split.length - 2 ];
   }
}
