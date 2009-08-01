package flexunit.flexui.data.filter
{
   import flexunit.flexui.controls.FlexUnitLabels;
   import flexunit.flexui.data.TestFunctionRowData;

   public class ErrorTestFunctionStatus implements ITestFunctionStatus
   {
      public function isTestFunctionVisible( test : TestFunctionRowData ) : Boolean
      {
         return ! test.testSuccessful;
      }
      
      public function get label() : String
      {
         return FlexUnitLabels.FAILURES_AND_ERRORS;
      }
   }
}