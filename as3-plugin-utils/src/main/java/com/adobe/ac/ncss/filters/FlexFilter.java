package com.adobe.ac.ncss.filters;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Locale;

public class FlexFilter implements FilenameFilter
{
   public boolean accept( final File dir,
                          final String name )
   {
      boolean accepted = false;

      if ( !new File( dir, name ).isDirectory() )
      {
         accepted = name.toLowerCase( Locale.US ).endsWith( ".as" )
               || name.toLowerCase( Locale.US ).endsWith( ".mxml" );
      }
      return accepted;
   }
}
