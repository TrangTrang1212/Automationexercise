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
        @BeforeMethod
        public void beforeEachMethod(){
            category = new Category(driver);
            search = new Search(driver);
        }
        @Test
        public void fillterTest(){
            String ca = "Men";
            String sub = "Tshirts";
            String brand = "Polo";
            String keyword = "Tshirt";
            //S3_01
            category.categories(ca, sub);
            boolean isSuccessCategory = category.isSuccessCategory(ca, sub);
            assertTrue( "Not found title: ", isSuccessCategory);
            ExtentReportListener.logStepWithScreenshot(driver, "Category is display correctly");
            //S3_02
            category.brands(brand);
            boolean isSuccessBrand = category.isSuccessBrand(brand);
            assertTrue( "Not found title: ", isSuccessBrand);
            ExtentReportListener.logStepWithScreenshot(driver, "Brand is display correctly");
            //S3_03
            search.search(keyword);
            boolean areProductsRelated = search.isSuccessSearch(keyword);
            assertTrue("Not found product: ", areProductsRelated);
            ExtentReportListener.logStepWithScreenshot(driver, "Search is display correctly");
        }

}
