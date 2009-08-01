package com.adobe.ac.pmd.maven;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.codehaus.doxia.site.renderer.DefaultSiteRenderer;
import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;

public class FlexPmdReportMojoTest extends FlexPmdTestBase
{
   @Test
   public void testExecuteReport() throws MojoExecutionException
   {
      new File( "target/site" ).mkdirs();
      final FlexPmdReportMojo mojo = new FlexPmdReportMojo( new File( "target" ),
                                                            new MavenProjectStub(),
                                                            null,
                                                            getTestDirectory() );

      mojo.setSiteRenderer( new DefaultSiteRenderer() );
      assertNotNull( "",
                     mojo.getName( Locale.ENGLISH ) );

      mojo.execute();
   }

   @Test
   public void testGetBundle()
   {
      final Locale[] availableLocales = Locale.getAvailableLocales();
      final ResourceBundle bundle = AbstractFlexPmdMojo.getBundle( availableLocales[ 0 ] );
      final ResourceBundle englishBundle = AbstractFlexPmdMojo.getBundle( Locale.ENGLISH );

      assertNotNull( "",
                     bundle );
      assertNotNull( "",
                     englishBundle );
   }
}
