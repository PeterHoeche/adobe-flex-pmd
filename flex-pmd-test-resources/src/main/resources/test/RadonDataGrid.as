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
			
			rowHeight= 34;
			draggableColumns = false;
			resizableColumns = false;
		}
		
		override protected function drawHighlightIndicator(
			indicator:Sprite, x:Number, y:Number, width:Number, height:Number, color:uint,itemRenderer:IListItemRenderer):void
		{
			super.drawSelectionIndicator( indicator, x, y, width, height, color, itemRenderer);
			var realWidth : Number = unscaledWidth - viewMetrics.left - viewMetrics.right;
			
			var g:Graphics = Sprite(indicator).graphics;
			g.clear();
			g.beginFill(0xFFFFFF,0.15);
			g.drawRect(0, 0, realWidth, height);
			g.endFill();
			
			indicator.x = x;
			indicator.y = y;
		}
		
		override protected function drawSelectionIndicator(
			indicator:Sprite, x:Number, y:Number, width:Number, height:Number, 
			color:uint, itemRenderer:IListItemRenderer):void
		{
			super.drawSelectionIndicator( indicator, x, y, width, height, color, itemRenderer);
			var realWidth : Number = unscaledWidth - viewMetrics.left - viewMetrics.right;
				
			var type:String = GradientType.LINEAR;
			var colors:Array = [0x2bc9f6, 0x0086ad];
			var alphas:Array = [1, 1];
			var ratios:Array = [0, 190 ];
			var spreadMethod:String = SpreadMethod.PAD;
			var interp:String = InterpolationMethod.RGB;
			var focalPtRatio:Number = 0;
	
			var matrix:Matrix = new Matrix();
			var boxRotation:Number = Math.PI/2; // 90˚
			var tx:Number = 0;
			var ty:Number = 0;
		
			var g:Graphics = Sprite(indicator).graphics;
			g.clear();
			
			matrix.createGradientBox(realWidth, height, boxRotation, tx, ty);
			g.beginGradientFill(
				type, 
            colors,
            alphas,
            ratios, 
            matrix, 
            spreadMethod, 
            interp, 
            focalPtRatio);
	            
			//g.beginFill(color);
			g.drawRect(0, 0, realWidth, height);
			g.endFill();
			
			indicator.x = x;
			indicator.y = y;
		}
    
		override protected function drawRowBackground(
			s:Sprite, rowIndex:int, y:Number, height:Number, color:uint, dataIndex:int):void
		{
			var contentHolder:ListBaseContentHolder = ListBaseContentHolder(s.parent);
			
			var background:Shape;
			if (rowIndex < s.numChildren)
			{
				background = Shape(s.getChildAt(rowIndex));
			}
			else
			{
				background = new FlexShape();
				background.name = "background";
				s.addChild(background);
			}
			
			background.y = y;
			
			// Height is usually as tall is the items in the row, but not if
			// it would extend below the bottom of listContent
			var height:Number = Math.min(height,contentHolder.height - y);
			
			var g:Graphics = background.graphics;
			g.clear();
			
			var backgroundAlpha : Number = getStyle("backgroundAlpha");
			
			if( color == 0x000000 )
			{
				backgroundAlpha = 0;
			}
			else if( color == 0xFFFFFF )
			{
				backgroundAlpha = 0.04;
			}
			
			g.beginFill(color, backgroundAlpha);
			g.drawRect(0, 0, contentHolder.width, height);
			g.endFill();
		}
		
		override protected function placeSortArrow():void
    	{
    		super.placeSortArrow();
    		
    		var sortedColumn : DataGridColumn = columns[sortIndex];
    		
    		for each( var dgcolumn : Object in columns )
			{
				if( dgcolumn == sortedColumn )
				{
					dgcolumn.setStyle( "headerStyleName", "radonDataGridSelectedHeader" );
				}
				else
				{
					dgcolumn.setStyle( "headerStyleName", "radonDataGridHeader" );
				}
			}
    	}
	}
}