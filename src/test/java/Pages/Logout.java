package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.AssertJUnit.fail;

public class Logout {
    protected WebDriver driver;
    private WebDriverWait wait;
    private Register register;
    private static final By LOGOUT_BUTTON = By.xpath("//a[text()=' Logout']");
    public Logout(WebDriver driver){
        this.driver = driver;
        register = new Register(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    public void logoutButton(){
        wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON)).click();
    }

    public void logout(){
        try {
            register.signupLoginLink();
            logoutButton();
            register.signupLoginLink();
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }
}
