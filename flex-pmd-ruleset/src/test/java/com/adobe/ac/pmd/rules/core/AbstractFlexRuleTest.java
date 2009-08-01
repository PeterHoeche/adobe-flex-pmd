package com.adobe.ac.pmd.rules.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.adobe.ac.pmd.FlexPmdTestBase;
import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;
import com.adobe.ac.pmd.parser.exceptions.TokenException;

public abstract class AbstractFlexRuleTest extends FlexPmdTestBase
{
   private final static class AssertPosition
   {
      public static AssertPosition create( final String message,
                                           final int expectedLine,
                                           final int actualLine )
      {
         return new AssertPosition( message, expectedLine, actualLine );
      }
      public int    actualLine;
      public int    expectedLine;

      public String message;

      private AssertPosition( final String messageToBeSet,
                              final int expectedLineToBeSet,
                              final int actualLineToBeSet )
      {
         super();
         message = messageToBeSet;
         expectedLine = expectedLineToBeSet;
         actualLine = actualLineToBeSet;
      }
   }

   protected static Map< String, ViolationPosition[] > addToMap( final Map< String, ViolationPosition[] > map,
                                                                 final String resource,
                                                                 final ViolationPosition[] positions )
   {
      map.put( resource,
               positions );
      return map;
   }

   /**
    * Test case which contains non-concerned files by the given rule
    * 
    * @throws TokenException
    * @throws IOException
    */
   @Test
   public final void testProcessNonViolatingFiles() throws IOException,
                                                   TokenException
   {
      final Map< String, List< IFlexViolation >> violatedFiles = extractActualViolatedFiles();
      final StringBuffer buffer = buildMessageName( violatedFiles );

      if ( !violatedFiles.isEmpty() )
      {
         fail( buffer.toString() );
      }
   }

   /**
    * Test case which contains violating files
    */
   @Test
   public final void testProcessViolatingFiles()
   {
      for ( final String fileName : getViolatingFiles().keySet() )
      {
         assertViolations( fileName,
                           getViolatingFiles().get( fileName ) );
      }
   }

   protected abstract AbstractFlexRule getRule();

   protected abstract Map< String, ViolationPosition[] > getViolatingFiles();

   protected List< IFlexViolation > processFile( final String resourcePath ) throws IOException,
                                                                            TokenException
   {
      return getRule().processFile( getTestFiles().get( resourcePath ),
                                    null,
                                    getTestFiles() );
   }

   private void assertViolations( final String resourcePath,
                                  final ViolationPosition[] expectedPositions )
   {
      try
      {
         final List< IFlexViolation > violations = processFile( resourcePath );

         assertEquals( VIOLATIONS_NUMBER_NOT_CORRECT
                             + " for " + resourcePath,
                       expectedPositions.length,
                       violations.size() );

         if ( expectedPositions.length != 0 )
         {
            final List< AssertPosition > failures = new ArrayList< AssertPosition >();

            for ( int i = 0; i < expectedPositions.length; i++ )
            {
               final IFlexViolation violation = violations.get( i );
               final ViolationPosition expectedPosition = expectedPositions[ i ];

               if ( expectedPosition.getBeginLine() != violation.getBeginLine() )
               {
                  failures.add( AssertPosition.create( BEGIN_LINE_NOT_CORRECT
                                                             + " at " + i + "th violation on " + resourcePath,
                                                       expectedPosition.getBeginLine(),
                                                       violation.getBeginLine() ) );
               }
               if ( expectedPosition.getEndLine() != violation.getEndLine() )
               {
                  failures.add( AssertPosition.create( END_LINE_NOT_CORRECT
                                                             + " at " + i + "th violation on " + resourcePath,
                                                       expectedPosition.getEndLine(),
                                                       violation.getEndLine() ) );
               }
            }
            printFailures( failures );
         }
      }
      catch ( final IOException e )
      {
         fail( e.getMessage() );
      }
      catch ( final TokenException e )
      {
         fail( e.getMessage() );
      }
   }

   private StringBuffer buildMessageName( final Map< String, List< IFlexViolation >> violatedFiles )
   {
      final StringBuffer buffer = new StringBuffer( 100 );

      for ( final String violatedFileName : violatedFiles.keySet() )
      {
         final List< IFlexViolation > violations = violatedFiles.get( violatedFileName );

         buffer.append( violatedFileName
               + " should not contain any violations " + " (" + violations.size() + " found" );

         if ( violations.size() == 1 )
         {
            buffer.append( " at "
                  + violations.get( 0 ).getBeginLine() + ":" + violations.get( 0 ).getEndLine() );
         }
         buffer.append( ")\n" );
      }
      return buffer;
   }

   private Map< String, List< IFlexViolation >> extractActualViolatedFiles() throws IOException,
                                                                            TokenException
   {
      final Map< String, List< IFlexViolation > > violatedFiles = new HashMap< String, List< IFlexViolation > >();

      for ( final Map.Entry< String, IFlexFile > fileNameEntry : getTestFiles().entrySet() )
      {
         if ( !getViolatingFiles().containsKey( fileNameEntry.getKey() ) )
         {
            final List< IFlexViolation > violations = processFile( fileNameEntry.getKey() );

            if ( !violations.isEmpty() )
            {
               violatedFiles.put( fileNameEntry.getKey(),
                                  violations );
            }
         }
      }
      return violatedFiles;
   }

   private void printFailures( final List< AssertPosition > failures )
   {
      if ( !failures.isEmpty() )
      {
         final StringBuffer message = new StringBuffer( 42 );

         for ( final AssertPosition assertPosition : failures )
         {
            message.append( assertPosition.message
                  + ": expected <" + assertPosition.expectedLine + "> but actually <"
                  + assertPosition.actualLine + ">\n" );
         }
         fail( message.toString() );
      }
   }
}