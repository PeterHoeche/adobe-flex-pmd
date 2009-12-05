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
package com.adobe.ac.pmd.model
{
   import com.adobe.ac.model.IDomainModel;
   
   import flash.events.Event;
   import flash.events.EventDispatcher;
   
   import mx.collections.ArrayCollection;
   import mx.collections.ListCollectionView;

   public class Rule extends EventDispatcher implements IDomainModel // NO PMD BindableClass
   {
      public static const NAME_CHANGE : String = "nameChange";

      public var since : String;
	  [Bindable]
      public var message : String;
	  [Bindable]
      public var examples : String;
	  [Bindable]
      public var description : String;
	  [Bindable]
      public var properties : ListCollectionView = new ArrayCollection();
	  [Bindable]
      public var priority : ViolationPriority;
	  [Bindable]
      public var ruleset : Ruleset;

      private var _name : String;

      public function Rule()
      {
      	ruleset = new Ruleset();
      }

      [Bindable( "nameChange" )]
      public function get name() : String
      {
         return _name;
      }

      public function set name( value : String ) : void
      {
         _name = value;
         dispatchEvent( new Event( NAME_CHANGE ) );
      }

      [Bindable( "nameChange" )]
      public function get shortName() : String
      {
         return name.substr( name.lastIndexOf( "." ) + 1 );
      }

      public function remove() : void
      {
         var ruleIndex : int = ruleset.rules.getItemIndex( this );

         if( ruleIndex != -1 )
         {
            ruleset.rules.removeItemAt( ruleIndex );
         }
      }
   }
}