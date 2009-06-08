package com.adobe.ac.pmd.engines;

public enum FlexPMDFormat
{
   HTML("flexPmd.html"), XML("pmd.xml");

   private String name;

   private FlexPMDFormat( final String formatName )
   {
      name = formatName;
   }

   @Override
   public String toString()
   {
      return name;
   }
}
