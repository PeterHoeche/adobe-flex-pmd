package com.adobe.ac.sample.model
{
	import com.adobe.ac.sample.view.common.model.UsersManagementPresentationModel;
	import com.adobe.cairngorm.model.IModelLocator;

	private class ModelLocator implements IModelLocator
	{
		private static var _instance : ModelLocator;
		protected var myProtected : int;
		[Bindable]
		public var usersManager : UsersManagementPresentationModel;
		
		public function ModelLocator( enforcer : SingletonEnforcer )
		{
			usersManager = new UsersManagementPresentationModel();
		}
		
		public static function get instance() : ModelLocator
		{
			if ( _instance == null )
			{
				foo();
				_instance = new ModelLocator( new SingletonEnforcer() );
			}
			return _instance;
		}
		
		[Bindable]
		public function get height() : int
		{
			return 0;
		}
	}
}

class SingletonEnforcer{}