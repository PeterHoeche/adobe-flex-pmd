package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestClass extends AbstractAs3ParserTest
{
   @Test
   public void testExtends() throws TokenException
   {
      assertPackageContent( "1",
                            "public class A extends B { } ",
                            "<content line=\"2\" column=\"1\">"
                                  + "<class line=\"2\" column=\"14\">"
                                  + "<name line=\"2\" column=\"14\">A</name><mod-list line=\"2\" column=\"16\">"
                                  + "<mod line=\"2\" column=\"16\">public</mod></mod-list><extends line=\"2\" "
                                  + "column=\"24\">B</extends><content line=\"2\" column=\"28\"></content>"
                                  + "</class></content>" );
   }

   @Test
   public void testFinalClass() throws TokenException
   {
      assertPackageContent( "",
                            "public final class Title{ }",
                            "<content line=\"2\" column=\"1\">"
                                  + "<class line=\"2\" column=\"20\">"
                                  + "<name line=\"2\" column=\"20\">Title</name>"
                                  + "<mod-list line=\"2\" column=\"25\">"
                                  + "<mod line=\"2\" column=\"25\">public</mod>"
                                  + "<mod line=\"2\" column=\"25\">final</mod></mod-list>"
                                  + "<content line=\"2\" " + "column=\"27\"></content>" + "</class>"
                                  + "</content>" );
   }

   @Test
   public void testFullFeatured() throws TokenException
   {
      // assertPackageContent( "",
      // "public class A { public static const RULE_REMOVED : String = \"ruleRemoved\";}",
      // "" );

      assertPackageContent( "1",
                            "public class A extends B implements C,D { } ",
                            "<content line=\"2\" column=\"1\"><class line=\"2\" column=\"14\">"
                                  + "<name line=\"2\" column=\"14\">A</name><mod-list line=\"2\" column=\"16\">"
                                  + "<mod line=\"2\" column=\"16\">public</mod></mod-list><extends line=\"2\" "
                                  + "column=\"24\">B</extends><implements-list line=\"2\" column=\"37\">"
                                  + "<implements line=\"2\" column=\"37\">C</implements><implements line=\"2\" "
                                  + "column=\"39\">D</implements></implements-list><content line=\"2\" column=\"43\">"
                                  + "</content></class></content>" );
   }

   @Test
   public void testImplementsList() throws TokenException
   {
      assertPackageContent( "1",
                            "public class A implements B,C { } ",
                            "<content line=\"2\" column=\"1\"><class line=\"2\" column=\"14\">"
                                  + "<name line=\"2\" column=\"14\">A</name><mod-list line=\"2\" "
                                  + "column=\"16\"><mod line=\"2\" column=\"16\">public</mod></mod-list>"
                                  + "<implements-list line=\"2\" column=\"27\"><implements line=\"2\" "
                                  + "column=\"27\">B</implements><implements line=\"2\" column=\"29\">"
                                  + "C</implements></implements-list><content line=\"2\" column=\"33\">"
                                  + "</content></class></content>" );
   }

   @Test
   public void testImplementsSingle() throws TokenException
   {
      assertPackageContent( "1",
                            "public class A implements B { } ",
                            "<content line=\"2\" column=\"1\"><class line=\"2\" column=\"14\">"
                                  + "<name line=\"2\" column=\"14\">A</name><mod-list line=\"2\" "
                                  + "column=\"16\"><mod line=\"2\" column=\"16\">public</mod></mod-list>"
                                  + "<implements-list line=\"2\" column=\"27\"><implements line=\"2\" "
                                  + "column=\"27\">B</implements></implements-list><content line=\"2\" "
                                  + "column=\"31\"></content></class></content>" );
   }

   @Test
   public void testImportInsideClass() throws TokenException
   {
      assertPackageContent( "",
                            "public final class Title{ import lala.lala; }",
                            "<content line=\"2\" column=\"1\">"
                                  + "<class line=\"2\" column=\"20\">"
                                  + "<name line=\"2\" column=\"20\">Title</name>"
                                  + "<mod-list line=\"2\" column=\"25\">"
                                  + "<mod line=\"2\" column=\"25\">public</mod>"
                                  + "<mod line=\"2\" column=\"25\">final</mod>" + "</mod-list>"
                                  + "<content line=\"2\" column=\"27\"><import line=\"2\" "
                                  + "column=\"34\">lala.lala</import></content>" + "</class>" + "</content>" );

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
