package com.adobe.ac.pmd.view
{
   import mx.controls.Label;
   import mx.core.UIComponent;
   
   public class GoodComponent extends UIComponent
   {
      override protected function updateDisplayList( w : Number, h : Number ) : void
      {
         super.updateDisplayList( w, h );
      }
      
      override protected function createChildren() : void
      {
         super.createChildren();
         
         addChild( new Label() );
         addChildAt( new Label() );
         removeChild( new Label() );
         removeChildAt( 0 );
      }
   }   
}