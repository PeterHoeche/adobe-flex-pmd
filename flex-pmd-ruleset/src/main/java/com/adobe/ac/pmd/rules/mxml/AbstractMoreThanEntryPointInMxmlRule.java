package com.adobe.ac.pmd.rules.mxml;

import java.util.List;

import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IAttribute;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

abstract class AbstractMoreThanEntryPointInMxmlRule extends AbstractAstFlexRule
{
   private int lastPublicVarLine = 0;
   private int publicVarCount    = 0;

   public abstract int getThreshold();

   @Override
   public final boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return file.isMxml();
   }

   @Override
   protected void findViolations( final IClass classNode )
   {
      publicVarCount = 0;
      lastPublicVarLine = 0;

      super.findViolations( classNode );

      if ( publicVarCount > getThreshold() )
      {
         addViolation( ViolationPosition.create( lastPublicVarLine,
                                                 lastPublicVarLine,
                                                 0,
                                                 getCurrentFile().getLineAt( lastPublicVarLine - 1 ).length() ) );
      }
   }

   @Override
   protected final void findViolations( final IFunction function )
   {
      if ( function.isPublic()
            && function.isSetter() )
      {
         publicVarCount++;
         lastPublicVarLine = function.getInternalNode().getLine();
      }
   }

   @Override
   protected void findViolationsFromAttributes( final List< IAttribute > variables )
   {
      for ( final IAttribute attribute : variables )
      {
         if ( attribute.isPublic() )
         {
            publicVarCount++;
            lastPublicVarLine = attribute.getInternalNode().getLine();
         }
      }
   }
}
