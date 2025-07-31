package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.fail;

public class Login {
    protected WebDriver driver;
    public Login(WebDriver driver){
        this.driver = driver;
    }
    public void loginWith(String email, String pw){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Signup / Login']"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-email' and @name='email']"))).sendKeys(email);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-password' and @name='password']"))).sendKeys(pw);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='form']/div/div/div[1]/div/form/button"))).click();
        }catch (Exception e){
            fail("Error" +e.getMessage());
        }
    }
    public boolean isLoginSuccess() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='header']/div/div/div/div[2]/div/ul/li[10]/a/text()")));
            String textTitle = text.getText();
            System.out.println(textTitle);
            return textTitle.contains(" Logged in as ");
        } catch (Exception e) {
            return false;
        }
    }
}
