package com.adobe.ac.pmd.rules.utils.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.adobe.ac.pmd.nodes.IVisible;
import com.adobe.ac.pmd.nodes.Modifier;

class ModifierHolderByVisibilityComparator implements Comparator< IVisible >, Serializable
{
   private static final int  INTERNAL_WEIGHT  = 2;
   private static final int  PROTECTED_WEIGHT = 1;
   private static final int  PUBLIC_WEIGHT    = 3;

   private static final long serialVersionUID = 2019528304660124281L;

   public final int compare( final IVisible firstModifierHolder,
                             final IVisible secondModifierHolder )
   {
      return getVisibilityWeight( secondModifierHolder )
            - getVisibilityWeight( firstModifierHolder );
   }

   private int getVisibilityWeight( final IVisible field )
   {
      int weight = 0;

      if ( field.isPublic() )
      {
         weight = PUBLIC_WEIGHT;
      }
      else if ( field.is( Modifier.INTERNAL ) )
      {
         weight = INTERNAL_WEIGHT;
      }
      else if ( field.is( Modifier.PROTECTED ) )
      {
         weight = PROTECTED_WEIGHT;
      }
      return weight;
   }
}
