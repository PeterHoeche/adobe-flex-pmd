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
package org.granite.util 
{	
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.utils.getQualifiedClassName;
	
	public class Enum implements IExternalizable
	{
		private var _name:String;
		
		public function Enum(name:String, restrictor:Restrictor) {
			_name = (restrictor is Restrictor ? name : constantOf(name).name);
		}
		
		public function get name():String {
			return _name;
		}
		
		protected function getConstants():Array {
			throw new Error("Should be overriden");
		}
		
		protected function constantOf(name:String):Enum {
			for each (var o:* in getConstants()) {
				var enum:Enum = Enum(o);
				if (enum.name == name)
					return enum;
			}
			throw new ArgumentError("Invalid " + getQualifiedClassName(this) + " value: " + name);
		}
		
		public function readExternal(input:IDataInput):void {
			_name = constantOf(input.readObject() as String).name;
		}
		
		public function writeExternal(output:IDataOutput):void {
			output.writeObject(_name);
		}
		
		public static function normalize(enum:Enum):Enum {
			return (enum == null ? null : enum.constantOf(enum.name));
		}
		
		public static function readEnum(input:IDataInput):Enum {
			return normalize(input.readObject() as Enum);
		}
		
		public function toString():String {
			return name;
		}
		
		public function equals(other:Enum):Boolean {
			return other === this || (
				other != null &&
				getQualifiedClassName(this) == getQualifiedClassName(other) &&
				other.name == this.name
			);
		}
		
		protected static function get _():Restrictor { // NO PMD ProtectedStaticMethod
			return new Restrictor();
		}
	}
}
class Restrictor {}
