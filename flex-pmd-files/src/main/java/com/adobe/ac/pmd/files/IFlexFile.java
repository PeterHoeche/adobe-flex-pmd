/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.files;

import java.util.List;

public interface IFlexFile
{
   int compareTo( final IFlexFile otherViolation );

   boolean contains( final String stringToLookup,
                     final int lineToBeIgnored );

   /**
    * @param line
    * @return true if the given line contains a comment closing tag
    */
   boolean doesCurrentLineContainCommentClosingTag( final String line );

   /**
    * @param line
    * @return true if the given line contains a comment opening tag
    */
   boolean doesCurrentLineContainCommentOpeningTag( final String line );

   /**
    * @param line
    * @return true if the given line contain a one line comment
    */
   boolean doesCurrentLineContainOneLineComment( final String line );

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

   List< String > getLines();

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