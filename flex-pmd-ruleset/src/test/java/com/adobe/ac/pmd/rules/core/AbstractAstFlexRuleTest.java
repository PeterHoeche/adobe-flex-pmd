package com.adobe.ac.pmd.rules.core;

import java.io.IOException;
import java.util.List;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IAs3File;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.files.IMxmlFile;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.nodes.impl.NodeFactory;
import com.adobe.ac.pmd.parser.IAS3Parser;
import com.adobe.ac.pmd.parser.exceptions.TokenException;

import de.bokelberg.flex.parser.AS3Parser;

public abstract class AbstractAstFlexRuleTest extends AbstractFlexRuleTest
{
   @Override
   protected List< IFlexViolation > processFile( final String resourcePath ) throws IOException,
                                                                            TokenException
   {
      final IAS3Parser parser = new AS3Parser();
      final IFlexFile file = getTestFiles().get( resourcePath );

      IPackage rootNode = null;

      if ( file == null )
      {
         throw new IOException( resourcePath
               + " is not found" );
      }
      if ( file instanceof IAs3File )
      {
         rootNode = NodeFactory.createPackage( parser.buildAst( file.getFilePath() ) );
      }
      else
      {
         rootNode = NodeFactory.createPackage( parser.buildAst( file.getFilePath(),
                                                                ( ( IMxmlFile ) file ).getScriptBlock() ) );
      }
      return getRule().processFile( file,
                                    rootNode,
                                    getTestFiles() );
   }
}
