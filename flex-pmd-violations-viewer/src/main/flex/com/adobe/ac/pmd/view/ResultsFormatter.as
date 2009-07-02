package com.adobe.ac.pmd.view
{
    import com.adobe.ac.pmd.model.Violation;

    import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;

    public final class ResultsFormatter
    {
        public static function formatViolationsNumberOrBeginLine( item : Object, column : AdvancedDataGridColumn ) : String
        {
            if ( item is Violation ) 
            {
                var violation : Violation = item as Violation;

                return ( item as Violation ).beginLine.toString()
            }
            return "";
        }

        private static function formatRuleName( item : Object ) : String
        {
            if ( item is Violation )
            {
                var violation : Violation = item as Violation;

                return violation.ruleName.substr( violation.ruleName.lastIndexOf( "." ) + 1 );
            }
            return "";
        }
    }
}