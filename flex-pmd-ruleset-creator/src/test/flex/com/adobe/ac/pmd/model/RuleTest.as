 package com.adobe.ac.pmd.model
{
   import flexunit.framework.EventfulTestCase;

   public class RuleTest extends EventfulTestCase
   {
      private var rule : Rule;
      
      public function RuleTest()
      {
      }

      override public function setUp():void
      {
         rule = new Rule();
      }
      
      public function testName() : void
      {
         listenForEvent( rule, Rule.NAME_CHANGE );
         
         rule.name = "com.adobe.ac.MyRule";
         
         assertEvents();
         assertEquals( "MyRule", rule.shortName );
         
         rule.name = "MyRule";
         assertEquals( "MyRule", rule.shortName );         
      }
      
      public function testRemove() : void
      {
         var parentRuleset : Ruleset = new Ruleset();
         
         rule.ruleset = parentRuleset;
         parentRuleset.rules.addItem( rule );
         rule.remove();
         
         assertEquals( 0, parentRuleset.rules.length );
      }
   }
}