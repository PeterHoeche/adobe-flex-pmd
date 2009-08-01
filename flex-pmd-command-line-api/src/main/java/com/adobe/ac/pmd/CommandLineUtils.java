package com.adobe.ac.pmd;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;

public final class CommandLineUtils
{
   public static void registerParameter( final JSAP jsap,
                                         final CommandLineOptions option,
                                         final boolean required ) throws JSAPException
   {
      final String optionName = option.toString();

      jsap.registerParameter( new FlaggedOption( optionName ).setStringParser( JSAP.STRING_PARSER )
                                                             .setRequired( required )
                                                             .setShortFlag( optionName.charAt( 0 ) )
                                                             .setLongFlag( optionName ) );
   }

   private CommandLineUtils()
   {
   }
}
