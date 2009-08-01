package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestReturnStatement extends AbstractStatementTest
{
   @Test
   public void testEmptyReturn() throws TokenException
   {
      assertStatement( "1",
                       "return",
                       "<return line=\"2\" column=\"1\"></return>" );

      assertStatement( "2",
                       "return;",
                       "<return line=\"2\" column=\"1\"></return>" );
   }

   @Test
   public void testReturnArrayLiteral() throws TokenException
   {
      assertStatement( "1",
                       "return []",
                       "<return line=\"1\" column=\"8\"><primary line=\"1\" column=\"8\">"
                             + "<array line=\"1\" column=\"8\"></array></primary></return>" );
      assertStatement( "2",
                       "return [];",
                       "<return line=\"1\" column=\"8\"><primary line=\"1\" column=\"8\">"
                             + "<array line=\"1\" column=\"8\"></array></primary></return>" );
   }
}
