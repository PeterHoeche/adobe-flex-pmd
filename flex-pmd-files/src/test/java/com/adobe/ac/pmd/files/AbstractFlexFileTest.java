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
                                 109 ) );
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
                    142,
                    as3.getLinesNb() );
      assertEquals( "",
                    100,
                    mxml.getLinesNb() );
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
