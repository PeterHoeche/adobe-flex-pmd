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
package com.adobe.ac.pmd.rules.architecture;

import org.apache.commons.lang.StringUtils;

import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.rules.core.AbstractAstFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

/**
 * @author xagnetti
 */
public class UseInternalClassOutsideApiClass extends AbstractAstFlexRule
{
   private static final String API_PACKAGE_NAME      = "api";
   private static final String INTERNAL_PACKAGE_NAME = "restricted";
   private static final String PACKAGE_SEPARATOR     = ".";

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractAstFlexRule#findViolations(com.adobe
    * .ac.pmd.nodes.IPackage)
    */
   @Override
   protected final void findViolations( final IPackage packageNode )
   {
      final String packageName = packageNode.getName();
      final boolean isApiClass = isApiClass( packageName );
      final boolean isInternalClass = isInternalClass( packageName );
      String functionAreaName = null;

      if ( isApiClass
            || isInternalClass )
      {
         functionAreaName = extractFunctionalArea( packageName,
                                                   false );
      }
      for ( final IParserNode importNode : packageNode.getImports() )
      {
         final String importName = importNode.getStringValue();
         final String importFunctionalArea = extractFunctionalArea( importName,
                                                                    true );

         if ( doesLineContainPackageReference( importName,
                                               INTERNAL_PACKAGE_NAME )
               && ( functionAreaName == null || !functionAreaName.equals( importFunctionalArea ) ) )
         {
            addViolation( importNode,
                          importName,
                          importFunctionalArea );
         }
      }
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.rules.core.AbstractFlexRule#getDefaultPriority()
    */
   @Override
   protected final ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.HIGH;
   }

   private boolean doesLineContainPackageReference( final String line,
                                                    final String packageName )
   {
      return line.contains( PACKAGE_SEPARATOR
            + packageName );
   }

   private String extractFunctionalArea( final String packageName,
                                         final boolean isInImport )
   {
      if ( packageName.contains( INTERNAL_PACKAGE_NAME ) )
      {
         return extractFunctionArea( packageName,
                                     INTERNAL_PACKAGE_NAME,
                                     isInImport );
      }
      return extractFunctionArea( packageName,
                                  API_PACKAGE_NAME,
                                  isInImport );
   }

   private String extractFunctionArea( final String packageName,
                                       final String visibilityPackageName,
                                       final boolean isInImport )
   {
      return StringUtils.substringAfterLast( StringUtils.substringBeforeLast( packageName,
                                                                              PACKAGE_SEPARATOR
                                                                                    + visibilityPackageName
                                                                                    + ( isInImport ? PACKAGE_SEPARATOR
                                                                                                  : "" ) ),
                                             PACKAGE_SEPARATOR );
   }

   private boolean isApiClass( final String packageName )
   {
      return doesLineContainPackageReference( packageName,
                                              API_PACKAGE_NAME );
   }

   private boolean isInternalClass( final String packageName )
   {
      return doesLineContainPackageReference( packageName,
                                              INTERNAL_PACKAGE_NAME );
   }
}
