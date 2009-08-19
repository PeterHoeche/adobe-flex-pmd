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
package suite.cases
{
    import flexunit.framework.TestCase;

    import math.RaoulUtil;

    public class RaoulTest
    {
        private var classToTestRef : math.RaoulUtil;
        private static var _allowEdit : ArrayCollection = new ArrayCollection( [ COMMENT_ADDED, COMMENT_UPDATED ] );

        private static var _locked : Boolean;
        {
        	loacked = true;
        }

        [Before]
        public function setUp() : void
        {
            classToTestRef = new RaoulUtil();
        }

        [Test]
        public function foo() : void
        {
            classToTestRef.foo1();
        }

        [Test]
        public function fooAgain() : void
        {
            classToTestRef.foo();
        }

        public static function editAllowed( status : ActionItemCommentStatus ) : Boolean
        {
            return _allowEdit.contains( status );
        }

        /** Locked constructor will fail if used outside of the enum class */
        public function RaoulTest( key : int, name : String )
        {
            if ( _locked )
            {
                throw new Error( "Enumeration constructor is private, do not use externally" );
            }
            _key = key;
            _name = name;
        }
    }
}