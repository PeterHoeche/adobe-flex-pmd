package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestDoStatement extends AbstractStatementTest
{
   @Test
   public void testDo() throws TokenException
   {
      assertStatement( "1",
                       "do{ trace( i ); } while( i++ );",
                       "<do line=\"1\" column=\"3\"><block line=\"1\" column=\"5\">"
                             + "<call line=\"1\" column=\"10\"><primary line=\"1\" column=\"5\">"
                             + "trace</primary><arguments line=\"1\" column=\"12\">"
                             + "<primary line=\"1\" column=\"12\">i</primary></arguments>"
                             + "</call></block><condition line=\"1\" column=\"26\">"
                             + "<post-inc line=\"1\" column=\"30\"><primary line=\"1\" column=\"26\">"
                             + "i</primary></post-inc></condition></do>" );
   }

   @Test
   public void testDoWithEmptyStatement() throws TokenException
   {
      assertStatement( "1",
                       "do ; while( i++ ); ",
                       "<do line=\"1\" column=\"4\"><stmt-empty line=\"1\" column=\"4\">;</stmt-empty>"
                             + "<condition line=\"1\" column=\"13\"><post-inc line=\"1\" column=\"17\">"
                             + "<primary line=\"1\" column=\"13\">i</primary></post-inc></condition></do>" );
   }

   @Test
   public void testDoWithoutBlock() throws TokenException
   {
      assertStatement( "1",
                       "do trace( i ); while( i++ ); ",
                       "<do line=\"1\" column=\"4\"><call line=\"1\" column=\"9\">"
                             + "<primary line=\"1\" column=\"4\">trace</primary><arguments line=\"1\" "
                             + "column=\"11\"><primary line=\"1\" column=\"11\">i</primary>"
                             + "</arguments></call><condition line=\"1\" column=\"23\">"
                             + "<post-inc line=\"1\" column=\"27\"><primary line=\"1\" "
                             + "column=\"23\">i</primary></post-inc></condition></do>" );
   }
}
