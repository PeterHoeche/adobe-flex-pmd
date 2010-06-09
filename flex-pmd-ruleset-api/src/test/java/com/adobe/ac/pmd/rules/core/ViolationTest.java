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
import static org.junit.Assert.assertFalse;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.rules.core.thresholded.IThresholdedRule;

public class ViolationTest extends FlexPmdTestBase
{
   private static final int              BEGINNING_COLUMN = 0;
   private static final int              BEGINNING_LINE   = 1;
   private static final int              ENDING_COLUMN    = 20;
   private static final int              ENDING_LINE      = 10;
   private static final IThresholdedRule INFO_RULE        = new EmptyRule();
   private static final String           RULE_SET_NAME    = "RuleSetName";
   private static final IFlexRule        WARNING_RULE     = new WarningRule();
   private IFlexFile                     abstractRowData;
   private IFlexFile                     abstractRowDataWithPackage;
   private IFlexFile                     iterationsListMxml;
   private final ViolationPosition       position;

   public ViolationTest()
   {
      super();

      position = ViolationPosition.create( 10,
                                           20,
                                           30,
                                           30 );
   }

   @Before
   public void setUp()
   {
      abstractRowData = getTestFiles().get( "AbstractRowData.as" );
      abstractRowDataWithPackage = getTestFiles().get( "com.adobe.ac.AbstractRowData.as" );
      iterationsListMxml = getTestFiles().get( "com.adobe.ac.ncss.mxml.IterationsList.mxml" );
   }

   @Test
   public void testCompareTo()
   {
      final Violation infoViolation = new Violation( position, INFO_RULE, null );
      final Violation infoViolation2 = new Violation( ViolationPosition.create( 11,
                                                                                20,
                                                                                30,
                                                                                30 ), INFO_RULE, null );
      final Violation warningViolation = new Violation( position, WARNING_RULE, null );
      final Violation warningViolation2 = new Violation( position, WARNING_RULE, null );

      assertEquals( -1,
                    infoViolation.compareTo( infoViolation2 ) );
      assertEquals( -1,
                    warningViolation.compareTo( infoViolation ) );
      assertEquals( 0,
                    warningViolation.compareTo( warningViolation2 ) );
      assertEquals( 1,
                    infoViolation2.compareTo( infoViolation ) );
      assertEquals( 1,
                    infoViolation.compareTo( warningViolation ) );
   }

   @Test
   public void testGetActualValueForTheCurrentViolation()
   {
      final IThresholdedRule thresholdRule = ( IThresholdedRule ) new Violation( position, INFO_RULE, null ).getRule();

      assertEquals( 0,
                    thresholdRule.getActualValueForTheCurrentViolation() );
   }

   @Test
   public void testGetClassName()
   {
      assertEquals( "",
                    new Violation( position, INFO_RULE, null ).getClassName() );
   }

   @Test
   public void testGetDefaultThreshold()
   {
      assertEquals( Integer.valueOf( ViolationPriority.LOW.toString() ),
                    Integer.valueOf( new Violation( position, INFO_RULE, null ).getRule().getPriority() ) );
   }

   @Test
   public void testGetDescription()
   {
      assertEquals( "emptyMessage",
                    new Violation( position, INFO_RULE, null ).getDescription() );
   }

   @Test
   public void testGetEndColumn()
   {
      assertEquals( 30,
                    new Violation( position, INFO_RULE, null ).getEndColumn() );
   }

   @Test
   public void testGetFileName()
   {
      final Violation infoViolation = new Violation( position, INFO_RULE, abstractRowData );

      assertEquals( "AbstractRowData.as",
                    infoViolation.getFilename() );

      final Violation infoViolation2 = new Violation( position, INFO_RULE, abstractRowDataWithPackage );

      assertEquals( "com.adobe.ac.AbstractRowData.as",
                    infoViolation2.getFilename() );
   }

   @Test
   public void testGetMethodName()
   {
      assertEquals( "",
                    new Violation( position, INFO_RULE, abstractRowData ).getMethodName() );

   }

   @Test
   public void testGetPackageName()
   {
      assertEquals( "",
                    new Violation( position, INFO_RULE, abstractRowData ).getPackageName() );

      assertEquals( "com.adobe.ac",
                    new Violation( position, INFO_RULE, abstractRowDataWithPackage ).getPackageName() );
   }

   @Test
   public void testGetRuleMessage()
   {
      assertEquals( "emptyMessage",
                    new Violation( position, INFO_RULE, null ).getRuleMessage() );

      assertEquals( "warning message",
                    new Violation( position, WARNING_RULE, null ).getRuleMessage() );
   }

   @Test
   public void testGetVariableName()
   {
      assertEquals( "",
                    new Violation( position, INFO_RULE, null ).getVariableName() );
   }

   @Test
   public void testIsSuppressed()
   {
      assertFalse( "",
                   new Violation( position, INFO_RULE, abstractRowData ).isSuppressed() );
   }

   @Test
   public void testToXmlString() throws FileNotFoundException,
                                URISyntaxException
   {
      final Violation infoViolation = new Violation( ViolationPosition.create( BEGINNING_LINE,
                                                                               ENDING_LINE,
                                                                               BEGINNING_COLUMN,
                                                                               ENDING_COLUMN ),
                                                     INFO_RULE,
                                                     null );

      assertEquals( "As3 file at a root level",
                    "      <violation beginline=\""
                          + BEGINNING_LINE + "\" endline=\"" + ENDING_LINE + "\" begincolumn=\""
                          + BEGINNING_COLUMN + "\" endcolumn=\"" + ENDING_COLUMN + "\" rule=\""
                          + INFO_RULE.getRuleName() + "\" ruleset=\"" + RULE_SET_NAME + "\" package=\""
                          + abstractRowData.getPackageName() + "\" class=\"" + abstractRowData.getClassName()
                          + "\" externalInfoUrl=\"" + Violation.RULESET_CREATOR_URL + "EmptyRule\" "
                          + "priority=\"" + INFO_RULE.getPriority() + "\">" + "emptyMessage" + "</violation>"
                          + infoViolation.getNewLine(),
                    infoViolation.toXmlString( abstractRowData,
                                               RULE_SET_NAME ) );

      final Violation warningViolation = new Violation( ViolationPosition.create( BEGINNING_LINE,
                                                                                  ENDING_LINE,
                                                                                  BEGINNING_COLUMN,
                                                                                  ENDING_COLUMN ),
                                                        WARNING_RULE,
                                                        null );

      assertEquals( "As3 File at a not-root level",
                    "      <violation beginline=\""
                          + BEGINNING_LINE + "\" endline=\"" + ENDING_LINE + "\" begincolumn=\""
                          + BEGINNING_COLUMN + "\" endcolumn=\"" + ENDING_COLUMN + "\" rule=\""
                          + WARNING_RULE.getRuleName() + "\" ruleset=\"" + RULE_SET_NAME + "\" package=\""
                          + abstractRowDataWithPackage.getPackageName() + "\" class=\""
                          + abstractRowData.getClassName() + "\" externalInfoUrl=\""
                          + Violation.RULESET_CREATOR_URL + "WarningRule\" " + "priority=\""
                          + WARNING_RULE.getPriority() + "\">" + "warning message" + "</violation>"
                          + warningViolation.getNewLine(),
                    warningViolation.toXmlString( abstractRowDataWithPackage,
                                                  RULE_SET_NAME ) );

      assertEquals( "Mxml File at a not-root level",
                    "      <violation beginline=\""
                          + BEGINNING_LINE + "\" endline=\"" + ENDING_LINE + "\" begincolumn=\""
                          + BEGINNING_COLUMN + "\" endcolumn=\"" + ENDING_COLUMN + "\" rule=\""
                          + WARNING_RULE.getRuleName() + "\" ruleset=\"" + RULE_SET_NAME + "\" package=\""
                          + iterationsListMxml.getPackageName() + "\" class=\""
                          + iterationsListMxml.getClassName() + "\" externalInfoUrl=\""
                          + Violation.RULESET_CREATOR_URL + "WarningRule\" " + "priority=\""
                          + WARNING_RULE.getPriority() + "\">" + "warning message" + "</violation>"
                          + warningViolation.getNewLine(),
                    warningViolation.toXmlString( iterationsListMxml,
                                                  RULE_SET_NAME ) );
   }
}
