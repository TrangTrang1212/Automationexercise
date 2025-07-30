package Tests;

import Pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;


public class Scenario1Test extends BaseTest {
    private Register register;
    private FillInfo fillInfo;
    private Logout logout;
    private String generatedEmail;
    private Login login;
    @BeforeMethod
    public void beforeEachMethod(){
        register = new Register(driver);
        fillInfo = new FillInfo(driver);
        logout = new Logout(driver);
        login = new Login(driver);
    }
    @Test (priority = 1)
    public void s1_TC01(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            String uniqueEmail = "demo@testt" + System.currentTimeMillis() % 10000 + ".vn";
            register.registerWith("demo", uniqueEmail, "Mrs", "123456","10", "8","2003","No","No","test", "test", "123 abdf", "Singapore", "HCM", "HCM", "700000","0987654321");
            if (register.isRegisterSuccess()) {
                System.out.println("Register Successfully.");
            } else {
                System.out.println("Register failed.");
            }
            generatedEmail = uniqueEmail;
            //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Signup / Login']"))).click();
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }
    @Test(priority = 2)
    public void s1_TC02(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Signup / Login']"))).click();
            fillInfo.fillSignupInfo("demo", generatedEmail);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-default' and @data-qa='signup-button']"))).click();
            WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form']/div/div/div[3]/div/form/p")));
            String texterorr = text.getText();
            System.out.println(texterorr);
            assertTrue(texterorr.contains("Email Address already exist!"), "Chưa có account tồn tại");
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }
    @Test(priority = 3)
    public void s1_TC03(){
        try {
            login.loginWith(generatedEmail,"123456");
            if (login.isLoginSuccess()) {
                System.out.println("Login Successfully.");
            } else {
                System.out.println("Login failed.");
            }
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }
    @Test(priority = 4)
    public void s1_TC04(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Logout']"))).click();
            login.loginWith(generatedEmail,"12345");
            WebElement erorr = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='form']/div/div/div[1]/div/form/p")));
            String textError = erorr.getText();
            assertTrue(textError.contains("Your email or password is incorrect!"), "Login success");
        }catch (Exception e){
            fail("Error " +e.getMessage());
        }
    }

}
