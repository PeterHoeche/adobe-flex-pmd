package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestConstStatement extends AbstractStatementTest
{
   @Test
   public void testFullFeaturedConst() throws TokenException
   {
      assertStatement( "1",
                       "const a : int = 4",
                       "<const-list line=\"1\" column=\"7\">"
                             + "<name-type-init line=\"1\" column=\"7\">"
                             + "<name line=\"1\" column=\"7\">a</name><type line=\"1\" column=\"9\">int</type>"
                             + "<init line=\"1\" column=\"17\"><primary line=\"1\" column=\"17\">4</primary>"
                             + "</init></name-type-init></const-list>" );
   }

   @Test
   public void testInitializedConst() throws TokenException
   {
      assertStatement( "1",
                       "const a = 4",
                       "<const-list line=\"1\" column=\"7\"><name-type-init line=\"1\" column=\"7\">"
                             + "<name line=\"1\" column=\"7\">a</name><type line=\"1\" column=\"9\">"
                             + "</type><init line=\"1\" column=\"11\"><primary line=\"1\" column=\"11\">4"
                             + "</primary></init></name-type-init></const-list>" );
   }

   @Test
   public void testSimpleConst() throws TokenException
   {
      assertStatement( "1",
                       "const a",
                       "<const-list line=\"1\" column=\"7\"><name-type-init line=\"1\" column=\"7\">"
                             + "<name line=\"1\" column=\"7\">a</name><type line=\"2\" column=\"1\">"
                             + "</type></name-type-init></const-list>" );
   }

   @Test
   public void testTypedConst() throws TokenException
   {
      assertStatement( "1",
                       "const a : Object",
                       "<const-list line=\"1\" column=\"7\"><name-type-init line=\"1\" column=\"7\">"
                             + "<name line=\"1\" column=\"7\">a</name><type line=\"1\" column=\"9\">Object</type>"
                             + "</name-type-init></const-list>" );
   }
}
