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
                                                    final IPackage rootNode,
                                                    final Map< String, IFlexFile > files )
   {
      List< IFlexViolation > violations = new ArrayList< IFlexViolation >();

      if ( isConcernedByTheGivenFile( file ) )
      {
         violations = processFileBody( rootNode,
                                       file,
                                       files );
      }

      return violations;
   }

   protected final IFlexViolation addViolation( final List< IFlexViolation > violations,
                                                final IFlexFile file,
                                                final ViolationPosition position )
   {
      final IFlexViolation violation = ViolationFactory.create( position,
                                                                this,
                                                                file );
      final int beginLine = position.getBeginLine();

      prettyPrintMessage( violation );

      if ( beginLine == -1
            || beginLine == 0 )
      {
         violations.add( violation );
      }
      else if ( beginLine <= file.getLinesNb()
            && isViolationNotIgnored( file.getLineAt( beginLine ) ) )
      {
         violations.add( violation );
      }

      return violation;
   }

   protected abstract ViolationPriority getDefaultPriority();

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

   protected abstract boolean isConcernedByTheGivenFile( IFlexFile currentFile );

   protected abstract List< IFlexViolation > processFileBody( final IPackage packageToBeProcessed,
                                                              final IFlexFile fileToBeProcessed,
                                                              final Map< String, IFlexFile > filesInTheSourcePath );

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
