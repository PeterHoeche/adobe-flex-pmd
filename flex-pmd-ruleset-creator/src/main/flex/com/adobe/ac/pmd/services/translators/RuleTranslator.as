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
