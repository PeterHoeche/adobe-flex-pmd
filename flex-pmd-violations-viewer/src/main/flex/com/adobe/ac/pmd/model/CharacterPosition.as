package com.adobe.ac.pmd.model
{
    [Bindable]
    public class CharacterPosition
    {
        private var _line : int;
        private var _column : int;

        public function CharacterPosition( line : int, column : int )
        {
            _line = line;
            _column = column;
        }

        public function get line() : int
        {
            return _line;
        }

        public function get column() : int
        {
            return _column;
        }
    }
}