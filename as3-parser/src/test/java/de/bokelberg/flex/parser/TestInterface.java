package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestInterface extends AbstractAs3ParserTest
{
   @Test
   public void testExtends() throws TokenException
   {
      assertPackageContent( "1",
                            "public interface A extends B { } ",
                            "<content line=\"2\" column=\"1\"><interface line=\"2\" column=\"18\">"
                                  + "<name line=\"2\" column=\"18\">A</name><mod-list line=\"2\" column=\"20\">"
                                  + "<mod line=\"2\" column=\"20\">public</mod>"
                                  + "</mod-list><extends line=\"2\" column=\"28\">B</extends>"
                                  + "<content line=\"2\" column=\"32\"></content></interface></content>" );

      assertPackageContent( "",
                            "   public interface ITimelineEntryRenderer extends IFlexDisplayObject, IDataRenderer{}",
                            "<content line=\"2\" column=\"4\"><interface line=\"2\" column=\"21\"><name line=\"2\" "
                                  + "column=\"21\">ITimelineEntryRenderer</name><mod-list line=\"2\" column=\"44\">"
                                  + "<mod line=\"2\" column=\"44\">public</mod></mod-list><extends line=\"2\" "
                                  + "column=\"52\">IFlexDisplayObject</extends><extends line=\"2\" column=\"72\">"
                                  + "IDataRenderer</extends><content line=\"2\" column=\"86\">"
                                  + "</content></interface></content>" );
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
