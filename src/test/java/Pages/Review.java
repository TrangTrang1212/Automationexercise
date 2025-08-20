package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.testng.AssertJUnit.fail;

public class Review {
    private FillInfo fillInfo;
    protected WebDriver driver;
    private WebDriverWait wait;
    private static final By REVIEW_BUTTON = By.id("button-review");
    private static final By ALERT_SUCCESS = By.xpath("//div[@class='alert-success alert']//span");
    public Review(WebDriver driver){
        this.driver = driver;
        fillInfo = new FillInfo(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    public void reviewButton(){
        wait.until(ExpectedConditions.elementToBeClickable(REVIEW_BUTTON)).click();
    }
    public void writeReview(String name, String email, String review){
        try {
            fillInfo.fillReview(name, email, review);
            reviewButton();
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }
    public boolean isReviewSuccess(){
        try {
            String textSuccess = wait.until(ExpectedConditions.elementToBeClickable(ALERT_SUCCESS)).getText();
            return textSuccess.contains("Thank you for your review.");
        }catch (Exception e){
            fail("Error: " +e.getMessage());
            return false;
        }
    }
}
