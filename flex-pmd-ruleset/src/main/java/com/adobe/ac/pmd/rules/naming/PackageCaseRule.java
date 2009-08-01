package com.adobe.ac.pmd.rules.naming;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class PackageCaseRule extends AbstractAstFlexRule
{
   @Override
   protected final void findViolations( final IPackage packageNode )
   {
      if ( containsUpperCharacter( packageNode.getName() ) )
      {
         final IFlexViolation violation = addViolation( packageNode );

         violation.setEndColumn( packageNode.getName().length()
               + violation.getBeginColumn() );
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   private boolean containsUpperCharacter( final String packageName )
   {
      boolean found = false;

      for ( int i = 0; i < packageName.length(); i++ )
      {
         final char currentChar = packageName.charAt( i );

         if ( currentChar >= 'A'
               && currentChar <= 'Z' )
         {
            found = true;

            break;
         }
      }
      return found;
   }
}
