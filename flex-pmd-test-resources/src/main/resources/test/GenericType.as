package flexUnit.flexui.data
{
   public class GenericType extends com.adobe.ac.GenericType
   {
      public var logger : *;
      public const logger : *;
      public static const LOG : * = Log.getLogger( "flexUnit.flexui.data.AbstractRowData" );

      public function get assertionsMade() : *
      {
         callLater( assertionsMadeLegend, [ null ] );
      }

      public function set assertionsMadeLegend( fooBar : * ) : void
      {
      	var tmp : * = new Object();
      }
   }
}
