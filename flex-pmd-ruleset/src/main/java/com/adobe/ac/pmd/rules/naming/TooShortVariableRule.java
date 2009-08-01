package com.adobe.ac.pmd.rules.naming;

import java.util.Map;
import java.util.regex.Matcher;

import net.sourceforge.pmd.PropertyDescriptor;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.IThresholdedRule;

public class TooShortVariableRule extends AbstractRegexpBasedRule implements IThresholdedRule
{
   private int length;

   public final int getActualValueForTheCurrentViolation()
   {
      return length;
   }

   public final int getDefaultThreshold()
   {
      return 3;
   }

   public final int getThreshold()
   {
      return getIntProperty( propertyDescriptorFor( getThresholdName() ) );
   }

   public final String getThresholdName()
   {
      return MINIMUM;
   }

   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return true;
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }

   @Override
   protected final String getRegexp()
   {
      return ".*var (.*):.*";
   }

   @Override
   protected final boolean isViolationDetectedOnThisMatchingLine( final String line,
                                                                  final IFlexFile file )
   {
      final Matcher matcher = getMatcher( line );
      boolean result = false;

      if ( !line.contains( "for" )
            && matcher.matches() )
      {
         length = matcher.group( 1 ).trim().length();

         result = length < getThreshold();
      }
      return result;
   }

   @Override
   protected final Map< String, PropertyDescriptor > propertiesByName()
   {
      return getRuleProperties( this );
   }
}
