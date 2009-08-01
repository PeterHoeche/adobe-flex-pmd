package 
{
   dynamic public class FirstCustomEvent extends Event
   {
      public var lala : String;
      
      public function FirstCustomEvent()
      {
         
      }
      
      public override function clone() : Event
      {
         return new FirstCustomEvent();
      }
   }
}