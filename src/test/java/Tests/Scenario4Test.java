package Tests;

import Pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;

public class Scenario4Test extends BaseTest {
    private Product productPage;
    private Cart cartPage;
    private Review reviewPage;
    private static final String PRODUCT_NAME = "Blue Top";
    private static final String QTY = "2";

    @BeforeMethod
    public void beforeEachMethod(){
        productPage = new Product(driver);
        cartPage = new Cart(driver);
        reviewPage = new Review(driver);
    }
    @Test(priority = 1)
    public void verifyProductDetail(){
        productPage.productDetail(PRODUCT_NAME);
        assertTrue( "Product doesn't match", productPage.isMatchProduct(PRODUCT_NAME));
        ExtentReportListener.logStepWithScreenshot(driver, "Product is display correctly");
    }
    @Test(priority = 2)
    public void verifyAddToCart(){
        cartPage.addToCart(QTY);
        assertTrue( "Can't add to cart ", cartPage.isAddSuccess());
        ExtentReportListener.logStepWithScreenshot(driver, "Add to cart successfully");
    }
    @Test(priority = 3)
    public void verifyReview(){
        cartPage.clickContinueShopping();
        reviewPage.writeReview("Test", "demo@gmail.com", "Không có review");
        assertTrue( "Can't be add to review ", reviewPage.isReviewSuccess());
        ExtentReportListener.logStepWithScreenshot(driver, "Review is added successfully");
        cartPage.navigateToHome();
    }
}
