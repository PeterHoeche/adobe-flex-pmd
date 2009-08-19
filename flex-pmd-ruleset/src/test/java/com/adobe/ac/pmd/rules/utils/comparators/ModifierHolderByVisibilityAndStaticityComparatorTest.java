/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
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
 *      * Neither the name of the Adobe Systems, Inc. nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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

      assertEquals( "publicStatic",
                    fields.get( 0 ).getName() );
      assertEquals( "internalStatic",
                    fields.get( 1 ).getName() );
      assertEquals( "protectedStatic",
                    fields.get( 2 ).getName() );
      assertEquals( "privateStatic",
                    fields.get( 3 ).getName() );
      assertEquals( "public",
                    fields.get( 4 ).getName() );
      assertEquals( "internal",
                    fields.get( 5 ).getName() );
      assertEquals( "protected",
                    fields.get( 6 ).getName() );
      assertEquals( "private",
                    fields.get( 7 ).getName() );
   }
}
