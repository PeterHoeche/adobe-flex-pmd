package com.adobe.ac.pmd.nodes.impl;

import static org.junit.Assert.assertEquals;
import net.sourceforge.pmd.PMDException;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.nodes.IMetaDataListHolder;
import com.adobe.ac.pmd.parser.IParserNode;

public class MetaDataNodeTest extends FlexPmdTestBase
{
   private static final String       NAME_DAY_CHANGE_TYPE_DEFAULT_NAME_EVENT = "name = \"dayChange\" ,"
                                                                                   + " type = \"DefaultNameEvent\"";
   private final IMetaDataListHolder modelLocator;
   private final IMetaDataListHolder unboundMetaData;

   public MetaDataNodeTest() throws PMDException
   {
      super();

      IParserNode ast = FileSetUtils.buildAst( getTestFiles().get( "cairngorm.BindableModelLocator.as" ) );
      modelLocator = NodeFactory.createPackage( ast ).getClassNode();
      ast = FileSetUtils.buildAst( getTestFiles().get( "UnboundMetadata.as" ) );
      unboundMetaData = NodeFactory.createPackage( ast ).getClassNode();
   }

   @Test
   public void testGetMetaDataName()
   {
      assertEquals( "Bindable",
                    modelLocator.getMetaData( "Bindable" ).get( 0 ).getName() );
      assertEquals( "Event",
                    unboundMetaData.getMetaData( "Event" ).get( 0 ).getName() );
   }

   @Test
   public void testGetMetaDataParameter()
   {
      assertEquals( "",
                    modelLocator.getMetaData( "Bindable" ).get( 0 ).getParameter() );
      assertEquals( NAME_DAY_CHANGE_TYPE_DEFAULT_NAME_EVENT,
                    unboundMetaData.getMetaData( "Event" ).get( 0 ).getParameter() );
   }
}
