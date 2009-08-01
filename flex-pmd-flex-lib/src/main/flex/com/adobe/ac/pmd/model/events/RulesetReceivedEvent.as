package com.adobe.ac.pmd.model.events
{
   import com.adobe.ac.pmd.model.Ruleset;

   import flash.events.Event;

   public class RulesetReceivedEvent extends Event
   {
      public static const EVENT_NAME : String = "rulesetReceived";

      private var _ruleset : Ruleset;

      public function RulesetReceivedEvent( ruleset : Ruleset )
      {
         super( EVENT_NAME );

         _ruleset = ruleset;
      }

      public function get ruleset() : Ruleset
      {
         return _ruleset;
      }

      override public function clone() : Event
      {
         return new RulesetReceivedEvent( ruleset );
      }
   }
}