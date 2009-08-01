package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestIfStatement extends AbstractStatementTest
{
   @Test
   public void testIf() throws TokenException
   {
      assertStatement( "1",
                       "if( true ){ trace( true ); }",
                       "<if line=\"1\" column=\"3\">"
                             + "<condition line=\"1\" column=\"5\">"
                             + "<primary line=\"1\" column=\"5\">true</primary></condition>"
                             + "<block line=\"1\" column=\"13\"><call line=\"1\" column=\"18\">"
                             + "<primary line=\"1\" column=\"13\">trace"
                             + "</primary><arguments line=\"1\" column=\"20\">"
                             + "<primary line=\"1\" column=\"20\">true</primary>"
                             + "</arguments></call></block></if>" );
   }

   @Test
   public void testIfElse() throws TokenException
   {
      assertStatement( "1",
                       "if( true ){ trace( true ); } else { trace( false )}",
                       "<if line=\"1\" column=\"3\"><condition line=\"1\" column=\"5\">"
                             + "<primary line=\"1\" column=\"5\">true"
                             + "</primary></condition><block line=\"1\" column=\"13\">"
                             + "<call line=\"1\" column=\"18\"><primary line=\"1\" column=\"13\">trace"
                             + "</primary><arguments line=\"1\" column=\"20\">"
                             + "<primary line=\"1\" column=\"20\">true</primary></arguments>"
                             + "</call></block><block line=\"1\" column=\"37\">"
                             + "<call line=\"1\" column=\"42\">"
                             + "<primary line=\"1\" column=\"37\">trace</primary>"
                             + "<arguments line=\"1\" column=\"44\">"
                             + "<primary line=\"1\" column=\"44\">false</primary>"
                             + "</arguments></call></block></if>" );
   }

   @Test
   public void testIfWithArrayAccessor() throws TokenException
   {
      assertStatement( "",
                       "if ( chart.getItemAt( 0 )[ xField ] > targetXFieldValue ){}",
                       "<if line=\"1\" column=\"4\"><condition line=\"1\" column=\"6\"><dot line=\"1\""
                             + " column=\"12\"><primary line=\"1\" "
                             + "column=\"6\">chart</primary><relation line=\"1\" "
                             + "column=\"12\"><call line=\"1\" column=\"21\"><primary line=\"1\" "
                             + "column=\"12\">getItemAt</primary><arguments line=\"1\" "
                             + "column=\"23\"><primary line=\"1\" "
                             + "column=\"23\">0</primary></arguments><array line=\"1\" "
                             + "column=\"26\"><primary line=\"1\" "
                             + "column=\"28\">xField</primary></array></call><op line=\"1\" "
                             + "column=\"37\">&gt;</op><primary "
                             + "line=\"1\" column=\"39\">targetXFieldValue</primary>"
                             + "</relation></dot></condition><block line=\"1\" "
                             + "column=\"59\"></block></if>" );
   }

   @Test
   public void testIfWithEmptyStatement() throws TokenException
   {
      assertStatement( "1",
                       "if( i++ ); ",
                       "<if line=\"1\" column=\"3\"><condition line=\"1\" column=\"5\">"
                             + "<post-inc line=\"1\" column=\"9\"><primary line=\"1\" column=\"5\">"
                             + "i</primary></post-inc></condition><stmt-empty line=\"1\" column=\"10\">;"
                             + "</stmt-empty></if>" );
   }

   @Test
   public void testIfWithoutBlock() throws TokenException
   {
      assertStatement( "1",
                       "if( i++ ) trace( i ); ",
                       "<if line=\"1\" column=\"3\"><condition line=\"1\" column=\"5\">"
                             + "<post-inc line=\"1\" column=\"9\"><primary line=\"1\" column=\"5\">i"
                             + "</primary></post-inc></condition><call line=\"1\" column=\"16\">"
                             + "<primary line=\"1\" column=\"11\">trace</primary><arguments line=\"1\" "
                             + "column=\"18\"><primary line=\"1\" column=\"18\">i</primary>"
                             + "</arguments></call></if>" );
   }
}
