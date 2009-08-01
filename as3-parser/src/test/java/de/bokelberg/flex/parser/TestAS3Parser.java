package de.bokelberg.flex.parser;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestAS3Parser extends AbstractAs3ParserTest
{
   @Test
   public void testBuildAst() throws IOException,
                             URISyntaxException
   {
      try
      {
         asp.buildAst( getClass().getResource( "/examples/unformatted/IContext.as" ).toURI().getPath() );

         asp.buildAst( getClass().getResource( "/examples/unformatted/Title.as" ).toURI().getPath() );
      }
      catch ( final TokenException e )
      {
         fail( e.getMessage() );
      }
   }
}
