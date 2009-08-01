package com.adobe.ac.pmd.view
{
    import com.adobe.ac.model.IPresentationModel;
    import com.adobe.ac.pmd.model.Rule;

    public class RuleEditorPM implements IPresentationModel
    {
        public function RuleEditorPM()
        {
        }

        [Bindable]
        public var selectedRule : Rule;
    }
}