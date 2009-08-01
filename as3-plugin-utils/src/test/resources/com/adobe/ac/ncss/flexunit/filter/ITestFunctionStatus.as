package flexunit.flexui.data.filter
{
   import flexunit.flexui.data.TestFunctionRowData;
   
   public interface ITestFunctionStatus
   {
      function isTestFunctionVisible( test : TestFunctionRowData ) : Boolean;
      function get label() : String;
   }
}