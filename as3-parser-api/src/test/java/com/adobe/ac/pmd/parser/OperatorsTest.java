package com.adobe.ac.pmd.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperatorsTest
{
   @Test
   public void testToString()
   {
      assertEquals( "&&",
                    Operators.AND.toString() );
   }
}
