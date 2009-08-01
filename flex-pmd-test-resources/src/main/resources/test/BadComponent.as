package com.adobe.ac.pmd.view
{
   import mx.controls.Label;
   import mx.core.UIComponent;
   
   public class BadComponent extends UIComponent
   {
      override protected function updateDisplayList( w : Number, h : Number ) : void
      {
         super.updateDisplayList( w, h );
         
         addChild( new Label() );
         addChildAt( new Label() );
         removeChild( new Label() );
         removeChildAt( 0 );
      }
      
      override protected function createChildren() : void
      {
         super.createChildren();
         
         addMyChildren();
      }
   }   
}