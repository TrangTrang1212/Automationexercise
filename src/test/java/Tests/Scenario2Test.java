package Tests;

import DTO.AccountInfo;
import Pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import static org.testng.AssertJUnit.assertTrue;

public class Scenario2Test extends BaseTest {
    private Login login;
    private Register register;
    private String generatedEmail;
    private static final String PASSWORD_CORRECTLY = "123456";

    @BeforeMethod
    public void beforeEachMethod(Method method){
        login = new Login(driver);
        register = new Register(driver);
    }
    @Test(priority = 1)
    public void deleteAccount(){
        generatedEmail = DataHelper.generateUniqueEmail();
        register.goToSignUpForm("demo", generatedEmail);
        AccountInfo accountInfo = new AccountInfo("Mrs", PASSWORD_CORRECTLY, "10", "8", "2003", "Yes", "No", "test", "test", "123 abdf", "Singapore", "HCM", "HCM", "700000", "0987654321");
        register.completeAccountInfo(accountInfo);
        register.signupLoginLink();
        assertTrue("Account hasn't been delete", login.isDeleteAccountSuccess());
        ExtentReportListener.logStepWithScreenshot(driver, "ACCOUNT DELETED! successfully");
    }
    @Test(priority = 2)
    public void verifyAccountAfterDelete(){
        login.loginWith(generatedEmail,"123456");
        Assert.assertTrue(login.isLoginFailed(), "Login success");
        ExtentReportListener.logStepWithScreenshot(driver, "Incorrect password message displayed");
    }
}
