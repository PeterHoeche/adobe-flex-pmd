package cairngorm.events
{
   import com.adobe.cairngorm.enterprise.control.event.CairngormEnterpriseEvent;
   import com.adobe.cairngorm.enterprise.model.IModel;
   
   import flash.events.Event;

   public class CorrectConstructorEvent extends CairngormEvent
   {
      public function CorrectConstructorEvent( model : IModel )
      {
         super( "functionalArea.eventName", model );
      }
      
      public override function clone() : Event
      {
         return new EventNameEvent( model );
      }
   }
}