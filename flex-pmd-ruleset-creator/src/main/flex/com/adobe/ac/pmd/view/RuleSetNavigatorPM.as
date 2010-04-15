/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
 *    All rights reserved.
 *
 *    Redistribution  and  use  in  source  and  binary  forms, with or without
 *    modification,  are  permitted  provided  that  the  following  conditions
 *    are met:
 *
 *      * Redistributions  of  source  code  must  retain  the  above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions  in  binary  form  must reproduce the above copyright
 *        notice,  this  list  of  conditions  and  the following disclaimer in
 *        the    documentation   and/or   other  materials  provided  with  the
 *        distribution.
 *      * Neither the name of the Adobe Systems, Incorporated. nor the names of
 *        its  contributors  may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS  SOFTWARE  IS  PROVIDED  BY THE  COPYRIGHT  HOLDERS AND CONTRIBUTORS
 *    "AS IS"  AND  ANY  EXPRESS  OR  IMPLIED  WARRANTIES,  INCLUDING,  BUT NOT
 *    LIMITED  TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,  INCIDENTAL,  SPECIAL,
 *    EXEMPLARY,  OR  CONSEQUENTIAL  DAMAGES  (INCLUDING,  BUT  NOT  LIMITED TO,
 *    PROCUREMENT  OF  SUBSTITUTE   GOODS  OR   SERVICES;  LOSS  OF  USE,  DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY,  WHETHER  IN  CONTRACT,  STRICT  LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE  OR  OTHERWISE)  ARISING  IN  ANY  WAY  OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.view
{
    import com.adobe.ac.model.IPresentationModel;
    import com.adobe.ac.pmd.api.IGetCustomRuleset;
    import com.adobe.ac.pmd.api.IGetRootRuleset;
    import com.adobe.ac.pmd.control.events.GetCustomRulesetEvent;
    import com.adobe.ac.pmd.control.events.GetRootRulesetEvent;
    import com.adobe.ac.pmd.model.Property;
    import com.adobe.ac.pmd.model.RootRuleset;
    import com.adobe.ac.pmd.model.Rule;
    import com.adobe.ac.pmd.model.Ruleset;
    import com.adobe.ac.pmd.model.ViolationPriority;
    import com.adobe.ac.pmd.model.events.RulesetReceivedEvent;
    import com.adobe.ac.pmd.services.translators.RootRulesetTranslator;
    
    import flash.events.Event;
    import flash.events.EventDispatcher;
    import flash.net.FileReference;

	[Event( name="rootRulesetReceived", type = "flash.events.Event" )]
    [Event( name="rulesetReceived", type = "com.adobe.ac.pmd.model.events.RulesetReceivedEvent" )] // NO PMD UnboundTypeInMetadata
    public class RuleSetNavigatorPM extends EventDispatcher implements IPresentationModel, IGetRootRuleset, IGetCustomRuleset
    {
		public static const ROOT_RULESET_RECEIVED : String = "rootRulesetReceived";
		public static const PARAMETERIZED_RULE_NAME : String = "com.adobe.ac.pmd.rules.parameterized.ParameterizedRegExpBasedRule";

		[Bindable]
		public var rootRuleset : RootRuleset;
		
		private var rulesetReceived : int;

        public function RuleSetNavigatorPM()
        {
        }
		
		public function addNewRegExpBasedRule() : Ruleset
		{
			var property : Property = new Property();
			
			property.name = "expression";

			var rule : Rule = new Rule();
			
			rule.name = PARAMETERIZED_RULE_NAME;
			rule.properties.addItem( property );
			rule.priority = ViolationPriority.INFO;
			rootRuleset.addRegExpBasedRule( rule );
			
			return rootRuleset.customRuleset;
		}

        public function getRootRuleset() : void
        {
            new GetRootRulesetEvent( this ).dispatch();
        }
		
		public function getCustomRuleset() : void
		{
			new GetCustomRulesetEvent( this ).dispatch();
		}
		
		public function onReceiveCustomRuleset( ruleset : RootRuleset ) : void
		{
			markRulesetAsEntirelyDeleted();
			
			for each ( var rule : Rule in Ruleset( ruleset.rulesets.getItemAt( 0 ) ).rules )
			{
				if ( rule.name != PARAMETERIZED_RULE_NAME )
				{
					markRuleAsUsed( rule );
				}
			}
			addCustomRuleset( ruleset );			
			rootRuleset.rulesChanged();
			dispatchEvent( new Event( ROOT_RULESET_RECEIVED ) );
			dispatchEvent( new RulesetReceivedEvent( ruleset.rulesets.getItemAt( 0 ) as Ruleset ) );			
		}
		
		private function addCustomRuleset( ruleset : RootRuleset ) : void
		{
			for each ( var rule : Rule in Ruleset( ruleset.rulesets.getItemAt( 0 ) ).rules )
			{
				if ( rule.name == PARAMETERIZED_RULE_NAME )
				{
					rootRuleset.addRegExpBasedRule( rule );
				}
			}
		}
		
		private function markRuleAsUsed( ruleToFind : Rule ) : void
		{
			for each ( var childRuleset : Ruleset in rootRuleset.rulesets )
			{
				for each ( var rule : Rule in childRuleset.rules )
				{
					if ( rule.name == ruleToFind.name )
					{
						rule.unDelete();
						return;
					}
				}
			}

		}

		private function markRulesetAsEntirelyDeleted() : void
		{
			for each ( var childRuleset : Ruleset in rootRuleset.rulesets )
			{
				for each ( var rule : Rule in childRuleset.rules )
				{
					rule.remove();
				}
			}
		}
		
        public function onReceiveRootRuleset( ruleset : RootRuleset ) : void
        {
			rulesetReceived = 0;
			rootRuleset = ruleset;
			
            for each ( var childRuleset : Ruleset in ruleset.rulesets )
            {
                childRuleset.addEventListener( RulesetReceivedEvent.EVENT_NAME, onRulesetReceived );
            }

            dispatchEvent( new Event( ROOT_RULESET_RECEIVED ) );
        }

        public function exportRootRuleset() : void
        {
            var xml : XML = RootRulesetTranslator.serializeRootRuleset( rootRuleset );
            var fileReference : FileReference = new FileReference();

            fileReference.save( xml, "pmd.xml" );
        }
		
		private function onRulesetReceived( event : RulesetReceivedEvent ) : void
		{
			rulesetReceived++;
			dispatchEvent( event );			
		}
    }
}