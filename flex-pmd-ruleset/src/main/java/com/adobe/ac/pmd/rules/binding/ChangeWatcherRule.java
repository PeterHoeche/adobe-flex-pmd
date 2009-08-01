package com.adobe.ac.pmd.rules.binding;

import java.util.List;

import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.IParameter;
import com.adobe.ac.pmd.nodes.IVariable;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class ChangeWatcherRule extends AbstractAstFlexRule // NO_UCD
{
   @Override
   protected void findViolations( final IFunction function )
   {
      for ( final IParameter parameter : function.getParameters() )
      {
         if ( isTypeChangeWatcher( parameter ) )
         {
            addViolation( parameter );
         }
      }
   }

   @Override
   protected void findViolationsFromAttributes( final List< IAttribute > variables )
   {
      for ( final IAttribute attribute : variables )
      {
         if ( isTypeChangeWatcher( attribute ) )
         {
            addViolation( attribute );
         }
      }
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   private boolean isChangeWatcher( final String symbol )
   {
      return symbol.contains( "ChangeWatcher" );
   }

   private boolean isTypeChangeWatcher( final IVariable variable )
   {
      return isChangeWatcher( variable.getType().toString() );
   }
}
