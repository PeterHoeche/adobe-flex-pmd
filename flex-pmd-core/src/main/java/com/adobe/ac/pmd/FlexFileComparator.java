package com.adobe.ac.pmd;

import java.io.Serializable;
import java.util.Comparator;

import com.adobe.ac.pmd.files.IFlexFile;

class FlexFileComparator implements Comparator< IFlexFile >, Serializable
{
   private static final long serialVersionUID = -7929554744612318974L;

   public final int compare( final IFlexFile firstFile, // NO_UCD
                             final IFlexFile secondFile )
   {
      return firstFile.compareTo( secondFile );
   }
}
