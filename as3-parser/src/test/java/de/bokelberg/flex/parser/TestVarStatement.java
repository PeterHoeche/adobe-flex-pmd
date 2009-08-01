package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestVarStatement extends AbstractStatementTest
{
   @Test
   public void testFullFeaturedVar() throws TokenException
   {
      assertStatement( "1",
                       "var a : int = 4",
                       "<var-list line=\"1\" column=\"5\">"
                             + "<name-type-init line=\"1\" column=\"5\">"
                             + "<name line=\"1\" column=\"5\">a</name><type line=\"1\" column=\"7\">int</type>"
                             + "<init line=\"1\" column=\"15\"><primary line=\"1\" column=\"15\">4</primary>"
                             + "</init></name-type-init></var-list>" );

      assertStatement( "with array",
                       "var colors:Array = [0x2bc9f6, 0x0086ad];",
                       "<var-list line=\"1\" column=\"5\">"
                             + "<name-type-init line=\"1\" column=\"5\">"
                             + "<name line=\"1\" column=\"5\">colors</name><type line=\"1\" column=\"11\">Array</type>"
                             + "<init line=\"1\" column=\"20\">"
                             + "<primary line=\"1\" column=\"20\"><array line=\"1\" column=\"20\">"
                             + "<primary line=\"1\" column=\"21\">0x2bc9f6</primary>"
                             + "<primary line=\"1\" column=\"31\">0x0086ad</primary>"
                             + "</array></primary></init></name-type-init></var-list>" );
   }

   @Test
   public void testInitializedVar() throws TokenException
   {
      assertStatement( "1",
                       "var a = 4",
                       "<var-list line=\"1\" column=\"5\"><name-type-init line=\"1\" column=\"5\">"
                             + "<name line=\"1\" column=\"5\">a</name><type line=\"1\" column=\"7\">"
                             + "</type><init line=\"1\" column=\"9\"><primary line=\"1\" column=\"9\">4</primary>"
                             + "</init></name-type-init></var-list>" );
   }

   @Test
   public void testSimpleVar() throws TokenException
   {
      assertStatement( "1",
                       "var a",
                       "<var-list line=\"1\" column=\"5\"><name-type-init line=\"1\" column=\"5\">"
                             + "<name line=\"1\" column=\"5\">a</name><type line=\"2\" column=\"1\">"
                             + "</type></name-type-init></var-list>" );
   }

   @Test
   public void testTypedVar() throws TokenException
   {
      assertStatement( "1",
                       "var a : Object",
                       "<var-list line=\"1\" column=\"5\"><name-type-init line=\"1\" column=\"5\">"
                             + "<name line=\"1\" column=\"5\">a</name><type line=\"1\" column=\"7\">Object</type>"
                             + "</name-type-init></var-list>" );
   }
}
