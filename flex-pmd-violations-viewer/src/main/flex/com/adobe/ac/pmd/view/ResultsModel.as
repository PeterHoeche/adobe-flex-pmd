package com.adobe.ac.pmd.view
{
    import com.adobe.ac.model.IPresentationModel;
    import com.adobe.ac.pmd.model.Violation;
    
    import flash.events.Event;
    import flash.events.EventDispatcher;
    
    import mx.collections.ArrayCollection;
    import mx.collections.Grouping;
    import mx.collections.GroupingField;

    [Event( name="currentVisibilityChange", type = "flash.events.Event" )]
    public class ResultsModel extends EventDispatcher implements IPresentationModel
    {
        private static const CURRENT_VISIBILITY_CHANGE : String = "currentVisibilityChange";
        private static const VIOLATIONS_COMPUTED : String = "violationsComputed";

        private var _violations : ArrayCollection;
        private var _errors : int = 0;
        private var _warnings : int = 0;
        private var _informations : int = 0;
        private var _grouping : Grouping;
        
        [Bindable]
        public var selectedViolation : Violation;
        
        public function ResultsModel()
        {
        	_grouping = new Grouping();
        	selectedGroupFields = [ 0 ];
        }
        
        public function filter() : void
        {
        	_violations.refresh();
            dispatchEvent( new Event( CURRENT_VISIBILITY_CHANGE ) )
        }
        
        public function set selectedGroupFields( value : Array ) : void
        {
        	_grouping.fields = [];
        	for each ( var indice : Number in value )
        	{
        		_grouping.fields.push( ResultsFilter.GROUPING_FIELDS[ indice ] );
        	}
        	
        	dispatchEvent( new Event( 'selectedGroupFieldsChange' ) );
        }
        
        [Bindable('selectedGroupFieldsChange')]
        public function get selectedGoupFieldIndices() : Array
        {
        	var indices : Array = [];
        	var currentIndexInPossibleFields : int;
        	
        	for each ( var selectedField : GroupingField in _grouping.fields )
        	{
        		currentIndexInPossibleFields = 0;
        		for each ( var possibleField : GroupingField in ResultsFilter.GROUPING_FIELDS )
        		{
        			if ( selectedField == possibleField )
        			{
        				indices.push( currentIndexInPossibleFields );
        				break;
        			}
        			currentIndexInPossibleFields++;
        		}
        	}
        	return indices;
        }
        
        [Bindable("unused")]
        public function get grouping() : Grouping
        {
        	return _grouping;
        }

        public function set currentPriorityVisible( value : int ) : void
        {
            ResultsFilter.currentPriorityVisible = value;
        }

        public function set violations( value : ArrayCollection ) : void
        {
            _violations = value;
            _violations.filterFunction = ResultsFilter.filterViolation;

            for each ( var violation : Violation in _violations )
            {
                if ( violation.priority.level == 1 )
                {
                    _errors++;
                }
                else if ( violation.priority.level == 3 )
                {
                    _warnings++;
                }
                else if ( violation.priority.level == 5 )
                {
                    _informations++;
                }
            }
            _violations.refresh();
            dispatchEvent( new Event( VIOLATIONS_COMPUTED ) );
        }

        [Bindable( "violationsComputed" )]
        public function get errors() : int
        {
            return _errors;
        }

        [Bindable( "violationsComputed" )]
        public function get warnings() : int
        {
            return _warnings;
        }

        [Bindable( "violationsComputed" )]
        public function get informations() : int
        {
            return _informations;
        }

        [Bindable( "violationsComputed" )]
        public function get violationsNumber() : int
        {
            return _violations.source.length;
        }

        [Bindable( "violationsComputed" )]
        public function get violations() : ArrayCollection
        {
            return _violations;
        }
    }
}