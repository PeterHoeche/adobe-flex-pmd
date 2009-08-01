package com.adobe.ac.pmd.rules.mxml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.files.IMxmlFile;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.ViolationPosition;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedFlexRule;

public class TooLongScriptBlockRule extends AbstractMaximizedFlexRule
{
   private int linesInScriptBlock;

   public final int getActualValueForTheCurrentViolation()
   {
      return linesInScriptBlock;
   }

   public final int getDefaultThreshold()
   {
      return 50;
   }

   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return file.isMxml();
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected final List< IFlexViolation > processFileBody( final IPackage rootNode,
                                                           final IFlexFile file,
                                                           final Map< String, IFlexFile > files )
   {
      final List< IFlexViolation > violations = new ArrayList< IFlexViolation >();
      final IMxmlFile mxml = ( IMxmlFile ) file;

      linesInScriptBlock = mxml.getEndingScriptBlock()
            - mxml.getBeginningScriptBlock();

      if ( linesInScriptBlock >= getThreshold() )
      {
         addViolation( violations,
                       file,
                       new ViolationPosition( mxml.getBeginningScriptBlock(), mxml.getEndingScriptBlock() ) );
      }
      return violations;
   }
}
