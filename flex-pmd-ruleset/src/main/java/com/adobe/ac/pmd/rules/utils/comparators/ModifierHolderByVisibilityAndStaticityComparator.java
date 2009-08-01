package com.adobe.ac.pmd.rules.utils.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.adobe.ac.pmd.nodes.IModifiersHolder;
import com.adobe.ac.pmd.nodes.IVisible;
import com.adobe.ac.pmd.nodes.Modifier;

public class ModifierHolderByVisibilityAndStaticityComparator implements Comparator< IVisible >, Serializable
{
   private static final long serialVersionUID = 355164117728770224L;

   public final int compare( final IVisible firstModifierHolder,
                             final IVisible secondModifierHolder )
   {
      int diff = getStaticityWeight( secondModifierHolder )
            - getStaticityWeight( firstModifierHolder );

      if ( diff == 0 )
      {
         diff = new ModifierHolderByVisibilityComparator().compare( firstModifierHolder,
                                                                    secondModifierHolder );
      }
      return diff;
   }

   private int getStaticityWeight( final IModifiersHolder field )
   {
      return field.is( Modifier.STATIC ) ? 1
                                        : 0;
   }
}
