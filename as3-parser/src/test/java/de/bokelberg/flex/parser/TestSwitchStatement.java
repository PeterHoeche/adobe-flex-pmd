package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestSwitchStatement extends AbstractStatementTest
{
   @Test
   public void testFullFeatured() throws TokenException
   {
      assertStatement( "1",
                       "switch( x ){ case 1 : trace('one'); break; default : trace('unknown'); }",
                       "<switch line=\"1\" column=\"7\"><condition line=\"1\" column=\"9\">"
                             + "<primary line=\"1\" column=\"9\">x</primary>"
                             + "</condition><cases line=\"1\" column=\"14\">"
                             + "<case line=\"1\" column=\"19\"><primary line=\"1\" column=\"19\">1</primary>"
                             + "<switch-block line=\"1\" column=\"23\"><call line=\"1\" column=\"28\">"
                             + "<primary line=\"1\" column=\"23\">trace</primary><arguments line=\"1\" column=\"29\">"
                             + "<primary line=\"1\" column=\"29\">'one'</primary></arguments>"
                             + "</call><primary line=\"1\" column=\"37\">break</primary>"
                             + "</switch-block></case><case line=\"1\" column=\"54\">"
                             + "<default line=\"1\" column=\"54\">"
                             + "default</default><switch-block line=\"1\" column=\"54\">"
                             + "<call line=\"1\" column=\"59\"><primary line=\"1\" column=\"54\">trace"
                             + "</primary>" + "<arguments line=\"1\" column=\"60\">"
                             + "<primary line=\"1\" column=\"60\">'unknown'</primary>"
                             + "</arguments></call></switch-block></case></cases></switch>" );
      assertStatement( "1",
                       "switch( x ){ case 1 : break; default:}",
                       "<switch line=\"1\" column=\"7\"><condition line=\"1\" column=\"9\"><primary line=\"1\" "
                             + "column=\"9\">x</primary></condition><cases line=\"1\" column=\"14\"><case line=\"1\" "
                             + "column=\"19\"><primary line=\"1\" column=\"19\">1</primary><switch-block line=\"1\" "
                             + "column=\"23\"><primary line=\"1\" column=\"23\">break</primary></switch-block></case>"
                             + "<case line=\"1\" column=\"38\"><default line=\"1\" column=\"38\">default</default>"
                             + "<switch-block line=\"1\" column=\"38\"></switch-block></case></cases></switch>" );

   }
}
