package Tests;

import Pages.BaseTest;
import Pages.Category;
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
        public void s3_TC01(){
            String ca = "Men";
            String sub = "Tshirts";
            category.categories(ca, sub);
            boolean isSuccess = category.isSuccessCategory(ca, sub);
            assertTrue( "Không tìm thấy tiêu đề: ", isSuccess);
        }
        @Test
        public void s3_TC02(){
            String brand = "Polo";
            category.brands(brand);
            boolean isSuccess = category.isSuccessBrand(brand);
            assertTrue( "Không tìm thấy tiêu đề: ", isSuccess);
        }
        @Test
        public void s3_TC03(){
            String keyword = "Tshirt";
            search.search(keyword);
            // Kiểm tra danh sách sản phẩm
            boolean areProductsRelated = search.isSuccessSearch(keyword);
            assertTrue("Không phải tất cả sản phẩm đều chứa từ khóa: ", areProductsRelated);
        }
}
