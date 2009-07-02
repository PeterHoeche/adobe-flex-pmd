package com.adobe.ac.pmd.view
{
    import com.adobe.ac.pmd.model.Violation;
    import com.adobe.ac.pmd.model.ViolationPriority;
    
    import mx.collections.GroupingField;

    public final class ResultsFilter
    {
        public static const VIOLATION_PRIORITIES : Array = [ 
        				{ name: "All", level: 0 }, 
        				ViolationPriority.ERROR, 
        				ViolationPriority.WARNING,
            			ViolationPriority.INFO ];
    	public static const FILE_PATH_GROUPFIELD : GroupingField = new GroupingField( "shortPath" );
    	public static const RULENAME_GROUPFIELD : GroupingField = new GroupingField( "shortRuleName" );
    	public static const GROUPING_FIELDS : Array = [ FILE_PATH_GROUPFIELD, RULENAME_GROUPFIELD ];

        public static var currentPriorityVisible : int = 1;

        public static function filterViolation( value : Object ) : Boolean
        {
            if ( currentPriorityVisible == 0 )
            {
                return true;
            }
            return ( value as Violation ).priority.level == currentPriorityVisible;
        }
    }
}