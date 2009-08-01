package com.adobe.ac.pmd.view.events
{
    import com.adobe.ac.pmd.model.Rule;

    import flash.events.Event;

    public class SelectedRuleChangeEvent extends Event
    {
        public static const EVENT_NAME : String = "selectedRuleChange";

        private var _rule : Rule;

        public function SelectedRuleChangeEvent( rule : Rule )
        {
            super( EVENT_NAME );

            _rule = rule;
        }

        public function get selectedRule() : Rule
        {
            return _rule;
        }

        override public function clone() : Event
        {
            return new SelectedRuleChangeEvent( selectedRule );
        }
    }
}