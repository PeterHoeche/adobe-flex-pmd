package com.adobe.ac.pmd.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedFlexRule;

class EmptyRule extends AbstractMaximizedFlexRule
{
   public int getActualValueForTheCurrentViolation()
   {
      return 0;
   }

   public int getDefaultThreshold()
   {
      return 10;
   }

   @Override
   public String getMessage()
   {
      return "emptyMessage";
   }

   @Override
   public int getThreshold()
   {
      return getDefaultThreshold();
   }

   @Override
   public boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return true;
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }

   @Override
   protected List< IFlexViolation > processFileBody( final IPackage rootNode,
                                                     final IFlexFile file,
                                                     final Map< String, IFlexFile > files )
   {
      return new ArrayList< IFlexViolation >();
   }
}
