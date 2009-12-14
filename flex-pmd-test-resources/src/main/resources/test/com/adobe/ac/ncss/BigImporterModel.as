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
package com.adobe.ac
{
   import com.adobe.ac.lala;
   import com.adobe.ac.ncss.lala;
   import mx.controls.ComboBox;
   import com.foo.frontendview.vo.PersonVO;
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

      public function foo( param1 : Number, param2 : Number, param3 : Number, param4 : Number, param5 : DataGridColumn ) : void
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