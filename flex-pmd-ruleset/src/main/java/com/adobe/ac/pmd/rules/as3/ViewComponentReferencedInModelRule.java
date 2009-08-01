package com.adobe.ac.pmd.rules.as3;

import java.util.Locale;
import java.util.regex.Matcher;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.rules.core.AbstractRegexpBasedRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class ViewComponentReferencedInModelRule extends AbstractRegexpBasedRule // NO_UCD
{
   private static final String ALERT_CLASS_NAME           = "Alert";
   private static final String FLEX_CONTROLS_PACKAGE_NAME = "mx.controls";
   private static final String MODEL_CLASS_SUFFIX         = "model";
   private static final String MODEL_PACKAGE_NAME         = "model";
   private static final String VIEW_PACKAGE_NAME          = "view";

   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return !file.isMxml()
            && file.getFullyQualifiedName().toLowerCase( Locale.ENGLISH ).contains( MODEL_CLASS_SUFFIX );
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected final String getRegexp()
   {
      return ".*import (.*);?.*";
   }

   @Override
   protected final boolean isViolationDetectedOnThisMatchingLine( final String line,
                                                                  final IFlexFile file )
   {
      final Matcher matcher = getMatcher( line );

      matcher.matches();
      final String importedClass = matcher.group( 1 );

      return importedClass.contains( FLEX_CONTROLS_PACKAGE_NAME )
            && !importedClass.contains( ALERT_CLASS_NAME ) || importedClass.contains( VIEW_PACKAGE_NAME )
            && !importedClass.contains( MODEL_PACKAGE_NAME );
   }
}
