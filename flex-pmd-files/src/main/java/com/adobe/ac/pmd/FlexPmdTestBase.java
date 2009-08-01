package com.adobe.ac.pmd;

import java.io.File;
import java.util.Map;

import com.adobe.ac.pmd.files.IFlexFile;

/**
 * This is a base class for any FlexPMD rule test case.
 * 
 * @author xagnetti
 */
public class FlexPmdTestBase // NO_UCD
{
   protected static final String          BEGIN_LINE_NOT_CORRECT        = "Begining line is not correct";     // NO_UCD
   protected static final String          END_LINE_NOT_CORRECT          = "Ending line is not correct";       // NO_UCD
   protected static final String          VIOLATIONS_NUMBER_NOT_CORRECT = "Violations number is not correct"; // NO_UCD

   /**
    * Test files placeholder. The key is the qualified file name
    */
   private final Map< String, IFlexFile > testFiles                     = ResourcesManagerTest.getInstance()
                                                                                              .getTestFiles();

   protected FlexPmdTestBase()
   {
   }

   protected final File getTestDirectory() // NO_UCD
   {
      return ResourcesManagerTest.getInstance().getTestRootDirectory();
   }

   protected final Map< String, IFlexFile > getTestFiles() // NO_UCD
   {
      return testFiles;
   }
}