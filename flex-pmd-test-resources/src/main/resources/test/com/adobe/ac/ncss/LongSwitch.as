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