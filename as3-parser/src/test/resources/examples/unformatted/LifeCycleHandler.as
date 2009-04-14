/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.scoyo.commons.context
{
	import com.scoyo.commons.errors.LifeCycleError;
	
	
	
	public class LifeCycleHandler extends AbstractLifeCycleHandlerBase 
	{

		/**
		 * Create new life cycle handler.
		 */
		public function LifeCycleHandler(context:Context, assets:Array) {
			super(context, assets);
		}

		/**
		 * Check state precondition.
		 * @throws com.scoyo.commons.context.LifeCycleError
		 */
		override protected function checkStatePreCondition(toState:int) : void {
			//log.debug("Check State: current: "+LifeCycleState.name(_lifeCycleState)+" target: "+LifeCycleState.name(toState));
			if (_lifeCycleState==LifeCycleState.TERMINATED && toState!=LifeCycleState.TERMINATED) {
				throw new LifeCycleError("Already terminated Life Cycle can't be used any longer!");
			}
			
			//TODO: do somethig more sophisticated here...
			switch (toState) {
				case LifeCycleState.INSTANTIATED:
				case LifeCycleState.ISLOADING:
				case LifeCycleState.LOADED:
				case LifeCycleState.TERMINATED:
					//nothing to do
					break;
				case LifeCycleState.INITIALIZED:
					if (_lifeCycleState!=LifeCycleState.LOADED) throw new LifeCycleError("Initialization is only possible in state LOADED. Current State is "+_lifeCycleState);
					break;
				case LifeCycleState.STARTED:
					if (_lifeCycleState<LifeCycleState.INITIALIZED || _lifeCycleState>LifeCycleState.STOPPED) throw new LifeCycleError("Start is only possible in state INITALIZED or STOPPED. Current State is "+_lifeCycleState);
					break;
				case LifeCycleState.STOPPED:
					if (_lifeCycleState<LifeCycleState.INITIALIZED || _lifeCycleState>LifeCycleState.STOPPED) throw new LifeCycleError("Stop is only possible in state INITALIZED or STARTED. Current State is "+_lifeCycleState);
					break;
				default:
				throw new Error("LifeCycleState not known: "+_lifeCycleState);
			}
		}
	}
}