package flexunit.flexui.data.filter
{
   import flexunit.flexui.controls.FlexUnitLabels;
   import flexunit.flexui.data.TestFunctionRowData;

   public class AllTestFunctionStatus implements ITestFunctionStatus
   {
      public function isTestFunctionVisible( test : TestFunctionRowData ) : Boolean
      {
         return true;
      }
      
      public function get label() : String
      {
         return FlexUnitLabels.ALL;
      }
   }
}