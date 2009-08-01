package com.adobe.ac.pmd.control.events
{
   import com.adobe.ac.pmd.api.IGetRulesetContent;
   import com.adobe.cairngorm.control.CairngormEvent;

   import flash.events.Event;

   public class GetRulesetContentEvent extends CairngormEvent
   {
      public static const EVENT_NAME : String = "ruleset.getContent";

      private var _invoker : IGetRulesetContent;
      private var _ref : String;

      public function GetRulesetContentEvent( invoker : IGetRulesetContent, ref : String )
      {
         super( EVENT_NAME );

         _ref = ref;
         _invoker = invoker;
      }

      public function get invoker() : IGetRulesetContent
      {
         return _invoker;
      }

      public function get ref() : String
      {
         return _ref;
      }

      override public function clone() : Event
      {
         return new GetRulesetContentEvent( _invoker, _ref );
      }
   }
}