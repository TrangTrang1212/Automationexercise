package Tests;

import Pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;

public class Scenario7Test extends BaseTest {
    private ContactUs contactUs;
    private Subscribe subscribe;
    private Cart cartPage;
    @BeforeMethod
    public void beforeEachMethod(){
        contactUs = new ContactUs(driver);
        subscribe = new Subscribe(driver);
        cartPage = new Cart(driver);
    }
    @Test
    public void verifyContactUs(){
        contactUs.contactUs("test", "email@email.com","Your details have been submitted successfully.","Your details have been submitted successfully.");
        assertTrue("Your details have been submitted failed", contactUs.isContactSuccess());
        ExtentReportListener.logStepWithScreenshot(driver,"Your details have been submitted successfully.");
    }
    @Test
    public void verifySubcription(){
        cartPage.navigateToHome();
        subscribe.subscription("test@email.com");
        assertTrue("You have been failed subscribed!",subscribe.isSubscriptionSuccess());
        ExtentReportListener.logStepWithScreenshot(driver,"You have been successfully subscribed!");
    }

}
