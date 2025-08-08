package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.FileAssert;

import java.time.Duration;

import static org.testng.Assert.fail;

public class FillInfo {
    protected WebDriver driver;
    public FillInfo(WebDriver driver){
        this.driver = driver;
    }
    public void fillSignupInfo(String name, String email){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='name' and @data-qa='signup-name']"))).sendKeys(name);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='email' and @data-qa='signup-email']"))).sendKeys(email);
        }catch (Exception e){
            fail("Erorr" +e.getMessage());
        }

    }
    private void selectDropdownByValueWithWait(By locator, String value) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            Select dropdown = new Select(element);
            dropdown.selectByValue(value);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            dropdown = new Select(element);
            WebElement selectedOption = dropdown.getFirstSelectedOption();
        }catch (Exception e){
            FileAssert.fail("Test error: " +e.getMessage());
        }
    }
    public void fillEnterAccountInfo(String value, String password, String day, String month, String year, String newsletter, String optin, String nameF, String nameL, String address, String country, String state, String city, String zipcode, String phone){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            if (value.equals("Mrs")){
                wait.until(ExpectedConditions.elementToBeClickable(By.id("id_gender2"))).click();
            } else if (value.equals("Mr")){
                wait.until(ExpectedConditions.elementToBeClickable(By.id("id_gender1"))).click();
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys(password);
            selectDropdownByValueWithWait(By.id("days"), day);
            selectDropdownByValueWithWait(By.id("months"),month);
            selectDropdownByValueWithWait(By.id("years"), year);
            if (newsletter.equalsIgnoreCase("Yes")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newsletter"))).click();
            }
            if (optin.equalsIgnoreCase("Yes")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("optin"))).click();
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first_name"))).sendKeys(nameF);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("last_name"))).sendKeys(nameL);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("address1"))).sendKeys(address);
            selectDropdownByValueWithWait(By.id("country"),country);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("state"))).sendKeys(state);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("city"))).sendKeys(city);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("zipcode"))).sendKeys(zipcode);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mobile_number"))).sendKeys(phone);
        }catch (Exception e){
            fail("Erorr" +e.getMessage());
        }
    }
    public void fillReview(String name, String email, String review){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
            nameField.clear();
            nameField.sendKeys(name);
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            emailField.clear();
            emailField.sendKeys(email);
            WebElement reviewField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("review")));
            reviewField.clear();
            reviewField.sendKeys(review);
        }catch (Exception e){
            FileAssert.fail("Test error: " +e.getMessage());
        }
    }
}
