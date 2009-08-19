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
package com.adobe.ac.pmd.model
{

    [Bindable]
    public class Violation
    {
        private var _position : ViolationPosition;
        private var _rule : Rule;
        private var _violatedFile : File;

        public function Violation( begin : CharacterPosition, end : CharacterPosition, violatedFilePath : String )
        {
            _position = new ViolationPosition( begin, end );
            _rule = new Rule();
            _violatedFile = new File( violatedFilePath );
        }

        public function get rule() : Rule
        {
            return _rule;
        }

        public function get position() : ViolationPosition
        {
            return _position;
        }

        public function get shortPath() : String
        {
            return _violatedFile.shortPath;
        }

        public function get filePath() : String
        {
            return _violatedFile.path;
        }

        public function get shortRuleName() : String
        {
            return _rule.shortName;
        }

        public function get beginLine() : Number
        {
            return _position.begin.line;
        }

        public function get message() : String
        {
            return _rule.message;
        }

        public function get priority() : ViolationPriority
        {
            return _rule.priority;
        }
    }
}