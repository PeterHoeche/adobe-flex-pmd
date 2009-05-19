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
package com.adobe.ac.pmd.nodes.utils;

import java.util.ArrayList;

import com.adobe.ac.pmd.nodes.ClassNode;
import com.adobe.ac.pmd.nodes.IModifiersHolder;
import com.adobe.ac.pmd.nodes.Modifier;

import de.bokelberg.flex.parser.KeyWords;
import de.bokelberg.flex.parser.Node;

final public class ModifierUtils
{
   public static void computeModifierList( final IModifiersHolder modifiable,
                                           final Node child )
   {
      modifiable.setModifiers( new ArrayList< Modifier >() );

      if ( child.children != null )
      {
         for ( final Node modifierNode : child.children )
         {
            final Modifier modifier = buildModifier( modifierNode.stringValue );

            modifiable.getModifiers().add( modifier );
         }
      }
   }

   public static boolean isFinal( final ClassNode classNode )
   {
      return isModiferPresent( classNode,
                               Modifier.FINAL );
   }

   public static boolean isInternal( final IModifiersHolder modifierHolder )
   {
      return isModiferPresent( modifierHolder,
                               Modifier.INTERNAL );
   }

   public static boolean isOverriden( final IModifiersHolder modifierHolder )
   {
      return isModiferPresent( modifierHolder,
                               Modifier.OVERRIDE );
   }

   public static boolean isPrivate( final IModifiersHolder modifierHolder )
   {
      return isModiferPresent( modifierHolder,
                               Modifier.PRIVATE );
   }

   public static boolean isProtected( final IModifiersHolder modifierHolder )
   {
      return isModiferPresent( modifierHolder,
                               Modifier.PROTECTED );
   }

   public static boolean isPublic( final IModifiersHolder modifierHolder )
   {
      return isModiferPresent( modifierHolder,
                               Modifier.PUBLIC );
   }

   public static boolean isStatic( final IModifiersHolder modifierHolder )
   {
      return isModiferPresent( modifierHolder,
                               Modifier.STATIC );
   }

   private static Modifier buildModifier( final String name )
   {
      Modifier modifier = null;
      if ( KeyWords.PUBLIC.equals( name ) )
      {
         modifier = Modifier.PUBLIC;
      }
      else if ( KeyWords.PRIVATE.equals( name ) )
      {
         modifier = Modifier.PRIVATE;
      }
      else if ( KeyWords.PROTECTED.equals( name ) )
      {
         modifier = Modifier.PROTECTED;
      }
      else if ( KeyWords.INTERNAL.equals( name ) )
      {
         modifier = Modifier.INTERNAL;
      }
      else if ( KeyWords.DYNAMIC.equals( name ) )
      {
         modifier = Modifier.DYNAMIC;
      }
      else if ( KeyWords.OVERRIDE.equals( name ) )
      {
         modifier = Modifier.OVERRIDE;
      }
      else if ( KeyWords.STATIC.equals( name ) )
      {
         modifier = Modifier.STATIC;
      }
      else if ( KeyWords.FINAL.equals( name ) )
      {
         modifier = Modifier.FINAL;
      }
      return modifier;
   }

   private static boolean isModiferPresent( final IModifiersHolder modifierHolder,
                                            final Modifier modifierToFind )
   {
      boolean isModifierPresent = false;

      for ( final Modifier modifier : modifierHolder.getModifiers() )
      {
         if ( modifier != null
               && modifier.equals( modifierToFind ) )
         {
            isModifierPresent = true;
         }
      }
      return isModifierPresent;
   }

   private ModifierUtils()
   {
   }
}
