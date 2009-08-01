package com.adobe.ac.pmd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;

public class CommandLineUtilsTest
{

   private static final String EXCLUDE_USAGE = "[(-e|--excludePackage) <excludePackage>]";
   private static final String OUTPUT_USAGE  = "[(-o|--outputDirectory) <outputDirectory>]";
   private static final String SOURCE_USAGE  = "(-s|--sourceDirectory) <sourceDirectory>";
   private static final String SPACE         = " ";

   @Test
   public void testRegisterParameter() throws JSAPException
   {
      final JSAP jsap = new JSAP();

      CommandLineUtils.registerParameter( jsap,
                                          CommandLineOptions.EXLUDE_PACKAGE,
                                          false );

      assertEquals( EXCLUDE_USAGE,
                    jsap.getUsage() );

      CommandLineUtils.registerParameter( jsap,
                                          CommandLineOptions.OUTPUT,
                                          false );

      assertEquals( EXCLUDE_USAGE
                          + SPACE + OUTPUT_USAGE,
                    jsap.getUsage() );

      CommandLineUtils.registerParameter( jsap,
                                          CommandLineOptions.SOURCE_DIRECTORY,
                                          true );

      assertEquals( EXCLUDE_USAGE
                          + SPACE + OUTPUT_USAGE + SPACE + SOURCE_USAGE,
                    jsap.getUsage() );
   }
}
