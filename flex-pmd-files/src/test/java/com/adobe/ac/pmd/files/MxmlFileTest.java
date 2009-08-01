package com.adobe.ac.pmd.files;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;

public class MxmlFileTest extends FlexPmdTestBase
{
   private IMxmlFile deleteRenderer;
   private IMxmlFile iterationsList;
   private IMxmlFile nestedComponent;

   @Before
   public void setUp()
   {
      iterationsList = ( IMxmlFile ) getTestFiles().get( "com.adobe.ac.ncss.mxml.IterationsList.mxml" );
      nestedComponent = ( IMxmlFile ) getTestFiles().get( "com.adobe.ac.ncss.mxml.NestedComponent.mxml" );
      deleteRenderer = ( IMxmlFile ) getTestFiles().get( "DeleteButtonRenderer.mxml" );
   }

   @Test
   public void testGetActionScriptScriptBlock()
   {
      final String[] deleteRendererLines = deleteRenderer.getScriptBlock();

      assertEquals( "",
                    "package {",
                    deleteRendererLines[ 0 ] );
      assertEquals( "",
                    "class DeleteButtonRenderer{",
                    deleteRendererLines[ 1 ] );
      assertEquals( "",
                    107,
                    deleteRendererLines.length );
      assertEquals( "",
                    "            import com.adobe.ac.pmd.model.Rule;",
                    deleteRendererLines[ 49 ] );
      assertEquals( "",
                    "}}",
                    deleteRendererLines[ deleteRendererLines.length - 1 ] );
   }

   @Test
   public void testGetMxmlScriptBlock()
   {
      final String[] iterationsListLines = iterationsList.getScriptBlock();

      assertEquals( "",
                    "package com.adobe.ac.ncss.mxml{",
                    iterationsListLines[ 0 ] );
      assertEquals( "",
                    "class IterationsList{",
                    iterationsListLines[ 1 ] );
      assertEquals( "",
                    "         import com.adobe.ac.anthology.model.object.IterationModelLocator;",
                    iterationsListLines[ 40 ] );
      assertEquals( "",
                    "}}",
                    iterationsListLines[ iterationsListLines.length - 1 ] );
      assertEquals( "",
                    100,
                    iterationsListLines.length );

   }

   @Test
   public void testGetMxmlScriptBlock2()
   {
      final String[] nestedLines = nestedComponent.getScriptBlock();

      assertEquals( "",
                    "package com.adobe.ac.ncss.mxml{",
                    nestedLines[ 0 ] );
      assertEquals( "",
                    "class NestedComponent{",
                    nestedLines[ 1 ] );
      assertEquals( "",
                    57,
                    nestedLines.length );
      assertEquals( "",
                    "}}",
                    nestedLines[ nestedLines.length - 1 ] );
   }
}
