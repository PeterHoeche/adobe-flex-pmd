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