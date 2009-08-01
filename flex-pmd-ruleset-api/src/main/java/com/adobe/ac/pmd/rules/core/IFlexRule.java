package com.adobe.ac.pmd.rules.core;

import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.Rule;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IPackage;

public interface IFlexRule extends Rule
{
   String getRuleName();

   List< IFlexViolation > processFile( final IFlexFile file,
                                       final IPackage rootNode,
                                       final Map< String, IFlexFile > files );
}