package com.adobe.ac.pmd.rules.binding;

import java.util.regex.Matcher;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.AbstractMaximizedRegexpBasedRule;

public class TooLongBindingExpressionRule extends AbstractMaximizedRegexpBasedRule // NO_UCD
{
   private int currentCount;

   public final int getActualValueForTheCurrentViolation()
   {
      return currentCount;
   }

   public final int getDefaultThreshold()
   {
      return 2;
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
   protected final String getRegexp()
   {
      return ".*=\"\\{([^\\}]*)\\}\".*";
   }

   @Override
   protected final boolean isViolationDetectedOnThisMatchingLine( final String line,
                                                                  final IFlexFile file )
   {
      final Matcher matcher = getMatcher( line );

      matcher.matches();
      currentCount = countChar( matcher.group( 1 ),
                                '.' );
      return matcher.matches()
            && currentCount > getThreshold();
   }

   private int countChar( final String input,
                          final char charToSearch )
   {
      int charCount = 0;

      for ( int i = 0; i < input.length(); i++ )
      {
         if ( input.charAt( i ) == charToSearch )
         {
            charCount++;
         }
      }

      return charCount;
   }
}
