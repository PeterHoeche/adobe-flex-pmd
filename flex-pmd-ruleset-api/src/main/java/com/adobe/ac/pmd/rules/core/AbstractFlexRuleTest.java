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
package com.adobe.ac.pmd.rules.core;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.Violation;

public abstract class AbstractFlexRuleTest
      extends FlexPmdTestBase
{
   /**
    * Test case which contains non-violating files but which are concerned by the given rule
    *
    * @throws FileNotFoundException
    * @throws URISyntaxException
    */
   @Test
   public abstract void testProcessConcernedButNonViolatingFiles() throws FileNotFoundException,
         URISyntaxException;

   /**
    * Test case which contains non-concerned files by the given rule
    *
    * @throws FileNotFoundException
    * @throws URISyntaxException
    */
   @Test
   public abstract void testProcessNonConcernedFiles() throws FileNotFoundException,
         URISyntaxException;

   /**
    * Test case which contains violating files
    *
    * @throws FileNotFoundException
    * @throws URISyntaxException
    */
   @Test
   public abstract void testProcessViolatingFiles()
         throws FileNotFoundException, URISyntaxException;

   final protected void assertEmptyViolations(
         final String resourcePath ) throws FileNotFoundException,
         URISyntaxException
   {
      assertViolations(
            resourcePath, new ViolationPosition[]
            {} );
   }

   final protected void assertViolations(
         final String resourcePath, final ViolationPosition[] expectedPositions )
         throws FileNotFoundException, URISyntaxException
   {
      final List< Violation > violations = processFile( resourcePath );

      if ( expectedPositions.length != 0 )
      {
         for ( int i = 0; i < expectedPositions.length; i++ )
         {
            final Violation violation = violations.get( i );
            final ViolationPosition expectedPosition = expectedPositions[ i ];

            assertEquals(
                  BEGIN_LINE_IS_NOT_CORRECT, expectedPosition.getBeginLine(),
                  violation.getBeginLine() );
            assertEquals(
                  END_LINE_IS_NOT_CORRECT, expectedPosition.getEndLine(),
                  violation.getEndLine() );
         }
      }
   }

   protected abstract AbstractFlexRule getRule();

   protected List< Violation > processFile(
         final String resourcePath ) throws FileNotFoundException,
         URISyntaxException
   {
      return getRule().processFile(
            testFiles.get( resourcePath ), null, testFiles );
   }
}