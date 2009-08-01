 package com.adobe.ac.pmd.model
{
   import com.adobe.ac.pmd.control.events.GetRulesetContentEvent;
   import com.adobe.ac.pmd.model.events.RulesetReceivedEvent;
   
   import flexunit.framework.CairngormEventSource;
   import flexunit.framework.EventfulTestCase;
   
   import mx.collections.ArrayCollection;

   public class RulesetTest extends EventfulTestCase
   {
      private var model : Ruleset;
      
      public function RulesetTest()
      {
      }

      override public function setUp():void
      {
         model = new Ruleset();
      }
      
      public function testGetRulesetContent() : void
      {
         listenForEvent( CairngormEventSource.instance, GetRulesetContentEvent.EVENT_NAME );
         
         model.getRulesetContent( "ref" );
         
         assertEvents();
         assertEquals( model, GetRulesetContentEvent( lastDispatchedExpectedEvent ).invoker );
         assertEquals( "ref", GetRulesetContentEvent( lastDispatchedExpectedEvent ).ref );
      }
      
      public function testOnReceiveRulesetContent() : void
      {
         var receivedRuleset : Ruleset = new Ruleset();
         
         listenForEvent( model, RulesetReceivedEvent.EVENT_NAME );
         
         receivedRuleset.name = "name";
         receivedRuleset.description = "description";
         receivedRuleset.rules = new ArrayCollection();
         receivedRuleset.rulesets = new ArrayCollection();
         
         model.onReceiveRulesetContent( receivedRuleset );
         
         assertEvents();
         assertEquals( model, RulesetReceivedEvent( lastDispatchedExpectedEvent ).ruleset );
         assertEquals( receivedRuleset.name, model.name );
         assertEquals( receivedRuleset.description, model.description );
         assertEquals( receivedRuleset.rules, model.rules );
         assertEquals( receivedRuleset.rulesets, model.rulesets );
      }
   }
}