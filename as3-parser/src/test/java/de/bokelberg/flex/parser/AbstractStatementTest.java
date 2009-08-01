package de.bokelberg.flex.parser;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public abstract class AbstractStatementTest extends AbstractAs3ParserTest
{
   protected void assertStatement( final String message,
                                   final String input,
                                   final String expected ) throws TokenException
   {
      scn.setLines( new String[]
      { input,
                  "__END__" } );
      asp.nextToken();
      final String result = new ASTToXMLConverter().convert( asp.parseStatement() );
      assertEquals( message,
                    expected,
                    result );
   }
}