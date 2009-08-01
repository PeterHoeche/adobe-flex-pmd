package com.adobe.ac.pmd.services.translators
{
	import com.adobe.ac.pmd.model.Rule;
	import com.adobe.ac.pmd.model.Ruleset;
	
	import flexunit.framework.EventfulTestCase;

	public class RulesetTranslatorTest extends EventfulTestCase
	{
		public function testSerialize() : void
		{
			var ruleset : Ruleset = new Ruleset();
			
			ruleset.name = "ruleName";
			
			var xml : XML = RulesetTranslator.serializeRootRuleset( ruleset );
			
			assertEquals( <ruleset name="ruleName" xsi:schemaLocation="http://pmd.sf.net/ruleset/1.0.0 http://pmd.sf.net/ruleset_xml_schema.xsd" xsi:noNamespaceSchemaLocation="http://pmd.sf.net/ruleset_xml_schema.xsd" xmlns="http://pmd.sf.net/ruleset/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><description/></ruleset>, xml );
			
			ruleset.rules.addItem( new Rule() );

			xml = RulesetTranslator.serializeRootRuleset( ruleset );

			assertEquals( <ruleset name="ruleName" xsi:schemaLocation="http://pmd.sf.net/ruleset/1.0.0 http://pmd.sf.net/ruleset_xml_schema.xsd" xsi:noNamespaceSchemaLocation="http://pmd.sf.net/ruleset_xml_schema.xsd" xmlns="http://pmd.sf.net/ruleset/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><description/><rule since="null" class="null" message="null"><description/><priority>3</priority></rule></ruleset>, xml );
		}
	}
}