/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.files;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import net.sourceforge.pmd.PMDException;

import com.adobe.ac.pmd.nodes.IPackage;
import com.adobe.ac.pmd.nodes.impl.NodeFactory;
import com.adobe.ac.pmd.parser.IAS3Parser;
import com.adobe.ac.pmd.parser.IParserNode;
import com.adobe.ac.pmd.parser.exceptions.TokenException;

import de.bokelberg.flex.parser.AS3Parser;

/**
 * @author xagnetti
 */
public final class FileSetUtils
{
   private static final ThreadPoolExecutor EXECUTOR = ( ThreadPoolExecutor ) Executors.newFixedThreadPool( 5 );
   private static final Logger             LOGGER   = Logger.getLogger( FileSetUtils.class.getName() );

   public static IParserNode buildAst( final IFlexFile file ) throws PMDException
   {
      final IAS3Parser parser = new AS3Parser();
      IParserNode rootNode = null;

      try
      {
         if ( file instanceof IMxmlFile )
         {
            rootNode = parser.buildAst( file.getFilePath(),
                                        ( ( IMxmlFile ) file ).getScriptBlock() );
         }
         else
         {
            rootNode = parser.buildAst( file.getFilePath() );
         }
      }
      catch ( final IOException e )
      {
         throw new PMDException( "While building AST: Cannot read "
               + file.getFullyQualifiedName(), e );
      }
      catch ( final TokenException e )
      {
         throw new PMDException( "TokenException thrown while building AST on "
               + file.getFullyQualifiedName() + " with message: " + e.getMessage(), e );
      }
      return rootNode;
   }

   public static Map< String, IPackage > computeAsts( final Map< String, IFlexFile > files ) throws PMDException
   {
      final Map< String, IPackage > asts = new HashMap< String, IPackage >();

      for ( final Entry< String, IFlexFile > fileEntry : files.entrySet() )
      {
         final IFlexFile file = fileEntry.getValue();

         try
         {
            final IParserNode node = buildThreadedAst( file );

            asts.put( file.getFullyQualifiedName(),
                      NodeFactory.createPackage( node ) );
         }
         catch ( final InterruptedException e )
         {
            logErrorWhileBuildingAst( file,
                                      e );
         }
         catch ( final ExecutionException e )
         {
            logErrorWhileBuildingAst( file,
                                      e );
         }
         catch ( final NullPointerException e )
         {
            logErrorWhileBuildingAst( file,
                                      e );
         }
      }
      return asts;
   }

   private static IParserNode buildThreadedAst( final IFlexFile file ) throws PMDException,
                                                                      InterruptedException,
                                                                      ExecutionException
   {
      final List< Callable< Object >> toRun = new ArrayList< Callable< Object >>();
      toRun.add( new Callable< Object >()
      {
         public Object call() throws PMDException
         {
            // Call the service.
            return buildAst( file );
         }
      } );
      final List< Future< Object >> futures = EXECUTOR.invokeAll( toRun,
                                                                  400,
                                                                  TimeUnit.SECONDS );
      // Find out what happened when the service was
      // called.

      return ( IParserNode ) futures.get( 0 ).get();
   }

   private static void logErrorWhileBuildingAst( final IFlexFile file,
                                                 final Exception exception )
   {
      LOGGER.warning( "while building AST on "
            + file.getFullyQualifiedName() + ", an error occured: " + exception.getMessage() );
   }

   private FileSetUtils()
   {
   }
}
