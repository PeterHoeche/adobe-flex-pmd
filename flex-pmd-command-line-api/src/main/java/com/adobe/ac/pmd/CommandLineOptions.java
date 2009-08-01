package com.adobe.ac.pmd;

public enum CommandLineOptions
{
   EXLUDE_PACKAGE("excludePackage"),
   OUTPUT("outputDirectory"),
   RULE_SET("ruleSet"),
   SOURCE_DIRECTORY("sourceDirectory");

   private String name;

   private CommandLineOptions( final String nameToBeSet )
   {
      name = nameToBeSet;
   }

   @Override
   public String toString()
   {
      return name;
   }
}
