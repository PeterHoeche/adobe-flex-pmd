package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestWhileStatement extends AbstractStatementTest
{
   @Test
   public void testWhile() throws TokenException
   {
      assertStatement( "1",
                       "while( i++ ){ trace( i ); }",
                       "<while line=\"1\" column=\"6\">"
                             + "<condition line=\"1\" column=\"8\">"
                             + "<post-inc line=\"1\" column=\"12\">"
                             + "<primary line=\"1\" column=\"8\">i</primary>"
                             + "</post-inc>"
                             + "</condition>"
                             + "<block line=\"1\" column=\"15\"><call line=\"1\" column=\"20\">"
                             + "<primary line=\"1\" column=\"15\">trace</primary>"
                             + "<arguments line=\"1\" column=\"22\"><primary line=\"1\" column=\"22\">i</primary>"
                             + "</arguments></call>" + "</block>" + "</while>" );
   }

   @Test
   public void testWhileWithEmptyStatement() throws TokenException
   {
      assertStatement( "1",
                       "while( i++ ); ",
                       "<while line=\"1\" column=\"6\">"
                             + "<condition line=\"1\" column=\"8\">"
                             + "<post-inc line=\"1\" column=\"12\">"
                             + "<primary line=\"1\" column=\"8\">i</primary>"
                             + "</post-inc></condition><stmt-empty line=\"1\" column=\"13\">;</stmt-empty></while>" );
   }

   @Test
   public void testWhileWithoutBlock() throws TokenException
   {
      assertStatement( "1",
                       "while( i++ ) trace( i ); ",
                       "<while line=\"1\" column=\"6\">"
                             + "<condition line=\"1\" column=\"8\">"
                             + "<post-inc line=\"1\" column=\"12\">"
                             + "<primary line=\"1\" column=\"8\">i</primary>"
                             + "</post-inc></condition><call line=\"1\" column=\"19\">"
                             + "<primary line=\"1\" column=\"14\">trace</primary><arguments line=\"1\" column=\"21\">"
                             + "<primary line=\"1\" column=\"21\">i</primary></arguments></call></while>" );
   }
}
