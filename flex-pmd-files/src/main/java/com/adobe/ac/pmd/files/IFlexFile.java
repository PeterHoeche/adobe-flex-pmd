package com.adobe.ac.pmd.files;

public interface IFlexFile extends Comparable< IFlexFile >
{
   boolean contains( final String stringToLookup,
                     final int lineToBeIgnored );

   String getClassName();

   /**
    * @return the token for comment closing
    */
   String getCommentClosingTag();

   /**
    * @return the token for comment opening
    */
   String getCommentOpeningTag();

   /**
    * @return java.io.File name
    */
   String getFilename();

   /**
    * @return java.io.File absolute path
    */
   String getFilePath();

   String getFullyQualifiedName();

   String getLineAt( int lineIndex );

   int getLinesNb();

   String getPackageName();

   /**
    * @return true if the file is a main MXML file
    */
   boolean isMainApplication();

   /**
    * @return true if the file is a MXML file
    */
   boolean isMxml();
}