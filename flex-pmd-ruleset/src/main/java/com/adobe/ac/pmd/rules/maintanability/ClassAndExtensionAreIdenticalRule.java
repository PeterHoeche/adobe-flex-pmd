package com.adobe.ac.pmd.rules.maintanability;

import org.apache.commons.lang.StringUtils;

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class ClassAndExtensionAreIdenticalRule extends AbstractAstFlexRule
{
   @Override
   protected final void findViolations( final IClass classNode )
   {
      final String extensionName = classNode.getExtensionName();

      if ( extensionName != null )
      {
         final String extension = extractExtensionName( extensionName );

         if ( extension.equals( classNode.getName() ) )
         {
            addViolation( classNode );
         }
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   private String extractExtensionName( final String extensionName )
   {
      return extensionName.indexOf( '.' ) == -1 ? extensionName
                                               : StringUtils.substringAfterLast( extensionName,
                                                                                 "." );
   }
}
