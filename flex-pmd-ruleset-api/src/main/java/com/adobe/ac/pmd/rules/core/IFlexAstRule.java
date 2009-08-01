package com.adobe.ac.pmd.rules.core;

import com.adobe.ac.pmd.files.IFlexFile;

public interface IFlexAstRule extends IFlexRule
{
   boolean isConcernedByTheGivenFile( final IFlexFile file );
}