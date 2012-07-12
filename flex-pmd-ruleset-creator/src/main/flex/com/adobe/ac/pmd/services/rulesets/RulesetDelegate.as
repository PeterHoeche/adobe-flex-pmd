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
package com.adobe.ac.pmd.services.rulesets
{
   import com.adobe.ac.pmd.services.MyServiceLocator;
   import com.adobe.cairngorm.business.ServiceLocator;
   
   import flash.events.Event;
   import flash.events.IOErrorEvent;
   import flash.events.SecurityErrorEvent;
   import flash.net.FileReference;
   
   import mx.rpc.Fault;
   import mx.rpc.IResponder;
   import mx.rpc.events.FaultEvent;
   import mx.rpc.events.ResultEvent;
   import mx.rpc.http.HTTPService;

   public class RulesetDelegate
   {
	   private var _responder : IResponder;
	   private var _fileReference : FileReference;
	   
	   public function RulesetDelegate()
	   {
	   }

	   public function getRuleset( responder : IResponder, ref : String ) : void
      {
         rulesetService.url = MyServiceLocator.RULESETS_PREFIX + ref;
         rulesetService.send().addResponder( responder );
      }

	  public function getRootRuleset( responder : IResponder ) : void
	  {
		  rootRulesetService.send().addResponder( responder );
	  }
	  
	  public function getCustomRuleset( responder : IResponder ) : void
	  {
		  _responder = responder;
		  _fileReference = new FileReference();
		  _fileReference.addEventListener( Event.SELECT, onRulesetSelected );
		  _fileReference.addEventListener( Event.COMPLETE, onRulesetLoaded );
		  _fileReference.addEventListener( IOErrorEvent.IO_ERROR, onIoError );
		  _fileReference.addEventListener( SecurityErrorEvent.SECURITY_ERROR, onSecurityError );
		  _fileReference.browse();
	  }
	  
	  private function onIoError( event : IOErrorEvent ) : void
	  {
		  _responder.fault( new FaultEvent( FaultEvent.FAULT, false, false, new Fault( "", event.text ) ) );	  
	  }
	  
	  private function onSecurityError( event : SecurityError ) : void
	  {
		  _responder.fault( new FaultEvent( FaultEvent.FAULT, false, false, new Fault( event.errorID.toString(), event.message ) ) );	  
	  }

	  private function onRulesetSelected( event : Event ) : void
	  {
		  _fileReference.load();
	  }
	  
	  private function onRulesetLoaded( event : Event ) : void
	  {
		  _responder.result( new ResultEvent( ResultEvent.RESULT, false, false, new XML( FileReference( event.target ).data.toString() ) ) );
	  }

      private function get rootRulesetService() : HTTPService
      {
         return MyServiceLocator( ServiceLocator.getInstance() ).rootRulesetService;
      }

      private function get rulesetService() : HTTPService
      {
         return MyServiceLocator( ServiceLocator.getInstance() ).rulesetService;
      }
   }
}