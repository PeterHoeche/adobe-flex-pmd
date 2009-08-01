package com.adobe.ac
{
   import com.adobe.ac.lala;
   import com.adobe.ac.ncss.lala;
   import mx.controls.ComboBox;
   import lala;
   import lala;

   import lala;

   import lala;
   import lala;
   import lala;
   import lala;
   import lala;

   import lala;

   import lala;
   import lala;
   import lala;
   import lala;

   public class BigModel   
   {
      public function BigModel()
      {    
         dispatchEvent( new Event( "pointlessEvent" ) );     
         return;
      }

      public function foo( param1 : Number, param2 : Number, param3 : Number, param4 : Number, param5 : Number ) : void
      {
         var iParam1 : int = param1;
      }

       public function updateIteration(iteration:IterationVO) : void
       {
          iteration.cascadeDatesChanges();
          try {
             AnthologyModel.getInstance().projectModel.iterationDataModel.commit();
          } 
          catch(error:Error) {
             trace("Error while persisting the edited iteration to the database");
             trace(error.message);
          }
       }    

       public function createTestsForStory( story:StoryVO, responder:IResponder ):void
       {
         for each ( var test:CustomerTestVO in story.customerTests ) {
           createTest( test );
         }
         var token:AsyncToken = dataService.commit();
         if ( responder && token ) {
           token.addResponder( responder );
         }
       }         
   }
}