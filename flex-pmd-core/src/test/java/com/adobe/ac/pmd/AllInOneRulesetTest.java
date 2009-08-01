package com.adobe.ac.pmd;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.TestCase;
import net.sourceforge.pmd.PMDException;

import com.adobe.ac.pmd.engines.FlexPmdXmlEngine;

public class AllInOneRulesetTest extends TestCase
{
   static protected final String OUTPUT_DIRECTORY_URL = "target/report/";

   public void testLoadRuleSet() throws URISyntaxException,
                                PMDException,
                                IOException
   {
      final File sourceDirectory = new File( getClass().getResource( "/test" ).toURI().getPath() );
      final URL ruleSetUrl = getClass().getResource( "/allInOneRuleset.xml" );

      assertNotNull( "RuleSet has not been found",
                     ruleSetUrl );

      assertNotNull( "RuleSet has not been found",
                     ruleSetUrl.toURI() );

      assertNotNull( "RuleSet has not been found",
                     ruleSetUrl.toURI().getPath() );

      final File outputDirectory = new File( OUTPUT_DIRECTORY_URL );
      final File ruleSetFile = new File( ruleSetUrl.toURI().getPath() );

      final FlexPmdXmlEngine engine = new FlexPmdXmlEngine( sourceDirectory, outputDirectory, "" );

      engine.executeReport( ruleSetFile );

      assertEquals( "Number of rules found is not correct",
                    44,
                    engine.getRuleSet().size() );
   }
}
