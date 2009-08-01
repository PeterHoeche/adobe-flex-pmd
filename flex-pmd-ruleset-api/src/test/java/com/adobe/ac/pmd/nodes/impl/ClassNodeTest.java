package com.adobe.ac.pmd.nodes.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import net.sourceforge.pmd.PMDException;

import org.junit.Before;
import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class ClassNodeTest extends FlexPmdTestBase
{
   private IClass modelLocator;
   private IClass nonBindableModelLocator;
   private IClass radonDataGrid;

   @Before
   public void setup() throws IOException,
                      TokenException,
                      PMDException
   {
      IParserNode ast = FileSetUtils.buildAst( getTestFiles().get( "RadonDataGrid.as" ) );
      radonDataGrid = NodeFactory.createPackage( ast ).getClassNode();
      ast = FileSetUtils.buildAst( getTestFiles().get( "cairngorm.BindableModelLocator.as" ) );
      modelLocator = NodeFactory.createPackage( ast ).getClassNode();
      ast = FileSetUtils.buildAst( getTestFiles().get( "cairngorm.NonBindableModelLocator.as" ) );
      nonBindableModelLocator = NodeFactory.createPackage( ast ).getClassNode();
   }

   @Test
   public void testGetAttributes()
   {
      assertEquals( 0,
                    radonDataGrid.getAttributes().size() );
   }

   @Test
   public void testGetConstants()
   {
      assertEquals( 0,
                    radonDataGrid.getConstants().size() );
   }

   @Test
   public void testGetConstructor()
   {
      assertNotNull( radonDataGrid.getConstructor() );
   }

   @Test
   public void testGetExtensionName()
   {
      assertEquals( "DataGrid",
                    radonDataGrid.getExtensionName() );
   }

   @Test
   public void testGetImplementations()
   {
      assertEquals( 0,
                    radonDataGrid.getImplementations().size() );
      assertEquals( 1,
                    modelLocator.getImplementations().size() );
   }

   @Test
   public void testGetMetaDataList()
   {
      assertEquals( 0,
                    radonDataGrid.getMetaDataCount() );
      assertNotNull( modelLocator.getMetaData( "Bindable" ) );
   }

   @Test
   public void testGetName()
   {
      assertEquals( "RadonDataGrid",
                    radonDataGrid.getName() );
   }

   @Test
   public void testIsFinal()
   {
      assertFalse( radonDataGrid.isFinal() );
   }

   @Test
   public void testVisibility()
   {
      assertTrue( radonDataGrid.isPublic() );
      assertTrue( modelLocator.is( Modifier.PROTECTED ) );
      assertTrue( nonBindableModelLocator.is( Modifier.PRIVATE ) );
      assertFalse( nonBindableModelLocator.is( Modifier.PROTECTED ) );
      assertFalse( nonBindableModelLocator.isPublic() );
      assertFalse( radonDataGrid.is( Modifier.PROTECTED ) );
      assertFalse( radonDataGrid.is( Modifier.PRIVATE ) );
      assertFalse( modelLocator.isPublic() );
      assertFalse( modelLocator.is( Modifier.PRIVATE ) );
   }
}
