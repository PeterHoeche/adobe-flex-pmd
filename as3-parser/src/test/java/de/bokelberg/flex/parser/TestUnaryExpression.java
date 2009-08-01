package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestUnaryExpression extends AbstractStatementTest
{
   @Test
   public void testArrayAccess() throws TokenException
   {
      assertStatement( "1",
                       "x[0]",
                       "<arr-acc line=\"1\" column=\"2\"><primary line=\"1\" column=\"1\">x<"
                             + "/primary><primary line=\"1\" column=\"3\">0</primary></arr-acc>" );
   }

   @Test
   public void testComplex() throws TokenException
   {
      assertStatement( "1",
                       "a.b['c'].d.e(1)",
                       "<dot line=\"1\" column=\"3\"><primary line=\"1\" column=\"1\">a"
                             + "</primary><dot line=\"1\" column=\"10\"><arr-acc line=\"1\" column=\"4\">"
                             + "<primary line=\"1\" column=\"3\">b</primary><primary line=\"1\" column=\"5\">"
                             + "'c'</primary></arr-acc><dot line=\"1\" column=\"12\"><primary line=\"1\" column=\"10\">"
                             + "d</primary><call line=\"1\" column=\"13\"><primary line=\"1\" column=\"12\">e"
                             + "</primary><arguments line=\"1\" column=\"14\"><primary line=\"1\" column=\"14\">1"
                             + "</primary></arguments></call></dot></dot></dot>" );

      assertStatement( "2",
                       "a.b['c']['d'].e(1)",
                       "<dot line=\"1\" column=\"3\"><primary line=\"1\" column=\"1\">a"
                             + "</primary><dot line=\"1\" column=\"15\"><arr-acc line=\"1\" column=\"4\">"
                             + "<primary line=\"1\" column=\"3\">b</primary><primary line=\"1\" column=\"5\">"
                             + "'c'</primary><primary line=\"1\" column=\"10\">'d'</primary>"
                             + "</arr-acc><call line=\"1\" column=\"16\"><primary line=\"1\" column=\"15\">"
                             + "e</primary><arguments line=\"1\" column=\"17\"><primary line=\"1\" column=\"17\">1"
                             + "</primary></arguments></call></dot></dot>" );
   }

   @Test
   public void testMethodCall() throws TokenException
   {
      assertStatement( "1",
                       "method()",
                       "<call line=\"1\" column=\"7\"><primary line=\"1\" column=\"1\">"
                             + "method</primary><arguments line=\"1\" column=\"8\"></arguments></call>" );

      assertStatement( "2",
                       "method( 1, \"two\" )",
                       "<call line=\"1\" column=\"7\"><primary line=\"1\" column=\"1\">"
                             + "method</primary><arguments line=\"1\" column=\"9\"><primary line=\"1\" column=\"9\">1"
                             + "</primary><primary line=\"1\" column=\"12\">\"two\"</primary></arguments></call>" );
   }

   @Test
   public void testMultipleMethodCall() throws TokenException
   {
      assertStatement( "1",
                       "method()()",
                       "<call line=\"1\" column=\"7\"><primary line=\"1\" column=\"1\">"
                             + "method</primary><arguments line=\"1\" column=\"8\"></arguments>"
                             + "<arguments line=\"1\" column=\"10\"></arguments></call>" );
   }

   @Test
   public void testParseUnaryExpressions() throws TokenException
   {
      assertStatement( "1",
                       "++x",
                       "<pre-inc line=\"1\" column=\"3\"><primary line=\"1\" column=\"3\">x</primary></pre-inc>" );
      assertStatement( "2",
                       "x++",
                       "<post-inc line=\"2\" column=\"1\"><primary line=\"1\" column=\"1\">x</primary></post-inc>" );
      assertStatement( "3",
                       "--x",
                       "<pre-dec line=\"1\" column=\"3\"><primary line=\"1\" column=\"3\">x</primary></pre-dec>" );
      assertStatement( "4",
                       "x--",
                       "<post-dec line=\"2\" column=\"1\"><primary line=\"1\" column=\"1\">x</primary></post-dec>" );
      assertStatement( "5",
                       "+x",
                       "<plus line=\"1\" column=\"2\"><primary line=\"1\" column=\"2\">x</primary></plus>" );
      assertStatement( "6",
                       "+ x",
                       "<plus line=\"1\" column=\"3\"><primary line=\"1\" column=\"3\">x</primary></plus>" );
      assertStatement( "7",
                       "-x",
                       "<minus line=\"1\" column=\"2\"><primary line=\"1\" column=\"2\">x</primary></minus>" );
      assertStatement( "8",
                       "- x",
                       "<minus line=\"1\" column=\"3\"><primary line=\"1\" column=\"3\">x</primary></minus>" );
      assertStatement( "9",
                       "delete x",
                       "<delete line=\"1\" column=\"8\"><primary line=\"1\" column=\"8\">x</primary></delete>" );
      assertStatement( "a",
                       "void x",
                       "<void line=\"1\" column=\"6\"><primary line=\"1\" column=\"6\">x</primary></void>" );
      assertStatement( "b",
                       "typeof x",
                       "<typeof line=\"1\" column=\"8\"><primary line=\"1\" column=\"8\">x</primary></typeof>" );
      assertStatement( "c",
                       "! x",
                       "<not line=\"1\" column=\"3\"><primary line=\"1\" column=\"3\">x</primary></not>" );
      assertStatement( "d",
                       "~ x",
                       "<b-not line=\"1\" column=\"3\"><primary line=\"1\" column=\"3\">x</primary></b-not>" );
      assertStatement( "e",
                       "x++",
                       "<post-inc line=\"2\" column=\"1\"><primary line=\"1\" column=\"1\">x</primary></post-inc>" );
      assertStatement( "f",
                       "x--",
                       "<post-dec line=\"2\" column=\"1\"><primary line=\"1\" column=\"1\">x</primary></post-dec>" );
   }
}
