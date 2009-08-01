package com.adobe.ac.pmd.rules.style;

import java.util.HashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.AbstractFlexRuleTest;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class OverLongLineRuleTest extends AbstractFlexRuleTest
{
   @Override
   protected AbstractFlexRule getRule()
   {
      return new OverLongLineRule();
   }

   @Override
   protected Map< String, ViolationPosition[] > getViolatingFiles()
   {
      final Map< String, ViolationPosition[] > violatedFiles = new HashMap< String, ViolationPosition[] >();

      addToMap( addToMap( violatedFiles,
                          "SkinStyles.as",
                          new ViolationPosition[]
                          { new ViolationPosition( 82, 82 ) } ),
                "com.adobe.ac.ncss.mxml.IterationsList2.mxml",
                new ViolationPosition[]
                { new ViolationPosition( 54, 54 ) } );

      addToMap( addToMap( addToMap( violatedFiles,
                                    "RadonDataGrid.as",
                                    new ViolationPosition[]
                                    { new ViolationPosition( 68, 68 ),
                                                new ViolationPosition( 84, 84 ),
                                                new ViolationPosition( 117, 117 ) } ),
                          "Simple.as",
                          new ViolationPosition[]
                          { new ViolationPosition( 1, 1 ) } ),
                "AbstractRowData.as",
                new ViolationPosition[]
                { new ViolationPosition( 46, 46 ),
                            new ViolationPosition( 47, 47 ),
                            new ViolationPosition( 49, 49 ) } );

      return violatedFiles;
   }
}
