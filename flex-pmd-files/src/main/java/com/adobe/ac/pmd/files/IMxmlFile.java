package com.adobe.ac.pmd.files;

public interface IMxmlFile extends IFlexFile
{
   int getBeginningScriptBlock();

   int getEndingScriptBlock();

   String[] getScriptBlock();
}