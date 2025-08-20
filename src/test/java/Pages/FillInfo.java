package Pages;

import DTO.AccountInfo;
import DTO.PaymentInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.FileAssert;
import java.nio.file.Paths;
import java.time.Duration;
import static org.testng.Assert.fail;

public class FillInfo {
    protected WebDriver driver;
    private ActionHelper action;
    //=== Locator Account ===
    private static final By ACCOUNT_GENDER_MRS = By.id("id_gender2");
    private static final By ACCOUNT_GENDER_MR = By.id("id_gender1");
    private static final By ACCOUNT_PASSWORD = By.id("password");
    private static final By ACCOUNT_DAY = By.id("days");
    private static final By ACCOUNT_MONTH = By.id("months");
    private static final By ACCOUNT_YEAR = By.id("years");
    private static final By ACCOUNT_NEWSLETTER = By.id("newsletter");
    private static final By ACCOUNT_OPTION = By.id("optin");
    private static final By ACCOUNT_FIRSTNAME = By.id("first_name");
    private static final By ACCOUNT_LASTNAME = By.id("last_name");
    private static final By ACCOUNT_ADDRESS1 = By.id("address1");
    private static final By ACCOUNT_COUNTRY = By.id("country");
    private static final By ACCOUNT_STATE = By.id("state");
    private static final By ACCOUNT_CITY = By.id("city");
    private static final By ACCOUNT_ZIPCODE = By.id("zipcode");
    private static final By ACCOUNT_PHONE = By.id("mobile_number");
    // === Locator Payment ===
    private static final By PAYMENT_CARD_NAME = By.xpath("//input[@class='form-control' and @name ='name_on_card']");
    private static final By PAYMENT_CARD_NUMBER = By.xpath("//input[@class='form-control card-number' and @name ='card_number']");
    private static final By PAYMENT_CVC = By.xpath("//input[@class='form-control card-cvc' and @name ='cvc']");
    private static final By PAYMENT_EXPIRATION = By.xpath("//input[@class='form-control card-expiry-month' and @name ='expiry_month']");
    private static final By PAYMENT_YEAR = By.xpath("//input[@class='form-control card-expiry-year' and @name ='expiry_year']");

    public FillInfo(WebDriver driver){
        this.driver = driver;
        action = new ActionHelper(driver);
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
    public void fillEnterAccountInfo(AccountInfo accountInfo){
        try {
            if (accountInfo.getValue().equals("Mrs")){
                action.click(ACCOUNT_GENDER_MRS);
            } else if (accountInfo.getValue().equals("Mr")){
                action.click(ACCOUNT_GENDER_MR);
            }
            action.sendKey(ACCOUNT_PASSWORD, accountInfo.getPassword());
            action.selectDropdownByValueWithWait(ACCOUNT_DAY, accountInfo.getDay());
            action.selectDropdownByValueWithWait(ACCOUNT_MONTH, accountInfo.getMonth());
            action.selectDropdownByValueWithWait(ACCOUNT_YEAR, accountInfo.getYear());
            if (accountInfo.getNewsletter().equalsIgnoreCase("Yes")) {
                action.click(ACCOUNT_NEWSLETTER);
            }
            if (accountInfo.getOptin().equalsIgnoreCase("Yes")) {
                action.click(ACCOUNT_OPTION);
            }
            action.sendKey(ACCOUNT_FIRSTNAME, accountInfo.getFirstName());
            action.sendKey(ACCOUNT_LASTNAME, accountInfo.getLastName());
            action.sendKey(ACCOUNT_ADDRESS1, accountInfo.getAddress());
            action.selectDropdownByValueWithWait(ACCOUNT_COUNTRY, accountInfo.getCountry());
            action.sendKey(ACCOUNT_STATE, accountInfo.getState());
            action.sendKey(ACCOUNT_CITY, accountInfo.getCity());
            action.sendKey(ACCOUNT_ZIPCODE, accountInfo.getZipcode());
            action.sendKey(ACCOUNT_PHONE, accountInfo.getPhone());
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
    public void fillPayment(PaymentInfo paymentInfo){
        try {
            action.sendKey(PAYMENT_CARD_NAME, paymentInfo.getNameCard());
            action.sendKey(PAYMENT_CARD_NUMBER, paymentInfo.getNumberCard());
            action.sendKey(PAYMENT_CVC, paymentInfo.getCvc());
            action.sendKey(PAYMENT_EXPIRATION, paymentInfo.getExpiration());
            action.sendKey(PAYMENT_YEAR, paymentInfo.getYear());
        }catch (Exception e){
            AssertJUnit.fail("Error: " +e.getMessage());
        }
    }
    public void fillContactUs(String name, String email, String subject, String message){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='name'and @class='form-control']"))).sendKeys(name);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='email'and @class='form-control']"))).sendKeys(email);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='subject'and @class='form-control']"))).sendKeys(subject);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='message'and @class='form-control']"))).sendKeys(message);
            String filePath = Paths.get("src/test/resources/sample.png").toAbsolutePath().toString();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("upload_file"))).sendKeys(filePath);
        }catch (Exception e){
            AssertJUnit.fail("Error: " +e.getMessage());
        }
    }
}
