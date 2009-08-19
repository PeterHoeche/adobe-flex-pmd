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
package com.adobe.cairngorm.work
{
   import flash.events.Event;

   /**
    * An event that occurs while processing a work-item.
    */  
   public class WorkEvent extends Event
   {
      //------------------------------------------------------------------------
      //
      //  Constants
      //
      //------------------------------------------------------------------------
      
      //-------------------------------
      //  Event Types : IWorkItem
      //-------------------------------

      public static const WORK_START    : String = "workStart";
      public static const WORK_COMPLETE : String = "workComplete";
      public static const WORK_FAULT    : String = "workFault";

      //-------------------------------
      //  Event Types : IWorkFlow
      //-------------------------------

      public static const WORK_PROGRESS : String = "workProgress";
      public static const CHILD_START    : String = "childStart";
      public static const CHILD_COMPLETE : String = "childComplete";
      public static const CHILD_FAULT    : String = "childFault";
      
      //------------------------------------------------------------------------
      //
      //  Constructor
      //
      //------------------------------------------------------------------------

      public function WorkEvent(
         type : String, 
         workItem : IWorkItem )
      {
         super( type );
         _workItem = workItem;
      }

      //------------------------------------------------------------------------
      //
      //  Static Factory Methods
      //
      //------------------------------------------------------------------------
      
      //-------------------------------
      //  Event Types : IWorkItem
      //-------------------------------

      public static function createStartEvent( 
         workItem : IWorkItem ) : WorkEvent
      {
         return new WorkEvent( WORK_START, workItem );
      }

      public static function createCompleteEvent( 
         workItem : IWorkItem ) : WorkEvent
      {
         return new WorkEvent( WORK_COMPLETE, workItem );
      }

      public static function createFaultEvent( 
         workItem : IWorkItem, 
         message : String = null ) : WorkEvent
      {
         var event : WorkEvent = new WorkEvent( WORK_FAULT, workItem );
         event._message = message;
         return event;
      }

      //-------------------------------
      //  Event Types : IWorkFlow
      //-------------------------------

      public static function createProgressEvent( 
         workItem : IWorkItem, 
         processed : uint,
         size : uint ) : WorkEvent
      {
         var event : WorkEvent = new WorkEvent( WORK_PROGRESS, workItem );
         event._processed = processed;
         event._size = size;
         return event;
      }

      public static function createChildStartEvent( 
         workItem : IWorkItem ) : WorkEvent
      {
         return new WorkEvent( CHILD_START, workItem );
      }

      public static function createChildCompleteEvent( 
         workItem : IWorkItem ) : WorkEvent
      {
         return new WorkEvent( CHILD_COMPLETE, workItem );
      }

      public static function createChildFaultEvent( 
         workItem : IWorkItem, 
         message : String = null ) : WorkEvent
      {
         var event : WorkEvent = new WorkEvent( CHILD_FAULT, workItem );
         event._message = message;
         return event;
      }

      //------------------------------------------------------------------------
      //
      //  Properties
      //
      //------------------------------------------------------------------------
      
      //-------------------------------
      //  workItem
      //-------------------------------
      
      private var _workItem : IWorkItem;
      
      /**
       * The work-item that the event applies to.
       */ 
      public function get workItem() : IWorkItem
      {
         return _workItem;
      }
      
      //-------------------------------
      //  processed
      //-------------------------------

      private var _processed : uint;
      
      public function get processed() :  uint
      {
         return _processed;
      }
      
      //-------------------------------
      //  size
      //-------------------------------

      private var _size : uint;
      
      public function get size() :  uint
      {
         return _size;
      }
      
      //-------------------------------
      //  message
      //-------------------------------

      private var _message : String;
      
      /**
       * The message desribing the cause of a workFault event.
       */ 
      public function get message() :  String
      {
         return _message;
      }
      
      //------------------------------------------------------------------------
      //
      //  Overrides : Event
      //
      //------------------------------------------------------------------------
      
      override public function clone() : Event
      {
         var event : WorkEvent = new WorkEvent( type, _workItem );
         
         event._message = _message;
         event._processed = _processed;
         event._size = _size;
         
         return event;
      }
   }
}