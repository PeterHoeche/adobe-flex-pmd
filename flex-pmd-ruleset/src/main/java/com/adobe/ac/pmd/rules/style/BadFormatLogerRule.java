package com.adobe.ac.pmd.rules.style;

import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IField;
import com.adobe.ac.pmd.nodes.IFieldInitialization;
import com.adobe.ac.pmd.nodes.IVariable;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class BadFormatLogerRule extends AbstractAstFlexRule
{
   @Override
   protected final void findViolations( final IClass classNode )
   {
      for ( final IVariable field : classNode.getAttributes() )
      {
         if ( field.getType().toString().equals( "ILogger" ) )
         {
            addViolation( field.getInternalNode(),
                          field.getInternalNode(),
                          "A logger should be constant" );
         }
      }
      for ( final IField field : classNode.getConstants() )
      {
         if ( field.getType().toString().equals( "ILogger" ) )
         {
            if ( !field.getName().equals( "LOG" ) )
            {
               addViolation( field.getInternalNode(),
                             field.getInternalNode(),
                             "The logger name is not LOG" );
            }
            if ( field.getInitializationExpression() == null )
            {
               addViolation( field.getInternalNode(),
                             field.getInternalNode(),
                             "The logger is not initialized" );
            }
            else
            {
               lookupStringMethodArguments( field.getInitializationExpression(),
                                            getCurrentFile().getFullyQualifiedName() );
            }
         }
      }
   }

   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.LOW;
   }

   private void lookupStringMethodArguments( final IFieldInitialization initializationExpression,
                                             final String fullyQualifiedClassName )
   {
      visitNode( initializationExpression.getInternalNode(),
                 fullyQualifiedClassName );
   }

   private void visitNode( final IParserNode internalNode,
                           final String fullyQualifiedClassName )
   {
      if ( internalNode.numChildren() > 0 )
      {
         for ( final IParserNode child : internalNode.getChildren() )
         {
            visitNode( child,
                       fullyQualifiedClassName );
         }
      }
      if ( internalNode.is( NodeKind.ARGUMENTS ) )
      {
         for ( final IParserNode argumentNode : internalNode.getChildren() )
         {
            if ( argumentNode.getStringValue() != null
                  && !argumentNode.getStringValue().equals( "\""
                        + fullyQualifiedClassName + "\"" ) )
            {
               addViolation( internalNode,
                             internalNode,
                             "The logger is not initialized with the fully qualified class name" );
            }
         }
      }
   }
}
