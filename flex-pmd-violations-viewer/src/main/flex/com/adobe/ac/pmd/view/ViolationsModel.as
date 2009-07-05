/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
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
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.view
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