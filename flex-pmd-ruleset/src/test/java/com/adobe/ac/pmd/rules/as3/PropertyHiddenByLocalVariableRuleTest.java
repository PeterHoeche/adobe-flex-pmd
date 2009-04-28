package com.adobe.ac.pmd.rules.as3;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.adobe.ac.pmd.rules.core.AbstractAstFlexRuleTest;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;

public class PropertyHiddenByLocalVariableRuleTest
      extends AbstractAstFlexRuleTest
{
   @Override
   @Test
   public void testProcessConcernedButNonViolatingFiles()
         throws FileNotFoundException, URISyntaxException
   {
      assertEmptyViolations( "GoodComponent.as" );
      assertEmptyViolations( "RadonDataGrid.as" );
   }

   @Override
   @Test
   public void testProcessNonConcernedFiles() throws FileNotFoundException,
         URISyntaxException
   {
      assertEmptyViolations( "Main.mxml" );
   }

   @Override
   @Test
   public void testProcessViolatingFiles() throws FileNotFoundException,
         URISyntaxException
   {
      assertViolations(
            "com.adobe.ac.ncss.VoidConstructor.as", new ViolationPosition[]
            { new ViolationPosition( 40, 40 ) } );
   }

   @Override
   protected AbstractFlexRule getRule()
   {
      return new PropertyHiddenByLocalVariableRule();
   }
}
