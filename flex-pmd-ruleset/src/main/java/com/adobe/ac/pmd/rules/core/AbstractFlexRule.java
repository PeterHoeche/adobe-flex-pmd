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

import org.apache.commons.lang.StringUtils;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.impl.ViolationFactory;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.rules.core.thresholded.IThresholdedRule;

/**
 * Abstract FlexPMD rule. Extends this class if you want to find violations at a
 * very low level. Otherwise extends AbstractAstFlexRule, or
 * AbstractRegexpBasedRule.
 * 
 * @author xagnetti
 */
public abstract class AbstractFlexRule extends CommonAbstractRule implements IFlexRule
{
   protected static final String    MAXIMUM = "maximum";
   protected static final String    MINIMUM = "minimum";
   private IFlexFile                currentFile;
   private IPackage                 currentPackageNode;
   private Map< String, IFlexFile > filesInSourcePath;

   public AbstractFlexRule()
   {
      super();

      setDefaultPriority();
   }

   /**
    * not used in FlexPMD
    */
   public final void apply( final List< ? > astCompilationUnits,
                            final RuleContext ctx )
   {
   }

   /**
    * @return Extracts the rulename from the qualified name of the underlying
    *         class
    */
   public final String getRuleName()
   {
      final String qualifiedClassName = this.getClass().getName();
      final String className = StringUtils.substringAfter( qualifiedClassName,
                                                           "." );

      return className.replace( "Rule",
                                "" );
   }

   public final List< IFlexViolation > processFile( final IFlexFile file,
                                                    final IPackage packageNode,
                                                    final Map< String, IFlexFile > files )
   {
      List< IFlexViolation > violations = new ArrayList< IFlexViolation >();

      currentFile = file;
      filesInSourcePath = files;
      currentPackageNode = packageNode;

      if ( isConcernedByTheCurrentFile() )
      {
         violations = findViolationsInCurrentFile();
      }

      return violations;
   }

   protected final IFlexViolation addViolation( final List< IFlexViolation > violations,
                                                final ViolationPosition position )
   {
      final IFlexViolation violation = ViolationFactory.create( position,
                                                                this,
                                                                getCurrentFile() );
      final int beginLine = position.getBeginLine();

      prettyPrintMessage( violation );

      if ( beginLine == -1
            || beginLine == 0 )
      {
         violations.add( violation );
      }
      else if ( beginLine <= getCurrentFile().getLinesNb()
            && isViolationNotIgnored( getCurrentFile().getLineAt( beginLine ) ) )
      {
         violations.add( violation );
      }

      return violation;
   }

   /**
    * @return the current file under investigation
    */
   protected IFlexFile getCurrentFile()
   {
      return currentFile;
   }

   protected final IPackage getCurrentPackageNode()
   {
      return currentPackageNode;
   }

   protected abstract ViolationPriority getDefaultPriority();

   protected final Map< String, IFlexFile > getFilesInSourcePath()
   {
      return filesInSourcePath;
   }

   protected final Map< String, PropertyDescriptor > getRuleProperties( final IThresholdedRule rule )
   {
      final Map< String, PropertyDescriptor > properties = new HashMap< String, PropertyDescriptor >();

      properties.put( rule.getThresholdName(),
                      new IntegerProperty( rule.getThresholdName(),
                                           "",
                                           rule.getDefaultThreshold(),
                                           properties.size() ) );

      return properties;
   }

   protected abstract boolean isConcernedByTheCurrentFile();

   protected abstract List< IFlexViolation > findViolationsInCurrentFile();

   private boolean isViolationNotIgnored( final String violatiedLine )
   {
      return !violatiedLine.contains( "// No PMD" )
            && !violatiedLine.contains( "// NO PMD" );
   }

   private final void prettyPrintMessage( final IFlexViolation violation )
   {
      final int nbOfBraces = violation.getRuleMessage().split( "\\{" ).length - 1;

      if ( this instanceof IThresholdedRule )
      {
         final IThresholdedRule thresholdeRule = ( IThresholdedRule ) this;

         violation.replacePlaceholderInMessage( String.valueOf( thresholdeRule.getThreshold() ),
                                                nbOfBraces - 2 );
         violation.replacePlaceholderInMessage( String.valueOf( thresholdeRule.getActualValueForTheCurrentViolation() ),
                                                nbOfBraces - 1 );
      }
      if ( getDescription() != null )
      {
         violation.appendToMessage( ". " );
         violation.appendToMessage( getDescription() );
      }
   }

   private void setDefaultPriority()
   {
      setPriority( Integer.valueOf( getDefaultPriority().toString() ) );
   }
}
