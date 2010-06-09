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
package com.adobe.ac.pmd.rules.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adobe.ac.pmd.IFlexViolation;

/**
 * @author xagnetti
 */
public abstract class AbstractRegexpBasedRule extends AbstractFlexRule
{
   private Pattern pattern;

   /**
    * 
    */
   public AbstractRegexpBasedRule()
   {
      super();
      compilePattern();
   }

   /**
    * 
    */
   public final void compilePattern()
   {
      pattern = Pattern.compile( getRegexp() );
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractFlexRule#findViolationsInCurrentFile()
    */
   @Override
   public final List< IFlexViolation > findViolationsInCurrentFile()
   {
      final List< IFlexViolation > violations = new ArrayList< IFlexViolation >();

      if ( "".compareTo( getRegexp() ) != 0 )
      {
         for ( int i = 1; i <= getCurrentFile().getLinesNb(); i++ )
         {
            final String line = getCurrentFile().getLineAt( i );

            if ( isCurrentLineConcerned( line )
                  && doesCurrentLineMacthes( line ) && isViolationDetectedOnThisMatchingLine( line )
                  && !line.contains( "/*" ) && !line.contains( "//" ) )
            {
               addViolation( violations,
                             ViolationPosition.create( i,
                                                       i,
                                                       0,
                                                       line.length() ) );
            }
         }
      }
      return violations;
   }

   /**
    * @param line
    * @return
    */
   final boolean doesCurrentLineMacthes( final String line )
   {
      return getMatcher( line ).matches();
   }

   /**
    * @param line
    * @return
    */
   protected final Matcher getMatcher( final String line )
   {
      final Matcher matcher = pattern.matcher( line );

      return matcher;
   }

   /**
    * @return
    */
   protected abstract String getRegexp();

   protected boolean isCurrentLineConcerned( final String line )
   {
      return true;
   }

   /**
    * @param line
    * @return
    */
   protected abstract boolean isViolationDetectedOnThisMatchingLine( final String line );
}