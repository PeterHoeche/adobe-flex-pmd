package com.adobe.ac.pmd.services.translators
{
   import com.adobe.ac.pmd.model.Rule;
   import com.adobe.ac.pmd.model.Ruleset;

   public class RulesetTranslator
   {
      public static function deserialize( xml : XML ) : Ruleset
      {
         var ruleset : Ruleset = new Ruleset();
         var children : XMLList = xml.children();

         ruleset.name = xml.@name;

         for( var i : int = 1; i < children.length(); i++ )
         {
            var ruleXml : XML = children[ i ];

            if( ruleXml.@ref != undefined )
            {
               var childRuleset : Ruleset = new Ruleset();

               childRuleset.isRef = true;
               childRuleset.getRulesetContent( ruleXml.@ref );
               ruleset.rulesets.addItem( childRuleset );
            }
            else
            {
               var rule : Rule = RuleTranslator.deserialize( ruleXml );

               rule.ruleset = ruleset;
               ruleset.rules.addItem( rule );
               ruleset.isRef = false;
            }
         }

         return ruleset;
      }

      public static function serializeRootRuleset( ruleset : Ruleset ) : XML
      {
         var xmlString : String = "<ruleset name=\"" + ruleset.name + "\"" + 
            "xmlns=\"http://pmd.sf.net/ruleset/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
            "xsi:schemaLocation=\"http://pmd.sf.net/ruleset/1.0.0 http://pmd.sf.net/ruleset_xml_schema.xsd\"" +
            "xsi:noNamespaceSchemaLocation=\"http://pmd.sf.net/ruleset_xml_schema.xsd\">" +
            "<description>" + ( ruleset.description ? ruleset.description : "" ) + "</description>";

         for each( var childRuleset : Ruleset in ruleset.rulesets )
         {
            xmlString += serializeRuleset( childRuleset ).toXMLString();
         }
         for each( var rule : Rule in ruleset.rules )
         {
            xmlString += RuleTranslator.serialize( rule ).toXMLString();
         }
         xmlString += "</ruleset>";

         return XML( xmlString );
      }

      private static function serializeRuleset( ruleset : Ruleset ) : XMLList
      {
         var xmlString : String = "";

         for each( var rule : Rule in ruleset.rules )
         {
            xmlString += RuleTranslator.serialize( rule ).toXMLString();
         }

         return new XMLList( xmlString );
      }
   }
}