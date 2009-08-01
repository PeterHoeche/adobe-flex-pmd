package com.adobe.ac.pmd.rules.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IPackage;

public abstract class AbstractRegexpBasedRule extends AbstractFlexRule
{
   @Override
   public final List< IFlexViolation > processFileBody( final IPackage rootNode,
                                                        final IFlexFile file,
                                                        final Map< String, IFlexFile > files )
   {
      final List< IFlexViolation > violations = new ArrayList< IFlexViolation >();

      for ( int i = 1; i <= file.getLinesNb(); i++ )
      {
         final String line = file.getLineAt( i );

         if ( doesCurrentLineMacthes( line )
               && isViolationDetectedOnThisMatchingLine( line,
                                                         file ) )
         {
            addViolation( violations,
                          file,
                          ViolationPosition.create( i,
                                                    i,
                                                    0,
                                                    line.length() ) );
         }
      }
      return violations;
   }

   final boolean doesCurrentLineMacthes( final String line )
   {
      return getMatcher( line ).matches();
   }

   protected final Matcher getMatcher( final String line )
   {
      final Pattern pattern = Pattern.compile( getRegexp() );
      final Matcher matcher = pattern.matcher( line );

      return matcher;
   }

   protected abstract String getRegexp();

   protected abstract boolean isViolationDetectedOnThisMatchingLine( final String line,
                                                                     final IFlexFile file );
}