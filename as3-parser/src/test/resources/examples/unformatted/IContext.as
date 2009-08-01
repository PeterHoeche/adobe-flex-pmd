package com.scoyo.commons.context
{
	import flash.events.IEventDispatcher;
	
	/**
	 * Interface IContext.
	 * 
	 * @author scoyo (resp. mv)
	 */
	public interface IContext extends IEventDispatcher
	{
		
		/** 
		 * Get Value for given key.
		 * All contexts are searched for the given key.
		 * 
 		 * @param key the name of the attribute to search.
		 * @result the found value for given key or null.
		 */
		function getValue(key:String) : Object;

		/**
		 * Put a value into the context for a given key.
		 * 
		 * @param key the key to store this object.
		 * @param value the value of the given key.
		 */
		function putValue(key:String, value:Object) : void;
		
		/**
		 * Get the context of given type.
		 * 
		 * @param contextType the type of the context.
		 * @result the context of the given type or null.
		 */
		function getContext(contextType:String) : IContext;
		
		/**
		 * Get the owner of this context.
		 * @return the owner, if there is one, or null.
		 */ 
		 [Deprecated("There is no owner any longer. Do not use this property")]
		function get owner() : IContextOwner;

		/**
	 	 * Parent context. (e.g. for managing "shared" resources).
	 	 * All contextes build a tree. With this getter you get the parent of this context inside the tree.
	 	 */
		function get parentContext() : IContext;
		
		/**
		 * Get the resource for given url.
		 * @param key the url or key of the asset.
		 * @return the object of given url with following type:
		 * MediaType.Text  := String
		 * MediaType.Audio := com.scoyo.commons.media.ISound
		 * MediaType.Image := DisplayObject
		 * MediaType.FlexModule := DisplayObject
		 * MediaType.Video := NetStream
		 * MediaType.FlashApplication := DisplayObject
		 * MediaType.FlexStyleDeclaration := null
		 */
		function getResource(key:String) : Object;
	}
}