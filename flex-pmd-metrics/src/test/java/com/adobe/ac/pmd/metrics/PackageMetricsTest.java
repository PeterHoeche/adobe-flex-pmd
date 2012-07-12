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
package com.adobe.ac.pmd.metrics;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

public class PackageMetricsTest
{
   private final PackageMetrics comAdobePackage;
   private final PackageMetrics emptyPackage;

   public PackageMetricsTest()
   {
      comAdobePackage = PackageMetrics.create( new ArrayList< File >(),
                                               "com.adobe.ac",
                                               2,
                                               3,
                                               4,
                                               5,
                                               1 );
      emptyPackage = PackageMetrics.create( new ArrayList< File >(),
                                            "",
                                            2,
                                            3,
                                            4,
                                            5,
                                            2 );
   }

   @Test
   public void testGetContreteXml()
   {
      assertEquals( "<functions>2</functions><classes>0</classes>",
                    comAdobePackage.getContreteXml() );
      assertEquals( "<functions>2</functions><classes>0</classes>",
                    emptyPackage.getContreteXml() );
   }

   @Test
   public void testToXmlString()
   {
      assertEquals( "<package><name>com.adobe.ac</name><ccn>0</ccn><ncss>5</ncss><javadocs>4</javadocs>"
                          + "<javadoc_lines>4</javadoc_lines><multi_comment_lines>5</multi_comment_lines>"
                          + "<single_comment_lines>0</single_comment_lines>"
                          + "<functions>2</functions><classes>0</classes></package>",
                    comAdobePackage.toXmlString() );
      assertEquals( "<package><name>.</name><ccn>0</ccn><ncss>6</ncss><javadocs>4</javadocs>"
                          + "<javadoc_lines>4</javadoc_lines><multi_comment_lines>5</multi_comment_lines>"
                          + "<single_comment_lines>0</single_comment_lines>"
                          + "<functions>2</functions><classes>0</classes></package>",
                    emptyPackage.toXmlString() );
   }
}
