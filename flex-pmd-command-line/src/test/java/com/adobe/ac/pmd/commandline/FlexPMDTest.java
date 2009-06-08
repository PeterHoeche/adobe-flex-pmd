package com.adobe.ac.pmd.commandline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import net.sourceforge.pmd.PMDException;

import org.junit.Test;

import com.adobe.ac.pmd.CommandLineOptions;
import com.martiansoftware.jsap.JSAPException;

public class FlexPMDTest
{
   @Test
   public void testAreCommandLineOptionsCorrect() throws FileNotFoundException,
                                                 JSAPException,
                                                 PMDException,
                                                 URISyntaxException
   {
      assertFalse( FlexPMD.areCommandLineOptionsCorrect( new String[]
      {} ) );

      assertFalse( FlexPMD.areCommandLineOptionsCorrect( new String[]
      { "-y",
                  "sourceDirectory",
                  "-p",
                  "outPutDirectory" } ) );

      assertTrue( FlexPMD.areCommandLineOptionsCorrect( new String[]
      { "-s",
                  "sourceDirectory",
                  "-o",
                  "target",
                  "-r",
                  "all_flex.xml" } ) );

      assertTrue( FlexPMD.areCommandLineOptionsCorrect( new String[]
      { "-s",
                  "sourceDirectory",
                  "-o",
                  "target" } ) );
   }

   @Test
   public void testGetCommandLineValue() throws JSAPException
   {
      FlexPMD.areCommandLineOptionsCorrect( new String[]
      { "-s",
                  "sourceDirectory",
                  "-o",
                  "target",
                  "-r",
                  "all_flex.xml" } );

      assertEquals( "sourceDirectory",
                    FlexPMD.getParameterValue( CommandLineOptions.SOURCE_DIRECTORY ) );
      assertEquals( "target",
                    FlexPMD.getParameterValue( CommandLineOptions.OUTPUT ) );
      assertEquals( "all_flex.xml",
                    FlexPMD.getParameterValue( CommandLineOptions.RULE_SET ) );
   }
}
