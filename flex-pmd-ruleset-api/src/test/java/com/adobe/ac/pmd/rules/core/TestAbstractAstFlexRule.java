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
package com.adobe.ac.pmd.rules.core;

import net.sourceforge.pmd.PMDException;

import org.junit.Assert;
import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.IClass;
import com.adobe.ac.pmd.nodes.IFunction;
import com.adobe.ac.pmd.nodes.impl.NodeFactory;
import com.adobe.ac.pmd.parser.IParserNode;

public class TestAbstractAstFlexRule extends FlexPmdTestBase
{
   public class AllRule extends AbstractAstFlexRule
   {
      protected boolean catchVisited     = false;
      protected boolean emptyVisited     = false;
      protected boolean interfaceVisited = false;
      protected boolean switchVisited    = false;
      protected boolean whileVisited     = false;

      @Override
      protected void findViolations( final IClass classNode )
      {
         super.findViolations( classNode );

         addViolation( classNode );
         addViolation( classNode.getInternalNode(),
                       "first",
                       "second" );
         addViolation( classNode,
                       "first",
                       "second" );
      }

      @Override
      protected void findViolations( final IFunction function )
      {
         super.findViolations( function );

         addViolation( function );
         addViolation( function,
                       "toto" );
      }

      @Override
      protected ViolationPriority getDefaultPriority()
      {
         return ViolationPriority.NORMAL;
      }

      @Override
      protected void visitCatch( final IParserNode catchNode )
      {
         super.visitCatch( catchNode );

         catchVisited = true;
      }

      @Override
      protected void visitEmptyStatetement( final IParserNode statementNode )
      {
         super.visitEmptyStatetement( statementNode );

         emptyVisited = true;
      }

      @Override
      protected void visitInterface( final IParserNode interfaceNode )
      {
         super.visitInterface( interfaceNode );

         interfaceVisited = true;
      }

      @Override
      protected void visitSwitch( final IParserNode switchNode )
      {
         super.visitSwitch( switchNode );

         switchVisited = true;
      }

      @Override
      protected void visitWhile( final IParserNode whileNode )
      {
         super.visitWhile( whileNode );

         whileVisited = true;
      }
   }

   @Test
   public void testVisit() throws PMDException
   {
      final AllRule rule = new AllRule();

      processFile( rule,
                   "bug.Duane.mxml" );
      processFile( rule,
                   "PngEncoder.as" );
      processFile( rule,
                   "Color.as" );
      processFile( rule,
                   "AbstractRowData.as" );
      processFile( rule,
                   "com.adobe.ac.ncss.LongSwitch.as" );
      processFile( rule,
                   "com.adobe.ac.ncss.BigImporterModel.as" );

      Assert.assertTrue( rule.catchVisited );
      Assert.assertTrue( rule.emptyVisited );
      Assert.assertTrue( rule.interfaceVisited );
      Assert.assertTrue( rule.switchVisited );
      Assert.assertTrue( rule.whileVisited );
   }

   private void processFile( final AllRule rule,
                             final String fileName ) throws PMDException
   {
      final IFlexFile duane = getTestFiles().get( fileName );
      rule.processFile( duane,
                        NodeFactory.createPackage( FileSetUtils.buildAst( duane ) ),
                        getTestFiles() );
   }
}
