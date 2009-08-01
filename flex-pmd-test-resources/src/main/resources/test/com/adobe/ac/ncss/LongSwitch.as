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
package com.adobe.ac
{
   public class BigModel   
   {
      public function BigModel()
      {         
      }

      public function foo() : void
      {
          switch(event.type){
             case GoogleSearchPanel.LAUNCH_GOOGLE_WEB_SEARCH:
                googleResquest.url = "";
                toto();
                break;
             case GoogleSearchPanel.LAUNCH_GOOGLE_IMAGE_SEARCH:                   
                googleResquest.url = "";
                vfdvfdvfd;
                vfdvdfvgfnbrn;
                break;
             case GoogleSearchPanel.LAUNCH_GOOGLE_NEWS_SEARCH:
                googleResquest.url = "";
                switch (e.oldState) {
                  case STATE_COMPARE_VIEW :
                     createPlaceHolders();
                     createPlaceHolderLabels();
                  break;
                  case STATE_COMPARE_VIEW :
                     if (productsInCompare.length < 3) {
                        drawPlaceHolder(PLACEHOLDER_COORDS[2] as Point);
                        lastPlaceholderLabel = createPlaceHolderLabel(PLACEHOLDER_COORDS[2] as Point);
                     }
                     showCompareButton();
                  break;
               }
                break;                     
          }
          testCase.setTestResult( this );

         var protectedTestCase : Protectable = Protectable( new ProtectedStartTestCase( testCase ) );

         var startOK : Boolean = doProtected( testCase, protectedTestCase );

         if ( startOK )
         {
              doContinue( testCase );
         }
         else
         {
             endTest( testCase );             
         }
         var startOK : Boolean = doProtected( testCase, protectedTestCase );

         if ( startOK )
         {
              doContinue( testCase );
         }
         else
         {
             endTest( testCase );             
         }         
      }
   }
}