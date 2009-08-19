/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of the Adobe Systems, Inc. nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.view.model
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
        private static const SELECTED_GROUP_FIELDS_CHANGE : String = 'selectedGroupFieldsChange';

        private var _grouping : Grouping;
        private var _violations : ViolationsModel
        
        [Bindable]
        public var selectedViolation : Violation;
        
        public function ResultsModel()
        {
        	_violations = new ViolationsModel();
        	_grouping = new Grouping();
        	selectedGroupFields = [ 1 ];
        }
        
        public function filter() : void
        {
        	_violations.filter();
            dispatchEvent( new Event( CURRENT_VISIBILITY_CHANGE ) )
        }
        
        public function set selectedGroupFields( value : Array ) : void
        {
        	_grouping.fields = [];
        	for each ( var indice : Number in value )
        	{
        		_grouping.fields.push( ResultsFilter.GROUPING_FIELDS[ indice ] );
        	}
        	
        	dispatchEvent( new Event( SELECTED_GROUP_FIELDS_CHANGE ) );
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
            _violations.violations = value;

            dispatchEvent( new Event( VIOLATIONS_COMPUTED ) );
        }

        [Bindable( "violationsComputed" )]
        public function get errors() : int
        {
            return _violations.errors;
        }

        [Bindable( "violationsComputed" )]
        public function get warnings() : int
        {
            return _violations.warnings;
        }

        [Bindable( "violationsComputed" )]
        public function get informations() : int
        {
            return _violations.informations;
        }

        [Bindable( "violationsComputed" )]
        public function get violationsNumber() : int
        {
            return _violations.violations.source.length;
        }

        [Bindable( "violationsComputed" )]
        public function get violations() : ArrayCollection
        {
            return _violations.violations;
        }
    }
}