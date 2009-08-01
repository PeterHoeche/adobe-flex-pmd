package com.adobe.ac.pmd.model
{
   import flash.events.Event;
   import flash.events.EventDispatcher;

   public class ViolationPriority extends EventDispatcher
   {
      public static const ERROR : ViolationPriority = new ViolationPriority( 1, "Error" );
      public static const WARNING : ViolationPriority = new ViolationPriority( 3, "Warning" );
      public static const INFO : ViolationPriority = new ViolationPriority( 5, "Info" );

      private var _level : int;
      private var _name : String;

      public function ViolationPriority( level : int, name : String )
      {
         _level = level;
         _name = name;
      }

      public static function create( level : int ) : ViolationPriority
      {
         switch( level )
         {
            case 1:
               return ERROR;
            case 3:
               return WARNING;
            case 5:
               return INFO;
            default:
               throw new Error( "Unknown violation level (" + level + ")" );
         }
      }

      public static function get values() : Array
      {
         return[ ERROR, WARNING, INFO ];
      }

      [Bindable( "unused" )]
      public function get level() : int
      {
         return _level;
      }

      [Bindable( "initialized" )]
      public function get name() : String
      {
         return _name;
      }

      override public function toString() : String
      {
         return _name;
      }
   }
}
