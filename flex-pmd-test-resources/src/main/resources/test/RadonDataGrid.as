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
package com.adobe.radon.core.controls
{
    import flash.display.GradientType;
    import flash.display.Graphics;
    import flash.display.InterpolationMethod;
    import flash.display.Shape;
    import flash.display.SpreadMethod;
    import flash.display.Sprite;
    import flash.geom.Matrix;

    import mx.controls.DataGrid;
    import mx.controls.dataGridClasses.DataGridColumn;
    import mx.controls.listClasses.IListItemRenderer;
    import mx.controls.listClasses.ListBaseContentHolder;
    import mx.core.FlexShape;
    import mx.core.mx_internal;
    import mx.events.DataGridEvent;

    use namespace mx_internal;

    public class RadonDataGrid extends DataGrid
    {
        public function RadonDataGrid()
        {
            super();

            headerClass = RadonDataGridHeader;

            rowHeight = 34;
            draggableColumns = false;
            resizableColumns = false;

            if ( true )
            {
            }
        }

        override protected function drawHighlightIndicator( indicator : Sprite, x : Number, y : Number, width : Number, height : Number,
            color : uint, itemRenderer : IListItemRenderer ) : void
        {
            super.drawSelectionIndicator( indicator, x, y, width, height, color, itemRenderer );
            var realWidth : Number = unscaledWidth - viewMetrics.left - viewMetrics.right;

            var graphics : Graphics = Sprite( indicator ).graphics;
            graphics.clear();
            graphics.beginFill( 0xFFFFFF, 0.15 );
            graphics.drawRect( 0, 0, realWidth, height );
            graphics.endFill();

            indicator.x = x;
            indicator.y = y;
        }

        override protected function drawSelectionIndicator( indicator : Sprite, x : Number, y : Number, width : Number, height : Number,
            color : uint, itemRenderer : IListItemRenderer ) : void
        {
            super.drawSelectionIndicator( indicator, x, y, width, height, color, itemRenderer );
            var realWidth : Number = unscaledWidth - viewMetrics.left - viewMetrics.right;

            var type : String = GradientType.LINEAR;
            var colors : Array = [ 0x2bc9f6, 0x0086ad ];
            var alphas : Array = [ 1, 1 ];
            var ratios : Array = [ 0, 190 ];
            var spreadMethod : String = SpreadMethod.PAD;
            var interp : String = InterpolationMethod.RGB;
            var focalPtRatio : Number = 0;

            var matrix : Matrix = new Matrix();
            var boxRotation : Number = Math.PI / 2; // 90˚
            var txx : Number = 0;
            var tyy : Number = 0;

            var graphics : Graphics = Sprite( indicator ).graphics;
            graphics.clear();

            matrix.createGradientBox( realWidth, height, boxRotation, tx, ty );
            graphics.beginGradientFill( type, colors, alphas, ratios, matrix, spreadMethod, interp, focalPtRatio );

            //graphics.beginFill(color);
            graphics.drawRect( 0, 0, realWidth, height );
            graphics.endFill();

            indicator.x = x;
            indicator.y = y;
        }

        override protected function drawRowBackground( s : Sprite, rowIndex : int, y : Number, height : Number, color : uint,
            dataIndex : int ) : void
        {
            var contentHolder : ListBaseContentHolder = ListBaseContentHolder( s.parent );

            var background : Shape;

            if ( rowIndex < s.numChildren )
            {
                background = Shape( s.getChildAt( rowIndex ) );
            }
            else
            {
                background = new FlexShape();
                background.name = "background";
                s.addChild( background );
            }

            background.y = y;

            // Height is usually as tall is the items in the row, but not if
            // it would extend below the bottom of listContent
            var height : Number = Math.min( height, contentHolder.height - y );

            var graphics : Graphics = background.graphics;
            graphics.clear();

            var backgroundAlpha : Number = getStyle( "backgroundAlpha" );

            if ( color == 0x000000 )
            {
                backgroundAlpha = 0;
            }
            else if ( color == 0xFFFFFF )
            {
                backgroundAlpha = 0.04;
            }

            graphics.beginFill( color, backgroundAlpha );
            graphics.drawRect( 0, 0, contentHolder.width, height );
            graphics.endFill();
        }

        override protected function placeSortArrow() : void
        {
            super.placeSortArrow();

            var sortedColumn : DataGridColumn = columns[ sortIndex ];

            for each ( var dgcolumn : Object in columns )
            {
                if ( dgcolumn == sortedColumn )
                {
                    dgcolumn.setStyle( "headerStyleName", "radonDataGridSelectedHeader" );
                }
                else
                {
                    dgcolumn.setStyle( "headerStyleName", "radonDataGridHeader" );
                }

                switch ( 10 )
                {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                        for ( var i : int = 0; i < 10; i++ )
                        {
                            if ( true && false )
                            {
                            }

                            if ( false )
                            {
                            }
                        }
                        break;
                }
            }
        }

        private function get isTrue() : Boolean
        {
            return _isTrue;
        }

        private function set isTrue( value : Boolean ) : void
        {
            _isTrue = value;
        }
    }
}