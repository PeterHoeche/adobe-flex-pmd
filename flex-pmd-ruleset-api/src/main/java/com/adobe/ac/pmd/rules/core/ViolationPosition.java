/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
 *    All rights reserved.
 *
 *    Redistribution  and  use  in  source  and  binary  forms, with or without
 *    modification,  are  permitted  provided  that  the  following  conditions
 *    are met:
 *
 *      * Redistributions  of  source  code  must  retain  the  above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions  in  binary  form  must reproduce the above copyright
 *        notice,  this  list  of  conditions  and  the following disclaimer in
 *        the    documentation   and/or   other  materials  provided  with  the
 *        distribution.
 *      * Neither the name of the Adobe Systems, Incorporated. nor the names of
 *        its  contributors  may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS  SOFTWARE  IS  PROVIDED  BY THE  COPYRIGHT  HOLDERS AND CONTRIBUTORS
 *    "AS IS"  AND  ANY  EXPRESS  OR  IMPLIED  WARRANTIES,  INCLUDING,  BUT NOT
 *    LIMITED  TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,  INCIDENTAL,  SPECIAL,
 *    EXEMPLARY,  OR  CONSEQUENTIAL  DAMAGES  (INCLUDING,  BUT  NOT  LIMITED TO,
 *    PROCUREMENT  OF  SUBSTITUTE   GOODS  OR   SERVICES;  LOSS  OF  USE,  DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY,  WHETHER  IN  CONTRACT,  STRICT  LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE  OR  OTHERWISE)  ARISING  IN  ANY  WAY  OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.rules.core;

/**
 * @author xagnetti
 */
public final class ViolationPosition
{
   /**
    * @param beginLineToBeSet
    * @param endLineToBeSet
    * @param beginColumnToBeSet
    * @param endColumnToBeSet
    * @return
    */
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

   /**
    * @param lineToBeSet
    */
   public ViolationPosition( final int lineToBeSet )
   {
      this( lineToBeSet, lineToBeSet, 0, 0 );
   }

   /**
    * @param beginLineToBeSet
    * @param endLineToBeSet
    */
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

   /**
    * @return
    */
   public int getBeginColumn()
   {
      return beginColumn;
   }

   /**
    * @return
    */
   public int getBeginLine()
   {
      return beginLine;
   }

   /**
    * @return
    */
   public int getEndColumn()
   {
      return endColumn;
   }

   /**
    * @return
    */
   public int getEndLine()
   {
      return endLine;
   }
}
