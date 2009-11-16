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
package com.adobe.ac.pmd.rules.unused;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adobe.ac.pmd.files.IAs3File;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UnusedPrivateMethodRule extends AbstractAstFlexRule
{
   private Map< String, IFunction > privateFunctions = null;

   @Override
   protected void findViolations( final IClass classNode )
   {
      fillPrivateFunctions( classNode.getFunctions() );

      findUnusedFunction( classNode.getBlock() );
      super.findViolations( classNode );

      addViolations();
   }

   @Override
   protected final void findViolations( final List< IFunction > functions )
   {
      super.findViolations( functions );

      for ( final IFunction function : functions )
      {
         findUnusedFunction( function.getBody() );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   private void addViolations()
   {
      final Set< Integer > ignoredLines = new HashSet< Integer >();

      for ( final String functionName : privateFunctions.keySet() )
      {
         final IFunction function = privateFunctions.get( functionName );
         ignoredLines.clear();
         ignoredLines.add( function.getInternalNode().getLine() );

         if ( getCurrentFile() instanceof IAs3File
               || !getCurrentFile().contains( functionName,
                                              ignoredLines ) )
         {
            addViolation( function );
         }
      }
   }

   private void fillPrivateFunctions( final List< IFunction > functions )
   {
      privateFunctions = new HashMap< String, IFunction >();

      for ( final IFunction function : functions )
      {
         if ( function.is( Modifier.PRIVATE ) )
         {
            privateFunctions.put( function.getName(),
                                  function );
         }
      }
   }

   private void findUnusedFunction( final IParserNode body )
   {
      if ( body != null )
      {
         if ( body.getStringValue() != null
               && !privateFunctions.isEmpty() )
         {
            for ( final String functionName : privateFunctions.keySet() )
            {
               if ( body.getStringValue().equals( functionName ) )
               {
                  privateFunctions.remove( functionName );
                  break;
               }
            }
         }
         if ( body.numChildren() != 0 )
         {
            for ( final IParserNode child : body.getChildren() )
            {
               findUnusedFunction( child );
            }
         }
      }
   }
}
