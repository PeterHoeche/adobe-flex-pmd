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
         
         for ( var i : int = 1; i < children.length(); i++ )
         {
            var ruleXml : XML = children[ i ];
            
            if ( ruleXml.@ref != undefined )
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
    "xsi:noNamespaceSchemaLocation=\"http://pmd.sf.net/ruleset_xml_schema.xsd\">\n\n" +    
    "   <description>" + ( ruleset.description ? ruleset.description : "" ) + "</description>\n\n";
         
         for each ( var childRuleset : Ruleset in ruleset.rulesets )
         {
            xmlString += serializeRuleset( childRuleset ).toXMLString();
         }
         xmlString += "</ruleset>\n";
         
         return XML( xmlString );
      }
      
      private static function serializeRuleset( ruleset : Ruleset ) : XMLList
      {
         var xmlString : String = "";
         
 //        xmlString += "      <description>" + ( ruleset.description ? ruleset.description : "" ) + "</description>\n";
        
         for each ( var rule : Rule in ruleset.rules )
         {
            xmlString += RuleTranslator.serialize( rule ).toXMLString();  
         }
         
   //      xmlString += "   </ruleset>\n"
         return new XMLList( xmlString );
      }
   }
}