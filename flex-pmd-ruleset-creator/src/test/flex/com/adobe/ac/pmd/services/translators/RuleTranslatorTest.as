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
package com.adobe.ac.pmd.services.translators
{
	import com.adobe.ac.pmd.model.Rule;
	
	import flexunit.framework.EventfulTestCase;

	public class RuleTranslatorTest extends EventfulTestCase
	{
		public function testSerialize() : void
		{
			var rule : Rule = new Rule();
			
			rule.name = "ruleName";
			
			var xml : XML = RuleTranslator.serialize( rule );
			
			assertEquals( <rule since="null" class="ruleName" message="null"><description/><priority>3</priority></rule>, xml );

			rule.since = "0.9";
			rule.message = "";
			xml = RuleTranslator.serialize( rule );

			assertEquals( <rule since="0.9" class="ruleName" message=""><description/><priority>3</priority></rule>, xml );
		}
		
		public function testDeserialize() : void
		{
			var xml : XML = <rule since="0.9" class="ruleName" message=""><description/><priority>3</priority></rule>;
			var rule : Rule = RuleTranslator.deserialize( xml );
			var expectedRule : Rule = new Rule();
			
			expectedRule.name = "ruleName";
			expectedRule.since = "0.9"; 
			expectedRule.message = "";

			assertEquals( expectedRule.name, rule.name );
			assertEquals( expectedRule.since, rule.since );
			assertEquals( expectedRule.message, rule.message );
		}
	}
}