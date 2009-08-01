package com.adobe.ac.pmd.rules.core;

public enum ViolationPriority
{
   HIGH
   {
      @Override
      public String toString()
      {
         return "1";
      }
   },
   LOW
   {
      @Override
      public String toString()
      {
         return "5";
      }
   },
   NORMAL
   {
      @Override
      public String toString()
      {
         return "3";
      }
   };
}
