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
package com.adobe.ac.pmd.rules.maintanability;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

import net.sourceforge.pmd.PMDException;

import com.adobe.ac.pmd.files.impl.FileUtils;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class AvoidUseOfAsKeywordRuleTest extends AbstractAstFlexRuleTest
{
   protected static final Logger LOGGER      = Logger.getLogger( AvoidUseOfAsKeywordRuleTest.class.getName() );
   private static final String   TEST_FOLDER = "/com/adobe/ac/ncss";

   public AvoidUseOfAsKeywordRuleTest()
   {
      super();
      final URL resource = this.getClass().getResource( "/test"
            + TEST_FOLDER );

      if ( resource != null )
      {
         try
         {
            setTestFiles( FileUtils.computeFilesList( new File( resource.toURI().getPath() ),
                                                      null,
                                                      "",
                                                      null ) );
         }
         catch ( final PMDException e )
         {
            LOGGER.warning( e.getLocalizedMessage() );
         }
         catch ( final URISyntaxException e )
         {
            LOGGER.warning( e.getLocalizedMessage() );
         }
      }
   }

   @Override
   protected ExpectedViolation[] getExpectedViolatingFiles()
   {
      return new ExpectedViolation[]
      { new ExpectedViolation( "LongSwitch.as", new ViolationPosition[]
       { new ViolationPosition( 60 ),
                   new ViolationPosition( 61 ) } ),
                  new ExpectedViolation( "NestedSwitch.as", new ViolationPosition[]
                  { new ViolationPosition( 50 ),
                              new ViolationPosition( 51 ) } ) };
   }

   @Override
   protected AbstractFlexRule getRule()
   {
      return new AvoidUseOfAsKeywordRule();
   }

   @Override
   protected File getTestDirectory()
   {
      return new File( super.getTestDirectory().getAbsolutePath()
            + TEST_FOLDER );
   }
}
