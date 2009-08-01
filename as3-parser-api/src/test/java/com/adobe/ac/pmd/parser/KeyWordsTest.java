package com.adobe.ac.pmd.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KeyWordsTest
{
   @Test
   public void testToString()
   {
      assertEquals( "as",
                    KeyWords.AS.toString() );
   }
}
