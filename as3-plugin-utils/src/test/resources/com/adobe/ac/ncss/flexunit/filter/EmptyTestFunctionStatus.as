package flexunit.flexui.data.filter
{
   import flexunit.flexui.controls.FlexUnitLabels;
   import flexunit.flexui.data.TestFunctionRowData;
   
   public class EmptyTestFunctionStatus implements ITestFunctionStatus
   {
      public function isTestFunctionVisible( test : TestFunctionRowData ) : Boolean
      {
         return test.assertionsMade == 0;
      }
      
      public function get label() : String
      {
         return FlexUnitLabels.EMPTY;
      }
   }
}