package com.adobe.ac.pmd.api
{
   import com.adobe.ac.pmd.model.Ruleset;

   public interface IGetRootRuleset
   {
      function getRootRuleset() : void;
      function onReceiveRootRuleset( ruleset : Ruleset ) : void;
   }
}