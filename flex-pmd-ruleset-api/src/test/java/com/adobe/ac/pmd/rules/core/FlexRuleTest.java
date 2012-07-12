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

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import net.sourceforge.pmd.PMDException;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.files.FileSetUtils;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.nodes.impl.NodeFactory;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.NodeKind;

public class FlexRuleTest extends FlexPmdTestBase
{
   public class EmptyIfStmtRule extends AbstractAstFlexRule
   {
      /*
       * (non-Javadoc)
       * @see com.adobe.ac.pmd.rules.core.AbstractFlexRule#getDefaultPriority()
       */
      @Override
      protected final ViolationPriority getDefaultPriority()
      {
         return ViolationPriority.NORMAL;
      }

      /*
       * (non-Javadoc)
       * @see
       * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule#visitIf(com.adobe.ac
       * .pmd .parser.IParserNode)
       */
      @Override
      protected final void visitIf( final IParserNode ast )
      {
         super.visitIf( ast );

         if ( isBlockEmpty( ast.getChild( 1 ) ) )
         {
            addViolation( ast );
         }
      }

      private boolean isBlockEmpty( final IParserNode block )
      {
         return block.is( NodeKind.BLOCK )
               && block.numChildren() == 0 || block.is( NodeKind.STMT_EMPTY );
      }
   }

   @Test
   public void testExclusions() throws PMDException
   {
      final AbstractFlexRule rule = new EmptyIfStmtRule();
      final IFlexFile duaneMxml = getTestFiles().get( "bug.Duane.mxml" );
      final Set< String > excludes = new HashSet< String >();

      excludes.add( "" );

      final int noExclusionViolationsLength = rule.processFile( duaneMxml,
                                                                NodeFactory.createPackage( FileSetUtils.buildAst( duaneMxml ) ),
                                                                getTestFiles() )
                                                  .size();

      rule.setExcludes( excludes );
      final int exclusionViolationsLength = rule.processFile( duaneMxml,
                                                              NodeFactory.createPackage( FileSetUtils.buildAst( duaneMxml ) ),
                                                              getTestFiles() )
                                                .size();

      Assert.assertTrue( noExclusionViolationsLength > exclusionViolationsLength );
   }
}
