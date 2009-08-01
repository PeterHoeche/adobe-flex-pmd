package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestExpression extends AbstractStatementTest
{
   @Test
   public void testAddExpression() throws TokenException
   {
      assertStatement( "1",
                       "5+6",
                       "<add line=\"1\" column=\"1\"><primary line=\"1\" "
                             + "column=\"1\">5</primary><op line=\"1\" "
                             + "column=\"2\">+</op><primary line=\"1\" column=\"3\">6</primary></add>" );
   }

   @Test
   public void testAndExpression() throws TokenException
   {
      assertStatement( "1",
                       "5&&6",
                       "<and line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5</primary>"
                             + "<op line=\"1\" column=\"2\">&&</op>"
                             + "<primary line=\"1\" column=\"4\">6</primary></and>" );
   }

   @Test
   public void testAssignmentExpression() throws TokenException
   {
      assertStatement( "1",
                       "x+=6",
                       "<assign line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">x"
                             + "</primary><op line=\"1\" column=\"2\">+=</op><primary line=\"1\" "
                             + "column=\"4\">6</primary></assign>" );
   }

   @Test
   public void testBitwiseAndExpression() throws TokenException
   {
      assertStatement( "1",
                       "5&6",
                       "<b-and line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">&</op><primary line=\"1\" "
                             + "column=\"3\">6</primary></b-and>" );
   }

   @Test
   public void testBitwiseOrExpression() throws TokenException
   {
      assertStatement( "1",
                       "5|6",
                       "<b-or line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">|</op><primary line=\"1\" "
                             + "column=\"3\">6</primary></b-or>" );
   }

   @Test
   public void testBitwiseXorExpression() throws TokenException
   {
      assertStatement( "1",
                       "5^6",
                       "<b-xor line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">^</op><primary line=\"1\" "
                             + "column=\"3\">6</primary></b-xor>" );
   }

   @Test
   public void testConditionalExpression() throws TokenException
   {
      assertStatement( "1",
                       "true?5:6",
                       "<conditional line=\"1\" column=\"5\"><primary line=\"1\" column=\"1\">"
                             + "true</primary><primary line=\"1\" column=\"6\">5"
                             + "</primary><primary line=\"1\" column=\"8\">6" + "</primary></conditional>" );
   }

   @Test
   public void testEqualityExpression() throws TokenException
   {
      assertStatement( "1",
                       "5!==6",
                       "<equality line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">!==</op><primary line=\"1\" "
                             + "column=\"5\">6</primary></equality>" );
   }

   @Test
   public void testMulExpression() throws TokenException
   {
      assertStatement( "1",
                       "5/6",
                       "<mul line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">/</op><primary line=\"1\" "
                             + "column=\"3\">6</primary></mul>" );
   }

   @Test
   public void testOrExpression() throws TokenException
   {
      assertStatement( "1",
                       "5||6",
                       "<or line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">||</op><primary line=\"1\" "
                             + "column=\"4\">6</primary></or>" );
   }

   @Test
   public void testRelationalExpression() throws TokenException
   {
      assertStatement( "1",
                       "5<=6",
                       "<relation line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">&lt;=</op><primary line=\"1\" "
                             + "column=\"4\">6</primary></relation>" );
   }

   @Test
   public void testShiftExpression() throws TokenException
   {
      assertStatement( "1",
                       "5<<6",
                       "<shift line=\"1\" column=\"1\"><primary line=\"1\" column=\"1\">5"
                             + "</primary><op line=\"1\" column=\"2\">&lt;&lt;</op><primary line=\"1\" "
                             + "column=\"4\">6</primary></shift>" );
   }
}
