package de.bokelberg.flex.parser;

import junit.framework.TestCase;

import org.junit.Before;

import com.adobe.ac.pmd.parser.IParserNode;

public abstract class AbstractAs3ParserTest extends TestCase
{
   protected final class ASTToXMLConverter
   {
      public String convert( final IParserNode ast )
      {
         final StringBuffer result = new StringBuffer();
         visitNodes( ast,
                     result,
                     0 );
         return result.toString();
      }
   }

   private static String escapeEntities( final String stringToEscape )
   {
      if ( stringToEscape == null )
      {
         return null;
      }

      final StringBuffer buffer = new StringBuffer();

      for ( int i = 0; i < stringToEscape.length(); i++ )
      {
         final char currentCharacter = stringToEscape.charAt( i );

         if ( currentCharacter == '<' )
         {
            buffer.append( "&lt;" );
         }
         else if ( currentCharacter == '>' )
         {
            buffer.append( "&gt;" );
         }
         else
         {
            buffer.append( currentCharacter );
         }
      }
      return buffer.toString();
   }

   private static void visitNodes( final IParserNode ast,
                                   final StringBuffer result,
                                   final int level )
   {
      result.append( "<"
            + ast.getId() + " line=\"" + ast.getLine() + "\" column=\"" + ast.getColumn() + "\">" );

      final int numChildren = ast.numChildren();
      if ( numChildren > 0 )
      {
         for ( int i = 0; i < numChildren; i++ )
         {
            visitNodes( ast.getChild( i ),
                        result,
                        level + 1 );
         }
      }
      else if ( ast.getStringValue() != null )
      {
         result.append( escapeEntities( ast.getStringValue() ) );
      }
      result.append( "</"
            + ast.getId() + ">" );
   }

   protected AS3Parser  asp;
   protected AS3Scanner scn;

   @Override
   @Before
   public void setUp()
   {
      asp = new AS3Parser();
      scn = asp.getScn();
   }
}