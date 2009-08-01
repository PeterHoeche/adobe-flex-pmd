package com.adobe.ac.pmd.rules.style;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.files.IMxmlFile;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class CopyrightMissingRule extends AbstractFlexRule
{
   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return true;
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
      final String commentOpeningTag = file.getCommentOpeningTag();
      final boolean hasMxmlCopyright = file instanceof IMxmlFile
            && file.getLinesNb() > 1 && file.getLineAt( 2 ).contains( commentOpeningTag );
      final String firstLine = file.getLineAt( 1 );
      final boolean isFirstLineContainCopyright = firstLine.startsWith( commentOpeningTag );

      if ( !isFirstLineContainCopyright
            && !hasMxmlCopyright )
      {
         addViolation( violations,
                       file,
                       new ViolationPosition( -1, -1 ) );
      }

      return violations;
   }
}
