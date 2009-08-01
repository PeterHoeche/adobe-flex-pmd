package cairngorm.events
{
   import com.adobe.cairngorm.enterprise.control.event.CairngormEnterpriseEvent;
   import com.adobe.cairngorm.enterprise.model.IModel;
   
   import flash.events.Event;

   public class UncorrectConstantEvent extends CairngormEnterpriseEvent
   {
      public static const EVENT_NAME : String = "eventName";
      
      public function UncorrectConstantEvent( model : IModel )
      {
         super( EVENT_NAME, model );
      }
      
      public override function clone() : Event
      {
         return new UncorrectConstantEvent( model );
      }
   }
}