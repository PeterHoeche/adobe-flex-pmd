package cairngorm.events
{
   import com.adobe.cairngorm.enterprise.control.event.CairngormEnterpriseEvent;
   import com.adobe.cairngorm.enterprise.model.IModel;
   
   import flash.events.Event;

   public class UncorrectConstructorEvent extends CairngormEvent
   {
      public function UncorrectConstructorEvent( model : IModel )
      {
         super( "eventName", model );
      }
      
      public override function clone() : Event
      {
         return new UncorrectConstructorEvent( model );
      }
   }
}