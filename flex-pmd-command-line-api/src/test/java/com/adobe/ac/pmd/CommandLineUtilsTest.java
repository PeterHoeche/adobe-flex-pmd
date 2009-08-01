/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
