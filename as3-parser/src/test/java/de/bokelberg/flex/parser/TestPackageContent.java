package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestPackageContent extends AbstractAs3ParserTest
{
   @Test
   public void testClass() throws TokenException
   {
      assertPackageContent( "1",
                            "public class A { }",
                            "<content line=\"2\" column=\"1\"><class line=\"2\" column=\"14\">"
                                  + "<name line=\"2\" column=\"14\">A</name><mod-list line=\"2\" column=\"16\">"
                                  + "<mod line=\"2\" column=\"16\">public</mod>"
                                  + "</mod-list><content line=\"2\" column=\"18\">"
                                  + "</content></class></content>" );
   }

   @Test
   public void testClassWithMetadata() throws TokenException
   {
      assertPackageContent( "1",
                            "[Bindable(name=\"abc\", value=\"123\")] public class A { }",
                            "<content line=\"2\" column=\"1\"><class line=\"2\" column=\"50\">"
                                  + "<name line=\"2\" column=\"50\">A</name><meta-list line=\"2\" column=\"52\">"
                                  + "<meta line=\"2\" column=\"37\""
                                  + ">Bindable ( name = \"abc\" , value = \"123\" )</meta>"
                                  + "</meta-list><mod-list line=\"2\" column=\"52\">"
                                  + "<mod line=\"2\" column=\"52\">public"
                                  + "</mod></mod-list><content line=\"2\" column=\"54\"></content></class></content>" );
   }

   @Test
   public void testClassWithSimpleMetadata() throws TokenException
   {
      assertPackageContent( "1",
                            "[Bindable] public class A { }",
                            "<content line=\"2\" column=\"1\"><class line=\"2\" column=\"25\">"
                                  + "<name line=\"2\" column=\"25\">A</name><meta-list line=\"2\" column=\"27\">"
                                  + "<meta line=\"2\" column=\"12\">Bindable</meta></meta-list>"
                                  + "<mod-list line=\"2\" column=\"27\"><mod line=\"2\" column=\"27\">public</mod>"
                                  + "</mod-list><content line=\"2\" column=\"29\"></content></class></content>" );
   }

   @Test
   public void testImports() throws TokenException
   {
      assertPackageContent( "1",
                            "import a.b.c;",
                            "<content line=\"2\" column=\"1\"><import line=\"2\" "
                                  + "column=\"8\">a.b.c</import></content>" );

      assertPackageContent( "2",
                            "import a.b.c import x.y.z",
                            "<content line=\"2\" column=\"1\"><import line=\"2\" column=\"8\">a.b.c"
                                  + "</import><import line=\"2\" column=\"21\">x.y.z</import></content>" );
   }

   @Test
   public void testInterface() throws TokenException
   {
      assertPackageContent( "1",
                            "public interface A { }",
                            "<content line=\"2\" column=\"1\"><interface line=\"2\" column=\"18\">"
                                  + "<name line=\"2\" column=\"18\">A</name><mod-list line=\"2\" column=\"20\">"
                                  + "<mod line=\"2\" column=\"20\">public</mod>"
                                  + "</mod-list><content line=\"2\" column=\"22\">"
                                  + "</content></interface></content>" );
   }

   @Test
   public void testUse() throws TokenException
   {
      assertPackageContent( "1",
                            "use myNamespace",
                            "<content line=\"2\" column=\"1\"><use line=\"2\" column=\"5\""
                                  + ">myNamespace</use></content>" );
   }

   private void assertPackageContent( final String message,
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
      final String result = new ASTToXMLConverter().convert( asp.parsePackageContent() );
      assertEquals( message,
                    expected,
                    result );
   }

}
