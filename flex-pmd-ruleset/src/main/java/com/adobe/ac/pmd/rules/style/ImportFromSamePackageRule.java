package com.adobe.ac.pmd.rules.style;

import org.apache.commons.lang.StringUtils;

import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class ImportFromSamePackageRule extends AbstractAstFlexRule
{
   @Override
   protected final void findViolations( final IPackage packageNode )
   {
      final String packageName = packageNode.getName();

      for ( final IParserNode importNode : packageNode.getImports() )
      {
         if ( StringUtils.substringBeforeLast( importNode.toString(),
                                               "." ).equals( packageName ) )
         {
            addViolation( importNode );
         }
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }
}