package de.bokelberg.flex.parser;

import org.junit.Test;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public class TestForStatement extends AbstractStatementTest
{
   @Test
   public void testSimpleFor() throws TokenException
   {
      assertStatement( "1",
                       "for( var i : int = 0; i < length; i++ ){ trace( i ); }",
                       "<for line=\"1\" column=\"6\"><init line=\"1\" column=\"6\">"
                             + "<var-list line=\"1\" column=\"10\"><name-type-init line=\"1\" "
                             + "column=\"10\"><name line=\"1\" column=\"10\">i</name><type line=\"1\" "
                             + "column=\"12\">int</type><init line=\"1\" column=\"20\">"
                             + "<primary line=\"1\" column=\"20\">0</primary></init>"
                             + "</name-type-init></var-list></init>"
                             + "<cond line=\"1\" column=\"23\"><relation line=\"1\" column=\"23\">"
                             + "<primary line=\"1\" column=\"23\">i</primary><op line=\"1\" "
                             + "column=\"25\">&lt;</op><primary line=\"1\" column=\"27\">length"
                             + "</primary></relation></cond><iter line=\"1\" column=\"35\">"
                             + "<post-inc line=\"1\" column=\"39\"><primary line=\"1\" column=\"35\">i"
                             + "</primary></post-inc></iter><block line=\"1\" column=\"42\"><call line=\"1\" "
                             + "column=\"47\"><primary line=\"1\" column=\"42\">trace"
                             + "</primary><arguments line=\"1\" column=\"49\"><primary line=\"1\" column=\"49\">i"
                             + "</primary></arguments></call></block></for>" );
   }

   @Test
   public void testSimpleForEach() throws TokenException
   {
      assertStatement( "1",
                       "for each( var obj : Object in list ){ obj.print( i ); }",
                       "<foreach line=\"1\" column=\"11\"><var line=\"1\" column=\"11\">"
                             + "<name-type-init line=\"1\" column=\"15\"><name line=\"1\" "
                             + "column=\"15\">obj</name><type line=\"1\" column=\"19\">Object"
                             + "</type></name-type-init></var><in line=\"1\" column=\"31\">"
                             + "<primary line=\"1\" column=\"31\">list</primary></in>"
                             + "<block line=\"1\" column=\"39\"><dot line=\"1\" column=\"43\">"
                             + "<primary line=\"1\" column=\"39\">obj</primary><call line=\"1\" "
                             + "column=\"48\"><primary line=\"1\" column=\"43\">print</primary>"
                             + "<arguments line=\"1\" column=\"50\"><primary line=\"1\" column=\"50\">"
                             + "i</primary></arguments></call></dot></block></foreach>" );

      // assertStatement(
      // "", "for each (var a:XML in classInfo..accessor) {}", "" );
   }

   @Test
   public void testSimpleForIn() throws TokenException
   {
      assertStatement( "1",
                       "for( var s : String in obj ){ trace( s, obj[ s ]); }",
                       "<forin line=\"1\" column=\"6\"><init line=\"1\" column=\"6\">"
                             + "<var-list line=\"1\" column=\"10\"><name-type-init line=\"1\" "
                             + "column=\"10\"><name line=\"1\" column=\"10\">s</name>"
                             + "<type line=\"1\" column=\"12\">String</type></name-type-init>"
                             + "</var-list></init><in line=\"1\" column=\"24\"><primary line=\"1\" "
                             + "column=\"24\">obj</primary></in></forin>" );
   }
}
