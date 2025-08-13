package Tests;

import Pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class Scenario5Test extends BaseTest {
    private Product product1;
    private Cart cart;
    private Register register;
    private Order order;
    @BeforeMethod
    public void beforeEachMethod(){
        product1 = new Product(driver);
        cart = new Cart(driver);
        order = new Order(driver);
        register = new Register(driver);
    }
    @Test
    public void orderTest(){
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            //Login
            String uniqueEmail = "demo@yyyq" + System.currentTimeMillis() % 10000 + ".vn";
            register.registerWith("demo", uniqueEmail, "Mrs", "123456","10", "8","2003","No","No","test", "test", "123 abdf", "Singapore", "HCM", "HCM", "700000","0987654321");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Signup / Login']"))).click();
            //S5_TC01a
            String[] products = {"Blue Top", "Men Tshirt", "Sleeveless Dress"};
            String[] qtys = {"1", "2", "3"};
            String[] prices = cart.addMultipleProduct(products, qtys);
            // Verify từng sản phẩm
            for (int i = 0; i < products.length; i++) {
                assertTrue("Cart hiển thị sai với sản phẩm: " + products[i], cart.isViewCartSuccess_02(products[i], prices[i], qtys[i]));
            }
            ExtentReportListener.logStepWithScreenshot(driver, "Cart is displayed correctly");
            //S5_TC02
            order.checkout();
            assertTrue("Can't redirect to CheckoutPage", order.isCheckOutProcess());
            ExtentReportListener.logStepWithScreenshot(driver,"Redirect to CheckoutPage successfully");
            //s5_TC03
            assertTrue("Order doesn't match with CartPage", order.checkViewMatchOrder());
            ExtentReportListener.logStepWithScreenshot(driver,"Order match with CartPage");
            //s5_TC04
            order.placeOrder("Test", "098765432141", "311", "01","2026");
            assertTrue("Place Order Failed",order.isPlaceOrderSuccessfully());
            ExtentReportListener.logStepWithScreenshot(driver,"Place order Successfully");
        }catch (Exception e){
            System.out.println("Error: " +e.getMessage());
        }

    }
}
