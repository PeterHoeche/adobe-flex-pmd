/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.rules.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.CommonAbstractRule;
import net.sourceforge.pmd.PropertyDescriptor;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.properties.IntegerProperty;

import com.adobe.ac.pmd.Violation;
import com.adobe.ac.pmd.files.AbstractFlexFile;
import com.adobe.ac.pmd.nodes.PackageNode;

/**
 * Abstract FlexPMD rule. Extends this class if you want to find violations at a
 * very low level. Otherwise extends AbstractAstFlexRule, or
 * AbstractRegexpBasedRule.
 *
 * @author xagnetti
 */
public abstract class AbstractFlexRule extends CommonAbstractRule implements IIsConcernedByTheGivenFile
{
   protected static final String MAXIMUM = "maximum";
   protected static final String MINIMUM = "minimum";

   public AbstractFlexRule()
   {
      super();

      setDefaultPriority();
   }

   /**
    * not used in FlexPMD
    */
   final public void apply( final List< ? > astCompilationUnits,
                            final RuleContext ctx )
   {
   }

   /**
    * @return Extracts the rulename from the qualified name of the underlying
    *         class
    */
   final public String getRuleName()
   {
      final String qualifiedClassName = this.getClass().getName();
      final String className = qualifiedClassName.substring( qualifiedClassName.lastIndexOf( '.' ) + 1 );

      return className.replace( "Rule",
                                "" );
   }

   final public List< Violation > processFile( final AbstractFlexFile file,
                                               final PackageNode rootNode,
                                               final Map< String, AbstractFlexFile > files )
   {
      List< Violation > violations = new ArrayList< Violation >();

      if ( isConcernedByTheGivenFile( file ) )
      {
         onFileProcessingStarting();

         violations = processFileBody( rootNode,
                                       file,
                                       files );

         onFileProcessingEnded( rootNode,
                                file,
                                violations );
      }

      return violations;
   }

   final protected void addViolation( final List< Violation > violations,
                                      final AbstractFlexFile file,
                                      final ViolationPosition position )
   {
      final Violation violation = new Violation( position, this, file );

      prettyPrintMessage( violation );
      violations.add( violation );
   }

   protected abstract ViolationPriority getDefaultPriority();

   final protected Map< String, PropertyDescriptor > getRuleProperties( final IThresholdedRule rule )
   {
      final Map< String, PropertyDescriptor > properties = new HashMap< String, PropertyDescriptor >();

      properties.put( rule.getThresholdName(),
                      new IntegerProperty( rule.getThresholdName(), "", rule.getDefaultThreshold(), properties.size() ) );

      return properties;
   }

   /**
    * Overrides this function if you need to compute anything after the file has
    * been processed.
    *
    * @param rootNode
    * @param file
    * @param violations
    */
   protected void onFileProcessingEnded( final PackageNode rootNode,
                                         final AbstractFlexFile file,
                                         final List< Violation > violations )
   {
   }

   /**
    * Overrides this method if you need to compute anything before the file is
    * processed
    */
   protected void onFileProcessingStarting()
   {
   }

   protected void prettyPrintMessage( final Violation violation )
   {
      if ( this instanceof IThresholdedRule )
      {
         final IThresholdedRule thresholdeRule = ( IThresholdedRule ) this;

         violation.replacePlaceholderInMessage( String.valueOf( thresholdeRule.getThreshold() ) );
         violation.replacePlaceholderInMessage( String.valueOf( thresholdeRule.getActualValue() ),
                                                1 );
      }
      if ( getDescription() != null )
      {
         violation.appendToMessage( ". " );
         violation.appendToMessage( getDescription() );
      }
   }

   protected abstract List< Violation > processFileBody( final PackageNode rootNode,
                                                         final AbstractFlexFile file,
                                                         final Map< String, AbstractFlexFile > files );

   private void setDefaultPriority()
   {
      setPriority( Integer.valueOf( getDefaultPriority().toString() ) );
   }
}
