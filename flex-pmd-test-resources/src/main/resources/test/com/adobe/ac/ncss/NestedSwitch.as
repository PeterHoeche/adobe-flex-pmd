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
package view {

	public class NestedPanel extends UIComponent 
	{
		protected function currentStateChangeHandler(e:StateChangeEvent):void {
			switch (e.newState) {
				case STATE_PRODUCTS_IN_COMPARE	:
					hideCloseButton();
					height = 230;
					borderBreakPointX = BORDER_BREAK_POINT.x;
					borderBreakPointY = BORDER_BREAK_POINT.y;
					drawCompareBackground();
					switch (e.oldState) {
						case "" :
							createPlaceHolders();
							createPlaceHolderLabels();
						break;
						case STATE_COMPARE_VIEW	:
							if (productsInCompare.length < 3) {
								drawPlaceHolder(PLACEHOLDER_COORDS[2] as Point);
								lastPlaceholderLabel = createPlaceHolderLabel(PLACEHOLDER_COORDS[2] as Point);
							}
							showCompareButton();
						break;
					}
				break;
				case STATE_COMPARE_VIEW	:
					if (productsInCompare.length < 3)
						removeChild(lastPlaceholderLabel);
					hideCompareButton();
					showCloseButton();
					createCompareInfoView();
				break;
				default :
					height = 40;
					drawDefaultBackground();
				break;
			}
		}
	}
}