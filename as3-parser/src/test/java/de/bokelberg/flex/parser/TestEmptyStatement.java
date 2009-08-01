package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestEmptyStatement extends AbstractStatementTest
{
   @Test
   public void testComplex() throws TokenException
   {
      assertStatement( "1",
                       "{;1;;}",
                       "<block line=\"1\" column=\"2\"><stmt-empty line=\"1\" column=\"2\">;"
                             + "</stmt-empty><primary line=\"1\" column=\"3\">1"
                             + "</primary><stmt-empty line=\"1\" column=\"5\">;</stmt-empty></block>" );
   }

   @Test
   public void testSimple() throws TokenException
   {
      assertStatement( "1",
                       ";",
                       "<stmt-empty line=\"1\" column=\"1\">;</stmt-empty>" );
   }
}
