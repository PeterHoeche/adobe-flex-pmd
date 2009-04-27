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
package com.adobe.ac.pmd.model
{
   import flash.events.Event;
   import flash.events.EventDispatcher;

   public class ViolationPriority extends EventDispatcher
   {
      public static const ERROR : ViolationPriority = new ViolationPriority( 1, "Error" );
      public static const WARNING : ViolationPriority = new ViolationPriority( 3, "Warning" );
      public static const INFO : ViolationPriority = new ViolationPriority( 5, "Info" );

      private static const INITIALIZED : String = "initialized";

      private var _level : int;
      private var _name : String;

      public function ViolationPriority( level : int, name : String )
      {
         _level = level;
         _name = name;
         dispatchEvent( new Event( INITIALIZED ) );
      }

      public static function create( level : int ) : ViolationPriority
      {
         switch( level )
         {
            case 1:
               return ERROR;
            case 3:
               return WARNING;
            case 5:
               return INFO;
            default:
               throw new Error( "Unknown violation level (" + level + ")" );
         }
      }

      public static function get values() : Array
      {
         return[ ERROR, WARNING, INFO ];
      }

      [Bindable( "initialized" )]
      public function get level() : int
      {
         return _level;
      }

      [Bindable( "initialized" )]
      public function get name() : String
      {
         return _name;
      }

      override public function toString() : String
      {
         return _name;
      }
   }
}
