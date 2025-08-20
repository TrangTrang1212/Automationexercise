package Pages;

import DTO.AccountInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.testng.Assert.fail;

public class Register {
    protected WebDriver driver;
    private FillInfo fillInfo;
    private ActionHelper actionHelper;
    // ==== Locator ====
    private static final By SIGNUP_LOGIN_LINK = By.xpath("//a[text()=' Signup / Login']");
    private static final By SIGNUP_BUTTON = By.xpath("//button[@class='btn btn-default' and @data-qa='signup-button']");
    private static final By CREATE_BUTTON = By.xpath("//button[@class='btn btn-default' and @data-qa='create-account']");
    private static final By ACCOUNT_CREATE_TEXT = By.xpath("//h2[@class='title text-center']/b");
    private static final By ACCOUNT_EXIST_TEXT = By.xpath("//*[@id='form']/div/div/div[3]/div/form/p");
    private static final String ACCOUNT_CREATE_MESSAGE = "ACCOUNT CREATED!";
    private static final String EMAIL_EXIST_MESSAGE = "Email Address already exist!";

    public Register(WebDriver driver){
        this.driver = driver;
        fillInfo = new FillInfo(driver);
        actionHelper = new ActionHelper(driver);
    }
    public void signupLoginLink(){
        actionHelper.click(SIGNUP_LOGIN_LINK);
    }
    public void signupButton(){
        actionHelper.click(SIGNUP_BUTTON);
    }
    public void goToSignUpForm(String name, String email){
        try {
            signupLoginLink();
            fillInfo.fillSignupInfo(name, email);
            signupButton();
        }catch (Exception e){
            fail("SignUpForm was error: " +e.getMessage());
        }

    }
    public void completeAccountInfo(AccountInfo accountInfo){
        try {
            fillInfo.fillEnterAccountInfo(accountInfo);
            actionHelper.scrollToElement(driver, CREATE_BUTTON);
            actionHelper.click(CREATE_BUTTON);
        }catch (Exception e){
            fail("CompleteAccountInfo was error: " +e.getMessage());
        }
    }
    public boolean isRegisterSuccess() {
        try {
            String textTitle = actionHelper.getText(ACCOUNT_CREATE_TEXT);
            return textTitle.contains(ACCOUNT_CREATE_MESSAGE);
        }catch (Exception e){
            fail("isRegisterSuccess was error: " +e.getMessage());
            return false;
        }
    }
    public boolean isRegisterFailed(){
        try {
            String textError = actionHelper.getText(ACCOUNT_EXIST_TEXT);
            return textError.equalsIgnoreCase(EMAIL_EXIST_MESSAGE);
        }catch (Exception e){
            fail("isRegisterFailed was error: " +e.getMessage());
            return false;
        }
    }
}
