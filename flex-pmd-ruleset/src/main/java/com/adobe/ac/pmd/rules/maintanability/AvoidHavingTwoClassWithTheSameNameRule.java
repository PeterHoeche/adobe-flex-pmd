package com.adobe.ac.pmd.rules.maintanability;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class AvoidHavingTwoClassWithTheSameNameRule extends AbstractFlexRule
{
   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   @Override
   protected final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return true;
   }

   @Override
   protected final List< IFlexViolation > processFileBody( final IPackage packageToBeProcessed,
                                                           final IFlexFile fileToBeProcessed,
                                                           final Map< String, IFlexFile > files )
   {
      final List< IFlexViolation > violations = new ArrayList< IFlexViolation >();
      final ViolationPosition position = new ViolationPosition( -1, -1 );

      for ( final Entry< String, IFlexFile > currentFileEntry : files.entrySet() )
      {
         final IFlexFile currentFile = currentFileEntry.getValue();

         if ( classNamesEqualsIgnoringExtension( currentFile.getClassName(),
                                                 fileToBeProcessed.getClassName() )
               && !currentFile.getPackageName().equals( fileToBeProcessed.getPackageName() ) )
         {
            addViolation( violations,
                          fileToBeProcessed,
                          position );
            break;
         }
      }
      return violations;
   }

   private boolean classNamesEqualsIgnoringExtension( final String firstClassName,
                                                      final String secondClassName )
   {
      return extractingClassName( firstClassName ).equals( extractingClassName( secondClassName ) );
   }

   private String extractingClassName( final String name )
   {
      final String[] split = name.split( "\\." );

      return split[ split.length - 2 ];
   }
}
