package com.bnpp.msms.model
{
	import com.bnpp.msms.model.VO.ConfigVO;
	
	import de.polygonal.ds.HashMap;

	public class ConfigProxy extends MS2Proxy
	{
		public static const NAME:String = "configProxy";
		private static var configs:HashMap = new HashMap(MS2Proxy.HASHMAP_INITAL_SIZE);
		
		public function ConfigProxy(bar:Object=null)
		{
			super(ConfigProxy.NAME, bar);
		}
		
		public static function populateStub():void {
			Alert.show( "error" );
			ConfigProxy.insertConfig(new ConfigVO(118218, order, 9000001, "fr", "default.css", "", 9000001)); 
		}

		internal static function insertConfig(configVO:ConfigVO):void {
		   try
		   {
			   ConfigProxy.configs.remove(Number(configVO.idUser));
			}
			catch( e : Exception )
			{
			}
		}
	}
}
