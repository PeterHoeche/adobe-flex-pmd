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
package com.adobe.ac.cpd.ant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.cpd.CPD;
import net.sourceforge.pmd.cpd.FileReporter;
import net.sourceforge.pmd.cpd.Renderer;
import net.sourceforge.pmd.cpd.ReportException;
import net.sourceforge.pmd.cpd.XMLRenderer;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import com.adobe.ac.cpd.FlexLanguage;
import com.adobe.ac.cpd.FlexTokenizer;

public class FlexCpdAntTask extends Task
{
   private String                encoding          = System.getProperty( "file.encoding" );
   private final List< FileSet > filesets          = new ArrayList< FileSet >();
   private int                   minimumTokenCount = FlexTokenizer.DEFAULT_MINIMUM_TOKENS;

   private File                  outputFile;

   public void addFileset( final FileSet set )
   {
      filesets.add( set );
   }

   @Override
   public void execute() throws BuildException
   {
      try
      {
         validateFields();

         log( "Starting run, minimumTokenCount is "
                    + minimumTokenCount,
              Project.MSG_INFO );

         log( "Tokenizing files",
              Project.MSG_INFO );
         final CPD cpd = new CPD( minimumTokenCount, new FlexLanguage() );
         cpd.setEncoding( encoding );
         tokenizeFiles( cpd );

         log( "Starting to analyze code",
              Project.MSG_INFO );
         final long timeTaken = analyzeCode( cpd );
         log( "Done analyzing code; that took "
               + timeTaken + " milliseconds" );

         log( "Generating report",
              Project.MSG_INFO );
         report( cpd );
      }
      catch ( final IOException ioe )
      {
         log( ioe.toString(),
              Project.MSG_ERR );
         throw new BuildException( "IOException during task execution", ioe );
      }
      catch ( final ReportException re )
      {
         re.printStackTrace();
         log( re.toString(),
              Project.MSG_ERR );
         throw new BuildException( "ReportException during task execution", re );
      }
   }

   public void setEncoding( final String encodingValue )
   {
      encoding = encodingValue;
   }

   public void setMinimumTokenCount( final int minimumTokenCount )
   {
      this.minimumTokenCount = minimumTokenCount;
   }

   public void setOutputFile( final File outputFile )
   {
      this.outputFile = outputFile;
   }

   private long analyzeCode( final CPD cpd )
   {
      final long start = System.currentTimeMillis();
      cpd.go();
      final long stop = System.currentTimeMillis();
      return stop
            - start;
   }

   private void report( final CPD cpd ) throws ReportException
   {
      if ( !cpd.getMatches().hasNext() )
      {
         log( "No duplicates over "
                    + minimumTokenCount + " tokens found",
              Project.MSG_INFO );
      }
      final Renderer renderer = new XMLRenderer( encoding );
      FileReporter reporter;
      if ( outputFile == null )
      {
         reporter = new FileReporter( encoding );
      }
      else if ( outputFile.isAbsolute() )
      {
         reporter = new FileReporter( outputFile, encoding );
      }
      else
      {
         reporter = new FileReporter( new File( getProject().getBaseDir(), outputFile.toString() ), encoding );
      }
      reporter.report( renderer.render( cpd.getMatches() ) );
   }

   private void tokenizeFiles( final CPD cpd ) throws IOException
   {
      for ( final FileSet fileSet : filesets )
      {
         final DirectoryScanner directoryScanner = fileSet.getDirectoryScanner( getProject() );
         final String[] includedFiles = directoryScanner.getIncludedFiles();
         for ( final String includedFile : includedFiles )
         {
            final File file = new File( directoryScanner.getBasedir()
                  + System.getProperty( "file.separator" ) + includedFile );
            log( "Tokenizing "
                       + file.getAbsolutePath(),
                 Project.MSG_VERBOSE );
            cpd.add( file );
         }
      }
   }

   private void validateFields() throws BuildException
   {
      if ( minimumTokenCount == 0 )
      {
         throw new BuildException( "minimumTokenCount is required and must be greater than zero" );
      }
      else if ( filesets.isEmpty() )
      {
         throw new BuildException( "Must include at least one FileSet" );
      }
   }
}
