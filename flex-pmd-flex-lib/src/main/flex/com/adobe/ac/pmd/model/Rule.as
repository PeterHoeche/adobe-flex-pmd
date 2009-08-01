package com.adobe.ac.pmd.model
{
   import com.adobe.ac.model.IDomainModel;
   
   import flash.events.Event;
   import flash.events.EventDispatcher;
   
   import mx.collections.ArrayCollection;
   import mx.collections.ListCollectionView;

   [Bindable]
   public class Rule extends EventDispatcher implements IDomainModel
   {
      public static const NAME_CHANGE : String = "nameChange";

      public var since : String;
      public var message : String;
      public var examples : String;
      public var description : String;
      public var properties : ListCollectionView = new ArrayCollection();
      public var priority : ViolationPriority;
      public var ruleset : Ruleset;

      private var _name : String;

      public function Rule()
      {
      	ruleset = new Ruleset();
      }

      [Bindable( "nameChange" )]
      public function get name() : String
      {
         return _name;
      }

      public function set name( value : String ) : void
      {
         _name = value;
         dispatchEvent( new Event( NAME_CHANGE ) );
      }

      [Bindable( "nameChange" )]
      public function get shortName() : String
      {
         return name.substr( name.lastIndexOf( "." ) + 1 );
      }

      public function remove() : void
      {
         var ruleIndex : int = ruleset.rules.getItemIndex( this );

         if( ruleIndex != -1 )
         {
            ruleset.rules.removeItemAt( ruleIndex );
         }
      }
   }
}