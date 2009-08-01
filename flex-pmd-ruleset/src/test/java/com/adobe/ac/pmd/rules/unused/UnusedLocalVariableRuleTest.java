package com.adobe.ac.pmd.rules.unused;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class UnusedLocalVariableRuleTest extends AbstractAstFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new UnusedLocalVariableRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      final HashMap< String, ViolationPosition[] > violations = new HashMap< String, ViolationPosition[] >();

      addToMap( violations,
                "DefaultNameEvent.as",
                new ViolationPosition[]
                { new ViolationPosition( 37, 37 ) } );

      addToMap( addToMap( addToMap( violations,
                                    "com.adobe.ac.ncss.BigImporterModel.as",
                                    new ViolationPosition[]
                                    { new ViolationPosition( 64, 64 ) } ),
                          "GenericType.as",
                          new ViolationPosition[]
                          { new ViolationPosition( 46, 46 ) } ),
                "ErrorToltipSkin.as",
                new ViolationPosition[]
                { new ViolationPosition( 163, 163 ),
                            new ViolationPosition( 166, 166 ),
                            new ViolationPosition( 184, 184 ),
                            new ViolationPosition( 183, 183 ),
                            new ViolationPosition( 165, 165 ) } );

      return addToMap( addToMap( addToMap( addToMap( violations,
                                                     "DeleteButtonRenderer.mxml",
                                                     new ViolationPosition[]
                                                     { new ViolationPosition( 69, 69 ) } ),
                                           "com.adobe.ac.ncss.VoidConstructor.as",
                                           new ViolationPosition[]
                                           { new ViolationPosition( 40, 40 ) } ),
                                 "RadonDataGrid.as",
                                 new ViolationPosition[]
                                 { new ViolationPosition( 101, 101 ),
                                             new ViolationPosition( 100, 100 ) } ),
                       "com.adobe.ac.ncss.BigModel.as",
                       new ViolationPosition[]
                       { new ViolationPosition( 47, 47 ) } );
   }
}
