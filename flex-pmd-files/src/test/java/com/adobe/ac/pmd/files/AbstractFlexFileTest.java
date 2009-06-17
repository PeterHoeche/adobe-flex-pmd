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
package com.adobe.ac.pmd.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;

public class AbstractFlexFileTest extends FlexPmdTestBase
{
   private IAs3File  as3;
   private IMxmlFile mainMxml;
   private IMxmlFile mxml;

   @Before
   public void init() throws FileNotFoundException,
                     URISyntaxException
   {
      as3 = ( IAs3File ) getTestFiles().get( "AbstractRowData.as" );
      mainMxml = ( IMxmlFile ) getTestFiles().get( "Main.mxml" );
      mxml = ( IMxmlFile ) getTestFiles().get( "com.adobe.ac.ncss.mxml.IterationsList.mxml" );
   }

   @Test
   public void testCompareTo()
   {
      assertTrue( "",
                  as3.compareTo( mxml ) < 0 );
      assertTrue( "",
                  mxml.compareTo( as3 ) > 0 );
      assertEquals( "",
                    0,
                    as3.compareTo( as3 ) );
   }

   @Test
   public void testContains()
   {
      assertTrue( "",
                  as3.contains( "logger",
                                0 ) );
      assertFalse( "",
                   as3.contains( "loggerr",
                                 0 ) );
      assertFalse( "",
                   as3.contains( "addEventListener",
                                 108 ) );
   }

   @Test
   public void testDoesCurrentLineContainCommentClosingTag()
   {
      assertTrue( "",
                  as3.doesCurrentLineContainCommentClosingTag( "*/" ) );
      assertTrue( "",
                  mxml.doesCurrentLineContainCommentClosingTag( "-->" ) );
      assertFalse( "",
                   mxml.doesCurrentLineContainCommentClosingTag( "*/" ) );
      assertFalse( "",
                   as3.doesCurrentLineContainCommentClosingTag( "-->" ) );
   }

   @Test
   public void testDoesCurrentLineContainCommentOpeningTag()
   {
      assertTrue( "",
                  as3.doesCurrentLineContainCommentOpeningTag( "/*" ) );
      assertTrue( "",
                  mxml.doesCurrentLineContainCommentOpeningTag( "<!--" ) );
      assertFalse( "",
                   mxml.doesCurrentLineContainCommentOpeningTag( "<--" ) );
      assertFalse( "",
                   as3.doesCurrentLineContainCommentOpeningTag( "**" ) );
   }

   @Test
   public void testDoesCurrentLineContainOneLineComment()
   {
      assertTrue( "",
                  as3.doesCurrentLineContainOneLineComment( "//" ) );
      assertFalse( "",
                   mxml.doesCurrentLineContainOneLineComment( "<!-- -->" ) );
      assertFalse( "",
                   mxml.doesCurrentLineContainOneLineComment( "<--" ) );
      assertFalse( "",
                   as3.doesCurrentLineContainOneLineComment( "**" ) );
   }

   @Test
   public void testGetClassName()
   {
      assertEquals( "",
                    "AbstractRowData.as",
                    as3.getClassName() );
      assertEquals( "",
                    "IterationsList.mxml",
                    mxml.getClassName() );
   }

   @Test
   public void testGetFilePath()
   {
      assertNotNull( "",
                     as3.getFilePath() );
      assertNotNull( "",
                     mxml.getFilePath() );
      assertNotNull( "",
                     mainMxml.getFilePath() );
   }

   @Test
   public void testGetLines()
   {
      assertEquals( "",
                    141,
                    as3.getLines().size() );
      assertEquals( "",
                    100,
                    mxml.getLines().size() );
   }

   @Test
   public void testGetPackageName()
   {
      assertEquals( "",
                    "",
                    as3.getPackageName() );
      assertEquals( "",
                    "com.adobe.ac.ncss.mxml",
                    mxml.getPackageName() );
   }

   @Test
   public void testGetPath()
   {
      assertEquals( "",
                    "AbstractRowData.as",
                    as3.getFullyQualifiedName() );
      assertEquals( "",
                    "com.adobe.ac.ncss.mxml.IterationsList.mxml",
                    mxml.getFullyQualifiedName() );
   }

   @Test
   public void testIsMainApplication()
   {
      assertFalse( "",
                   as3.isMainApplication() );
      assertFalse( "",
                   mxml.isMainApplication() );
      assertTrue( "",
                  mainMxml.isMainApplication() );
   }

   @Test
   public void testIsMxml()
   {
      assertFalse( "",
                   as3.isMxml() );
      assertTrue( "",
                  mxml.isMxml() );
   }
}
