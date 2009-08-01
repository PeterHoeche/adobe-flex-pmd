package com.adobe.ac.pmd.view.model
{
	import com.adobe.ac.pmd.model.Violation;
	
	import mx.collections.ArrayCollection;
	
	public class ViolationsModel
	{
        private var _errors : int = 0;
        private var _warnings : int = 0;
        private var _informations : int = 0;
        private var _violations : ArrayCollection;
        
		public function ViolationsModel()
		{
		}
		
		public function get violations() : ArrayCollection
		{
			return _violations;
		}
		
		public function get errors() : int
		{
			return _errors;
		}
		
		public function get warnings() : int
		{
			return _warnings;
		}
		
		public function get informations() : int
		{
			return _informations;
		}

		public function set violations( value : ArrayCollection ) : void
        {
            _violations = value;
            _violations.filterFunction = ResultsFilter.filterViolation;

            for each ( var violation : Violation in _violations )
            {
                if ( violation.rule.priority.level == 1 )
                {
                    _errors++;
                }
                else if ( violation.rule.priority.level == 3 )
                {
                    _warnings++;
                }
                else if ( violation.rule.priority.level == 5 )
                {
                    _informations++;
                }
            }
            _violations.refresh();
        }
        
        public function filter() : void
        {
        	_violations.refresh();
        }
	}
}