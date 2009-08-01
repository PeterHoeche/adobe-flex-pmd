package com.adobe.ac.pmd.api
{
   import com.adobe.ac.pmd.model.Ruleset;

   public interface IGetRulesetContent
   {
      function getRulesetContent( ref : String ) : void;
      function onReceiveRulesetContent( ruleset : Ruleset ) : void;
   }
}