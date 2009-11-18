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
import java.util.logging.Logger;

import net.sourceforge.pmd.cpd.CPD;
import net.sourceforge.pmd.cpd.Match;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.IFlexFile;

public class FlexCpdTest extends FlexPmdTestBase
{
   private static final Logger LOGGER = Logger.getLogger( FlexCpdTest.class.getName() );

   @Test
   public void tokenize() throws IOException
   {
      final CPD cpd = new CPD( 50, new FlexLanguage() );
      tokenizeFiles( cpd );

      LOGGER.info( "Starting to analyze code" );
      final long timeTaken = analyzeCode( cpd );
      LOGGER.info( "Done analyzing code; that took "
            + timeTaken + " milliseconds" );

      final Iterator< Match > matchIterator = cpd.getMatches();

      assertEquals( "",
                    true,
                    matchIterator.hasNext() );

      final Match firstMatch = matchIterator.next();

      assertEquals( "",
                    59,
                    firstMatch.getLineCount() );

      final Match secondMatch = matchIterator.next();

      assertEquals( "",
                    19,
                    secondMatch.getLineCount() );

      final Match thirdMatch = matchIterator.next();

      assertEquals( "",
                    16,
                    thirdMatch.getLineCount() );
   }

   private long analyzeCode( final CPD cpd )
   {
      final long start = System.currentTimeMillis();
      cpd.go();
      final long stop = System.currentTimeMillis();
      return stop
            - start;
   }

   private void tokenizeFiles( final CPD cpd ) throws IOException
   {
      for ( final Entry< String, IFlexFile > includedFile : getTestFiles().entrySet() )
      {
         cpd.add( new File( includedFile.getValue().getFilePath() ) );
      }
   }

}
