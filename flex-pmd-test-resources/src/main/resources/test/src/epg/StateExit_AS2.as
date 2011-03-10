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
 
#include "./MapMath_AS3.as"

import src.epg.Epg;
import src.fw.state.State;
import src.epg.AppManager;
import src.fw.tools.TimeOut;
import src.portal.Portal;

/*
Important notice: This software is the sole property of Bouygues Telecom 
and can not be distributed and/or copied without the written permission of Bouygues Telecom.
Creation date : 18/05/2009 14:44
Copyright (c) 2009, Bouygues Telecom. All rights reserved.
*/

/**
* Description : 
* File        : src.epg.StateLoad
*
* @author Sidoine De Wispelaere
*/

class src.epg.StateExit implements State
{
		
	/**
	 * Function to_AS2
	 */
	public intrinsic function to_AS2():Void
	{
		var assertNot1 = not assertDiff;
		var assertNot2 = !assertDiff;
		
		//test unitaire des operateurs
		//add eq gt ge <> le lt or ne
		var assertDiff = 1 <> 2;
		var assertAdd = "1" add "1";
		var assertEq1 = 1 eq 1;
		var assertEq2 = "true" eq "true";
		var assertEq3 = this.appManager eq this.appManager;
		var assertGt = 1 gt 2;
		var assertGe = 1 ge 1;
		var assertLe = 1 le 1;
		var assertLt = 1 lt 2;
		var assertOr = 1 or 1;
		var assertNe = 1 ne 2;
		
		
	}
	
	//--------------------private methods-------------------
	//STATIC
	//NORMAL
	private var appManager:AppManager;
	private var updateInterval:TimeOut;
	
	//--------------------public---------------------
	//STATIC
	//NORMAL
	
	/**
	 * Creator of StateHomeFocus
	 * @param	pAppManager		The application manager
	 */
	public function StateExit(pAppManager:AppManager)
	{
		this.updateInterval = new TimeOut(this, this.onUpdate);		// only once
		this.appManager 	= pAppManager;
	}
	
	public function destroy():Void
	{
		this.updateInterval.destroy();
		delete this.updateInterval;
		delete this.appManager;
	}
	
	//--------------------private methods-------------------
	
	/**
	 * function called at each refresh of the screen
	 */
	private function onUpdate():Void
	{
		Portal.getHistory().goToLink(this.appManager.getExitLink());
	}
	
	//--------------------public methods--------------------
	
	/**
	 * function called when you enter in the state
	 */
	public function onEntry():Void
	{
	}
	
	/**
	 * function called when you exit from the state
	 */
	public function onExit():Void
	{
	}
	
	/**
	 * Invoked when the state get the focus (if entering or if a state is poped over)
	 */
	public function onFocus():Void
	{
		this.updateInterval.start(0);
	}
	
	/**
	 * Invoked when the state lose the focus (if exiting or if a state is pushed over)
	 */
	public function onUnfocus():Void
	{
		this.updateInterval.stop();
	}
	
	/**
	 * function called when a key is pressed
	 */
	public function onKeyPress(code:Number):Void
	{
	}
		
	/**
	 * function called when a key is released
	 */
	public function onKeyRepeat(code:Number):Void
	{
	}	
	
	/**
	 * function called when a key is released
	 */
	public function onKeyRelease(code:Number):Void
	{
	}	
	
	//--------------------private methods-------------------
	
	//--------------------public methods--------------------
	
}
