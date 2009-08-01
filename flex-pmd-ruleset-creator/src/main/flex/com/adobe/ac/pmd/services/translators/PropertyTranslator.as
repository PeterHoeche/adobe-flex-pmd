package com.adobe.ac.pmd.services.translators
{
   import com.adobe.ac.pmd.model.Property;

   import mx.collections.ArrayCollection;
   import mx.collections.ListCollectionView;

   public class PropertyTranslator
   {
      public static function deserializeProperties( xmlList : XMLList ) : ListCollectionView
      {
         var properties : ListCollectionView = new ArrayCollection();

         for( var childIndex : int = 0; childIndex < xmlList.length(); childIndex++ )
         {
            var propertyXml : XML = xmlList[ childIndex ];
            var property : Property = new Property();

            property.name = propertyXml.@name;
            property.value = propertyXml.children()[ 0 ].toString();
            properties.addItem( property );
         }
         return properties;
      }
   }
}