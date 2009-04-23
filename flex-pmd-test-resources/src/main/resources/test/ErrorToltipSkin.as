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
package com.adobe.radon.core.skin
{

	import flash.display.GradientType;
	import flash.display.Graphics;
	import flash.display.InterpolationMethod;
	import flash.display.SpreadMethod;
	import flash.filters.DropShadowFilter;
	import flash.geom.Matrix;

	import mx.core.EdgeMetrics;
	import mx.graphics.RectangularDropShadow;
	import mx.skins.RectangularBorder;

	/**
	 *  The skin for a ToolTip.
	 */
	public class ErrorToolTipSkin extends RectangularBorder
	{
		//--------------------------------------------------------------------------
		//
		//  Constructor
		//
		//--------------------------------------------------------------------------

		/**
		 *  Constructor.
		 */
		public function ErrorToolTipSkin() 
		{
			super(); 
		}

		//--------------------------------------------------------------------------
		//
		//  Variables
		//
		//--------------------------------------------------------------------------

		/**
		 *  @private
		 */
		private var dropShadow:RectangularDropShadow;

		//--------------------------------------------------------------------------
		//
		//  Overridden properties
		//
		//--------------------------------------------------------------------------

		//----------------------------------
		//  borderMetrics
		//----------------------------------

		/**
		 *  @private
		 *  Storage for the borderMetrics property.
		 */
		private var _borderMetrics:EdgeMetrics;

		/**
		 *  @private
		 */
		override public function get borderMetrics():EdgeMetrics
		{     
			if (_borderMetrics)
				return _borderMetrics;

			var borderStyle:String = getStyle("borderStyle");
			switch (borderStyle)
			{
				case "errorTipRight":
				{
					_borderMetrics = new EdgeMetrics(15, 1, 3, 3);
					break;
				}

				case "errorTipAbove":
				{
					_borderMetrics = new EdgeMetrics(3, 1, 3, 15);
					break;
				}

				case "errorTipBelow":
				{
					_borderMetrics = new EdgeMetrics(3, 13, 3, 3);
					break;
				}

				default: // "toolTip"
				{
					_borderMetrics = new EdgeMetrics(3, 1, 3, 3);
					break;
				}
			}

			return _borderMetrics;
		}

		//--------------------------------------------------------------------------
		//
		//  Overridden methods
		//
		//--------------------------------------------------------------------------

		/**
		 *  @private
		 *  If borderStyle may have changed, clear the cached border metrics.
		 */
		override public function styleChanged(styleProp:String):void
		{
			if (styleProp == "borderStyle" ||
				styleProp == "styleName" ||
				styleProp == null)
			{
				_borderMetrics = null;
			}

			invalidateDisplayList();
		}

		/**
		 *  @private
		 *  Draw the background and border.
		 */
		override protected function updateDisplayList(w:Number, h:Number):void
		{  
			super.updateDisplayList(w, h);

			var borderStyle:String = getStyle("borderStyle");
			var backgroundColor:uint = getStyle("backgroundColor");
			var backgroundAlpha:Number= getStyle("backgroundAlpha");
			var borderColor:uint = getStyle("borderColor");
			var cornerRadius:Number = getStyle("cornerRadius");
			var shadowColor:uint = getStyle("shadowColor");
			var shadowAlpha:Number = 0.1;

			var g:Graphics = graphics;
			g.clear();

			filters = [];

			var type:String = GradientType.LINEAR;
			var colors:Array = [0xff8300, 0xff4200];
			var alphas:Array = [1, 1];
			var ratios:Array = [0, 125 ];
			var spreadMethod:String = SpreadMethod.PAD;
			var interp:String = InterpolationMethod.RGB;
			var focalPtRatio:Number = 0;

			var matrix:Matrix = new Matrix();
			var boxRotation:Number = Math.PI/2; // 90Ëš
			var tx:Number = 0;
			var ty:Number = 0;

			switch (borderStyle)
			{
				case "toolTip":
				{
					// face
					drawRoundRect(
						3, 1, w - 6, h - 4, cornerRadius,
						backgroundColor, backgroundAlpha) 

					if (!dropShadow)
						dropShadow = new RectangularDropShadow();

					dropShadow.distance = 3;
					dropShadow.angle = 90;
					dropShadow.color = 0;
					dropShadow.alpha = 0.4;

					dropShadow.tlRadius = cornerRadius + 2;
					dropShadow.trRadius = cornerRadius + 2;
					dropShadow.blRadius = cornerRadius + 2;
					dropShadow.brRadius = cornerRadius + 2;

					dropShadow.drawShadow(graphics, 3, 0, w - 6, h - 4);

					break;
				}

				case "errorTipRight":
				{
					// border 
					matrix.createGradientBox(w - 11, h - 2, boxRotation, tx, ty);
					g.beginGradientFill(
						type, 
						colors,
						alphas,
						ratios, 
						matrix, 
						spreadMethod, 
						interp, 
						focalPtRatio);

					drawRoundRect(
						11, 0, w - 11, h - 2, 0 );

					// left pointer 
					g.beginGradientFill(
						type, 
						colors,
						alphas,
						ratios, 
						matrix, 
						spreadMethod, 
						interp, 
						focalPtRatio);

					g.moveTo(11, 7);
					g.lineTo(0, 13);
					g.lineTo(11, 19);
					g.moveTo(11, 7);
					g.endFill();

					filters = [ new DropShadowFilter(2, 90, 0, 0.4) ];
					break;
				}

				case "errorTipAbove":
				{
					// border 
					matrix.createGradientBox(w, h - 13, boxRotation, tx, ty);
					g.beginGradientFill(
						type, 
						colors,
						alphas,
						ratios, 
						matrix, 
						spreadMethod, 
						interp, 
						focalPtRatio);

					drawRoundRect(
						0, 0, w, h - 13, 0 ); 

					// bottom pointer 
					g.beginGradientFill(
						type, 
						colors,
						alphas,
						ratios, 
						matrix, 
						spreadMethod, 
						interp, 
						focalPtRatio);

					g.moveTo(9, h - 13);
					g.lineTo(15, h - 2);
					g.lineTo(21, h - 13);
					g.moveTo(9, h - 13);
					g.endFill();

					filters = [ new DropShadowFilter(2, 90, 0, 0.4) ];
					break;
				}

				case "errorTipBelow":
				{
					// border 
					matrix.createGradientBox(w, h - 13, boxRotation, tx, ty);
					g.beginGradientFill(
						type, 
						colors,
						alphas,
						ratios, 
						matrix, 
						spreadMethod, 
						interp, 
						focalPtRatio);

					drawRoundRect(
						0, 11, w, h - 13, 0 );

					// top pointer 
					g.beginGradientFill(
						type, 
						colors,
						alphas,
						ratios, 
						matrix, 
						spreadMethod, 
						interp, 
						focalPtRatio);

					g.moveTo(9, 11);
					g.lineTo(15, 0);
					g.lineTo(21, 11);
					g.moveTo(10, 11);
					g.endFill();

					filters = [ new DropShadowFilter(2, 90, 0, 0.4) ];
					break;
				}
			}
		}
	}
}
