package com.adobe.ac.pmd.files.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import net.sourceforge.pmd.PMDException;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.IFlexFile;

public class FileUtilsTest extends FlexPmdTestBase
{
   @Test
   public void testComputeFilesList() throws PMDException
   {
      Map< String, IFlexFile > files;
      files = FileUtils.computeFilesList( getTestDirectory(),
                                          null,
                                          "",
                                          null );

      Assert.assertEquals( 82,
                           files.size() );

      final List< String > excludePatterns = new ArrayList< String >();
      excludePatterns.add( "bug" );
      files = FileUtils.computeFilesList( getTestDirectory(),
                                          null,
                                          "",
                                          excludePatterns );

      Assert.assertEquals( 71,
                           files.size() );
   }
}
