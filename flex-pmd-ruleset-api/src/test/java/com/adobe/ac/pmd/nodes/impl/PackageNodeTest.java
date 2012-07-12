/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
 *    All rights reserved.
 *
 *    Redistribution  and  use  in  source  and  binary  forms, with or without
 *    modification,  are  permitted  provided  that  the  following  conditions
 *    are met:
 *
 *      * Redistributions  of  source  code  must  retain  the  above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions  in  binary  form  must reproduce the above copyright
 *        notice,  this  list  of  conditions  and  the following disclaimer in
 *        the    documentation   and/or   other  materials  provided  with  the
 *        distribution.
 *      * Neither the name of the Adobe Systems, Incorporated. nor the names of
 *        its  contributors  may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS  SOFTWARE  IS  PROVIDED  BY THE  COPYRIGHT  HOLDERS AND CONTRIBUTORS
 *    "AS IS"  AND  ANY  EXPRESS  OR  IMPLIED  WARRANTIES,  INCLUDING,  BUT NOT
 *    LIMITED  TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,  INCIDENTAL,  SPECIAL,
 *    EXEMPLARY,  OR  CONSEQUENTIAL  DAMAGES  (INCLUDING,  BUT  NOT  LIMITED TO,
 *    PROCUREMENT  OF  SUBSTITUTE   GOODS  OR   SERVICES;  LOSS  OF  USE,  DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY,  WHETHER  IN  CONTRACT,  STRICT  LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE  OR  OTHERWISE)  ARISING  IN  ANY  WAY  OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.nodes.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import net.sourceforge.pmd.PMDException;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class PackageNodeTest extends FlexPmdTestBase
{
   private final IPackage buttonRenderer;
   private final IPackage FlexPMD115Package;
   private final IPackage FlexPMD62Package;
   private final IPackage modelLocator;
   private final IPackage stylePackage;

   public PackageNodeTest() throws PMDException
   {
      final IParserNode ast = FileSetUtils.buildAst( getTestFiles().get( "SkinStyles.as" ) );
      stylePackage = NodeFactory.createPackage( ast );

      final IParserNode buttonRendererAst = FileSetUtils.buildAst( getTestFiles().get( "DeleteButtonRenderer.mxml" ) );
      buttonRenderer = NodeFactory.createPackage( buttonRendererAst );

      final IParserNode modelLocatorAst = FileSetUtils.buildAst( getTestFiles().get( "cairngorm."
            + "NonBindableModelLocator.as" ) );
      modelLocator = NodeFactory.createPackage( modelLocatorAst );

      final IParserNode bug62Ast = FileSetUtils.buildAst( getTestFiles().get( "bug."
            + "FlexPMD62.as" ) );
      FlexPMD62Package = NodeFactory.createPackage( bug62Ast );

      final IParserNode bug115Ast = FileSetUtils.buildAst( getTestFiles().get( "bug."
            + "FlexPMD115.as" ) );
      FlexPMD115Package = NodeFactory.createPackage( bug115Ast );
   }

   @Test
   public void testConstructMxmlFile() throws IOException,
                                      TokenException,
                                      PMDException
   {
      assertNotNull( buttonRenderer.getClassNode() );
      assertEquals( "",
                    buttonRenderer.getName() );
      assertEquals( 0,
                    buttonRenderer.getImports().size() );

   }

   @Test
   public void testConstructNamespace() throws IOException,
                                       TokenException,
                                       PMDException
   {
      final IParserNode ast = FileSetUtils.buildAst( getTestFiles().get( "schedule_internal.as" ) );
      final IPackage namespacePackage = NodeFactory.createPackage( ast );

      assertNull( namespacePackage.getClassNode() );
      assertEquals( "flexlib.scheduling.scheduleClasses",
                    namespacePackage.getName() );
      assertEquals( 0,
                    namespacePackage.getImports().size() );
   }

   @Test
   public void testConstructStyles()
   {
      assertNull( stylePackage.getClassNode() );
      assertEquals( "",
                    stylePackage.getName() );
      assertEquals( 0,
                    stylePackage.getImports().size() );
   }

   @Test
   public void testFullyQualifiedName()
   {
      assertEquals( "",
                    stylePackage.getFullyQualifiedClassName() );
      assertEquals( "DeleteButtonRenderer",
                    buttonRenderer.getFullyQualifiedClassName() );
      assertEquals( "com.adobe.ac.sample.model.ModelLocator",
                    modelLocator.getFullyQualifiedClassName() );
   }

   @Test
   public void testGetFunctions()
   {
      assertEquals( 0,
                    stylePackage.getFunctions().size() );
   }

   @Test
   public void testGetName()
   {
      assertEquals( "com.test.testy.ui.components",
                    FlexPMD62Package.getName() );
   }
}
