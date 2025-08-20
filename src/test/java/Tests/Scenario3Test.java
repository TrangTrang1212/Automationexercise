package Tests;

import Pages.BaseTest;
import Pages.Category;
import Pages.ExtentReportListener;
import Pages.Search;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static org.testng.AssertJUnit.assertTrue;

public class Scenario3Test extends BaseTest {
        private Category category;
        private Search search;
        private static final String CATEGORY = "Men";
        private static final String SUB_CATEGORY = "Tshirts";
        private static final String BRAND ="Polo";
        private static final String KEYWORD ="Tshirt";

        @BeforeMethod
        public void beforeEachMethod(){
            category = new Category(driver);
            search = new Search(driver);
        }
        @Test
        public void verifyFillterCategory(){
            category.categories(CATEGORY, SUB_CATEGORY);
            assertTrue( "Not found title: ", category.isSuccessCategory(CATEGORY, SUB_CATEGORY));
            ExtentReportListener.logStepWithScreenshot(driver, "Category is display correctly");
        }
        @Test
        public void verifyFillterBrand(){
            category.brands(BRAND);
            assertTrue( "Not found title: ", category.isSuccessBrand(BRAND));
            ExtentReportListener.logStepWithScreenshot(driver, "Brand is display correctly");
        }
        @Test
        public void verifySearchKeyword(){
            search.search(KEYWORD);
            assertTrue("Not found product: ", search.isSuccessSearch(KEYWORD));
            ExtentReportListener.logStepWithScreenshot(driver, "Search is display correctly");
        }


}
