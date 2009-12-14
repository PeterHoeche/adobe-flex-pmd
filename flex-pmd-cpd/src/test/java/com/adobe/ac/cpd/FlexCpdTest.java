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
package com.adobe.ac.cpd;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import net.sourceforge.pmd.cpd.CPD;
import net.sourceforge.pmd.cpd.Match;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.IFlexFile;

public class FlexCpdTest extends FlexPmdTestBase
{
   private class ExpectedMatchInformation
   {
      private final int lineCount;
      private final int markCount;
      private final int tokenCount;

      public ExpectedMatchInformation( final int tokenCountToBeSet,
                                       final int markCountToBeSet,
                                       final int lineCountToBeSet )
      {
         lineCount = lineCountToBeSet;
         tokenCount = tokenCountToBeSet;
         markCount = markCountToBeSet;
      }
   }
   final ExpectedMatchInformation[] EXPECTED = new ExpectedMatchInformation[]
                                             { new ExpectedMatchInformation( 107, 2, 7 ),
               new ExpectedMatchInformation( 79, 2, 17 ),
               new ExpectedMatchInformation( 77, 2, 6 ),
               new ExpectedMatchInformation( 64, 2, 7 ),
               new ExpectedMatchInformation( 60, 3, 14 ),
               new ExpectedMatchInformation( 57, 2, 7 ),
               new ExpectedMatchInformation( 53, 2, 8 ),
               new ExpectedMatchInformation( 48, 2, 18 ),
               new ExpectedMatchInformation( 44, 2, 7 ),
               new ExpectedMatchInformation( 44, 2, 13 ),
               new ExpectedMatchInformation( 43, 2, 13 ),
               new ExpectedMatchInformation( 41, 2, 16 ),
               new ExpectedMatchInformation( 41, 2, 17 ),
               new ExpectedMatchInformation( 40, 2, 14 ),
               new ExpectedMatchInformation( 40, 2, 7 ),
               new ExpectedMatchInformation( 40, 2, 15 ),
               new ExpectedMatchInformation( 40, 2, 15 ),
               new ExpectedMatchInformation( 38, 2, 3 ),
               new ExpectedMatchInformation( 35, 2, 14 ),
               new ExpectedMatchInformation( 35, 2, 16 ),
               new ExpectedMatchInformation( 34, 2, 8 ),
               new ExpectedMatchInformation( 34, 2, 6 ),
               new ExpectedMatchInformation( 33, 2, 6 ),
               new ExpectedMatchInformation( 32, 2, 15 ),
               new ExpectedMatchInformation( 31, 2, 11 ),
               new ExpectedMatchInformation( 30, 2, 2 ),
               new ExpectedMatchInformation( 29, 2, 12 ),
               new ExpectedMatchInformation( 28, 3, 9 ),
               new ExpectedMatchInformation( 28, 2, 13 ),
               new ExpectedMatchInformation( 28, 2, 8 ),
               new ExpectedMatchInformation( 27, 2, 10 ),
               new ExpectedMatchInformation( 27, 2, 2 ),
               new ExpectedMatchInformation( 27, 2, 14 ) };

   @Test
   public void test119() throws IOException
   {
      final CPD cpd = new CPD( FlexTokenizer.DEFAULT_MINIMUM_TOKENS, new FlexLanguage() );

      cpd.add( new File( "src/test/resources/test/FlexPMD119.mxml" ) );
      cpd.go();

      final Iterator< Match > matchIterator = cpd.getMatches();
      final Match match = matchIterator.next();

      assertEquals( 41,
                    match.getFirstMark().getBeginLine() );
      assertEquals( 81,
                    match.getSecondMark().getBeginLine() );
   }

   @Test
   public void tokenize() throws IOException
   {
      final Iterator< Match > matchIterator = getMatchIterator();

      for ( int currentIndex = 0; matchIterator.hasNext()
            && currentIndex < EXPECTED.length; currentIndex++ )
      {
         final Match currentMatch = matchIterator.next();

         assertEquals( "The token count is not correct on the "
                             + currentIndex + "th index",
                       EXPECTED[ currentIndex ].tokenCount,
                       currentMatch.getTokenCount() );

         assertEquals( "The mark count is not correct on the "
                             + currentIndex + "th index",
                       EXPECTED[ currentIndex ].markCount,
                       currentMatch.getMarkCount() );

         assertEquals( "The line count is not correct on the "
                             + currentIndex + "th index",
                       EXPECTED[ currentIndex ].lineCount,
                       currentMatch.getLineCount() );
      }
   }

   private Iterator< Match > getMatchIterator() throws IOException
   {
      final CPD cpd = new CPD( FlexTokenizer.DEFAULT_MINIMUM_TOKENS, new FlexLanguage() );

      for ( final Entry< String, IFlexFile > includedFile : getTestFiles().entrySet() )
      {
         cpd.add( new File( includedFile.getValue().getFilePath() ) );
      }
      cpd.go();

      return cpd.getMatches();
   }
}
