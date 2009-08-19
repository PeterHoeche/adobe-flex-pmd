/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
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
 *      * Neither the name of the Adobe Systems, Inc. nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.services.translators
{
    import com.adobe.ac.pmd.model.Property;
    import com.adobe.ac.pmd.model.Rule;
    import com.adobe.ac.pmd.model.ViolationPriority;

    public class RuleTranslator
    {
        private static const INDENT : String = "        ";

        public static function deserialize( ruleXml : XML ) : Rule
        {
            var rule : Rule = new Rule();

            rule.since = ruleXml.@since;
            rule.name = ruleXml.attribute( "class" );
            rule.message = ruleXml.@message;

            for ( var childIndex : int = 0; childIndex < ruleXml.children().length(); childIndex++ )
            {
                var child : XML = ruleXml.children()[ childIndex ];
                var name : String = child.name().toString().replace( child.namespace() + "::", "" );

                deserializeChildren( rule, name, child.children() );
            }

            return rule;
        }

        public static function serialize( rule : Rule ) : XML
        {
            var xmlString : String = "<rule since=\"" + rule.since + "\" class=\"" + rule.name + "\" message=\"" + rule.message +
                "\">";

            xmlString += "<description>" + ( rule.description ? rule.description : "" ) + "</description>";
            xmlString += "<priority>" + ( rule.priority ? rule.priority.level : ViolationPriority.WARNING.level ) + "</priority>";

            if ( rule.properties.length > 0 )
            {
                xmlString += "<properties>";

                for each ( var property : Property in rule.properties )
                {
                    xmlString += "<property name=\"" + property.name + "\">";
                    xmlString += "<value>" + property.value + "</value>";
                    xmlString += "</property>";
                }
                xmlString += "</properties>";
            }

            if ( rule.examples )
            {
                xmlString += "<example><![CDATA[" + rule.examples + "]]></example>";
            }
            xmlString += "</rule>";
            return XML( xmlString );
        }

        private static function deserializeChildren( rule : Rule, propertyName : String, value : XMLList ) : void
        {
            switch ( propertyName )
            {
                case "priority":
                    rule.priority = ViolationPriority.create( Number( value.toString() ) );
                    break;
                case "description":
                    rule.description = value.toString();
                    break;
                case "properties":
                    rule.properties = PropertyTranslator.deserializeProperties( value );
                    break;
                case "example":
                    rule.examples = value.toString();
                    break;
                default:
                    break;
            }
        }
    }
}
