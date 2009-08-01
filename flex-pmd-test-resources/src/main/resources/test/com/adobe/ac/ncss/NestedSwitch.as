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