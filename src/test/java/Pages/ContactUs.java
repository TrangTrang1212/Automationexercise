package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.testng.AssertJUnit.fail;

public class ContactUs {
    protected WebDriver driver;
    private FillInfo fillInfo;
    private ActionHelper actions;
    // === Locator ===
    private static final By CONTACTUS_LINK = By.xpath("//a[@href='/contact_us']");
    private static final By SUBMIT_BTN = By.xpath("//input[@class='btn btn-primary pull-left submit_form' and @name= 'submit']");
    private static final By CONTACTUS_TEXT_SUCCESS = By.xpath("//div[@class='status alert alert-success']");
    private static final String CONTACTUS_MESSAGE_SUCCESS = "Success! Your details have been submitted successfully.";
    public ContactUs(WebDriver driver){
        this.driver = driver;
        actions = new ActionHelper(driver);
        fillInfo = new FillInfo(driver);
    }
    public void contactUs(String name, String email, String subject, String message){
        try {
            actions.click(CONTACTUS_LINK);
            fillInfo.fillContactUs(name, email, subject, message);
            actions.click(SUBMIT_BTN);
            actions.acceptAlert();
        }catch (Exception e){
            fail("contactUs was error: " +e.getMessage());
        }
    }
    public boolean isContactSuccess(){
        try {
            String textSuccess = actions.getText(CONTACTUS_TEXT_SUCCESS);
            return textSuccess.contains(CONTACTUS_MESSAGE_SUCCESS);
        }catch (Exception e){
            fail("isContactSuccess was error: " +e.getMessage());
            return false;
        }
    }
}
