package com.adobe.ac.pmd.rules.utils.comparators;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.nodes.INamable;
import com.adobe.ac.pmd.nodes.IVisible;
import com.adobe.ac.pmd.nodes.Modifier;

class VisibleMock implements IVisible, INamable
{
   private final Map< Modifier, Object > modifiers;
   private final String                  name;

   public VisibleMock( final String modifierName )
   {
      name = modifierName;
      modifiers = new HashMap< Modifier, Object >();
   }

   public void add( final Modifier modifier )
   {
      modifiers.put( modifier,
                     null );
   }

   public String getName()
   {
      return name;
   }

   public boolean is( final Modifier modifier ) // NOPMD by xagnetti on 7/7/09 3:13 PM
   {
      return modifiers.containsKey( modifier );
   }

   public boolean isPublic()
   {
      return is( Modifier.PUBLIC );
   }
}
