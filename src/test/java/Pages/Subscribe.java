package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.testng.AssertJUnit.fail;

public class Subscribe {
    protected WebDriver driver;
    private ActionHelper actions;

    //=== Locator ===
    private static final By SUBSCRIBE_EMAIL = By.id("susbscribe_email");
    private static final By SUBSCRIBE_BTN = By.id("subscribe");
    private static final By SUBSCRIBE_TEXT_SUCCESS = By.xpath("//div[@class='alert-success alert']");
    private static final String SUBSCRIBE_MESSAGE_SUCCESS = "You have been successfully subscribed!";
    public Subscribe(WebDriver driver){
        this.driver = driver;
        actions = new ActionHelper(driver);
    }
    public void subscription(String email){
        try {
            actions.sendKey(SUBSCRIBE_EMAIL, email);
            actions.click(SUBSCRIBE_BTN);
        }catch (Exception e){
            fail("subscription was error: " +e.getMessage());
        }
    }
    public boolean isSubscriptionSuccess(){
        try {
            String textSuccess = actions.getText_fast(SUBSCRIBE_TEXT_SUCCESS);
            return textSuccess.contains(SUBSCRIBE_MESSAGE_SUCCESS);
        }catch (Exception e){
            fail("isSubscriptionSuccess was error: " +e.getMessage());
            return false;
        }
    }
}
