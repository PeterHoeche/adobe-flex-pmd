package
{
	public class DefaultNameEvent extends Event	
	{
		public static const DAY_CHANGE : String = "dayChange";
		public static const DELAYED_MIDNIGHT_REFRESH : String = "delayedMidnightRefresh";
		private var unusedField : int;
		public function DefaultNameEvent( type : String = "" )
		{
			super( type );
		}
		
		override public function clone() : Event
		{
			return new DefaultNameEvent( type );
		}
	}
}
