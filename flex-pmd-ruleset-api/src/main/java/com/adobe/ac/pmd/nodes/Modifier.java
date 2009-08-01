package com.adobe.ac.pmd.nodes;

import com.adobe.ac.pmd.parser.KeyWords;

public enum Modifier
{
   DYNAMIC, FINAL, INTERNAL, OVERRIDE, PRIVATE, PROTECTED, PUBLIC, STATIC;

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
}
