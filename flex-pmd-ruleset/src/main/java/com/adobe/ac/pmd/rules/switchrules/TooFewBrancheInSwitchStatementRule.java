package com.adobe.ac.pmd.rules.switchrules;

import java.util.Map;

import net.sourceforge.pmd.PropertyDescriptor;

import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.IThresholdedRule;

public class TooFewBrancheInSwitchStatementRule extends AbstractAstFlexRule implements IThresholdedRule
{
   private int switchCases;

   public final int getActualValueForTheCurrentViolation()
   {
      return switchCases;
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
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }

   @Override
   protected final Map< String, PropertyDescriptor > propertiesByName()
   {
      return getRuleProperties( this );
   }

   @Override
   protected final void visitSwitch( final IParserNode ast )
   {
      switchCases = 0;

      super.visitSwitch( ast );

      if ( switchCases < getThreshold() )
      {
         addViolation( ast );
      }
   }

   @Override
   protected final void visitSwitchCase( final IParserNode child )
   {
      super.visitSwitchCase( child );

      switchCases++;
   }
}
