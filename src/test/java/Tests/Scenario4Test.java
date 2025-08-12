package Tests;

import Pages.*;
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
    public void cartTest(){
        //S4_TC01
        String product = "Blue Top";
        product1.productDetail(product);
        boolean isMatch = product1.isMatchProduct(product);
        assertTrue( "Product doesn't match", isMatch);
        ExtentReportListener.logStepWithScreenshot(driver, "Product is display correctly");
        //S4_TC02
        String qty = "2";
        cart.addToCart(qty);
        boolean isAddToCart = cart.isAddSuccess();
        assertTrue( "Can't add to cart ", isAddToCart);
        ExtentReportListener.logStepWithScreenshot(driver, "Add to cart successfully");
        boolean isViewCart = cart.isViewCartSuccess_01(qty);
        assertTrue( "Product is showed incorrectly", isViewCart);
        ExtentReportListener.logStepWithScreenshot(driver, "Product is display correctly");
        driver.findElement(By.xpath("//a[@href='/']")).click();
        //S4_TC03
        product1.productDetail(product);
        review.writeReview("Test", "demo@gmail.com", "Không có review");
        boolean isReview = review.isReviewSuccess();
        assertTrue( "Can't be add to review ", isReview);
        ExtentReportListener.logStepWithScreenshot(driver, "Review is added successfully");
        driver.findElement(By.xpath("//a[@href='/']")).click();
    }
}
