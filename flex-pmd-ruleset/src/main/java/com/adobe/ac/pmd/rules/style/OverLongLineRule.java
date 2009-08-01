package com.adobe.ac.pmd.rules.style;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.ViolationPosition;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedFlexRule;

public class OverLongLineRule extends AbstractMaximizedFlexRule
{
   private int currentLineLength;

   public final int getActualValueForTheCurrentViolation()
   {
      return currentLineLength;
   }

   public final int getDefaultThreshold()
   {
      return 120;
   }

   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return true;
   }

   @Override
   public final List< IFlexViolation > processFileBody( final IPackage rootNode,
                                                        final IFlexFile file,
                                                        final Map< String, IFlexFile > files )
   {
      final List< IFlexViolation > violations = new ArrayList< IFlexViolation >();

      if ( isConcernedByTheGivenFile( file ) )
      {
         for ( int i = 1; i <= file.getLinesNb(); i++ )
         {
            final String line = file.getLineAt( i );

            if ( !line.trim().startsWith( "import" )
                  && line.length() >= getThreshold() )
            {
               currentLineLength = line.length();
               final ViolationPosition position = ViolationPosition.create( i,
                                                                            i,
                                                                            0,
                                                                            currentLineLength );

               addViolation( violations,
                             file,
                             position );
            }
         }
      }
      return violations;
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }
}
