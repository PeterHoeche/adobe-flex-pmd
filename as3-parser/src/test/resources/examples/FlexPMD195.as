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
﻿package
{
	import flash.display.Sprite;
	
	public class FlexPMD195 extends Sprite {
		public function computeJustifyAdjustment(lineArray:Array, firstLineIndex:int, numLines:int):Number
		{
			adj = 0;
			
			if (numLines == 1)
			{
				return 0; // do nothing
			}
			
			// first line unchanged
			var firstLine:IVerticalJustificationLine = lineArray[firstLineIndex];
			var firstBaseLine:Number = getBaseline(firstLine);
			
			// descent of the last line on the bottom of the frame
			var lastLine:IVerticalJustificationLine = lineArray[firstLineIndex + numLines - 1];
			var frameBottom:Number = _textFrame.compositionHeight - Number(_textFrame.effectivePaddingBottom);
			var allowance:Number = frameBottom - getBottomOfLine(lastLine);
			if (allowance < 0)
			{
				return 0; // Some text scrolled out; don't justify
			}
			var lastBaseLine:Number = getBaseline(lastLine);
			
			adj = allowance/(lastBaseLine - firstBaseLine); // multiplicative factor by which the space between consecutive lines is increased
			return adj;
		} 
	}
}