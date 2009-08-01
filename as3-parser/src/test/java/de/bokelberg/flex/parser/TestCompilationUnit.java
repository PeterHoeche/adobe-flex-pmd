package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestCompilationUnit extends AbstractAs3ParserTest
{
   @Test
   public void testEmptyPackage() throws TokenException
   {
      assertCompilationUnit( "1",
                             "package a { } ",
                             "<compilation-unit line=\"-1\" column=\"-1\">"
                                   + "<package line=\"1\" column=\"9\">" + "<name line=\"1\" column=\"11\">a"
                                   + "</name><content line=\"1\" column=\"13\">"
                                   + "</content></package><content line=\"2\" column=\"1\">"
                                   + "</content></compilation-unit>" );
   }

   @Test
   public void testEmptyPackagePlusLocalClass() throws TokenException
   {
      assertCompilationUnit( "1",
                             "package a { } class Local { }",
                             "<compilation-unit line=\"-1\" column=\"-1\"><package line=\"1\" column=\"9\">"
                                   + "<name line=\"1\" column=\"11\">a</name><content line=\"1\" column=\"13\">"
                                   + "</content></package><content line=\"1\" column=\"15\"><class line=\"1\" "
                                   + "column=\"21\"><name line=\"1\" column=\"21\">Local</name>"
                                   + "<mod-list line=\"1\" column=\"27\"></mod-list><content line=\"1\" column=\"29\">"
                                   + "</content></class></content></compilation-unit>" );
   }

   @Test
   public void testPackageWithClass() throws TokenException
   {
      assertCompilationUnit( "1",
                             "package a { public class B { } } ",
                             "<compilation-unit line=\"-1\" column=\"-1\"><package line=\"1\" column=\"9\">"
                                   + "<name line=\"1\" column=\"11\">a</name><content line=\"1\" column=\"13\">"
                                   + "<class line=\"1\" column=\"26\"><name line=\"1\" column=\"26\">B</name>"
                                   + "<mod-list line=\"1\" column=\"28\"><mod line=\"1\" column=\"28\">public"
                                   + "</mod></mod-list><content line=\"1\" column=\"30\"></content>"
                                   + "</class></content></package><content line=\"2\" column=\"1\">"
                                   + "</content></compilation-unit>" );
   }

   @Test
   public void testPackageWithInterface() throws TokenException
   {
      assertCompilationUnit( "1",
                             "package a { public interface B { } } ",
                             "<compilation-unit line=\"-1\" column=\"-1\"><package line=\"1\" column=\"9\">"
                                   + "<name line=\"1\" column=\"11\">a</name><content line=\"1\" column=\"13\">"
                                   + "<interface line=\"1\" column=\"30\"><name line=\"1\" column=\"30\">B</name>"
                                   + "<mod-list line=\"1\" column=\"32\"><mod line=\"1\" column=\"32\">public</mod>"
                                   + "</mod-list><content line=\"1\" column=\"34\"></content></interface>"
                                   + "</content></package><content line=\"2\" column=\"1\"></content>"
                                   + "</compilation-unit>" );
   }

   private void assertCompilationUnit( final String message,
                                       final String input,
                                       final String expected ) throws TokenException
   {
      scn.setLines( new String[]
      { input,
                  "__END__" } );
      final String result = new ASTToXMLConverter().convert( asp.parseCompilationUnit() );
      assertEquals( message,
                    expected,
                    result );
   }
}
