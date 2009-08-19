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
package com.adobe.cairngorm.work{   import mx.logging.ILogger;   import mx.logging.Log;      /**    * A work-flow that processes its children in sequence. The next child is    * started only when the previous child completes.    */    public class SequenceWorkFlow extends WorkFlow   {      //------------------------------------------------------------------------      //      //  Constants      //      //------------------------------------------------------------------------      private static const LOG : ILogger =          Log.getLogger( "com.adobe.cairngorm.work.SequenceWorkFlow" );               //------------------------------------------------------------------------      //      //  Private Variables      //      //------------------------------------------------------------------------      /** The index of the work-item currently processing. */      private var currentIndex : uint = 0;            //------------------------------------------------------------------------      //      //  Constructor      //      //------------------------------------------------------------------------      public function SequenceWorkFlow()      {         super();      }      //------------------------------------------------------------------------      //      //  Implementation : WorkItem      //      //------------------------------------------------------------------------      override protected function performWork() : void      {         if ( Log.isDebug() )         {            LOG.debug(                "Starting sequence work-flow: label={0}, children={1}, size={2}",               label,               children.length,               size );         }                  startNextWorkItem();      }            //------------------------------------------------------------------------      //      //  Overrides : WorkFlow      //      //------------------------------------------------------------------------      override protected function onChildWorkComplete( event : WorkEvent ) : void      {         super.onChildWorkComplete( event );         startNextWorkItem();      }            override protected function onChildWorkFault( event : WorkEvent ) : void      {         super.onChildWorkFault( event );         if ( Log.isError() )         {            LOG.error( "Fault during sequence work-flow: label={0}", label );         }         fault( event.message );      }      //------------------------------------------------------------------------      //      //  Private methods      //      //------------------------------------------------------------------------      private function startNextWorkItem() : void      {         if ( hasMoreWorkItems )         {            final var skipped : Boolean = processChild( getNextChild() ) == false;                        if ( skipped )            {               startNextWorkItem();            }          }         else         {            if ( Log.isDebug() )            {               LOG.debug( "Completed sequence work-flow: label={0}", label );            }            complete();         }      }      private function get hasMoreWorkItems() : Boolean      {         return children && currentIndex < children.length;      }      private function getNextChild() : IWorkItem      {         return children[ currentIndex++ ] as IWorkItem;      }   }}