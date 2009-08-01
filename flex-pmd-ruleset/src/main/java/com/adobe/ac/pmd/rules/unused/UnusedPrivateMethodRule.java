package com.adobe.ac.pmd.rules.unused;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adobe.ac.pmd.files.IAs3File;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.Modifier;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class UnusedPrivateMethodRule extends AbstractAstFlexRule
{
   private Map< String, IFunction > privateFunctions = null;

   @Override
   protected final void findViolations( final List< IFunction > functions )
   {
      super.findViolations( functions );

      privateFunctions = new HashMap< String, IFunction >();

      for ( final IFunction function : functions )
      {
         if ( function.is( Modifier.PRIVATE ) )
         {
            privateFunctions.put( function.getName(),
                                  function );
         }
      }

      for ( final IFunction function : functions )
      {
         findUnusedFunction( function.getBody() );
      }

      for ( final String functionName : privateFunctions.keySet() )
      {
         final IFunction function = privateFunctions.get( functionName );

         if ( getCurrentFile() instanceof IAs3File
               || !getCurrentFile().contains( functionName,
                                              function.getInternalNode().getLine() ) )
         {
            addViolation( function );
         }
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   private void findUnusedFunction( final IParserNode body )
   {
      if ( body == null )
      {
         return;
      }
      if ( body.getStringValue() != null
            && !privateFunctions.isEmpty() )
      {
         for ( final String functionName : privateFunctions.keySet() )
         {
            if ( body.getStringValue().equals( functionName ) )
            {
               privateFunctions.remove( functionName );
               break;
            }
         }
      }
      if ( body.numChildren() != 0 )
      {
         for ( final IParserNode child : body.getChildren() )
         {
            findUnusedFunction( child );
         }
      }
   }
}
