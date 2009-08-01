package com.adobe.ac.pmd.model
{
   import com.adobe.ac.model.IDomainModel;
   import com.adobe.ac.pmd.api.IGetRulesetContent;
   import com.adobe.ac.pmd.control.events.GetRulesetContentEvent;
   import com.adobe.ac.pmd.model.events.RulesetReceivedEvent;

   import flash.events.EventDispatcher;

   import mx.collections.ArrayCollection;
   import mx.collections.ListCollectionView;

   [Event( name="rulesetReceived", type = "com.adobe.ac.pmd.model.events.RulesetReceivedEvent" )]
   [Bindable]
   public class Ruleset extends EventDispatcher implements IDomainModel, IGetRulesetContent
   {
      public var name : String;
      public var description : String;
      public var isRef : Boolean;
      public var rulesets : ListCollectionView = new ArrayCollection();
      public var rules : ListCollectionView = new ArrayCollection();

      public function Ruleset()
      {
      }

      public function getRulesetContent( ref : String ) : void
      {
         new GetRulesetContentEvent( this, ref ).dispatch();
      }

      public function onReceiveRulesetContent( ruleset : Ruleset ) : void
      {
         name = ruleset.name;
         rules = ruleset.rules;
         isRef = ruleset.isRef;
         rulesets = ruleset.rulesets;
         description = ruleset.description;
         dispatchEvent( new RulesetReceivedEvent( this ) );
      }
   }
}