package com.adobe.ac.pmd.nodes.utils;

import static org.junit.Assert.assertEquals;
import net.sourceforge.pmd.PMDException;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.impl.NodeFactory;
import com.adobe.ac.pmd.parser.IParserNode;

public class FunctionUtilsTest extends FlexPmdTestBase
{
   @Test
   public void testComputeFunctionLength() throws PMDException
   {
      final IFlexFile file = getTestFiles().get( "RadonDataGrid.as" );
      final IParserNode dataGridAst = FileSetUtils.buildAst( file );
      final IClass radonDataGrid = NodeFactory.createPackage( dataGridAst ).getClassNode();

      assertEquals( 6,
                    FunctionUtils.computeFunctionLength( file,
                                                         radonDataGrid.getFunctions().get( 0 ).getBody() ) );

      assertEquals( 9,
                    FunctionUtils.computeFunctionLength( file,
                                                         radonDataGrid.getFunctions().get( 1 ).getBody() ) );

      assertEquals( 21,
                    FunctionUtils.computeFunctionLength( file,
                                                         radonDataGrid.getFunctions().get( 2 ).getBody() ) );

      assertEquals( 16,
                    FunctionUtils.computeFunctionLength( file,
                                                         radonDataGrid.getFunctions().get( 3 ).getBody() ) );

      assertEquals( 10,
                    FunctionUtils.computeFunctionLength( file,
                                                         radonDataGrid.getFunctions().get( 4 ).getBody() ) );
   }
}
