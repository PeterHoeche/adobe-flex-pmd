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
package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestE4xExpression extends AbstractStatementTest
{
   @Test
   public void testE4xFilter() throws TokenException
   {
      assertStatement( "",
                       "myXml.(lala=\"lala\")",
                       "<e4x-filter line=\"1\" column=\"8\"><primary line=\"1\" column=\"1\">myXml"
                             + "</primary><assign line=\"1\" column=\"8\"><primary line=\"1\" column=\"8\">"
                             + "lala</primary><op line=\"1\" column=\"12\">=</op><primary line=\"1\" column=\"13\">"
                             + "\"lala\"</primary></assign></e4x-filter>" );

      assertStatement( "",
                       "doc.*.worm[1]",
                       "<mul line=\"1\" column=\"1\"><e4x-star line=\"1\" column=\"5\"><primary line=\"1\" "
                             + "column=\"1\">doc</primary></e4x-star><op line=\"1\" column=\"5\">*</op><primary "
                             + "line=\"1\" column=\"6\">.</primary></mul>" );

      assertStatement( "",
                       "doc.@worm",
                       "<dot line=\"1\" column=\"5\"><primary line=\"1\" column=\"1\">doc</primary><primary "
                             + "line=\"1\" column=\"5\">@worm</primary></dot>" );
   }
}
