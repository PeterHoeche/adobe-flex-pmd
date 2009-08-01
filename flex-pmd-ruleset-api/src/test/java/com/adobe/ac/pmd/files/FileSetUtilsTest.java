package com.adobe.ac.pmd.files;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import net.sourceforge.pmd.PMDException;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.nodes.IPackage;

public class FileSetUtilsTest extends FlexPmdTestBase
{
   @Test
   public void testComputeAsts() throws PMDException
   {
      final Map< String, IPackage > asts = FileSetUtils.computeAsts( getTestFiles() );

      assertTrue( asts.size() > 49 );
   }
}
