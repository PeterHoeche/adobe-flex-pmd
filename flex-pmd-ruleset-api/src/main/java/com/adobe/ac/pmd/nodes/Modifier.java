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
package com.adobe.ac.pmd.nodes;

import com.adobe.ac.pmd.parser.KeyWords;

/**
 * @author xagnetti
 */
public enum Modifier
{
   DYNAMIC, FINAL, INTERNAL, OVERRIDE, PRIVATE, PROTECTED, PUBLIC, STATIC;

   /**
    * @param name
    * @return
    */
   public static Modifier create( final String name )
   {
      Modifier modifier = null;
      if ( KeyWords.PUBLIC.toString().equals( name ) )
      {
         modifier = Modifier.PUBLIC;
      }
      else if ( KeyWords.PRIVATE.toString().equals( name ) )
      {
         modifier = Modifier.PRIVATE;
      }
      else if ( KeyWords.PROTECTED.toString().equals( name ) )
      {
         modifier = Modifier.PROTECTED;
      }
      else if ( KeyWords.INTERNAL.toString().equals( name ) )
      {
         modifier = Modifier.INTERNAL;
      }
      else if ( KeyWords.DYNAMIC.toString().equals( name ) )
      {
         modifier = Modifier.DYNAMIC;
      }
      else if ( KeyWords.OVERRIDE.toString().equals( name ) )
      {
         modifier = Modifier.OVERRIDE;
      }
      else if ( KeyWords.STATIC.toString().equals( name ) )
      {
         modifier = Modifier.STATIC;
      }
      else if ( KeyWords.FINAL.toString().equals( name ) )
      {
         modifier = Modifier.FINAL;
      }
      return modifier;
   }

   private Modifier()
   {
   }
}
