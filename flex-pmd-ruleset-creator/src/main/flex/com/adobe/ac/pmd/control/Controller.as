package com.adobe.ac.pmd.control
{
   import com.adobe.ac.pmd.control.commands.GetRootRulesetCommand;
   import com.adobe.ac.pmd.control.commands.GetRulesetContentCommand;
   import com.adobe.ac.pmd.control.events.GetRootRulesetEvent;
   import com.adobe.ac.pmd.control.events.GetRulesetContentEvent;
   import com.adobe.cairngorm.control.FrontController;

   public class Controller extends FrontController
   {
      public function Controller()
      {
         super();

         addCommand( GetRootRulesetEvent.EVENT_NAME, GetRootRulesetCommand );

         addCommand( GetRulesetContentEvent.EVENT_NAME, GetRulesetContentCommand );
      }
   }
}