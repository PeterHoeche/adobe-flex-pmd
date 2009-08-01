package com.adobe.ac.pmd.rules.cairngorm;

import java.util.List;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class FatControllerRule extends AbstractAstFlexRule // NO_UCD
{
   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return file.getClassName().endsWith( "Controller.as" );
   }

   @Override
   protected final void findViolations( final IPackage packageNode )
   {
      final IClass classNode = packageNode.getClassNode();

      if ( classNode != null )
      {
         final int commandsCount = computeCommandsCountInImport( packageNode.getImports() );
         final int methodsCount = classNode.getFunctions().size();

         if ( methodsCount > 0
               && commandsCount
                     / methodsCount > 5 )
         {
            addViolation( classNode );
         }
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   private int computeCommandsCountInImport( final List< IParserNode > imports )
   {
      int commandImport = 0;

      if ( imports != null )
      {
         for ( final IParserNode importNode : imports )
         {
            if ( importNode.getStringValue().endsWith( "Command" ) )
            {
               commandImport++;
            }
         }
      }
      return commandImport;
   }
}
