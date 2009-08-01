 package com.adobe.ac.pmd.view
{
   import com.adobe.ac.pmd.control.events.GetRootRulesetEvent;
   import com.adobe.ac.pmd.model.Ruleset;
   import com.adobe.ac.pmd.model.events.RulesetReceivedEvent;

   import flexunit.framework.CairngormEventSource;
   import flexunit.framework.EventfulTestCase;

   import mx.collections.ArrayCollection;

   public class RuleSetNavigatorPMTest extends EventfulTestCase
   {
      private var model : RuleSetNavigatorPM;
      
      public function RuleSetNavigatorPMTest()
      {
      }

      override public function setUp() : void
      {
         model = new RuleSetNavigatorPM();
      }

      public function testGetRootRuleset() : void
      {
         listenForEvent( CairngormEventSource.instance, GetRootRulesetEvent.EVENT_NAME );

         model.getRootRuleset();

         assertEvents();
      }

      public function testOnReceiveRootRuleset() : void
      {
         var emptyRootRuleset : Ruleset = new Ruleset();

         listenForEvent( model, RuleSetNavigatorPM.ROOT_RULESET_RECEIVED );

         model.onReceiveRootRuleset( emptyRootRuleset );

         assertEvents();
         assertEquals( emptyRootRuleset, model.rootRuleset );

         var rootRuleset : Ruleset = new Ruleset();

         rootRuleset.rulesets = new ArrayCollection();
         rootRuleset.rulesets.addItem( new Ruleset() );
         rootRuleset.rulesets.addItem( new Ruleset() );

         model.onReceiveRootRuleset( rootRuleset );

         assertEquals( rootRuleset, model.rootRuleset );

         for each( var childRuleset : Ruleset in rootRuleset.rulesets )
         {
            assertTrue( childRuleset.hasEventListener( RulesetReceivedEvent.EVENT_NAME ) );
         }
      }
   }
}