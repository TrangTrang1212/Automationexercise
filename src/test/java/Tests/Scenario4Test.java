package Tests;

import Pages.BaseTest;
import Pages.Cart;
import Pages.Product;
import Pages.Review;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class Scenario4Test extends BaseTest {
    private Product product1;
    private Cart cart;
    private Review review;
    @BeforeMethod
    public void beforeEachMethod(){
        product1 = new Product(driver);
        cart = new Cart(driver);
        review = new Review(driver);
    }
    @Test
    public void s4_TC01(){
        String product = "Blue Top";
        product1.productDetail(product);
        boolean isMatch = product1.isMatchProduct(product);
        assertTrue( "Product không khớp", isMatch);
    }
    @Test
    public void s4_TC02(){
        String qty = "2";
        cart.addToCart(qty);
        boolean isAddToCart = cart.isAddSuccess();
        assertTrue( "Không thêm product vào giỏ hàng ", isAddToCart);
        boolean isViewCart = cart.isViewCartSuccess(qty);
        assertTrue( "Hiển thị không đúng product", isViewCart);
        driver.findElement(By.xpath("//a[@href='/']")).click();
    }
    @Test
    public void s4_TC03(){
        String product = "Blue Top";
        product1.productDetail(product);
        review.writeReview("Test", "demo@gmail.com", "Không có review");
        boolean isReview = review.isReviewSuccess();
        assertTrue( "Không thêm review vào ", isReview);
    }
}
