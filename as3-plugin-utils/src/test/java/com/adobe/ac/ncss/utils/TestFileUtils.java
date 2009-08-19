/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
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
 *      * Neither the name of the Adobe Systems, Inc. nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.ncss.utils;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import com.adobe.ac.ncss.filters.FlexFilter;

public class TestFileUtils extends TestCase
{
   @Test
   public void testIsLineACorrectStatement()
   {
      assertFalse( FileUtils.isLineACorrectStatement( "    { " ) );
      assertFalse( FileUtils.isLineACorrectStatement( "    } " ) );
      assertFalse( FileUtils.isLineACorrectStatement( "{" ) );
      assertFalse( FileUtils.isLineACorrectStatement( "}" ) );
      assertFalse( FileUtils.isLineACorrectStatement( "    class MyModel " ) );
      assertFalse( FileUtils.isLineACorrectStatement( "class MyModel" ) );
      assertFalse( FileUtils.isLineACorrectStatement( "function lala() : void" ) );
      assertFalse( FileUtils.isLineACorrectStatement( "var i : int" ) );
      assertFalse( FileUtils.isLineACorrectStatement( "lalla; cdcdvf" ) );
      assertTrue( FileUtils.isLineACorrectStatement( "var i : int;" ) );
      assertTrue( FileUtils.isLineACorrectStatement( "  foo( bar );" ) );
      assertTrue( FileUtils.isLineACorrectStatement( "lalla;" ) );
   }

   @Test
   public void testListFiles()
   {
      assertEquals( 24,
                    FileUtils.listFiles( new File( "." ),
                                         new FlexFilter(),
                                         true ).size() );

      assertEquals( 0,
                    FileUtils.listFiles( new File( "./src/main/java" ),
                                         new FlexFilter(),
                                         true ).size() );
   }

   @Test
   public void testReadFile()
   {
      assertEquals( 75,
                    FileUtils.readFile( new File( "./src/test/resources/com/adobe/ac/ncss/mxml/IterationsList.mxml" ) )
                             .size() );

      assertEquals( 0,
                    FileUtils.readFile( new File( "./DoNotExistFile.as" ) ).size() );
   }
}
