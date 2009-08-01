package com.adobe.ac.pmd.model
{
   import com.adobe.ac.model.IDomainModel;

   import mx.collections.ArrayCollection;
   import mx.collections.ListCollectionView;

   public class Property implements IDomainModel
   {
      public var name : String;
      public var value : int;

      public function Property()
      {
      }
   }
}