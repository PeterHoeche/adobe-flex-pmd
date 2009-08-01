package de.bokelberg.flex.parser;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.adobe.ac.pmd.files.impl.FileUtils;

import de.bokelberg.flex.parser.AS3Scanner.Token;

public class TestAS3ScannerWithFiles extends AbstractAs3ParserTest
{
   @Test
   public void testSimple() throws IOException,
                           URISyntaxException
   {
      final String[] expected = new String[]
      { "package",
                  "simple",
                  "{",
                  "public",
                  "class",
                  "Simple",
                  "{",
                  "public",
                  "function",
                  "Simple",
                  "(",
                  ")",
                  "{",
                  "trace",
                  "(",
                  "\"Simple\"",
                  ")",
                  ";",
                  "}",
                  "}" };
      assertFile( expected,
                  "Simple.as" );
   }

   private void assertFile( final String[] expected,
                            final String fileName ) throws IOException,
                                                   URISyntaxException
   {
      final String[] lines = FileUtils.readStrings( new File( getClass().getResource( "/examples/unformatted/" )
                                                                       .toURI()
                                                                       .getPath()
            + fileName ) );
      assertLines( expected,
                   lines );
   }

   private void assertLines( final String[] expected,
                             final String[] lines )
   {
      scn.setLines( lines );
      for ( int i = 0; i < expected.length; i++ )
      {
         assertText( Integer.toString( i ),
                     expected[ i ] );
      }
   }

   private void assertText( final String message,
                            final String text )
   {
      Token token = null;
      token = scn.nextToken();
      assertEquals( message,
                    text,
                    token.getText() );
   }
}
