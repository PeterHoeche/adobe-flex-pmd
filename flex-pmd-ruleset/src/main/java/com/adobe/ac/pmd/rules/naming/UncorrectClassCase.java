package com.adobe.ac.pmd.rules.naming;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UncorrectClassCase extends AbstractFlexRule
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
      final char firstChar = file.getClassName().charAt( 0 );

      if ( firstChar < 'A'
            || firstChar > 'Z' )
      {
         addViolation( violations,
                       file,
                       new ViolationPosition( 1, file.getLinesNb() ) );
      }
      return violations;
   }
}
