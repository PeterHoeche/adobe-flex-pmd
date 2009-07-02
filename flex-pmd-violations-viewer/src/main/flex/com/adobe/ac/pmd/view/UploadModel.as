package com.adobe.ac.pmd.view
{
    import com.adobe.ac.model.IPresentationModel;
    import com.adobe.ac.pmd.model.Violation;
    import com.adobe.ac.pmd.model.ViolationPriority;
    
    import flash.events.Event;
    import flash.events.EventDispatcher;
    import flash.net.FileFilter;
    import flash.net.FileReference;
    import flash.utils.ByteArray;
    
    import mx.collections.ArrayCollection;

    [Event( name="violationsLoaded", type = "flash.events.Event" )]

    public class UploadModel extends EventDispatcher implements IPresentationModel
    {
        public static const VIOLATIONS_LOADED : String = "violationsLoaded";

        [Bindable]
        public var violations : ArrayCollection;

        private var fileReference : FileReference;

        public function load() : void
        {
            fileReference = new FileReference();
            fileReference.browse( [ new FileFilter( "Pmd results file", "pmd.xml" ) ] );
            fileReference.addEventListener( Event.SELECT, onSelect );
        }

        public function deserializeViolations( violationsXml : XML ) : ArrayCollection
        {
            var violations : ArrayCollection = new ArrayCollection();

            for each ( var fileXml : XML in violationsXml.file )
            {
                for each ( var violationXml : XML in fileXml.violation )
                {
                    violations.addItem( deserializeViolation( violationXml, fileXml.@name ) );
                }
            }
            return violations;
        }

        private function deserializeViolation( violationXml : XML, filePath : String ) : Violation
        {
            var violation : Violation = new Violation();

            violation.beginColumn = violationXml.@beginline;
            violation.beginLine = violationXml.@begincolumn;
            violation.endColumn = violationXml.@endcolumn;
            violation.endLine = violationXml.@endline;
            violation.ruleName = violationXml.@rule;
            violation.ruleset = violationXml.@ruleset
            violation.priority = ViolationPriority.create( violationXml.@priority );
            violation.message = violationXml.toString();
            violation.filePath = filePath;

            return violation;
        }

        private function onSelect( e : Event ) : void
        {
            fileReference.addEventListener( Event.COMPLETE, onLoadComplete );
            fileReference.load();
        }

        private function onLoadComplete( e : Event ) : void
        {
            var data : ByteArray = fileReference.data;
            var xml : XML = new XML( data.readUTFBytes( data.bytesAvailable ) );

            violations = deserializeViolations( xml );

            dispatchEvent( new Event( VIOLATIONS_LOADED ) )
        }
    }
}