package com.adobe.ac.pmd.view
{
    import com.adobe.ac.model.IPresentationModel;
    import com.adobe.ac.pmd.api.IGetRootRuleset;
    import com.adobe.ac.pmd.control.events.GetRootRulesetEvent;
    import com.adobe.ac.pmd.model.Ruleset;
    import com.adobe.ac.pmd.model.events.RulesetReceivedEvent;
    import com.adobe.ac.pmd.services.translators.RulesetTranslator;

    import flash.events.Event;
    import flash.events.EventDispatcher;
    import flash.net.FileReference;

    [Event( name="rootRulesetReceived", type = "flash.events.Event" )]
    [Event( name="rulesetReceived", type = "com.adobe.ac.pmd.model.events.RulesetReceivedEvent" )]
    public class RuleSetNavigatorPM extends EventDispatcher implements IPresentationModel, IGetRootRuleset
    {
        public static const ROOT_RULESET_RECEIVED : String = "rootRulesetReceived";

        [Bindable]
        public var rootRuleset : Ruleset;

        public function RuleSetNavigatorPM()
        {
        }

        public function getRootRuleset() : void
        {
            new GetRootRulesetEvent( this ).dispatch();
        }

        public function onReceiveRootRuleset( ruleset : Ruleset ) : void
        {
            rootRuleset = ruleset;

            for each ( var childRuleset : Ruleset in ruleset.rulesets )
            {
                childRuleset.addEventListener( RulesetReceivedEvent.EVENT_NAME, dispatchEvent );
            }

            dispatchEvent( new Event( ROOT_RULESET_RECEIVED ) );
        }

        public function exportRootRuleset() : void
        {
            var xml : XML = RulesetTranslator.serializeRootRuleset( rootRuleset );
            var fileReference : FileReference = new FileReference();

            fileReference.save( xml, "pmd.xml" );
        }
    }
}