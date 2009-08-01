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