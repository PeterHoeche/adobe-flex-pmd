package
{
   import com.adobe.ac.pmd.model.RuleTest;
   import com.adobe.ac.pmd.model.RulesetTest;
   
   import flexunit.framework.TestSuite;

   public class AllTests extends TestSuite
   {
      public function AllTests()
      {
         super();

         addTestSuite( RulesetTest );
         addTestSuite( RuleTest );
      }
   }
}
