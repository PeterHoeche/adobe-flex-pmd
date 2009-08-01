package com.adobe.ac.pmd.ant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import net.sourceforge.pmd.PMDException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.adobe.ac.pmd.engines.FlexPmdXmlEngine;

public class FlexPmdAntTask extends Task // NO_UCD
{
   private File   outputDirectory;
   private String packageToExclude;
   private File   ruleSet;
   private File   sourceDirectory;

   @Override
   public final void execute()
   {
      try
      {
         final FlexPmdXmlEngine engine = new FlexPmdXmlEngine( sourceDirectory,
                                                               outputDirectory,
                                                               packageToExclude );

         engine.executeReport( ruleSet );
      }
      catch ( final PMDException e )
      {
         throw new BuildException( e );
      }
      catch ( final FileNotFoundException e )
      {
         throw new BuildException( e );
      }
      catch ( final URISyntaxException e )
      {
         throw new BuildException( e );
      }
      catch ( final IOException e )
      {
         throw new BuildException( e );
      }
   }

   public final void setOutputDirectory( final File outputDirectoryToBeSet )
   {
      outputDirectory = outputDirectoryToBeSet;
   }

   public final void setPackageToExclude( final String packageToExcludeToBeSet )
   {
      packageToExclude = packageToExcludeToBeSet;
   }

   public final void setRuleSet( final File ruleSetToBeSet )
   {
      ruleSet = ruleSetToBeSet;
   }

   public final void setSourceDirectory( final File sourceDirectoryToBeSet )
   {
      sourceDirectory = sourceDirectoryToBeSet;
   }
}
