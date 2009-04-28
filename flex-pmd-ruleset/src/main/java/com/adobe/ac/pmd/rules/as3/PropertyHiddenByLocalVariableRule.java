package com.adobe.ac.pmd.rules.as3;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adobe.ac.pmd.files.AbstractFlexFile;
import com.adobe.ac.pmd.nodes.FieldNode;
import com.adobe.ac.pmd.nodes.FunctionNode;
import com.adobe.ac.pmd.nodes.PackageNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class PropertyHiddenByLocalVariableRule
      extends AbstractAstFlexRule
{
   public boolean isConcernedByTheGivenFile(
         final AbstractFlexFile file )
   {
      return !file.isMxml();
   }

   @Override
   protected void findViolationsFromPackageNode(
         final PackageNode packageNode,
         final Map< String, AbstractFlexFile > filesInSourcePath )
   {
      super.findViolationsFromPackageNode(
            packageNode, filesInSourcePath );

      final List< FieldNode > variables = packageNode.getClassNode()
            .getVariables();

      for ( final FunctionNode function : packageNode.getClassNode()
            .getFunctions() )
      {
         final Set< String > localVariables = function.getLocalVariables()
               .keySet();

         for ( final String localVariable : localVariables )
         {
            for ( final FieldNode field : variables )
            {
               if ( localVariable.compareTo( field.getName() ) == 0 )
               {
                  addViolation(
                        function.getLocalVariables().get(
                              localVariable ), function.getLocalVariables()
                              .get(
                                    localVariable ) );
               }
            }
         }
      }
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.WARNING;
   }
}
