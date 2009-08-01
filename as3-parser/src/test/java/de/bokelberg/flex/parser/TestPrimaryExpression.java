package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestPrimaryExpression extends AbstractAs3ParserTest
{
   @Test
   public void testArrayLiteral() throws TokenException
   {
      assertPrimary( "[1,2,3]",
                     "<array line=\"1\" column=\"1\"><primary line=\"1\" column=\"2\">1"
                           + "</primary><primary line=\"1\" column=\"4\">2</primary>"
                           + "<primary line=\"1\" column=\"6\">3</primary></array>" );
   }

   @Test
   public void testBooleans() throws TokenException
   {
      assertPrimary( "true" );
      assertPrimary( "false" );
   }

   @Test
   public void testFunctionLiteral() throws TokenException
   {
      assertPrimary( "function ( a : Object ) : * { trace('test'); }",
                     "<lambda line=\"1\" column=\"10\"><parameter-list line=\"1\" column=\"12\">"
                           + "<parameter line=\"1\" column=\"12\"><name-type-init line=\"1\" column=\"12\">"
                           + "<name line=\"1\" column=\"12\">a</name><type line=\"1\" column=\"14\">"
                           + "Object</type></name-type-init></parameter></parameter-list>"
                           + "<type line=\"1\" column=\"25\">*</type><block line=\"1\" column=\"31\">"
                           + "<call line=\"1\" column=\"36\"><primary line=\"1\" column=\"31\">trace</primary>"
                           + "<arguments line=\"1\" column=\"37\"><primary line=\"1\" column=\"37\">'test'"
                           + "</primary></arguments></call></block></lambda>" );
   }

   @Test
   public void testNull() throws TokenException
   {
      assertPrimary( "null" );
   }

   @Test
   public void testNumbers() throws TokenException
   {
      assertPrimary( "1" );
      assertPrimary( "0xff" );
      assertPrimary( "0777" );
      assertPrimary( ".12E5" );
   }

   @Test
   public void testObjectLiteral() throws TokenException
   {
      assertPrimary( "{a:1,b:2}",
                     "<object line=\"1\" column=\"1\"><prop line=\"1\" column=\"2\">"
                           + "<name line=\"1\" column=\"2\">a</name><value line=\"1\" column=\"4\">"
                           + "<primary line=\"1\" column=\"4\">1</primary></value></prop><prop line=\"1\" column=\"6\">"
                           + "<name line=\"1\" column=\"6\">b</name><value line=\"1\" column=\"8\">"
                           + "<primary line=\"1\" column=\"8\">2</primary></value></prop></object>" );
   }

   @Test
   public void testStrings() throws TokenException
   {
      assertPrimary( "\"string\"" );
      assertPrimary( "'string'" );
   }

   @Test
   public void testUndefined() throws TokenException
   {
      assertPrimary( "undefined" );
   }

   private void assertPrimary( final String input ) throws TokenException
   {
      assertPrimary( input,
                     input );
   }

   private void assertPrimary( final String input,
                               final String expected ) throws TokenException
   {
      scn.setLines( new String[]
      { input,
                  "__END__" } );
      asp.nextToken();
      final String result = new ASTToXMLConverter().convert( asp.parsePrimaryExpression() );
      assertEquals( "unexpected",
                    "<primary line=\"1\" column=\"1\">"
                          + expected + "</primary>",
                    result );
   }

}
