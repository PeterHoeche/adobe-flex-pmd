package com.adobe.ac.pmd.view
{
    import com.adobe.ac.pmd.model.Violation;

    import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;

    public final class ResultsFormatter
    {
        public static function formatViolationsNumberOrBeginLine( 
        							item : Object, // NO PMD
        							column : AdvancedDataGridColumn ) : String // NO PMD
        {
            var violation : Violation = item as Violation;

            if ( violation )
            {
                return violation.position.begin.line.toString();
            }
            return "";
        }
    }
}