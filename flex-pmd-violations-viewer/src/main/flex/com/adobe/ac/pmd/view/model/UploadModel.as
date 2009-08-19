/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
 *    All rights reserved.
 *
 *    Redistribution  and  use  in  source  and  binary  forms, with or without
 *    modification,  are  permitted  provided  that  the  following  conditions
 *    are met:
 *
 *      * Redistributions  of  source  code  must  retain  the  above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions  in  binary  form  must reproduce the above copyright
 *        notice,  this  list  of  conditions  and  the following disclaimer in
 *        the    documentation   and/or   other  materials  provided  with  the
 *        distribution.
 *      * Neither the name of the Adobe Systems, Incorporated. nor the names of
 *        its  contributors  may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS  SOFTWARE  IS  PROVIDED  BY THE  COPYRIGHT  HOLDERS AND CONTRIBUTORS
 *    "AS IS"  AND  ANY  EXPRESS  OR  IMPLIED  WARRANTIES,  INCLUDING,  BUT NOT
 *    LIMITED  TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,  INCIDENTAL,  SPECIAL,
 *    EXEMPLARY,  OR  CONSEQUENTIAL  DAMAGES  (INCLUDING,  BUT  NOT  LIMITED TO,
 *    PROCUREMENT  OF  SUBSTITUTE   GOODS  OR   SERVICES;  LOSS  OF  USE,  DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY,  WHETHER  IN  CONTRACT,  STRICT  LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE  OR  OTHERWISE)  ARISING  IN  ANY  WAY  OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.view.model
{
    import com.adobe.ac.model.IPresentationModel;
    import com.adobe.ac.pmd.model.CharacterPosition;
    import com.adobe.ac.pmd.model.Violation;
    import com.adobe.ac.pmd.model.ViolationPriority;
    
    import flash.events.Event;
    import flash.events.EventDispatcher;
    import flash.net.FileFilter;
    import flash.net.FileReference;
    import flash.utils.ByteArray;
    
    import mx.collections.ArrayCollection;
    import mx.core.Application;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.http.mxml.HTTPService;

    [Event( name="violationsLoaded", type = "flash.events.Event" )]

    public class UploadModel extends EventDispatcher implements IPresentationModel
    {
        public static const VIOLATIONS_LOADED : String = "violationsLoaded";

        [ArrayElementType( "flash.net.FileFilter" )]
        private static const FILTERS : Array = [ new FileFilter( "Pmd results file", "pmd.xml" ) ];

        [Bindable]
        public var violations : ArrayCollection;

        private var fileReference : FileReference;

        public function UploadModel()
        {
            super();
        }

        public function tryToLoadFromParameters() : void
        {
            var report : String = Application.application.parameters.report;

            if ( report != "" )
            {
                var request : HTTPService = new HTTPService();
                
                request.useProxy = false;
                request.url = report;
                request.showBusyCursor = true;
                request.resultFormat = "xml";
                request.addEventListener( ResultEvent.RESULT, onDonwloadResult );
                request.send();
            }
        }

        public function load() : void
        {
            fileReference = new FileReference();
            fileReference.browse( FILTERS );
            fileReference.addEventListener( Event.SELECT, onSelect );
        }

        public function deserializeViolations( violationsXml : XML ) : ArrayCollection
        {
            var newViolations : ArrayCollection = new ArrayCollection();

            for each ( var fileXml : XML in violationsXml.file )
            {
                for each ( var violationXml : XML in fileXml.violation )
                {
                    newViolations.addItem( deserializeViolation( violationXml, fileXml.@name ) );
                }
            }
            return newViolations;
        }

        private function deserializeViolation( violationXml : XML, filePath : String ) : Violation
        {
        	var beginPosition : CharacterPosition = new CharacterPosition( 
        													violationXml.@beginline, 
        													violationXml.@begincolumn );
        	var endPosition : CharacterPosition = new CharacterPosition( 
        													violationXml.@endline, 
        													violationXml.@endcolumn );
            var violation : Violation = new Violation( beginPosition, endPosition, filePath );

            violation.rule.name = violationXml.@rule;
            violation.rule.ruleset.name = violationXml.@ruleset
            violation.rule.priority = ViolationPriority.create( violationXml.@priority );
            violation.rule.message = violationXml.toString();

            return violation;
        }

        private function onDonwloadResult( e : ResultEvent ) : void
        {
        	violations = deserializeViolations( new XML( e.result ) );

            dispatchEvent( new Event( VIOLATIONS_LOADED ) )
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