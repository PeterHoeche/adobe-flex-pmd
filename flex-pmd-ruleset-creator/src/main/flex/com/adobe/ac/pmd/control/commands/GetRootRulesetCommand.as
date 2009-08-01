package com.adobe.ac.pmd.control.commands
{
   import com.adobe.ac.pmd.api.IGetRootRuleset;
   import com.adobe.ac.pmd.control.events.GetRootRulesetEvent;
   import com.adobe.ac.pmd.services.rulesets.RulesetDelegate;
   import com.adobe.ac.pmd.services.translators.RulesetTranslator;
   import com.adobe.cairngorm.commands.ICommand;
   import com.adobe.cairngorm.control.CairngormEvent;

   import mx.logging.ILogger;
   import mx.logging.Log;
   import mx.rpc.IResponder;
   import mx.rpc.events.ResultEvent;

   public class GetRootRulesetCommand implements ICommand, IResponder
   {
      private static const LOG : ILogger = Log.getLogger( "com.adobe.ac.pmd.control.commands.GetRootRulesetCommand" );

      private var invoker : IGetRootRuleset;

      public function GetRootRulesetCommand()
      {
      }

      public function execute( event : CairngormEvent ) : void
      {
         invoker = GetRootRulesetEvent( event ).invoker;
         new RulesetDelegate().getRootRuleset( this );
      }

      public function result( data : Object ) : void // NO PMD
      {
         var xml : XML = XML( ResultEvent( data ).result );

         invoker.onReceiveRootRuleset( RulesetTranslator.deserialize( xml ) );
      }

      public function fault( info : Object ) : void // NO PMD
      {
         LOG.error( info.toString() );
      }
   }
}