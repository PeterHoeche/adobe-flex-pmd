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

import java.io.File;

import net.sourceforge.pmd.cpd.SourceCode;
import net.sourceforge.pmd.cpd.TokenEntry;
import net.sourceforge.pmd.cpd.Tokenizer;
import net.sourceforge.pmd.cpd.Tokens;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.files.IMxmlFile;
import com.adobe.ac.pmd.files.impl.FileUtils;
import com.adobe.ac.pmd.parser.KeyWords;

import de.bokelberg.flex.parser.AS3Scanner;
import de.bokelberg.flex.parser.AS3Scanner.Token;

public class FlexTokenizer implements Tokenizer
{
   public static final Integer DEFAULT_MINIMUM_TOKENS = 75;

   public void tokenize( final SourceCode tokens,
                         final Tokens tokenEntries )
   {
      try
      {
         final AS3Scanner scanner = new AS3Scanner();

         final IFlexFile flexFile = FileUtils.create( new File( tokens.getFileName() ),
                                                      new File( "" ) );

         if ( flexFile instanceof IMxmlFile )
         {
            final IMxmlFile mxml = ( IMxmlFile ) flexFile;

            scanner.setLines( mxml.getActualScriptBlock() );
         }
         else
         {
            scanner.setLines( tokens.getCode().toArray( new String[ tokens.getCode().size() ] ) );
         }
         Token currentToken = scanner.moveToNextToken();

         while ( currentToken != null
               && currentToken.getText().compareTo( KeyWords.EOF.toString() ) != 0 )
         {
            tokenEntries.add( new TokenEntry( currentToken.getText(),
                                              tokens.getFileName(),
                                              currentToken.getLine() ) );
            currentToken = scanner.moveToNextToken();
         }
      }
      catch ( final Exception e )
      {
      }
      finally
      {
         tokenEntries.add( TokenEntry.getEOF() );
      }
   }
}
