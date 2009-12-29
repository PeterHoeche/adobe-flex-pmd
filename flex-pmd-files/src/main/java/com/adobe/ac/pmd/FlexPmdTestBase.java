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
import java.util.Map;

import com.adobe.ac.pmd.files.IFlexFile;

/**
 * This is a base class for any FlexPMD rule test case.
 * 
 * @author xagnetti
 */
public class FlexPmdTestBase // NO_UCD
{
   protected static final String          BEGIN_LINE_NOT_CORRECT        = "Begining line is not correct";     // NO_UCD
   protected static final String          END_LINE_NOT_CORRECT          = "Ending line is not correct";       // NO_UCD
   protected static final String          VIOLATIONS_NUMBER_NOT_CORRECT = "Violations number is not correct"; // NO_UCD

   /**
    * Test files placeholder. The key is the qualified file name
    */
   private final Map< String, IFlexFile > testFiles                     = ResourcesManagerTest.getInstance()
                                                                                              .getTestFiles();

   protected FlexPmdTestBase()
   {
   }

   protected String getLineSeparator()
   {
      return System.getProperty( "line.separator" );
   }

   protected final File getTestDirectory() // NO_UCD
   {
      return ResourcesManagerTest.getInstance().getTestRootDirectory();
   }

   protected final Map< String, IFlexFile > getTestFiles() // NO_UCD
   {
      return testFiles;
   }

}