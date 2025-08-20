package Tests;

import DTO.AccountInfo;
import DTO.PaymentInfo;
import Pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;

public class Scenario5Test extends BaseTest {
    private Cart cart;
    private Register register;
    private Order order;
    private String generatedEmail;
    private static final String PASSWORD_VALID = "123456";
    private static final String[] PRODUCT_LIST = {"Blue Top", "Men Tshirt", "Sleeveless Dress"};
    private static final String[] QTY = {"1", "2", "3"};
    @BeforeMethod
    public void beforeEachMethod(){
        cart = new Cart(driver);
        order = new Order(driver);
        register = new Register(driver);
    }
    @Test(priority = 1)
    public void verifyCartItem(){
        generatedEmail = DataHelper.generateUniqueEmail();
        register.goToSignUpForm("demo", generatedEmail);
        AccountInfo accountInfo = new AccountInfo("Mrs", PASSWORD_VALID, "10", "8", "2003", "Yes", "No", "test", "test", "123 abdf", "Singapore", "HCM", "HCM", "700000", "0987654321");
        register.completeAccountInfo(accountInfo);
        register.signupLoginLink();
        String[] prices = cart.addMultipleProduct(PRODUCT_LIST, QTY);
        for (int i = 0; i < PRODUCT_LIST.length; i++) {
            assertTrue("Cart is displayed incorrectly: " + PRODUCT_LIST[i], cart.verifyCartItem(PRODUCT_LIST[i], prices[i], QTY[i]));
        }
        //ExtentReportListener.logStepWithScreenshot(driver, "Cart is displayed correctly");
    }
    @Test(priority = 2)
    public void verifyProcessCheckoutBTN(){
        order.clickProcessToCheckoutBTN();
        assertTrue("Can't redirect to CheckoutPage", order.isCheckOutProcess());
        //ExtentReportListener.logStepWithScreenshot(driver,"Redirect to CheckoutPage successfully");
    }
    @Test(priority = 3)
    public void verifyOrderMatchCart(){
        assertTrue("Order doesn't match with CartPage", order.checkViewMatchOrder());
        //ExtentReportListener.logStepWithScreenshot(driver,"Order match with CartPage");
    }
    @Test(priority = 4)
    public void orderTest(){
        PaymentInfo paymentInfo = new PaymentInfo("Test", "098765432141", "311", "01","2026");
        order.placeOrder(paymentInfo);
        assertTrue("Place Order Failed",order.isPlaceOrderSuccessfully());
        //ExtentReportListener.logStepWithScreenshot(driver,"Place order Successfully");
    }
}
