package com.adobe.ac.pmd.rules.core;

public final class ViolationPosition
{
   public static ViolationPosition create( final int beginLineToBeSet,
                                           final int endLineToBeSet,
                                           final int beginColumnToBeSet,
                                           final int endColumnToBeSet )
   {
      return new ViolationPosition( beginLineToBeSet, endLineToBeSet, beginColumnToBeSet, endColumnToBeSet );
   }
   private final int beginColumn;
   private final int beginLine;
   private final int endColumn;

   private final int endLine;

   public ViolationPosition( final int beginLineToBeSet,
                             final int endLineToBeSet )
   {
      this( beginLineToBeSet, endLineToBeSet, 0, 0 );
   }

   private ViolationPosition( final int beginLineToBeSet,
                              final int endLineToBeSet,
                              final int beginColumnToBeSet,
                              final int endColumnToBeSet )
   {
      super();

      beginLine = beginLineToBeSet;
      beginColumn = beginColumnToBeSet;
      endLine = endLineToBeSet;
      endColumn = endColumnToBeSet;
   }

   public int getBeginColumn()
   {
      return beginColumn;
   }

   public int getBeginLine()
   {
      return beginLine;
   }

   public int getEndColumn()
   {
      return endColumn;
   }

   public int getEndLine()
   {
      return endLine;
   }
}
