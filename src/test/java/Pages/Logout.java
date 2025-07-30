package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.AssertJUnit.fail;

public class Logout {
    protected WebDriver driver;
    public Logout(WebDriver driver){
        this.driver = driver;
    }
    public void logout(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Signup / Login']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Logout']"))).click();
            // Kiểm tra đã logout thành công (xuất hiện nút SignUp/Login)
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=' Signup / Login']")));
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }
}
