package com.adobe.ac.pmd.files.impl;

import java.io.File;

import com.adobe.ac.pmd.files.IAs3File;

class As3File extends AbstractFlexFile implements IAs3File
{
   protected As3File( final File file,
                      final File rootDirectory )
   {
      super( file, rootDirectory );
   }

   @Override
   public final String getCommentClosingTag()
   {
      return "*/";
   }

   @Override
   public final String getCommentOpeningTag()
   {
      return "/*";
   }

   @Override
   public final boolean isMainApplication()
   {
      return false;
   }

   @Override
   public final boolean isMxml()
   {
      return false;
   }
}
