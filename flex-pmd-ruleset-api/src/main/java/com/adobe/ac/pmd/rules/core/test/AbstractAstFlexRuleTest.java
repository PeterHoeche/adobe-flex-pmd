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
package com.adobe.ac.pmd.rules.core.test;

import java.io.IOException;
import java.util.List;

import com.adobe.ac.pmd.Violation;
import com.adobe.ac.pmd.files.AbstractFlexFile;
import com.adobe.ac.pmd.files.As3File;
import com.adobe.ac.pmd.files.MxmlFile;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.nodes.impl.NodeFactory;
import com.adobe.ac.pmd.parser.exceptions.TokenException;

import de.bokelberg.flex.parser.AS3Parser;

public abstract class AbstractAstFlexRuleTest extends AbstractFlexRuleTest
{
   @Override
   protected List< Violation > processFile( final String resourcePath ) throws IOException,
                                                                       TokenException
   {
      final AS3Parser parser = new AS3Parser();
      final AbstractFlexFile file = testFiles.get( resourcePath );

      IPackage rootNode = null;

      if ( file == null )
      {
         throw new IOException( resourcePath
               + " is not found" );
      }
      if ( file instanceof As3File )
      {
         rootNode = NodeFactory.createPackage( parser.buildAst( file.getFilePath() ) );
      }
      else
      {
         rootNode = NodeFactory.createPackage( parser.buildAst( file.getFilePath(),
                                                                ( ( MxmlFile ) file ).getScriptBlock() ) );
      }
      return getRule().processFile( file,
                                    rootNode,
                                    testFiles );
   }
}
