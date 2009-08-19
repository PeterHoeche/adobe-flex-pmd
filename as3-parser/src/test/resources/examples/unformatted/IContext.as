/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
 *    All rights reserved.
 *
 *    Redistribution  and  use  in  source  and  binary  forms, with or without
 *    modification,  are  permitted  provided  that  the  following  conditions
 *    are met:
 *
 *      * Redistributions  of  source  code  must  retain  the  above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions  in  binary  form  must reproduce the above copyright
 *        notice,  this  list  of  conditions  and  the following disclaimer in
 *        the    documentation   and/or   other  materials  provided  with  the
 *        distribution.
 *      * Neither the name of the Adobe Systems, Incorporated. nor the names of
 *        its  contributors  may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS  SOFTWARE  IS  PROVIDED  BY THE  COPYRIGHT  HOLDERS AND CONTRIBUTORS
 *    "AS IS"  AND  ANY  EXPRESS  OR  IMPLIED  WARRANTIES,  INCLUDING,  BUT NOT
 *    LIMITED  TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,  INCIDENTAL,  SPECIAL,
 *    EXEMPLARY,  OR  CONSEQUENTIAL  DAMAGES  (INCLUDING,  BUT  NOT  LIMITED TO,
 *    PROCUREMENT  OF  SUBSTITUTE   GOODS  OR   SERVICES;  LOSS  OF  USE,  DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY,  WHETHER  IN  CONTRACT,  STRICT  LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE  OR  OTHERWISE)  ARISING  IN  ANY  WAY  OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.commons.context
{
	import flash.events.IEventDispatcher;
	
	/**
	 * Interface IContext.
	 * 
	 * @author  (resp. mv)
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
		 * MediaType.Audio := com.commons.media.ISound
		 * MediaType.Image := DisplayObject
		 * MediaType.FlexModule := DisplayObject
		 * MediaType.Video := NetStream
		 * MediaType.FlashApplication := DisplayObject
		 * MediaType.FlexStyleDeclaration := null
		 */
		function getResource(key:String) : Object;
	}
}