package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.AssertJUnit.fail;

public class Review {
    private ScrollUtil scrollUtil;
    private FillInfo fillInfo;
    protected WebDriver driver;
    public Review(WebDriver driver){
        this.driver = driver;
        scrollUtil = new ScrollUtil(driver);
        fillInfo = new FillInfo(driver);
    }
    public void writeReview(String name, String email, String review){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            fillInfo.fillReview(name, email, review);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("button-review"))).click();
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }
    public boolean isReviewSuccess(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement textSuccess = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='alert-success alert']//span")));
            String text = textSuccess.getText();
            return text.contains("Thank you for your review.");
        }catch (Exception e){
            fail("Error: " +e.getMessage());
            return false;
        }
    }
}
