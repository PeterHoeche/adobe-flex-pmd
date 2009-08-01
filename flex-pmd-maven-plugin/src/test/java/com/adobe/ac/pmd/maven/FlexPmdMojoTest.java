package com.adobe.ac.pmd.maven;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Locale;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.codehaus.doxia.site.renderer.DefaultSiteRenderer;
import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;

public class FlexPmdMojoTest extends FlexPmdTestBase
{
   @Test
   public void testExecuteReport() throws MojoExecutionException
   {
      final File outputDirectoryToBeSet = new File( "target/pmd" );

      outputDirectoryToBeSet.mkdirs();

      final FlexPmdMojo mojo = new FlexPmdMojo( outputDirectoryToBeSet,
                                                new MavenProjectStub(),
                                                null,
                                                getTestDirectory() );

      mojo.setSiteRenderer( new DefaultSiteRenderer() );
      assertNotNull( "",
                     mojo.getName( Locale.ENGLISH ) );

      mojo.execute();

      new File( "target/pmd.xml" ).delete();
   }
}
