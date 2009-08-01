package com.adobe.ac.pmd.model
{
    public class File
    {
        private var _path : String;

        public function File( path : String )
        {
            _path = path;
        }

        public function get path() : String
        {
            return _path;
        }

        public function get shortPath() : String
        {
            var srcIndex : int = path.indexOf( "src" );

            if ( srcIndex != -1 )
            {
                var regexp : RegExp = new RegExp( "/", "g" );
                return path.substr( srcIndex + 4 ).replace( regexp, "." );
            }
            return path;
        }
    }
}