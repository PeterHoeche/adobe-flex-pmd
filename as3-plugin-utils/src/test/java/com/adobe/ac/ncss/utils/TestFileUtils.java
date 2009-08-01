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
