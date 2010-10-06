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
package com.adobe.ac.pmd;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.sourceforge.pmd.PMDException;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.files.impl.FileUtils;
import com.adobe.ac.utils.StackTraceUtils;

/**
 * Internal utility which finds out the test resources, and map them to their
 * qualified names.
 * 
 * @author xagnetti
 */
public final class ResourcesManagerTest
{
   private static ResourcesManagerTest instance = null;
   private static final Logger         LOGGER   = Logger.getLogger( ResourcesManagerTest.class.getName() );

   /**
    * @return
    */
   public static synchronized ResourcesManagerTest getInstance() // NOPMD
   {
      if ( instance == null )
      {
         try
         {
            new LoggerUtils().loadConfiguration();
            instance = new ResourcesManagerTest( "/test" );
         }
         catch ( final URISyntaxException e )
         {
            LOGGER.warning( StackTraceUtils.print( e ) );
         }
         catch ( final PMDException e )
         {
            LOGGER.warning( StackTraceUtils.print( e ) );
         }
      }
      return instance;
   }

   private final Map< String, IFlexFile > testFiles;
   private final File                     testRootDirectory;

   private ResourcesManagerTest( final String directory ) throws URISyntaxException,
                                                         PMDException
   {
      final URL resource = this.getClass().getResource( directory );

      if ( resource == null )
      {
         LOGGER.severe( directory
               + " folder is not found in the resource" );
         testRootDirectory = null;
         testFiles = new LinkedHashMap< String, IFlexFile >();
      }
      else
      {
         testRootDirectory = new File( resource.toURI().getPath() );
         testFiles = FileUtils.computeFilesList( testRootDirectory,
                                                 null,
                                                 "",
                                                 null );
      }
   }

   /**
    * @return
    */
   public Map< String, IFlexFile > getTestFiles()
   {
      return testFiles;
   }

   protected File getTestRootDirectory()
   {
      return testRootDirectory;
   }
}
