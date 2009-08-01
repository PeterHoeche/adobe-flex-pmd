package com.adobe.ac.pmd.nodes.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import net.sourceforge.pmd.PMDException;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class PackageNodeTest extends FlexPmdTestBase
{
   private final IPackage buttonRenderer;
   private final IPackage modelLocator;
   private final IPackage stylePackage;

   public PackageNodeTest() throws PMDException
   {
      final IParserNode ast = FileSetUtils.buildAst( getTestFiles().get( "SkinStyles.as" ) );
      stylePackage = NodeFactory.createPackage( ast );

      final IParserNode buttonRendererAst = FileSetUtils.buildAst( getTestFiles().get( "DeleteButtonRenderer.mxml" ) );
      buttonRenderer = NodeFactory.createPackage( buttonRendererAst );

      final IParserNode modelLocatorAst = FileSetUtils.buildAst( getTestFiles().get( "cairngorm."
            + "NonBindableModelLocator.as" ) );
      modelLocator = NodeFactory.createPackage( modelLocatorAst );
   }

   @Test
   public void testConstructMxmlFile() throws IOException,
                                      TokenException,
                                      PMDException
   {
      assertNotNull( buttonRenderer.getClassNode() );
      assertEquals( "",
                    buttonRenderer.getName() );
      assertEquals( 0,
                    buttonRenderer.getImports().size() );

   }

   @Test
   public void testConstructNamespace() throws IOException,
                                       TokenException,
                                       PMDException
   {
      final IParserNode ast = FileSetUtils.buildAst( getTestFiles().get( "schedule_internal.as" ) );
      final IPackage namespacePackage = NodeFactory.createPackage( ast );

      assertNull( namespacePackage.getClassNode() );
      assertEquals( "flexlib.scheduling.scheduleClasses",
                    namespacePackage.getName() );
      assertEquals( 0,
                    namespacePackage.getImports().size() );
   }

   @Test
   public void testConstructStyles()
   {
      assertNull( stylePackage.getClassNode() );
      assertEquals( "",
                    stylePackage.getName() );
      assertEquals( 0,
                    stylePackage.getImports().size() );
   }

   @Test
   public void testFullyQualifiedName()
   {
      assertEquals( "",
                    stylePackage.getFullyQualifiedClassName() );
      assertEquals( "DeleteButtonRenderer",
                    buttonRenderer.getFullyQualifiedClassName() );
      assertEquals( "com.adobe.ac.sample.model.ModelLocator",
                    modelLocator.getFullyQualifiedClassName() );
   }
}
