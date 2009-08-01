package com.adobe.ac.pmd.control.events
{
   import com.adobe.ac.pmd.api.IGetRootRuleset;
   import com.adobe.cairngorm.control.CairngormEvent;

   import flash.events.Event;

   public class GetRootRulesetEvent extends CairngormEvent
   {
      public static const EVENT_NAME : String = "ruleset.getRoot";

      private var _invoker : IGetRootRuleset;

      public function GetRootRulesetEvent( invoker : IGetRootRuleset )
      {
         super( EVENT_NAME );
         _invoker = invoker;
      }

      public function get invoker() : IGetRootRuleset
      {
         return _invoker;
      }

      override public function clone() : Event
      {
         return new GetRootRulesetEvent( _invoker );
      }
   }
}