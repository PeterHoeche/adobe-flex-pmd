package com.adobe.ac.pmd.rules.utils.comparators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.adobe.ac.pmd.nodes.Modifier;

public class ModifierHolderByVisibilityAndStaticityComparatorTest
{
   @Test
   public void testCompare() // NOPMD by xagnetti on 7/7/09 3:13 PM
   {
      final List< VisibleMock > fields = new ArrayList< VisibleMock >();

      fields.add( create( "publicStatic",
                          true,
                          Modifier.PUBLIC ) );
      fields.add( create( "internalStatic",
                          true,
                          Modifier.INTERNAL ) );
      fields.add( create( "protectedStatic",
                          true,
                          Modifier.PROTECTED ) );
      fields.add( create( "privateStatic",
                          true,
                          Modifier.PRIVATE ) );
      fields.add( create( "public",
                          false,
                          Modifier.PUBLIC ) );
      fields.add( create( "internal",
                          false,
                          Modifier.INTERNAL ) );
      fields.add( create( "protected",
                          false,
                          Modifier.PROTECTED ) );
      fields.add( create( "private",
                          false,
                          Modifier.PRIVATE ) );

      shuffleSortAndAssert( fields );
      shuffleSortAndAssert( fields );
      shuffleSortAndAssert( fields );
   }

   private VisibleMock create( final String name,
                               final boolean isStatic,
                               final Modifier modifier )
   {
      final VisibleMock holder = new VisibleMock( name );

      if ( isStatic )
      {
         holder.add( Modifier.STATIC );
      }
      holder.add( modifier );
      return holder;
   }

   private void shuffleSortAndAssert( final List< VisibleMock > fields )
   {
      Collections.shuffle( fields );
      Collections.sort( fields,
                        new ModifierHolderByVisibilityAndStaticityComparator() );

      assertEquals( "",
                    "publicStatic",
                    fields.get( 0 ).getName() );
      assertEquals( "",
                    "internalStatic",
                    fields.get( 1 ).getName() );
      assertEquals( "",
                    "protectedStatic",
                    fields.get( 2 ).getName() );
      assertEquals( "",
                    "privateStatic",
                    fields.get( 3 ).getName() );
      assertEquals( "",
                    "public",
                    fields.get( 4 ).getName() );
      assertEquals( "",
                    "internal",
                    fields.get( 5 ).getName() );
      assertEquals( "",
                    "protected",
                    fields.get( 6 ).getName() );
      assertEquals( "",
                    "private",
                    fields.get( 7 ).getName() );
   }
}
