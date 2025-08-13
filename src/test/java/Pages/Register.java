package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class Register {
    private FillInfo fillInfo;
    protected WebDriver driver;
    public Register(WebDriver driver){
        this.driver = driver;
        fillInfo = new FillInfo(driver);
    }
    public void registerWith(String name, String email, String value, String password, String day, String month, String year, String newsletter, String optin, String nameF, String nameL, String address, String country, String state, String city, String zipcode, String phone){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Signup / Login']"))).click();
            fillInfo.fillSignupInfo(name, email);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-default' and @data-qa='signup-button']"))).click();
            fillInfo.fillEnterAccountInfo(value, password, day, month, year, newsletter, optin, nameF, nameL, address, country, state, city, zipcode, phone);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-default' and @data-qa='create-account']"))).click();

        }catch (Exception e){
            fail("Test error" +e.getMessage());
        }
    }

    public boolean isRegisterSuccess() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='title text-center']/child::b")));
            String textTitle = text.getText();
            //System.out.println(textTitle);
            return textTitle.contains("ACCOUNT CREATED!");
        } catch (Exception e) {
            return false;
        }
    }
}
