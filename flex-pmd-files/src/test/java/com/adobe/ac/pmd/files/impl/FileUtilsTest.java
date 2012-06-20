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
package com.adobe.ac.pmd.files.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import net.sourceforge.pmd.PMDException;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.IFlexFile;

public class FileUtilsTest extends FlexPmdTestBase
{
   @Test
   public void testComputeFilesList() throws PMDException
   {
      Map< String, IFlexFile > files;
      files = FileUtils.computeFilesList( getTestDirectory(),
                                          null,
                                          "",
                                          null );

      Assert.assertEquals( 108,
                           files.size() );

      final List< String > excludePatterns = new ArrayList< String >();
      excludePatterns.add( "bug" );
      files = FileUtils.computeFilesList( getTestDirectory(),
                                          null,
                                          "",
                                          excludePatterns );

      Assert.assertEquals( 91,
                           files.size() );
   }

   @Test
   public void testComputeFilesListWithEmptySourceFolder() throws PMDException
   {
      final Map< String, IFlexFile > files = FileUtils.computeFilesList( new File( getTestDirectory().getAbsolutePath()
                                                                               + "/" + "empty/emptyFolder" ),
                                                                         null,
                                                                         "",
                                                                         null );

      Assert.assertEquals( 1,
                           files.size() );
   }

   @Test
   public void testComputeFilesListWithoutSource()
   {
      try
      {
         FileUtils.computeFilesList( null,
                                     null,
                                     "",
                                     null );
         Assert.fail();
      }
      catch ( final PMDException e )
      {
         Assert.assertEquals( "sourceDirectory is not specified",
                              e.getMessage() );
      }
   }

   @Test
   public void testComputeFilesListWithSourceFile() throws PMDException
   {
      final Map< String, IFlexFile > files = FileUtils.computeFilesList( new File( getTestFiles().get( "AbstractRowData.as" )
                                                                                                 .getFilePath() ),
                                                                         null,
                                                                         "",
                                                                         null );

      Assert.assertEquals( 1,
                           files.size() );
   }

   @Test
   public void testComputeFilesListWithSourceList() throws PMDException
   {
      final List< File > sourceList = new ArrayList< File >();

      sourceList.add( getTestDirectory() );
      final Map< String, IFlexFile > files = FileUtils.computeFilesList( null,
                                                                         sourceList,
                                                                         "",
                                                                         null );

      Assert.assertEquals( 108,
                           files.size() );
   }
}
