package com.adobe.ac.pmd.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

class WarningRule extends AbstractFlexRule
{
   @Override
   public String getMessage()
   {
      return "warning message.";
   }

   @Override
   public boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return true;
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected List< IFlexViolation > processFileBody( final IPackage rootNode,
                                                     final IFlexFile file,
                                                     final Map< String, IFlexFile > files )
   {
      return new ArrayList< IFlexViolation >();
   }
}
