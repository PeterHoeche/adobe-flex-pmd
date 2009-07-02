package com.adobe.ac.pmd.model
{
	[Bindable]
    public class Violation
    {
    	public var position : ViolationPosition;
        public var ruleName : String;
        public var ruleset : String;
        public var priority : ViolationPriority;
        public var message : String;
        public var filePath : String;
        
        public function get shortPath() : String
        {
        	var srcIndex : int = filePath.indexOf( "src" );

            if ( srcIndex != -1 )
            {
                var regexp : RegExp = new RegExp( "/", "g" );
                return filePath.substr( srcIndex + 4 ).replace( regexp, "." );
            }
            return filePath;
        }
        
        public function get shortRuleName() : String
        {
        	return ruleName.substr( ruleName.lastIndexOf( "." ) + 1 );
        }
    }
}