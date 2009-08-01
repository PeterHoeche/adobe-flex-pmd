package com.adobe.ac.pmd.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NodeKindTest
{
   @Test
   public void testToString()
   {
      assertEquals( "add",
                    NodeKind.ADD.toString() );
   }
}
