package com.adobe.ac
{
   public class VoidConstructor   
   {
      public var iNumber : Number;
      
      public function VoidConstructor() : void
      {         
         return;
         var iNumber : lala;
      }

      public function foo() : void
      {
         throw new Error( "" );
         return null;
      }
   }
}