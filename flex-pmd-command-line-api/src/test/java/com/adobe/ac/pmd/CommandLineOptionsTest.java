package com.adobe.ac.pmd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommandLineOptionsTest
{
   @Test
   public void testToString()
   {
      assertEquals( "outputDirectory",
                    CommandLineOptions.OUTPUT.toString() );
   }
}
