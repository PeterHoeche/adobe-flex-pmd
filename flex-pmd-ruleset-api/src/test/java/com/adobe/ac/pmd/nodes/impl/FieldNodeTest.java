package com.adobe.ac.pmd.nodes.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sourceforge.pmd.PMDException;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.parser.IParserNode;

public class FieldNodeTest extends FlexPmdTestBase
{
   @Test
   public void testVisibility() throws PMDException
   {
      final IParserNode ast = FileSetUtils.buildAst( getTestFiles().get( "cairngorm.NonBindableModelLocator.as" ) );
      final IClass nonBindableModelLocator = NodeFactory.createPackage( ast ).getClassNode();
      final IAttribute first = nonBindableModelLocator.getAttributes().get( 0 );
      final IAttribute second = nonBindableModelLocator.getAttributes().get( 1 );
      final IAttribute third = nonBindableModelLocator.getAttributes().get( 2 );

      assertTrue( first.is( Modifier.PRIVATE ) );
      assertFalse( first.isPublic() );
      assertFalse( first.is( Modifier.PROTECTED ) );
      assertTrue( second.is( Modifier.PROTECTED ) );
      assertFalse( second.isPublic() );
      assertFalse( second.is( Modifier.PRIVATE ) );
      assertTrue( third.isPublic() );
      assertFalse( third.is( Modifier.PROTECTED ) );
      assertFalse( third.is( Modifier.PRIVATE ) );
   }
}
