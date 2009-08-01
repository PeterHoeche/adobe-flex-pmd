package
{
   import com.adobe.ac.pmd.model.RuleTest;
   import com.adobe.ac.pmd.model.RulesetTest;
   import com.adobe.ac.pmd.services.translators.RuleTranslatorTest;
   import com.adobe.ac.pmd.services.translators.RulesetTranslatorTest;
   import com.adobe.ac.pmd.view.RuleSetNavigatorPMTest;
   
   import flexunit.framework.TestSuite;

   public class AllTests extends TestSuite
   {
      public function AllTests()
      {
         super();

         addTestSuite( RuleSetNavigatorPMTest );
         addTestSuite( RulesetTest );
         addTestSuite( RuleTest );
         addTestSuite( RuleTranslatorTest );
         addTestSuite( RulesetTranslatorTest );
      }
   }
}
