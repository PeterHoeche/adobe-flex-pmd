/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
 *    All rights reserved.
 *
 *    Redistribution  and  use  in  source  and  binary  forms, with or without
 *    modification,  are  permitted  provided  that  the  following  conditions
 *    are met:
 *
 *      * Redistributions  of  source  code  must  retain  the  above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions  in  binary  form  must reproduce the above copyright
 *        notice,  this  list  of  conditions  and  the following disclaimer in
 *        the    documentation   and/or   other  materials  provided  with  the
 *        distribution.
 *      * Neither the name of the Adobe Systems, Incorporated. nor the names of
 *        its  contributors  may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS  SOFTWARE  IS  PROVIDED  BY THE  COPYRIGHT  HOLDERS AND CONTRIBUTORS
 *    "AS IS"  AND  ANY  EXPRESS  OR  IMPLIED  WARRANTIES,  INCLUDING,  BUT NOT
 *    LIMITED  TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,  INCIDENTAL,  SPECIAL,
 *    EXEMPLARY,  OR  CONSEQUENTIAL  DAMAGES  (INCLUDING,  BUT  NOT  LIMITED TO,
 *    PROCUREMENT  OF  SUBSTITUTE   GOODS  OR   SERVICES;  LOSS  OF  USE,  DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY,  WHETHER  IN  CONTRACT,  STRICT  LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE  OR  OTHERWISE)  ARISING  IN  ANY  WAY  OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.adobe.ac.pmd.FlexPmdParameters;
import com.adobe.ac.pmd.FlexPmdViolations;
import com.adobe.ac.pmd.engines.FlexPmdXmlEngine;
import com.adobe.ac.pmd.engines.PmdEngineUtils;

public class FlexPmdAntTask extends Task // NO_UCD
{
   private boolean failOnError;
   private boolean failOnRuleViolation;
   private File    outputDirectory;
   private String  packageToExclude;
   private File    ruleSet;
   private File    sourceDirectory;

   @Override
   public final void execute()
   {
      try
      {
         presetParameters();

         final FlexPmdXmlEngine engine = new FlexPmdXmlEngine( new FlexPmdParameters( packageToExclude,
                                                                                      outputDirectory,
                                                                                      ruleSet,
                                                                                      sourceDirectory ) );
         final FlexPmdViolations violations = new FlexPmdViolations();

         engine.executeReport( violations );

         if ( failOnError )
         {
            final String message = PmdEngineUtils.findFirstViolationError( violations );

            if ( message.length() > 0 )
            {
               throw new BuildException( message );
            }
         }
         if ( failOnRuleViolation
               && !violations.getViolations().isEmpty() )
         {
            throw new BuildException( "At least one violation has been found" );
         }
      }
      catch ( final Exception e )
      {
         throw new BuildException( e );
      }
   }

   public final String getPackageToExclude()
   {
      return packageToExclude;
   }

   public final void setFailOnError( final boolean failOnErrorToBeSet )
   {
      failOnError = failOnErrorToBeSet;
   }

   public final void setFailOnRuleViolation( final boolean failOnRuleViolation )
   {
      this.failOnRuleViolation = failOnRuleViolation;
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

   private void presetParameters()
   {
      if ( packageToExclude == null )
      {
         packageToExclude = "";
      }
   }
}
