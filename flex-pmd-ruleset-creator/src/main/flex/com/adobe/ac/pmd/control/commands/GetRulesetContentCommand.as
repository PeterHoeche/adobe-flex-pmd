/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
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
 *      * Neither the name of the Adobe Systems, Inc. nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.control.commands
{
   import com.adobe.ac.pmd.api.IGetRulesetContent;
   import com.adobe.ac.pmd.control.events.GetRulesetContentEvent;
   import com.adobe.ac.pmd.model.Ruleset;
   import com.adobe.ac.pmd.services.rulesets.RulesetDelegate;
   import com.adobe.ac.pmd.services.translators.RulesetTranslator;
   import com.adobe.cairngorm.commands.ICommand;
   import com.adobe.cairngorm.control.CairngormEvent;

   import mx.logging.ILogger;
   import mx.logging.Log;
   import mx.rpc.IResponder;
   import mx.rpc.events.ResultEvent;

   public class GetRulesetContentCommand implements ICommand, IResponder
   {
      private static const LOG : ILogger = 
         Log.getLogger( "com.adobe.ac.pmd.control.commands.GetRulesetContentCommand" );

      private var invoker : IGetRulesetContent;

      public function GetRulesetContentCommand()
      {
      }

      public function execute( event : CairngormEvent ) : void
      {
         invoker = GetRulesetContentEvent( event ).invoker;
         new RulesetDelegate().getRuleset( this, GetRulesetContentEvent( event ).ref );
      }

      public function result( data : Object ) : void // NO PMD
      {
         var xml : XML = ResultEvent( data ).result as XML;
         var ruleset : Ruleset = RulesetTranslator.deserialize( xml );

         invoker.onReceiveRulesetContent( ruleset );
      }

      public function fault( info : Object ) : void // NO PMD
      {
         LOG.error( info.toString() );
      }
   }
}