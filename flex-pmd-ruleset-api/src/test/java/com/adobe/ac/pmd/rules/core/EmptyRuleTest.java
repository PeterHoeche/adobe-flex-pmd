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
package com.adobe.ac.pmd.rules.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.rules.core.AbstractFlexRuleTest.AssertPosition;

public class EmptyRuleTest extends FlexPmdTestBase
{
   @Test
   public void addViolationEmptyRule()
   {
      final List< IFlexViolation > violatons = new EmptyRule().processFile( null,
                                                                            null,
                                                                            null );

      assertEquals( 1,
                    violatons.size() );

      final IFlexViolation firstViolation = violatons.get( 0 );

      assertEquals( 0,
                    firstViolation.getBeginLine() );
      assertEquals( 0,
                    firstViolation.getEndLine() );
      assertEquals( "emptyMessage. description",
                    firstViolation.getRuleMessage() );
   }

   @Test
   public void addViolationWarningRule()
   {
      final List< IFlexViolation > violatons = new WarningRule().processFile( null,
                                                                              null,
                                                                              null );

      assertEquals( 1,
                    violatons.size() );

      final IFlexViolation firstViolation = violatons.get( 0 );

      assertEquals( 0,
                    firstViolation.getBeginLine() );
      assertEquals( 0,
                    firstViolation.getEndLine() );
      assertEquals( "warning message",
                    firstViolation.getRuleMessage() );
   }

   @Test
   public void testBuildFailuresMessage()
   {
      final ArrayList< AssertPosition > position = new ArrayList< AssertPosition >();

      position.add( AssertPosition.create( "message",
                                           1,
                                           2 ) );

      assertEquals( "message: expected <1> but actually <2>\n",
                    AbstractFlexRuleTest.buildFailuresMessage( position ).toString() );
   }

   @Test
   public void testBuildFailureViolations()
   {
      final ViolationPosition[] expectedPositions = new ViolationPosition[]
      { new ViolationPosition( 0 ) };
      final ArrayList< IFlexViolation > violations = new ArrayList< IFlexViolation >();

      violations.add( new Violation( new ViolationPosition( 1 ), new EmptyRule(), null ) );

      final List< AssertPosition > positions = AbstractFlexRuleTest.buildFailureViolations( "",
                                                                                            expectedPositions,
                                                                                            violations );

      assertEquals( 2,
                    positions.size() );
      assertEquals( "Begining line is not correct at 0th violation on ",
                    positions.get( 0 ).message );
      assertEquals( "Ending line is not correct at 0th violation on ",
                    positions.get( 1 ).message );
   }

   @Test
   public void testBuildMessageName()
   {
      final Map< String, List< IFlexViolation >> violatedFiles = new LinkedHashMap< String, List< IFlexViolation > >();
      final ArrayList< IFlexViolation > emptyList = new ArrayList< IFlexViolation >();

      violatedFiles.put( "file1",
                         emptyList );

      violatedFiles.put( "file2",
                         emptyList );

      assertEquals( "file1 should not contain any violations  (0 found)\n"
                          + "file2 should not contain any violations  (0 found)\n",
                    AbstractFlexRuleTest.buildMessageName( violatedFiles ).toString() );

      final ArrayList< IFlexViolation > oneItemList = new ArrayList< IFlexViolation >();

      oneItemList.add( new Violation( new ViolationPosition( 0 ), new EmptyRule(), null ) );
      violatedFiles.put( "file2",
                         oneItemList );

      assertEquals( "file1 should not contain any violations  (0 found)\n"
                          + "file2 should not contain any violations  (1 found at 0:0)\n",
                    AbstractFlexRuleTest.buildMessageName( violatedFiles ).toString() );
   }
}
