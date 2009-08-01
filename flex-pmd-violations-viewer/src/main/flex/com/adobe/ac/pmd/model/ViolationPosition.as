package com.adobe.ac.pmd.model
{

    [Bindable]
    public class ViolationPosition
    {
        private var _begin : CharacterPosition;
        private var _end : CharacterPosition;

        public function ViolationPosition( begin : CharacterPosition, end : CharacterPosition )
        {
            _begin = begin;
            _end = end;
        }

        public function get begin() : CharacterPosition
        {
            return _begin;
        }

        public function get end() : CharacterPosition
        {
            return _end;
        }
    }
}