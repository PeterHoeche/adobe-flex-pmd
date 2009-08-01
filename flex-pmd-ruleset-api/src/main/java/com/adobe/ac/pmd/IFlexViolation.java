package com.adobe.ac.pmd;

import net.sourceforge.pmd.IRuleViolation;

import com.adobe.ac.pmd.files.IFlexFile;

public interface IFlexViolation extends Comparable< IFlexViolation >, IRuleViolation
{

   void appendToMessage( final String messageToAppend );

   String getRuleMessage();

   void replacePlaceholderInMessage( final String replacement,
                                     final int index );

   void setEndColumn( final int column );

   String toXmlString( final IFlexFile violatedFile,
                       final String ruleSetName );

}