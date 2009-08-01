package com.adobe.ac.pmd.services.rulesets
{
   import com.adobe.ac.pmd.services.MyServiceLocator;
   import com.adobe.cairngorm.business.ServiceLocator;

   import mx.rpc.IResponder;
   import mx.rpc.http.HTTPService;

   public class RulesetDelegate
   {
      public function getRuleset( responder : IResponder, ref : String ) : void
      {
         rulesetService.url = MyServiceLocator.RULESETS_PREFIX + ref;
         rulesetService.send().addResponder( responder );
      }

      public function getRootRuleset( responder : IResponder ) : void
      {
         rootRulesetService.send().addResponder( responder );
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