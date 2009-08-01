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