package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.testng.Assert.fail;

public class Login {
    protected WebDriver driver;
    private Register register;
    private ActionHelper action;
    // === Locator ===
    private static final By EMAIL_FIELD = By.xpath("//input[@data-qa='login-email' and @name='email']");
    private static final By PASSWORD_FIELD = By.xpath("//input[@data-qa='login-password' and @name='password']");
    private static final By LOGIN_BUTTON = By.xpath("//*[@id='form']/div/div/div[1]/div/form/button");
    private static final By LOGIN_SUCCESS_TEXT = By.xpath("//div[@class='shop-menu pull-right']/ul/li/a[text()=' Logged in as ']");
    private static final String LOGIN_SUCCESS_MESSAGE = "Logged in as demo";
    private static final By LOGIN_FAILED_TEXT = By.xpath("//*[@id='form']/div/div/div[1]/div/form/p");
    private static final String LOGIN_FAILED_MESSAGE = "Your email or password is incorrect!";
    private static final By DELETE_BUTTON = By.xpath("//a[@href='/delete_account']");
    private static final By DELETE_TITLE_TEXT = By.xpath("//h2[@class='title text-center' and @data-qa='account-deleted']/child::b");
    private static final String DELETE_MESSAGE = "ACCOUNT DELETED!";

    public Login(WebDriver driver){
        this.driver=driver;
        register = new Register(driver);
        action = new ActionHelper(driver);
    }
    public void loginWith(String email, String pw){
        try {
            register.signupLoginLink();
            action.sendKey(EMAIL_FIELD, email);
            action.sendKey(PASSWORD_FIELD, pw);
            action.click(LOGIN_BUTTON);
        }catch (Exception e){
            fail("loginWith was error: " +e.getMessage());
        }
    }
    public boolean isLoginSuccess() {
        try {
            String textSuccess = action.getText(LOGIN_SUCCESS_TEXT);
            return textSuccess.equalsIgnoreCase(LOGIN_SUCCESS_MESSAGE);
        }catch (Exception e){
            fail("isLoginSuccess was error: " +e.getMessage());
            return false;
        }
    }
    public boolean isLoginFailed() {
        try {
            String textError = action.getText(LOGIN_FAILED_TEXT);
            return textError.equalsIgnoreCase(LOGIN_FAILED_MESSAGE);
        }catch (Exception e){
            fail("isLoginFailed was error: " +e.getMessage());
            return false;
        }
    }
    public boolean isDeleteAccountSuccess(){
        try {
            action.click(DELETE_BUTTON);
            String deleteTitle = action.getText(DELETE_TITLE_TEXT);
            return deleteTitle.equalsIgnoreCase(DELETE_MESSAGE);
        }catch (Exception e){
            fail("isDeleteAccountSuccess was error: " +e.getMessage());
            return false;
        }

    }
}
