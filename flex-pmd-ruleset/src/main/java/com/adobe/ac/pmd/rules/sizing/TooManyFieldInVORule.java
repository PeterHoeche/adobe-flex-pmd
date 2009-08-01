package com.adobe.ac.pmd.rules.sizing;

import com.adobe.ac.pmd.files.IFlexFile;

public class TooManyFieldInVORule extends TooManyFieldsRule
{
   @Override
   public int getDefaultThreshold()
   {
      return 15;
   }

   @Override
   public boolean isConcernedByTheGivenFile( final IFlexFile file )
   {
      return file.getClassName().endsWith( "VO.as" );
   }
}
