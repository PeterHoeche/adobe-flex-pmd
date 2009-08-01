package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestInterfaceContent extends AbstractAs3ParserTest
{
   @Test
   public void testImports() throws TokenException
   {
      assertInterfaceContent( "1",
                              "import a.b.c;",
                              "<import line=\"2\" column=\"8\">a.b.c</import>" );

      assertInterfaceContent( "2",
                              "import a.b.c import x.y.z",
                              "<import line=\"2\" column=\"8\">a.b.c</import>"
                                    + "<import line=\"2\" column=\"21\">x.y.z</import>" );
   }

   @Test
   public void testMethods() throws TokenException
   {
      assertInterfaceContent( "1",
                              "function a()",
                              "<function line=\"3\" column=\"1\">"
                                    + "<name line=\"2\" column=\"10\">a</name>"
                                    + "<parameter-list line=\"2\" column=\"12\">"
                                    + "</parameter-list><type line=\"3\" column=\"1\">"
                                    + "</type></function>" );

      assertInterfaceContent( "2",
                              "function set a( value : int ) : void",
                              "<set line=\"3\" column=\"1\"><name line=\"2\" column=\"14\">a"
                                    + "</name><parameter-list line=\"2\" column=\"17\">"
                                    + "<parameter line=\"2\" column=\"17\">"
                                    + "<name-type-init line=\"2\" column=\"17\">"
                                    + "<name line=\"2\" column=\"17\">value</name>"
                                    + "<type line=\"2\" column=\"23\">int</type>"
                                    + "</name-type-init></parameter></parameter-list>"
                                    + "<type line=\"2\" column=\"31\">void</type></set>" );

      assertInterfaceContent( "3",
                              "function get a() : int",
                              "<get line=\"3\" column=\"1\"><name line=\"2\" column=\"14\">a"
                                    + "</name><parameter-list line=\"2\" column=\"16\">"
                                    + "</parameter-list><type line=\"2\" column=\"18\">int" + "</type></get>" );
   }

   private void assertInterfaceContent( final String message,
                                        final String input,
                                        final String expected ) throws TokenException
   {
      scn.setLines( new String[]
      { "{",
                  input,
                  "}",
                  "__END__" } );
      asp.nextToken(); // first call
      asp.nextToken(); // skip {
      final String result = new ASTToXMLConverter().convert( asp.parseInterfaceContent() );
      assertEquals( message,
                    "<content line=\"2\" column=\"1\">"
                          + expected + "</content>",
                    result );
   }
}
